package com.example.yangchaoming.bappdemo.step_test

import android.animation.Animator
import android.animation.ObjectAnimator
import android.app.Activity
import android.content.Intent
import android.graphics.SurfaceTexture
import android.os.Bundle
import android.os.Handler
import android.os.HandlerThread
import android.util.Log
import android.view.TextureView
import com.example.yangchaoming.bappdemo.R
import com.example.yangchaoming.bappdemo.step_test.Camera2.AutoFitTextureView
import com.example.yangchaoming.bappdemo.step_test.Camera2.CameraForHeartRate
import com.example.yangchaoming.bappdemo.step_test.view.HeartRateProgressView

class HeartRateActivity : Activity() {
    val tag = "HeartRateActivity";

    private lateinit var textureView: AutoFitTextureView
    private lateinit var heartProgress: HeartRateProgressView
    private var mBackgroundThread: HandlerThread? = null
    private var mBackgroundHandler: Handler? = null
    private lateinit var mCamera2: CameraForHeartRate
//
    private var maxTime: Float = 10 * 1000f     //总时长

    private var heartRateList: ArrayList<Int> = ArrayList()  //心率值 集合

    private lateinit var animator: ObjectAnimator

    companion object {
        val AVERAGE_HEART_RATE: String = "averageHeartRate"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_heartrate)
        initView()

    }

    private fun initView() {
        textureView=findViewById(R.id.textureView)
        heartProgress = findViewById(R.id.heart_progress)
        heartProgress.maxValue = maxTime
        heartProgress.currentValue = maxTime

        // 创建 ObjectAnimator 对象
        animator = ObjectAnimator.ofFloat(heartProgress, "currentValue", maxTime, 0f)
        animator.duration = maxTime.toLong()

        animator.addListener(object : Animator.AnimatorListener {
            override fun onAnimationRepeat(animation: Animator?) {
            }

            override fun onAnimationEnd(animation: Animator?) {
                if (heartProgress.currentValue == 0f) {
//                    Log.e(tag, "onAnimationEnd: ");
                    val arerageHeartRate = getAverageHeartRate()
                    val intent = Intent()
                    intent.putExtra(AVERAGE_HEART_RATE, arerageHeartRate)
                    setResult(Activity.RESULT_OK, intent)
                    finish()
                }
            }

            override fun onAnimationCancel(animation: Animator?) {
            }

            override fun onAnimationStart(animation: Animator?) {
            }

        })

            mCamera2 = CameraForHeartRate()


    }


    override fun onResume() {
        super.onResume()
        Log.e(tag, "onResume: ");
        startBackgroundThread()

        mCamera2?.heartRateListener = object : CameraForHeartRate.HeartRateListener {
            override fun onFingerDetectionChanged(hasTouch: Boolean) {
//                Log.e(tag, "onFingerDetectionChanged: $hasTouch")

                runOnUiThread {
                    if (hasTouch) { //手指放在摄像头和闪光灯下
                        if (animator.isPaused) animator.resume() else animator.start()
                    } else {
                        animator.cancel()
                        heartRateList.clear()
                        val reSet = ObjectAnimator.ofFloat(heartProgress, "currentValue", heartProgress.currentValue, maxTime)
                        reSet.duration = this@HeartRateActivity.resources.getInteger(android.R.integer.config_shortAnimTime).toLong()
                        reSet.start()
                    }
                }

            }

            override fun onHeartRateChanged(i: Int) {
                Log.e(tag, "onHeartRateChanged: $i");
                if (i > 0) {
                    heartRateList.add(i)
                    runOnUiThread {
                        heartProgress.textValue = i
                    }
                }
            }
        }
        if (textureView.isAvailable()) {
            mCamera2.openCamera(textureView,this, mBackgroundHandler!!)
        } else {
            textureView.surfaceTextureListener = object :TextureView.SurfaceTextureListener{
                override fun onSurfaceTextureSizeChanged(surface: SurfaceTexture?, width: Int, height: Int) {
                }

                override fun onSurfaceTextureUpdated(surface: SurfaceTexture?) {
                }

                override fun onSurfaceTextureDestroyed(surface: SurfaceTexture?): Boolean {
                    return true
                }

                override fun onSurfaceTextureAvailable(surface: SurfaceTexture?, width: Int, height: Int) {
                    mCamera2.openCamera(textureView,this@HeartRateActivity, mBackgroundHandler!!)
                }

            }
        }

    }

    override fun onPause() {
        Log.e(tag, "onPause: ");
        mCamera2.closeCamera()
        stopBackgroundThread()
        super.onPause()
    }

    /**
     * Starts a background thread and its [Handler].
     */
    private fun startBackgroundThread() {
        mBackgroundThread = HandlerThread("CameraBackground")
        mBackgroundThread?.start()
        mBackgroundHandler = Handler(mBackgroundThread?.looper)
    }

    /**
     * Stops the background thread and its [Handler].
     */
    private fun stopBackgroundThread() {
        val quitSafely = mBackgroundThread?.quitSafely()
        Log.e(tag, "stopBackgroundThread: $quitSafely");
        try {
            mBackgroundThread?.join()
            mBackgroundThread = null
            mBackgroundHandler = null
        } catch (e: InterruptedException) {
            e.printStackTrace()
        }

    }


    private fun getAverageHeartRate(): Int {
        if (heartRateList.size == 0) return 0
        var totalValue = 0
        for (item in heartRateList) {
            totalValue += item
        }
        return totalValue / heartRateList.size
    }

}