package com.example.yangchaoming.bappdemo.video.bean

data class VideoInfoBean (val index:Int, var seletect:Boolean?=false, var id:String?="", var duration:Long=0, var name:String, var dateAdd:String, var dataPath:String)
//MediaStore.Video.VideoColumns._ID,
//MediaStore.Video.VideoColumns.DATE_ADDED,
//MediaStore.Video.VideoColumns.DATA,
//MediaStore.Video.VideoColumns.DISPLAY_NAME,
//MediaStore.Video.VideoColumns.DURATION