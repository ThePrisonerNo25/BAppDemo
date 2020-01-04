package com.example.yangchaoming.bappdemo.bapp_course.course_beans

data class FitnessCourseBean (val courseId: String,
                              var coverImg:String?,
                              var courseDetail:String?,
                              var isCharge:Int?,
                              var marketPrice:String?,
                              var name:String?,
                              var sellingPrice:String?,
                              var title:String?,
                              var videoNum:Int?,
                              var watchNum:Int?
                              )

//courseDetail (string, optional): 详情 ,
//courseId (string, optional): course_id ,
//coverImg (string, optional): 封面图片 ,
//isCharge (integer, optional): 0不收费,1收费 ,
//marketPrice (number, optional): 市场价 ,
//name (string, optional): 课程名称 ,
//sellingPrice (number, optional): 销售价 ,
//title (string, optional): 课程标题 ,
//videoNum (integer, optional): 视频数量 ,
//watchNum (integer, optional): 观看次数