package com.example.yangchaoming.bappdemo.glide_demo

import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.load.MultiTransformation
import com.bumptech.glide.load.resource.bitmap.*
import com.bumptech.glide.request.RequestOptions
import com.example.yangchaoming.bappdemo.GlideApp
import com.example.yangchaoming.bappdemo.R
import com.example.yangchaoming.bappdemo.utils.CommonUtil
import kotlinx.android.synthetic.main.acitity_glide_demo.*

class GlideDemoActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.acitity_glide_demo)
//        val url = "https://pics4.baidu.com/feed/0d338744ebf81a4c6f094de414c8d75e242da6ca.jpeg?token=7b5b775cfecfe9dadd102bede1862fef&s=4E8AC70940AA2EA50921F1C30100A0A2"

        Handler().post {
            setImage()
        }

    }

    private fun setImage() {
        val layoutParams = iv_img.layoutParams
        layoutParams.width = CommonUtil.dp2px(150f, this)
        layoutParams.height = CommonUtil.dp2px(180f, this)
        iv_img.layoutParams = layoutParams

        val url = "https://images.unsplash.com/photo-1607489180229-4b3ec6ce3884?ixid=MXwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHw%3D&ixlib=rb-1.2.1&auto=format&fit=crop&w=633&q=80"
        //        val url = ""
        val dp2px = CommonUtil.dp2px(24f, this).toFloat()
        //        val glideRequestOption = RequestOptions()
        ////                .transform(MultiTransformation(CenterCrop(), RoundedCorners(CommonUtil.dp2px(24f, this))))
        //                .transform(MultiTransformation(CenterCrop(), GranularRoundedCorners(dp2px, dp2px, 0f, 0f)))
        ////                .transform(GranularRoundedCorners(dp2px,dp2px,0f,0f))
        ////                .override(200,200)
        //        Glide.with(this).load(url).apply(glideRequestOption).into(iv_img)

//        GlideApp.with(this).load(url)
        GlideApp.with(this).load("R.mipmap.girl1")
//                .placeholder(R.mipmap.girl1)
    //                .placeholder(R.mipmap.girl1)
                    .error(R.mipmap.girl1)
                .transform(MultiTransformation(CenterCrop(), GranularRoundedCorners(dp2px, dp2px, 0f, 0f)))
    //                .fallback(R.mipmap.ic_launcher)
                .into(iv_img)

    }
}