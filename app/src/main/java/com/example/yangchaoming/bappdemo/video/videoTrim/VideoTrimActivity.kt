package com.example.yangchaoming.bappdemo.video.videoTrim

import android.content.ContentValues
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.yangchaoming.bappdemo.R
import com.hw.videoprocessor.VideoProcessor
import io.reactivex.*
import java.io.File
import android.media.MediaMetadataRetriever
import com.hw.videoprocessor.util.VideoProgressListener
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import android.graphics.Bitmap
import android.os.Handler
import android.provider.MediaStore
import android.util.DisplayMetrics
import android.view.View
import android.webkit.MimeTypeMap
import android.widget.LinearLayout
import android.widget.TextView
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.observers.DisposableCompletableObserver
import io.reactivex.observers.DisposableObserver
import io.reactivex.observers.DisposableSingleObserver


class VideoTrimActivity : AppCompatActivity() {

    companion object {
        const val VIDEO_PATH_KEY = "video_path_key"
    }

    lateinit var videoView: VideoTrimView
    lateinit var progressbar: LinearLayout
    lateinit var progressbarText: TextView
    private var videoPath: String = ""

    private var dispose = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_video_trim)
        initialData()

        videoView = findViewById(R.id.video_trim_view)
        progressbar = findViewById(R.id.progressbar)
        progressbarText = findViewById(R.id.progressbar_text)

        if (!videoPath.isNullOrEmpty()) {
            videoView.initVideoByURI(Uri.parse(videoPath))
        }

        videoView.videoTrimListener = object : VideoTrimView.VideoTrimListener {
            override fun canCel() {
                finish()
            }

            override fun trimVideo(path: String, startTime: Long, endTime: Long) {
//                Log.e("trimVideo", "trimVideo:path $path startTime:$startTime endtime:$endTime");
                progressbar.visibility = View.VISIBLE
                val moviesDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MOVIES)
                moviesDir.mkdirs()

                val fileNamePrefix = "cut_video";
                val fileType = ".mp4"
                var fileName = fileNamePrefix + fileType
                var destfile = File(moviesDir, fileName)
                var index = 0
                while (destfile.exists()) {
                    index++
                    fileName=fileNamePrefix + index + fileType
                    destfile = File(moviesDir,fileName )
                }

                val retriever = MediaMetadataRetriever()
                retriever.setDataSource(path)
                val originWidth = retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_VIDEO_WIDTH).toInt()
                val originHeight = retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_VIDEO_HEIGHT).toInt()
                val bitrate = retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_BITRATE).toInt()
//                val duration = retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION).toInt()

                retriever.release()


                var outWidth:Float = originWidth.toFloat()
                var outHeight:Float = originHeight.toFloat()

                if(originHeight>originWidth && originWidth>540){
                    outWidth = 540f
                    outHeight = originHeight.toFloat()/originWidth*outWidth
                }else if(originHeight < originWidth && originHeight>540){
                    outHeight = 540f
                    outWidth = originWidth.toFloat()/originHeight * outHeight
                }
                Log.e("trimVideo", "trimVideo:originWidth = $originWidth ----originHeight = $originHeight  -------bitrate = $bitrate");
                Log.e("trimVideo", "trimVideo:outWidth = $outWidth ----outHeight = $outHeight  -------bitrate = $bitrate");



                val newBitrate =  2.5 *outHeight*outWidth
                Log.e("trimVideo", "trimVideo: newbitrate = ${newBitrate}");
                dispose.add(Observable.create(ObservableOnSubscribe<Float> { e ->
                    val listener = VideoProgressListener { progress ->
//                        Log.e("trimVideo", "trimVideo: $progress");
                        e.onNext(progress)
                        if (progress == 1.0f) e.onComplete()
                    }
                    VideoProcessor.processor(this@VideoTrimActivity)
                            .input(path)
                            .output(destfile.absolutePath)
                            .outWidth(outWidth.toInt())
                            .outHeight(outHeight.toInt())
                            .bitrate(newBitrate.toInt())
                            .startTimeMs(startTime.toInt())//用于剪辑视频
                            .endTimeMs(endTime.toInt())    //用于剪辑视频
                            .progressListener(listener)
                            .process()


                }).subscribeOn(Schedulers.newThread())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(object : DisposableObserver<Float>() {
                            override fun onComplete() {
                                progressbar.visibility = View.GONE


                                // add new file to your media library
                                val values =  ContentValues(5);
                                val current = System.currentTimeMillis()
                                values.put(MediaStore.Video.Media.TITLE,  fileName);
                                values.put(MediaStore.Video.Media.DATE_ADDED, (current / 1000).toInt());
                                values.put(MediaStore.Video.Media.MIME_TYPE, "video/mp4")
                                values.put(MediaStore.Video.Media.DATA, destfile.absolutePath);
                                values.put(MediaStore.Video.Media.DURATION, endTime - startTime);

                                val base = MediaStore.Video.Media.EXTERNAL_CONTENT_URI
                                val newUri = contentResolver.insert(base, values)
//                                Log.e("onComplete", "onComplete: ${newUri.path}");
                                Handler().postDelayed({ finish()},1000)

                            }

                            override fun onNext(t: Float) {
//                                Log.e("trimVideo", "onNext: $t:");

                                try {
                                    val percent = String.format("视频处理中...%.2f%%",t*100)
                                    progressbarText.text =percent
                                } catch (e: Exception) {
//                                    Log.e("onNext", "onNext: ${e.toString()}");
                                }
                            }

                            override fun onError(e: Throwable) {
//                                Log.e("trimVideo", "onError: $e:");
                                progressbarText.text = "视频处理中... $e"
                                progressbar.visibility = View.GONE
                            }

                        }))

            }

        }
    }


    private fun initialData() {
        videoPath = intent.extras?.getString(VIDEO_PATH_KEY) ?: ""
//        Log.e("initialData", "initialData: $videoPath");
    }

    override fun onPause() {
        super.onPause()
        videoView.pauseVideo()
    }

    override fun onResume() {
        super.onResume()
        videoView.playVideo()
    }

    override fun onDestroy() {
        videoView.onDestory()
        dispose.clear()
        super.onDestroy()
    }
}