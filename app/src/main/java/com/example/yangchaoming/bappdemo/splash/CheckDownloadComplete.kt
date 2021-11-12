package com.example.yangchaoming.bappdemo.splash

import android.app.DownloadManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.net.Uri.parse
import android.util.Log
import java.io.File

class CheckDownloadComplete(val listener: OnDownloadListener?) : BroadcastReceiver() {
    val TAG = CheckDownloadComplete::class.simpleName
    override fun onReceive(context: Context, intent: Intent) {
        val action = intent.action
        if (action == DownloadManager.ACTION_DOWNLOAD_COMPLETE) {
            val query = DownloadManager.Query()
            query.setFilterById(intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, 0))
            val manager = context.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
            val cursor = manager.query(query)
            if (cursor.moveToFirst()) {
                if (cursor.count > 0) {
                    val status = cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_STATUS))
                    val download_id = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, 0)

                    // status contain Download Status
                    // download_id contain current download reference id
                    if (status == DownloadManager.STATUS_SUCCESSFUL) {

                        val fileUriIdx: Int = cursor.getColumnIndex(DownloadManager.COLUMN_LOCAL_URI)
                        val fileUri: String = cursor.getString(fileUriIdx)
                        val path = parse(fileUri).path
                        if(path.isNullOrEmpty()){
                            listener?.onDownloadFail(download_id)
                        }else{
                            val mFile = File(path)
                            listener?.onDownloadSuccess(download_id, mFile.path)
                        }


                        //file contains downloaded file name

                        // do your stuff here on download success
                    }else if (status == DownloadManager.STATUS_FAILED)
                    {
                        listener?.onDownloadFail(download_id)
                    }else if(status == DownloadManager.STATUS_RUNNING){
                        Log.e(TAG, "onReceive: DownloadManager.STATUS_RUNNING")
                    }
                }
            }
            cursor.close()
        }
    }

    interface OnDownloadListener {
        fun onDownloadSuccess(download_id: Long, filePath: String)
        fun onDownloadFail(download_id: Long)
    }
}