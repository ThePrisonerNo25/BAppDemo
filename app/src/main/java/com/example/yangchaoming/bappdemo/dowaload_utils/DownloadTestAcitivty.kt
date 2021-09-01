package com.example.yangchaoming.bappdemo.dowaload_utils

import android.app.DownloadManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Context.DOWNLOAD_SERVICE
import android.content.Intent
import android.content.IntentFilter
import android.database.Cursor
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.os.Handler
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.getExternalCacheDirs
import androidx.core.content.ContextCompat.getSystemService
import com.example.yangchaoming.bappdemo.R
import kotlinx.android.synthetic.main.activity_download_test_acitivty.*
import java.io.File


class DownloadTestAcitivty : AppCompatActivity() {

    var downloadID: Long = -1

    // using broadcast method
    private val onDownloadComplete: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent) {
            //Fetching the download id received with the broadcast
            val id = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1)
            //Checking if the received broadcast is for our enqueued download by matching download id
            if (downloadID == id) {
                Toast.makeText(this@DownloadTestAcitivty, "Download Completed", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_download_test_acitivty)

        registerReceiver(onDownloadComplete, IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE));
        download_btn.setOnClickListener {
            beginDownload()
        }
    }

    override fun onDestroy() {
        unregisterReceiver(onDownloadComplete)
        super.onDestroy()
    }

    private fun beginDownload() {
//        val downloadUrl = "https://ejoyst-cp-dev-test.oss-cn-shenzhen.aliyuncs.com/oms/1280%2B720%282%29.mp4"
        val downloadUrl = "https://ejoyst-cp-dev-test.oss-cn-shenzhen.aliyuncs.com/oms/%5B%E9%98%B3%E5%85%89%E7%94%B5%E5%BD%B1-www.ygdy8.com%5D%E6%B5%B7%E8%B4%BC%E7%8E%8B-911.mp4"
//        val downloadUrl = "https://ss2.bdstatic.com/70cFvnSh_Q1YnxGkpoWK1HF6hhy/it/u=1249633517,2092238595&fm=26&gp=0.jpg"
//        val fileName = "yijian-release.mp4"
//        val directoryDownloads = Environment.DIRECTORY_DOWNLOADS
//        val externalFilesDir = getExternalFilesDir(directoryDownloads)
//        if (!externalFilesDir!!.exists()) externalFilesDir.mkdirs()
//        val file = File(externalFilesDir, fileName)
//        if(file.exists()){
//            file.delete()
//        }
//        if (!file.exists()) file.mkdirs()
        val preFile = File(getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS),"yijian-release.mp4")
        if(preFile.exists()){
            Log.e("exists", "beginDownload: ")
            preFile.delete()
        }


        val request = DownloadManager.Request(Uri.parse(downloadUrl))
//                .setNotificationVisibility(DownloadManager.Request.VISIBILITY_HIDDEN) // Visibility of the download Notification
//                .setDestinationUri(Uri.fromFile(file)) // Uri of the destination file
                .setDestinationInExternalFilesDir(this,Environment.DIRECTORY_DOWNLOADS,"yijian-release.mp4")
                .setTitle("yijian") // Title of the Download Notification
                .setDescription("Downloading") // Description of the Download Notification
//                .setAllowedOverMetered(true) // Set if download is allowed on Mobile network
//                .setAllowedOverRoaming(true) // Set if download is allowed on roaming network
        val downloadManager = getSystemService(DOWNLOAD_SERVICE) as DownloadManager
        downloadID = downloadManager.enqueue(request) // enqueue puts the download request in the queue.

        // using query method
        Thread(Runnable {
            kotlin.run {
                queryProgress(downloadManager)
            }
        }).start()


    }


    private fun queryProgress(downloadManager: DownloadManager) {
        var finishDownload = false
        var progress: Int
        while (!finishDownload) {
            val cursor: Cursor = downloadManager.query(DownloadManager.Query().setFilterById(downloadID))
            if (cursor.moveToFirst()) {
                val status: Int = cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_STATUS))
                when (status) {
                    DownloadManager.STATUS_FAILED -> {
                        Log.e("beginDownload", "STATUS_FAILED: ");
                        finishDownload = true
                    }
                    DownloadManager.STATUS_PAUSED -> {
                        Log.e("beginDownload", "STATUS_PAUSED: ");
                    }
                    DownloadManager.STATUS_PENDING -> {
                        Log.e("beginDownload", "STATUS_PENDING: ");
                    }
                    DownloadManager.STATUS_RUNNING -> {
                        Log.e("beginDownload", "STATUS_RUNNING: ");
                        val total: Long = cursor.getLong(cursor.getColumnIndex(DownloadManager.COLUMN_TOTAL_SIZE_BYTES))
                        if (total >= 0) {
                            val downloaded: Long = cursor.getLong(cursor.getColumnIndex(DownloadManager.COLUMN_BYTES_DOWNLOADED_SO_FAR))
                            progress = (downloaded * 100L / total).toInt()


                            Log.e("beginDownload", "STATUS_RUNNING: $progress");
                            runOnUiThread {
                                progress_bar.progress = progress
                            }

                            // if you use downloadmanger in async task, here you can use like this to display progress.
                            // Don't forget to do the division in long to get more digits rather than double.
                            //  publishProgress((int) ((downloaded * 100L) / total));
                        }
                    }
                    DownloadManager.STATUS_SUCCESSFUL -> {
                        progress = 100
                        runOnUiThread {
                            progress_bar.progress = progress
                            Toast.makeText(this, "Download Completed", Toast.LENGTH_SHORT).show()
                        }
                        Log.e("beginDownload", "STATUS_SUCCESSFUL: $progress");
                        // if you use aysnc task
                        // publishProgress(100);
                        finishDownload = true

                    }
                }
            }
            cursor.close()
        }
    }
}