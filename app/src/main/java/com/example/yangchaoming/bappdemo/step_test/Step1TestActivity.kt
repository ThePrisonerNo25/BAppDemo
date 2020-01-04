package com.example.yangchaoming.bappdemo.step_test

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.yangchaoming.bappdemo.R
import com.example.yangchaoming.bappdemo.step_test.view.ButtonStartView
import com.example.yangchaoming.bappdemo.step_test.view.TimeProgressView
import android.media.MediaPlayer
import android.util.Log
import android.animation.Animator
import android.animation.ObjectAnimator
import androidx.core.app.ActivityOptionsCompat
import androidx.core.util.Pair


class Step1TestActivity : AppCompatActivity() {
    val tag = "Step1TestActivity";
    private lateinit var btnStart: ButtonStartView
    private lateinit var btnRestart: Button
    private lateinit var btnSkip: Button
    private lateinit var timeProgressView: TimeProgressView
//    private lateinit var timeProgressView: HeartRateProgressView

    private var maxTime:Float=10*1000F    //总时长
    private var currentTime:Float=maxTime  //当前时刻

    private val REQUST_CODE_COUNTDOWN=122


    private lateinit var animator:ObjectAnimator

    private  var mediaPlayer:MediaPlayer?=null
    companion object{
         val RESULT_OK=100
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_step1_test)
//        window.exitTransition = null
//        window.reenterTransition= null
        mediaPlayer = MediaPlayer.create(this, R.raw.tjcs_music)
        mediaPlayer?.isLooping=true
        initView()
    }

    private fun initView() {
        btnStart = findViewById(R.id.btn_start)
        btnRestart = findViewById<Button>(R.id.btn_restart)
        btnSkip = findViewById(R.id.btn_skip)
        timeProgressView = findViewById(R.id.time_progress)

        btnStart.setOnClickListener {
            run {
                if (btnStart.isStart) {//开始状态 (初始化状态)
                    val intent = Intent(this, CountdownActivity::class.java)
//                    startActivityForResult(Intent(this, SecondActivity::class.java), ActivityOptionsCompat.makeSceneTransitionAnimation(this).toBundle())

                    val makeSceneTransitionAnimation = ActivityOptionsCompat.makeSceneTransitionAnimation(this, Pair(btnStart,"btn_start"))
                    startActivityForResult(intent,REQUST_CODE_COUNTDOWN, makeSceneTransitionAnimation.toBundle())
                } else {// 非开始状态
                    btnStart.setRunState(!btnStart.isRunning)
                }

            }
        }

        btnRestart.setOnClickListener {  run{
            btnStart.setRestart()
        } }

        btnStart.mStateChangeListener = object : ButtonStartView.StateChangeListener {
            override fun restart() {
                if(animator.isRunning)animator.end()
            }
            override fun stateChanged(isRunning: Boolean) {
                Log.e(tag, "stateChanged: isRunning  = $isRunning");
                if(isRunning){
                    if(animator.isPaused)animator.resume() else animator.start()
                }else{
                    if(animator.isRunning)animator.pause()
                }
            }
        }

        timeProgressView.maxValue=maxTime
        timeProgressView.currentValue=currentTime
        // 创建 ObjectAnimator 对象
        animator = ObjectAnimator.ofFloat(timeProgressView, "currentValue", maxTime, 0f)
        animator.duration=maxTime.toLong()


        animator.addPauseListener(object :Animator.AnimatorPauseListener{
            override fun onAnimationPause(animation: Animator?) {
                Log.e(tag, "onAnimationPause: ");
                mediaPlayer?.pause()
            }

            override fun onAnimationResume(animation: Animator?) {
                Log.e(tag, "onAnimationResume: ");
                mediaPlayer?.start()
            }

        })


        animator.addListener(object:Animator.AnimatorListener{
            override fun onAnimationRepeat(animation: Animator?) {
                Log.e(tag, "onAnimationRepeat: ");
            }

            override fun onAnimationEnd(animation: Animator?) {
                Log.e(tag, "onAnimationEnd: ");

                btnStart.setRestart()
                currentTime=maxTime
                timeProgressView.currentValue=maxTime
                mediaPlayer?.pause()
                mediaPlayer?.seekTo(0)
            }

            override fun onAnimationCancel(animation: Animator?) {
                Log.e(tag, "onAnimationCancel: ");
            }

            override fun onAnimationStart(animation: Animator?) {
                Log.e(tag, "onAnimationStart: ");
                //播放音频
                if(mediaPlayer!=null&&!mediaPlayer!!.isPlaying) mediaPlayer!!.start()
            }

        })
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode==REQUST_CODE_COUNTDOWN){
            if(RESULT_OK==resultCode){
                btnStart.setRunState(true)
            }
        }
    }



}