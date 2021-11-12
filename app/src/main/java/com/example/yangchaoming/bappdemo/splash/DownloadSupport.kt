//package com.example.yangchaoming.bappdemo.splash
//
//import android.annotation.SuppressLint
//import android.app.DownloadManager
//import android.content.Context
//import android.database.Cursor
//import android.net.Uri
//import android.os.Handler
//import android.os.Message
//import android.util.Log
//import androidx.appcompat.app.AppCompatActivity
//import com.example.yangchaoming.bappdemo.MyApplication
//import java.io.File
//import java.security.AccessController.getContext
//
//class DownloadSupport {
//    lateinit var context:Context
//    private var downloadId: Long =-1
//    private var onDownloadListener: OnDownloadListener? = null
//    val OnFail = 0
//    val OnDownloading = 1
//    val OnDownloadSuccess = 2
//
//
//
////    private val mainHandler = object : Handler(){
////        override fun handleMessage(msg: Message?) {
////            when(msg?.arg1){
////                OnFail -> {
////                    onDownloadListener?.onDownloadFail();
////                }
////                OnDownloading -> {
////                    onDownloadListener?.onDownloading(msg?.arg2);
////                }
////                OnDownloadSuccess -> {
////                    onDownloadListener?.onDownloadSuccess(msg?.obj.toString());
////                }
////            }
////        }
////    }
//
//
//
//    constructor(context_: Context,callBack : DownloadSupport.OnDownloadListener ,handler: Handler){
//        this.onDownloadListener = callBack
//        this.context = context_
//        handler
//    }
//
//    fun download(url: String?, name: String, saveFilePath: String) {
//        val file = File(saveFilePath)
//        if (!file.exists()) {
//            val request = DownloadManager.Request(Uri.parse(url))
//            request.setDestinationUri(Uri.fromFile(file))
//            request.setTitle("怦怦健身教练")
//            request.setDescription("正在下载文件：${name}")
//            val downloadManager = context.getSystemService(AppCompatActivity.DOWNLOAD_SERVICE) as DownloadManager
//            downloadId = downloadManager.enqueue(request)
//            Thread(Runnable {
//                kotlin.run {
//                    queryProgress(downloadManager,saveFilePath)
//                }
//            }).start()
//
//        } else {
//            val msg = mainHandler.obtainMessage()
//            msg.arg1 = OnDownloadSuccess
//            msg.obj = saveFilePath
//            mainHandler.sendMessage(msg)
//        }
//    }
//
//    private fun queryProgress(downloadManager: DownloadManager, saveFilePath:String) {
//        var finishDownload = false
//        var progress: Int
//        while (!finishDownload) {
//            val cursor: Cursor = downloadManager.query(DownloadManager.Query().setFilterById(downloadId))
//            if (cursor.moveToFirst()) {
//                val status: Int = cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_STATUS))
//                when (status) {
//                    DownloadManager.STATUS_FAILED -> {
//                        Log.e("beginDownload", "STATUS_FAILED: ");
//                        finishDownload = true
//                        val message = Message.obtain()
//                        message.arg1 = OnFail
//                        mainHandler.sendMessage(message);
//                    }
//                    DownloadManager.STATUS_PAUSED -> {
//                        Log.e("beginDownload", "STATUS_PAUSED: ");
//                    }
//                    DownloadManager.STATUS_PENDING -> {
//                        Log.e("beginDownload", "STATUS_PENDING: ");
//                    }
//                    DownloadManager.STATUS_RUNNING -> {
//                        Log.e("beginDownload", "STATUS_RUNNING: ");
//                        val total: Long = cursor.getLong(cursor.getColumnIndex(DownloadManager.COLUMN_TOTAL_SIZE_BYTES))
//                        if (total >= 0) {
//                            val downloaded: Long = cursor.getLong(cursor.getColumnIndex(DownloadManager.COLUMN_BYTES_DOWNLOADED_SO_FAR))
//                            progress = (downloaded * 100L / total).toInt()
//
//                            val message = Message.obtain()
//                            message.arg1 = OnDownloading
//                            message.arg2 = progress
//                            mainHandler.sendMessage(message);
//                            Log.e("beginDownload", "STATUS_RUNNING: $progress");
//
//                            // if you use downloadmanger in async task, here you can use like this to display progress.
//                            // Don't forget to do the division in long to get more digits rather than double.
//                            //  publishProgress((int) ((downloaded * 100L) / total));
//                        }
//                    }
//                    DownloadManager.STATUS_SUCCESSFUL -> {
//                        progress = 100
//                        // if you use aysnc task
//                        // publishProgress(100);
//                        finishDownload = true
//
//                        val message = Message.obtain()
//                        message.arg1 = OnDownloadSuccess
//                        message.obj  = saveFilePath
//                        mainHandler.sendMessage(message);
//                        Log.e("beginDownload", "STATUS_SUCCESSFUL: $progress");
//
//                    }
//                }
//            }
//            cursor.close()
//        }
//    }
//
//    interface OnDownloadListener {
//        fun onDownloadSuccess(filePath:String)
//        fun onDownloading(var1: Int)
//        fun onDownloadFail()
//    }
//
//}