package com.example.yangchaoming.bappdemo.step_test

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.os.Bundle
import android.os.CountDownTimer
import android.transition.Fade
import android.transition.Transition
import android.transition.TransitionInflater
import android.transition.TransitionListenerAdapter
import android.util.Log
import android.view.View
import android.view.ViewAnimationUtils
import android.view.ViewGroup
import android.view.animation.AccelerateInterpolator
import android.view.animation.AnimationUtils
import android.view.animation.Interpolator
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.yangchaoming.bappdemo.R
import com.example.yangchaoming.bappdemo.step_test.view.ButtonStartView


class CountdownActivity : AppCompatActivity(){
    private lateinit var tvTime:TextView

    private lateinit var timer:CountDownTimer
    private lateinit var btnStart: View
    private lateinit var interpolator: Interpolator
    private lateinit var rootView:ViewGroup
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_countdown)
        timer=object: CountDownTimer(3000,1000){
            override fun onFinish() {
                setResult(Step1TestActivity.RESULT_OK)
                finishAfterTransition()
            }

            override fun onTick(millisUntilFinished: Long) {
                tvTime.text=( "${millisUntilFinished / 1000+1}")
            }

        }
        initView()
        setupWindowAnimations();

    }

    fun setupWindowAnimations(){
        //插值器
        interpolator = AnimationUtils.loadInterpolator(this, android.R.interpolator.linear_out_slow_in)
        setupEnterAnimations();
        setupEndAnimations();
    }

    fun setupEnterAnimations(){
//        window.enterTransition=null
        val inflateTransition = TransitionInflater.from(this).inflateTransition(R.transition.changebounds_with_arcmotion)
        window.sharedElementEnterTransition=inflateTransition
        inflateTransition.addListener(object :Transition.TransitionListener{
            override fun onTransitionEnd(transition: Transition?) {
                transition!!.removeListener(this)
                hideTarget()
                animateRevealShow(rootView)
//                animateViewsIn()
            }

            override fun onTransitionResume(transition: Transition?) {

            }

            override fun onTransitionPause(transition: Transition?) {

            }

            override fun onTransitionCancel(transition: Transition?) {

            }

            override fun onTransitionStart(transition: Transition?) {

            }

        })

    }

    private fun animateViewsIn() {
            for (i in 0 until rootView.childCount){
                val childView = rootView.getChildAt(i)
                childView.animate()
                        .setStartDelay(100 + i * 100L)
                        .setInterpolator(interpolator)
                        .alpha(1f)
                        .scaleX(1f)
                        .scaleY(1f);
            }

    }

    fun animateRevealShow(viewRoot:View){
        val cx = (viewRoot.left + viewRoot.right) / 2
        val cy = (viewRoot.top + viewRoot.bottom) / 2
        val finalRadius = Math.max(viewRoot.width, viewRoot.height)

        val anim = ViewAnimationUtils.createCircularReveal(viewRoot, cx, cy, 0f, finalRadius.toFloat())
//        viewRoot.visibility = View.VISIBLE
        anim.duration = 300
        anim.interpolator = AccelerateInterpolator()
        anim.addListener(object :AnimatorListenerAdapter(){
            override fun onAnimationEnd(animation: Animator?) {
                super.onAnimationEnd(animation)
                timer.start()
            }

            override fun onAnimationStart(animation: Animator?) {
                super.onAnimationStart(animation)

            }
        })
        anim.start()
    }

    private fun hideTarget() {
        btnStart.animate().alpha(0f).duration=50
        Log.e("hideTarget", "hideTarget:--111111 " +btnStart.visibility);
//        btnStart.visibility = View.GONE
        Log.e("hideTarget", "hideTarget:---222222 " +btnStart.visibility);
    }

    fun setupEndAnimations(){
        val fade=Fade()
        window.returnTransition=fade
//        fade.startDelay = 500
        fade.duration = 300
        fade.addListener(object :Transition.TransitionListener{
            override fun onTransitionEnd(transition: Transition?) {

            }

            override fun onTransitionResume(transition: Transition?) {

            }

            override fun onTransitionPause(transition: Transition?) {

            }

            override fun onTransitionCancel(transition: Transition?) {

            }

            override fun onTransitionStart(transition: Transition?) {
                Log.e("33333333", "onTransitionStart: ");
                transition!!.removeListener(this)
                animateViewsOut()
                animateRevealHide(rootView)

            }

        })


    }

    fun animateViewsOut(){
        for (i in 0 until rootView.childCount){
            val childView = rootView.getChildAt(i)
            if(childView.id==R.id.btn_start){
                childView.animate().alpha(1f)
            }else{
                childView.animate()
                        .setStartDelay(i.toLong())
                        .setInterpolator(interpolator)
                        .alpha(0f)
                        .scaleX(0f)
                        .scaleY(0f);
            }

        }
    }

    fun animateRevealHide(view:ViewGroup){
        val cx=(view.left+view.right)/2
        val cy=(view.top+view.bottom)/2
        val initialRadius=view.width
        val animator=ViewAnimationUtils.createCircularReveal(view,cx,cy,initialRadius.toFloat(),0f)
        animator.addListener(object :AnimatorListenerAdapter(){
            override fun onAnimationEnd(animation: Animator?) {
                super.onAnimationEnd(animation)
                rootView.visibility=View.VISIBLE
            }
        })
        animator.duration=300
        animator.start()
    }

    override fun onBackPressed() {
        finishAfterTransition()
//        super.onBackPressed()
    }


    private fun initView() {
        rootView=findViewById(R.id.rootView)
        tvTime=findViewById(R.id.time)
        btnStart=findViewById(R.id.btn_start)
        tvTime.outlineProvider
//        timer.start()
        Log.e("initView", "initView: ");
    }

    override fun onDestroy() {
        timer.cancel()
        super.onDestroy()
    }

}