package com.example.yangchaoming.bappdemo.step_test

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.yangchaoming.bappdemo.R
import com.example.yangchaoming.bappdemo.step_test.Camera3.CameraActivity
import com.example.yangchaoming.bappdemo.step_test.view.DialogHeartRate

class Step2TestActivity :AppCompatActivity(), View.OnClickListener {


    val tag = "Step2TestActivity";
    private lateinit var ivBack:ImageView
    private lateinit var tvManualInput:TextView
    private lateinit var btnFirstTest:Button
    private lateinit var btnSecondTest:Button
    private lateinit var btnThirdTest:Button
    private lateinit var btnCommit:Button
    private lateinit var tvFirstResult:TextView
    private lateinit var tvSecondResult:TextView
    private lateinit var tvThirdResult:TextView
    private lateinit var heartRateDialog: DialogHeartRate

    companion object{
        val REQUST_CODE_1=101
        val REQUST_CODE_2=102
        val REQUST_CODE_3=103
    }

    private lateinit var heartRate: HeartRateBean
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_step2_test)
        heartRateDialog= DialogHeartRate()
        heartRate=HeartRateBean()
        initView()

    }

    private fun initView() {
        ivBack=findViewById(R.id.iv_back)
        tvManualInput=findViewById(R.id.tv_manual_input)
        btnFirstTest=findViewById(R.id.btn_first_time)
        btnSecondTest=findViewById(R.id.btn_second_time)
        btnThirdTest=findViewById(R.id.btn_third_time)
        btnCommit=findViewById(R.id.btn_commit)
        tvFirstResult=findViewById(R.id.tv_first_time_result)
        tvSecondResult=findViewById(R.id.tv_second_time_result)
        tvThirdResult=findViewById(R.id.tv_third_time_result)

        btnSecondTest.isSelected=true
        btnSecondTest.isEnabled=false
        btnThirdTest.isSelected=true
        btnThirdTest.isEnabled=false
        btnCommit.isSelected=false
        btnCommit.isEnabled=false

        btnFirstTest.setOnClickListener(this)
        btnSecondTest.setOnClickListener(this)
        btnThirdTest.setOnClickListener(this)
        tvManualInput.setOnClickListener(this)
        btnCommit.setOnClickListener(this)
//        tvManualInput.setOnClickListener { v->run{
//            heartRateDialog.show(supportFragmentManager,"heartRateDialog")
//        } }

        heartRateDialog.dialogListener=object : DialogHeartRate.DialogHeartListener{
            override fun commit(time1: String, time2: String, time3: String) {
                Log.e(tag, "commit: $time1 $time2 $time3");
                heartRate.firstRate=time1
                heartRate.secondRate=time2
                heartRate.thirdRate=time3
                heartRateDialog.dismiss()
                btnCommit.isEnabled=true
                btnCommit.isSelected=true
            }
        }


    }

    override fun onClick(v: View?) {

        when(v?.id){
            R.id.tv_manual_input -> heartRateDialog.show(supportFragmentManager,"heartRateDialog")
            R.id.btn_first_time->{
//                var intent= Intent(this,CameraActivity::class.java)
                var intent= Intent(this,HeartRateActivity::class.java)
                startActivityForResult(intent, REQUST_CODE_1)
            }
            R.id.btn_second_time->{
                var intent= Intent(this,HeartRateActivity::class.java)
                startActivityForResult(intent, REQUST_CODE_2)
            }
            R.id.btn_third_time->{
                var intent= Intent(this,HeartRateActivity::class.java)
                startActivityForResult(intent, REQUST_CODE_3)
            }
            R.id.btn_commit->{
                if(heartRate.firstRate.isNullOrEmpty()||heartRate.secondRate.isNullOrEmpty()||heartRate.thirdRate.isNullOrEmpty()){
                    Toast.makeText(this, "请完善所有数据", Toast.LENGTH_SHORT).show();
                    return
                }

            }

        }
    }

    private fun startBtnEnable(btn:Button,time:Long){
       var timer=object: CountDownTimer(time,1000){
            override fun onFinish() {
                btn.isSelected=false
                btn.isEnabled=true
                btn.text = "测试心率"
            }

            override fun onTick(millisUntilFinished: Long) {
                btn.isSelected=true
                var millis = millisUntilFinished / 1000
                btn.text = "测试心率("+millis+"s)"
            }

        }.start()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
//        Log.e(tag, "onActivityResult: aaaa")

        if(resultCode== Activity.RESULT_OK ){
            val extras = data?.extras?.getInt(HeartRateActivity.AVERAGE_HEART_RATE)
            if(extras==null){
                Toast.makeText(this, "心率测试数据异常，请重新测试", Toast.LENGTH_SHORT).show();
                return
            }
            when(requestCode){
                REQUST_CODE_1 -> {
                    heartRate.firstRate = extras.toString()
                    tvFirstResult.text="$extras 次/分钟"
                    tvFirstResult.visibility=View.VISIBLE
                    btnFirstTest.visibility=View.INVISIBLE
                    startBtnEnable(btnSecondTest,5*1000L)
                }
                REQUST_CODE_2 ->  {
                    heartRate.secondRate=extras.toString()
                    tvSecondResult.text="$extras 次/分钟"
                    tvSecondResult.visibility=View.VISIBLE
                    btnSecondTest.visibility=View.INVISIBLE
                    startBtnEnable(btnThirdTest,5*1000L)
                }
                REQUST_CODE_3 ->{
                    heartRate.thirdRate=extras.toString()
                    tvThirdResult.text="$extras 次/分钟"
                    tvThirdResult.visibility=View.VISIBLE
                    btnThirdTest.visibility=View.INVISIBLE
                    btnCommit.isEnabled=true
                    btnCommit.isSelected=true
                }
            }
        }
    }


}