package com.example.yangchaoming.bappdemo.bapp_course

import android.util.Log
import com.alibaba.fastjson.JSON
import com.example.yangchaoming.bappdemo.popupwindow.Article
import com.example.yangchaoming.bappdemo.popupwindow.ArticleJava
import com.google.gson.Gson

object Place2 {
    @JvmStatic
    fun main(args: Array<String>) {
//        val str = "{'body':null,'title':'null'}"
//        val user = JSON.parseObject(str, ArticleJava::class.java)
//
//        println("body:" + user.getBody())

        val json = """
       {
            "title": "Most elegant",
            "body": null,
            "payWall": false,
            "body222":"null",
            "ignoredProperty": "Ignored",
            "defaultNull": null
       }
    """
//
//    val article = JSON.parseObject(json, Article::class.java)
//    Log.e("onCreate", "onCreate: $article");


        // "viewCount": 9999,
    val article = Gson().fromJson(json, Article::class.java)
//    Log.e("onCreate", "onCreate: $article");
        println(article)

//    if(article.titleImage.isNullOrEmpty()){
////        Log.e("onCreate", " article isNullOrEmpty");
//        println("article isNullOrEmpty")
//    }
//
//    if (article.body.isNullOrEmpty()){
////        Log.e("onCreate", "body isNullOrEmpty");
//        println("body isNullOrEmpty")
//    }

        if (article.defaultNull == true){
//        Log.e("onCreate", "body isNullOrEmpty");
            println("defaultNull is True")
        }

        if (!article.defaultNull2){
//        Log.e("onCreate", "body isNullOrEmpty");
            println("defaultNull2 is false")
        }else{
            println("defaultNull2 true")
        }

        //---------------------------------
//        val arr = JSONArray(json)
//    val jsonObject = arr.getJSONObject(0)
//    Log.e("onCreate", "onCreate: ${arr}");


//    Gson().fromJson<ArrayList<JSONObject>>(json,ArrayList<JSONObject>::class.java)
//    val parseString = JsonParser.parseString(json).asJsonArray
//    Log.e("onCreate", "onCreate: $parseString");

        //------------------
//        val obj = JSONObject(json)
//
//        if(obj.isNull("body")){
//            Log.e("onCreate", "isNull ");
//        }
//          val a=   obj.getString("body")
//        Log.e("onCreate", "onCreate:a= $a");
//
////    if(obj.has("abc")){
////        val c = obj.getString("abc")
////        Log.e("onCreate", "onCreate: c= $c");
////    }
//        if(obj.isNull("abc")){
//            Log.e("onCreate", "onCreatebj.isNull(\"abc\")");
//        }


//        val b = obj.getString("abc")
//    Log.e("onCreate", "onCreate:b= $b");

    }
}
