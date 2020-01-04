package com.example.yangchaoming.bappdemo.video

import android.content.Context
import android.database.Cursor
import android.media.MediaMetadataRetriever
import android.provider.MediaStore
import com.example.yangchaoming.bappdemo.video.bean.VideoInfoBean

class VideoPresenter (val context: Context,val view: VideoContract.View){
    var videoSubList =ArrayList<VideoInfoBean>()


    fun mapVideoSubsidiaryData(cursor: Cursor): ArrayList<VideoInfoBean> {
        videoSubList.clear()
        var mMetadataRetriever = MediaMetadataRetriever()
        for (i in 0 until cursor.count){
            if(!cursor.moveToPosition(i)) continue
            cursor.moveToPosition(i)
            val mapVideoInfo = mapVideoInfo(i, cursor,mMetadataRetriever) ?: continue
            videoSubList.add(mapVideoInfo)
        }
        mMetadataRetriever.release()
        return videoSubList
    }

    private fun mapVideoInfo(index: Int, cursor: Cursor, mMetadataRetriever: MediaMetadataRetriever):VideoInfoBean?{
//        MediaStore.Video.VideoColumns._ID,
//        MediaStore.Video.VideoColumns.DATE_ADDED,
//        MediaStore.Video.VideoColumns.DATA,
//        MediaStore.Video.VideoColumns.DISPLAY_NAME,
//        MediaStore.Video.VideoColumns.DURATION
        val id = cursor.getString(cursor.getColumnIndex(MediaStore.Video.Media._ID))
        val dateAdd = cursor.getLong(cursor.getColumnIndex(MediaStore.Video.Media.DATE_ADDED))
        val dataPath = cursor.getString(cursor.getColumnIndex(MediaStore.Video.Media.DATA))
        val name = cursor.getString(cursor.getColumnIndex(MediaStore.Video.Media.DISPLAY_NAME))
        val duration = cursor.getLong(cursor.getColumnIndex(MediaStore.Video.Media.DURATION))/1000

//        mMetadataRetriever.setDataSource(dataPath)
//        val duration_ = mMetadataRetriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION).toLong()/1000

        val videoInfoBean = VideoInfoBean(index, false, id, duration, name, dateAdd.toString(), dataPath)
//        val videoInfoBean = VideoInfoBean(index, false, id, duration_, name, dateAdd.toString(), dataPath)

//        Log.e("mapVideoInfo", "mapVideoInfo: $videoInfoBean");
        return videoInfoBean
    }

}