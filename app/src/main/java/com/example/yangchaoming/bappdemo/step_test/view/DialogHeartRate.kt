package com.example.yangchaoming.bappdemo.step_test.view

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.example.yangchaoming.bappdemo.R



class DialogHeartRate :DialogFragment(){
    private lateinit var etTimes1: EditText
    private lateinit var etTimes2: EditText
    private lateinit var etTimes3: EditText
    private lateinit var btnCommit: Button

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        return inflater.inflate(R.layout.dialog_heart_rate,container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        etTimes1=view.findViewById(R.id.et_times_1)
        etTimes2=view.findViewById(R.id.et_times_2)
        etTimes3=view.findViewById(R.id.et_times_3)
        btnCommit=view.findViewById(R.id.btn_commit)
        btnCommit.setOnClickListener { run{
            val text1 = etTimes1.text?.toString()
            val text2 = etTimes2.text?.toString()
            val text3 = etTimes3.text?.toString()
            if(text1.isNullOrEmpty()||text2.isNullOrEmpty()||text3.isNullOrEmpty()){
                Toast.makeText(context, "请完善数据", Toast.LENGTH_SHORT).show();
                return@setOnClickListener
            }

            dialogListener?.commit(text1,text2,text3)

        } }
    }

    override fun onStart() {
        super.onStart()
        val width = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,320f,resources.displayMetrics).toInt()
        val height = ViewGroup.LayoutParams.WRAP_CONTENT
        dialog?.window?.setLayout(width, height)
        dialog?.window?.setBackgroundDrawable( ColorDrawable(Color.TRANSPARENT))
    }

     interface DialogHeartListener{
        fun commit(time1:String,time2:String,time3:String)
    }

     var dialogListener:DialogHeartListener?=null
}