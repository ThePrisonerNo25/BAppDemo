package com.example.yangchaoming.bappdemo.miscellaneous

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.yangchaoming.bappdemo.R
import com.example.yangchaoming.bappdemo.demo1.bean.DynamicCircleCommentBean
import com.example.yangchaoming.bappdemo.demo1.widget.CommentViewGroup
import com.example.yangchaoming.bappdemo.demo1.widget.LikesWrapContainer
import kotlin.collections.ArrayList


class TestActivity3 : AppCompatActivity() {
    var tag="TestActivity3"
    private lateinit var list:ArrayList<String>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test3)
        val likeContainer = findViewById<LikesWrapContainer>(R.id.like_container)
        val btnAdd = findViewById<Button>(R.id.btn_add)
        val btnReset = findViewById<Button>(R.id.btn_reset)

        initData();
        likeContainer.setDatas(list);

        btnAdd.setOnClickListener{
            list.add("item #_add" )
//            likeContainer.setDatas(list);

//            clickCallBack={a,b->run{
//                Log.e("btnReset","a=$a,b=$b")
//            }}

        }

        btnReset.setOnClickListener {
            Log.e("btnReset","btnReset")
            setListeren(1,2,clickCallBack)
        }


        val tvColorString=findViewById<TextView>(R.id.tv_color_string);

//        val spannableString = SpannableString("设置文字的前景色为淡蓝色")
//        val colorSpan = ForegroundColorSpan(Color.parseColor("#0099EE"))
//        spannableString.setSpan(colorSpan, 9, spannableString.length, Spanned.SPAN_INCLUSIVE_EXCLUSIVE)
//        tvColorString.setText(spannableString)


//        val calendar = Calendar.getInstance()
//        val sdf = SimpleDateFormat("dd-MMM-yyyy hh:mm:ss")
//        sdf.setTimeZone(TimeZone.getTimeZone("GMT+8"))
//
//        val rt = sdf.format(calendar.getTime())


//        val data=Date()
//        val instance = Calendar.getInstance()
//        instance.time=data
//        val am_pm = instance.get(Calendar.AM_PM)
//        val outputFormat = SimpleDateFormat("KK:mm ")
//        val formattedTime = outputFormat.format(instance.getTime())
//        tvColorString.setText(instance.time.getStringTimeStampWithDate())


//        val data=Date()
//        val instance = Calendar.getInstance()
//        instance.time=data
//        instance.add(Calendar.DAY_OF_MONTH,-3)
//        val stringTimeStampWithDate = instance.time.getStringTimeStampWithDate()
//        tvColorString.setText(stringTimeStampWithDate)


//        val s="2019-06-03 09:32"
//        val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm",
//                Locale.getDefault())
//        dateFormat.timeZone=TimeZone.getDefault()
//        val parse = dateFormat.parse(s)
//        val instance = Calendar.getInstance()
//        instance.time=parse;
//        tvColorString.setText(instance.time.getStringTimeStampWithDate())


//        val s="2019-06-03 19:32"
//        tvColorString.setText(DynamicUtil.getFormatDay(s))

        val initCvData = initCvData();

        val cv = findViewById<CommentViewGroup>(R.id.cv)
//        cv.setDatas(initCvData)

        var arrayList = ArrayList<Arrobj>()
        for(i in 0..3){
            arrayList.add (Arrobj("name #$i", i))
        }

        Log.e(tag, "onCreate:00000 ${arrayList.toString()}");

        arrayList[2]= Arrobj("changed", 100)

        Log.e(tag, "onCreate:11111111 ${arrayList.toString()}");
    }

    private fun initCvData(): ArrayList<DynamicCircleCommentBean> {

        var c_commentList:ArrayList<DynamicCircleCommentBean> = ArrayList<DynamicCircleCommentBean>();
        for (j in 0 ..10){
            var commentObj=DynamicCircleCommentBean("commentId# $j","commentMemberId# $j",j%2,"效果很不错！坚持！ $j",
                    "2019-6-4 16:4${j%9}","headUrl $j","评论人姓名 $j","jack $j")
            c_commentList.add(commentObj)
        }

        return c_commentList;
    }


    private fun initData() {
         list = ArrayList<String>()
        for (i in 0..9) {
            val bean = "item #_$i"
            list.add(bean)
        }

    }

    public var clickCallBack:((Int,Int)->Unit)?=null

    fun setListeren(a:Int, b:Int , c:((Int,Int)->Unit)?){
       c?.invoke(a,b)
    }




}