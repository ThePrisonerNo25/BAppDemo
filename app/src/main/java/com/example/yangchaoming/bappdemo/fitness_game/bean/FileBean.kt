package com.example.yangchaoming.bappdemo.fitness_game.bean

data class FileBean(
        var type: Int?,
        var url: String
)

//type (integer, optional): 文件类型 0图片 1视频 ,
//url (string, optional): 文件绝对路径地址