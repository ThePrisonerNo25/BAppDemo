package com.example.yangchaoming.bappdemo.step_test.Camera2

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.ImageFormat
import android.hardware.camera2.*
import android.media.Image
import android.media.ImageReader
import android.os.Handler
import android.util.Log
import android.view.Surface
import android.widget.Toast
import androidx.core.content.ContextCompat
import java.util.*
import java.util.concurrent.Semaphore
import java.util.concurrent.TimeUnit
import java.util.concurrent.atomic.AtomicBoolean

class CameraForHeartRate {
    val tag = "CameraForHeartRate";
    private var textureView:AutoFitTextureView?=null
    private var context_:Activity?=null
    private var mBackgroundHandler:Handler?=null

    private var mImageReader: ImageReader? = null

    /**
     * Whether the current camera device supports Flash or not.
     */
    private var mFlashSupported: Boolean = false

    /**
     * ID of the current [CameraDevice].
     */
    private var mCameraId: String? = null

    /**
     * A [Semaphore] to prevent the app from exiting before closing the camera.
     */
    private val mCameraOpenCloseLock = Semaphore(1)

    /**
     * A reference to the opened [CameraDevice].
     */
    private var mCameraDevice: CameraDevice? = null

    /**
     * [CaptureRequest.Builder] for the camera preview
     */
    private var mPreviewRequestBuilder: CaptureRequest.Builder? = null

    /**
     * A [CameraCaptureSession] for camera preview.
     */
    private var mCaptureSession: CameraCaptureSession? = null

    /**
     * [CaptureRequest] generated by [.mPreviewRequestBuilder]
     */
    private var mPreviewRequest: CaptureRequest? = null


//    private var surface: Surface? = null

    internal var averageTimer = 3

    private var fingerDetected: Boolean = false
        set(value) {
            if (field != value)
                heartRateListener?.onFingerDetectionChanged(value)
            field = value
        }

    interface HeartRateListener {
        fun onFingerDetectionChanged(b: Boolean)
        fun onHeartRateChanged(i: Int)
    }

    var heartRateListener: HeartRateListener? = null


    /**
     * This a callback object for the [ImageReader]. "onImageAvailable" will be called when a
     * still image is ready to be saved.
     */
    private val mOnImageAvailableListener = object : ImageReader.OnImageAvailableListener {
        internal var beatsIndex = 0
        internal var beats = 0.0
        internal var startTime = System.currentTimeMillis()
        internal var averageIndex = 0

        internal val PROCESSING = AtomicBoolean(false)

        internal val AVERAGE_ARRAY_SIZE = 4
        internal val AVERAGE_ARRAY = IntArray(AVERAGE_ARRAY_SIZE)

        internal val BEATS_ARRAY_SIZE = 3
        internal val BEATS_ARRAY = IntArray(BEATS_ARRAY_SIZE)

        internal var currentPixelType: PulseType = PulseType.OFF

        private var previousBeatsAverage: Int = 0

        override fun onImageAvailable(reader: ImageReader) {
            //            Log.e(TAG, "onImageAvailable: ");
            //            Image image = reader.acquireLatestImage();

            var image: Image? = null
            try {
                image = reader.acquireNextImage()
                val width = image!!.width
                val height = image.height
                Log.e(tag, "onImageAvailable: image.getWidth()" + image.width)
                if (image != null) {
                    val bytes1 = ImageProcessing.getBytesFromImageAsType(image, ImageProcessing.NV21)
                    image.close()
                    if (!PROCESSING.compareAndSet(false, true)) {
                        //                            Log.e(TAG, "has to return");
                        image.close()
                        return
                    }
//                    Log.e(TAG, "onImageAvailable: width*height" + width * height)
                    Log.e(tag, "onImageAvailable: size" + bytes1!!.size)
                    val imageAverage = ImageProcessing.decodeYUV420SPtoRedAvg(bytes1.clone(), width, height)
                    Log.e(tag, "onImageAvailable: imageAverage=$imageAverage")
                    if (imageAverage == 0 || imageAverage < 199) {
                        PROCESSING.set(false)
                        fingerDetected = false
                        return
                    }
                    fingerDetected = true

                    var averageArrayAverage = 0
                    var averageArrayCount = 0

                    for (averageEntry in AVERAGE_ARRAY) {
                        if (averageEntry > 0) {
                            averageArrayAverage += averageEntry
                            averageArrayCount++
                        }
                    }

                    val rollingAverage = if (averageArrayCount > 0) averageArrayAverage / averageArrayCount else 0
//                    Log.e(TAG, "onImageAvailable:rollingAverage $rollingAverage")
//                    log("rollingAverage: " + rollingAverage)

                    var newType = currentPixelType

                    if (imageAverage < rollingAverage) {
                        newType = PulseType.ON
                        if (newType != currentPixelType) {
                            beats++
                        }
                    } else if (imageAverage > rollingAverage) {
                        newType = PulseType.OFF
                    }

                    if (averageIndex == AVERAGE_ARRAY_SIZE) {
                        averageIndex = 0
                    }

                    AVERAGE_ARRAY[averageIndex] = imageAverage
                    averageIndex++

                    if (newType != currentPixelType) {
                        currentPixelType = newType
//                        Log.e(TAG, "onImageAvailable:previousBeatsAverage $previousBeatsAverage");
//                        publishSubject.onNext(Bpm(previousBeatsAverage, currentPixelType))
                        heartRateListener?.onHeartRateChanged(previousBeatsAverage)
                    }

                    val endTime = System.currentTimeMillis()
                    val totalTimeInSecs = (endTime - startTime) / 1000.0
//                    Log.e(TAG, "onImageAvailable: totalTimeInSecs $totalTimeInSecs beats $beats");
                    if (totalTimeInSecs >= averageTimer) {
                        val beatsPerSecond = beats / totalTimeInSecs
//                        Log.e(TAG, "onImageAvailable: beatsPerSecond $beatsPerSecond -- beats $beats -- totalTimeInSecs $totalTimeInSecs");
                        val beatsPerMinute = (beatsPerSecond * 60.0).toInt()
                        if (beatsPerMinute < 40 || beatsPerMinute > 180) {
                            startTime = System.currentTimeMillis()
                            beats = 0.0
                            PROCESSING.set(false)
                            return
                        }

                        if (beatsIndex == BEATS_ARRAY_SIZE) {
                            beatsIndex = 0
                        }

                        BEATS_ARRAY[beatsIndex] = beatsPerMinute
                        beatsIndex++

                        var beatsArrayAverage = 0
                        var beatsArrayCount = 0

                        for (beatsEntry in BEATS_ARRAY) {
                            if (beatsEntry > 0) {
                                beatsArrayAverage += beatsEntry
                                beatsArrayCount++
                            }
                        }

                        val beatsAverage = beatsArrayAverage / beatsArrayCount
//                        Log.e(TAG, "beatsAverage: $beatsArrayAverage --  $beatsArrayCount");
                        previousBeatsAverage = beatsAverage
                        heartRateListener?.onHeartRateChanged(beatsAverage)
//                        Log.e(TAG, "onImageAvailable:beatsAverage $beatsAverage");
//                        publishSubject.onNext(Bpm(beatsAverage, currentPixelType))

                        startTime = System.currentTimeMillis()
                        beats = 0.0
                    }

                    PROCESSING.set(false)
                }


            } catch (e: Exception) {
                e.printStackTrace()
                image?.close()
            }

        }

    }

    /**
     * [CameraDevice.StateCallback] is called when [CameraDevice] changes its state.
     */
    private val mStateCallback = object : CameraDevice.StateCallback() {

        override fun onOpened(cameraDevice: CameraDevice) {
            // This method is called when the camera is opened.  We start camera preview here.
            Log.e(tag, "onOpened: ");
            mCameraOpenCloseLock.release()
            mCameraDevice = cameraDevice
            createCameraPreviewSession()
        }

        override fun onDisconnected(cameraDevice: CameraDevice) {
            Log.e(tag, "onDisconnected: ");
            mCameraOpenCloseLock.release()
            cameraDevice.close()
            mCameraDevice = null
        }

        override fun onError(cameraDevice: CameraDevice, error: Int) {
            Toast.makeText(context_, "CameraDevice ErrorCode$error", Toast.LENGTH_SHORT).show()
            mCameraOpenCloseLock.release()
            cameraDevice.close()
            mCameraDevice = null
            val activity = context_
            activity?.finish()
        }

    }

    internal enum class PulseType {
        OFF, ON
    }


    /**
     * Opens the camera.
     */
    fun openCamera(textureView: AutoFitTextureView,context1:Activity,mBackgroundHandler_:Handler) {
        this.textureView=textureView
        this.context_=context1
        this.mBackgroundHandler=mBackgroundHandler_

        if (ContextCompat.checkSelfPermission(context_!!, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(context_, "请开启相机权限", Toast.LENGTH_SHORT).show()
            return
        }

        //        setUpCameraOutputs(width, height);
        setUpCameraOutputs()
        //        configureTransform(width, height);
        val activity = context_
        val manager = activity!!.getSystemService(Context.CAMERA_SERVICE) as CameraManager
        
        try {
            if (!mCameraOpenCloseLock.tryAcquire(2500, TimeUnit.MILLISECONDS)) {
                throw RuntimeException("Time out waiting to lock camera opening.")
            }
            Log.e(tag, "openCamera:mCameraId $mCameraId");
            Log.e(tag, "openCamera: $mBackgroundHandler");
            manager.openCamera(mCameraId!!, mStateCallback, mBackgroundHandler)
        } catch (e: CameraAccessException) {
            e.printStackTrace()
        } catch (e: InterruptedException) {
            throw RuntimeException("Interrupted while trying to lock camera opening.", e)
        }

    }


    /**
     * Closes the current [CameraDevice].
     */
    fun closeCamera() {
        textureView=null
        this.context_=null
        this.mBackgroundHandler=null
        try {
            mCameraOpenCloseLock.acquire()
            if (null != mCaptureSession) {
                Log.e(tag, "closeCamera: mCaptureSession!!.close()");
                mCaptureSession!!.close()
                mCaptureSession = null
            }
            if (null != mCameraDevice) {
                Log.e(tag, "closeCamera: mCameraDevice!!.close()");
                mCameraDevice!!.close()
                mCameraDevice = null
            }
            if (null != mImageReader) {
                Log.e(tag, "closeCamera: mImageReader!!.close()");
                mImageReader!!.close()
                mImageReader = null
            }
        } catch (e: InterruptedException) {
            throw RuntimeException("Interrupted while trying to lock camera closing.", e)
        } finally {
            mCameraOpenCloseLock.release()
            Log.e(tag, "closeCamera: ");
        }
    }

    /**
     * Creates a new [CameraCaptureSession] for camera preview.
     */
    private fun createCameraPreviewSession() {
        try {



            // We set up a CaptureRequest.Builder with the output Surface.
            mPreviewRequestBuilder = mCameraDevice!!.createCaptureRequest(CameraDevice.TEMPLATE_PREVIEW)

//            val surface = Surface(textureView!!.surfaceTexture)
//            mPreviewRequestBuilder!!.addTarget(surface)

            val imageSurface = mImageReader!!.surface
            mPreviewRequestBuilder!!.addTarget(imageSurface)
//            Log.e("aaa", "createCameraPreviewSession: ${imageSurface.isValid}");

            // Here, we create a CameraCaptureSession for camera preview.
            mCameraDevice!!.createCaptureSession(Arrays.asList<Surface>(imageSurface),
                    object : CameraCaptureSession.StateCallback() {

                        override fun onConfigured(cameraCaptureSession: CameraCaptureSession) {
                            // The camera is already closed
                            if (null == mCameraDevice) {
                                return
                            }

                            // When the session is ready, we start displaying the preview.
                            mCaptureSession = cameraCaptureSession
                            try {
                                // Auto focus should be continuous for camera preview.
                                mPreviewRequestBuilder!!.set(CaptureRequest.CONTROL_AF_MODE,
                                        CaptureRequest.CONTROL_AF_MODE_CONTINUOUS_PICTURE)
                                // Flash is automatically enabled when necessary.
                                //                                setAutoFlash(mPreviewRequestBuilder);
                                mPreviewRequestBuilder!!.set(CaptureRequest.CONTROL_AE_MODE, CameraMetadata.CONTROL_AF_MODE_AUTO)
                                mPreviewRequestBuilder!!.set(CaptureRequest.FLASH_MODE, CameraMetadata.FLASH_MODE_TORCH)

                                // Finally, we start displaying the camera preview.
                                mPreviewRequest = mPreviewRequestBuilder!!.build()
                                mCaptureSession!!.setRepeatingRequest(mPreviewRequest!!, null, mBackgroundHandler)

                            } catch (e: CameraAccessException) {
//                                Log.e("aaaaaaa", "onConfigured: ");
                                e.printStackTrace()
                            }

                        }

                        override fun onConfigureFailed(
                                cameraCaptureSession: CameraCaptureSession) {
                            Toast.makeText(context_, "Failed", Toast.LENGTH_SHORT).show()
                        }
                    }, null
            )
        } catch (e: CameraAccessException) {
            e.printStackTrace()
        }

    }


    /**
     * Sets up member variables related to camera.
     */
    private fun setUpCameraOutputs() {
        val activity = context_
        val manager = activity!!.getSystemService(Context.CAMERA_SERVICE) as CameraManager
        try {
            for (cameraId in manager.cameraIdList) {
                val characteristics = manager.getCameraCharacteristics(cameraId)

                // We don't use a front facing camera in this sample.
                val facing = characteristics.get(CameraCharacteristics.LENS_FACING)
                if (facing != null && facing == CameraCharacteristics.LENS_FACING_FRONT) {
                    continue
                }

                val map = characteristics.get(
                        CameraCharacteristics.SCALER_STREAM_CONFIGURATION_MAP) ?: continue
                val outputSizes = map.getOutputSizes(ImageFormat.YUV_420_888)
                //                for (int i = 0; i < outputSizes.length; i++) {
                //                    Log.e(TAG, "outputSizes: "+outputSizes[i].toString() );
                //                }
                val outputSize = outputSizes[outputSizes.size-2]
//                val outputSize = outputSizes[0]

                // For still image captures, we use the largest available size.
                //                Size largest = Collections.max(
                //                        Arrays.asList(map.getOutputSizes(ImageFormat.YUV_420_888)),
                //                        new CompareSizesByArea());
                mImageReader = ImageReader.newInstance(outputSize.width, outputSize.height,
                        ImageFormat.YUV_420_888, /*maxImages*/3)

                mImageReader!!.setOnImageAvailableListener(mOnImageAvailableListener, mBackgroundHandler)


                // Check if the flash is supported.
                val available = characteristics.get(CameraCharacteristics.FLASH_INFO_AVAILABLE)
                mFlashSupported = available ?: false

                mCameraId = cameraId
                return
            }
        } catch (e: CameraAccessException) {
            e.printStackTrace()
        } catch (e: NullPointerException) {
            // Currently an NPE is thrown when the Camera2API is used but not supported on the
            // device this code runs.
            //            ErrorDialog.newInstance(getString(R.string.camera_error))
            //                    .show(getChildFragmentManager(), FRAGMENT_DIALOG);
            Toast.makeText(context_, "" + e.toString(), Toast.LENGTH_SHORT).show()
        }

    }

}
