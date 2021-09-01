package com.example.yangchaoming.bappdemo.bapp_course

import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import java.util.*
import kotlin.collections.HashMap

object PlaceMoshi {
    @JvmStatic
    fun main(args: Array<String>) {

        val json = """
       {
            "name": "Most elegant",
            "age":null,
            "user_money":20
       }
    """

        val mothi = Moshi.Builder().build()
        val adapter1 = mothi.adapter<MothiBean>(MothiBean::class.java)
        val fromJson = adapter1.fromJson(json)
        print(fromJson)
        val jsonString = adapter1.toJson(MothiBean("adf", null, 100))
        print(jsonString)


        val adapter2 = mothi.adapter<MothiBean>(MothiBean::class.java).serializeNulls()
        val jsonString2 = adapter2.toJson(MothiBean("adf", null, 100))
        print(jsonString2)





//        val employeeMap: MutableMap<String, Any?> = HashMap()
//
//        employeeMap["id"] = 1
//        employeeMap["name"] = "Dhatri"
//        employeeMap["tag"] = null
//        employeeMap["tag1"] = ""
//        employeeMap["hobbies"] = listOf("Trekking", "Singing")
//
//        val addressMap: MutableMap<String, Any?> = HashMap()
//        addressMap["city"] = "Hyderabad"
//        addressMap["country"] = "India"
//
//        employeeMap["address"] = addressMap
//
//        val moshi = Moshi.Builder().build()
//        val jsonAdapter: JsonAdapter<Map<String, Any?>> = moshi.adapter(Types.newParameterizedType(MutableMap::class.java, String::class.java, Any::class.java))
//
////        val json: String = jsonAdapter.indent("  ").toJson(employeeMap)
//        val json: String = jsonAdapter.toJson(employeeMap)
//        println(json)

    }

}
