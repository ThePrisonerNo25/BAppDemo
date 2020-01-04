package com.example.yangchaoming.bappdemo.transiton

import android.app.Activity
import android.os.Bundle
import android.os.PersistableBundle
import android.transition.Fade
import android.transition.TransitionInflater
import androidx.appcompat.app.AppCompatActivity
import com.example.yangchaoming.bappdemo.R
import kotlinx.android.synthetic.main.activity_image.*

class ImageActivity : AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_image)
        val fade = Fade()

        window.enterTransition = fade

        val inflateTransition = TransitionInflater.from(this).inflateTransition(R.transition.image_detail_trasition)
        window.sharedElementEnterTransition= inflateTransition

        postponeEnterTransition();
        initialData()

        image_detail.setOnClickListener {
            it.transitionName =id+(pos+2)
            setResult(Activity.RESULT_OK)
            finishAfterTransition()
        }

    }
    var pos:Int =-1
    var id:String =""
    fun initialData(){
        val any = intent.getStringExtra(OneActivity.IMAGE_ITEM_TRANSITION)
        id = intent.getStringExtra(OneActivity.IMAGE_ITEM_ID)
        image_detail.transitionName=any
        pos= intent.getIntExtra(OneActivity.IMAGE_ITEM_TRANSITION,-1)
        startPostponedEnterTransition()
    }
}