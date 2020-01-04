package com.example.yangchaoming.bappdemo.bapp_course.course_beans

data class CourseCommentBean (val id:String,
                              var comment:String?,
                              var createTime:String?,
                              var mobile:String?,
                              var name:String?,
                              var nickName:String?
                              )

//comment (string, optional): 内容 ,
//createTime (string, optional): 创建时间 ,
//id (string, optional): id ,
//mobile (string, optional): 手机号码 ,
//name (string, optional): 教练姓名 ,
//nickName (string, optional): 教练昵称