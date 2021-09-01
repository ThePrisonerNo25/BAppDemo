package com.example.yangchaoming.bappdemo.bapp_course

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class MothiBean(
    val name:String,
    val age:Int?,
    @Json(name ="user_money")
    val money:Int
)
