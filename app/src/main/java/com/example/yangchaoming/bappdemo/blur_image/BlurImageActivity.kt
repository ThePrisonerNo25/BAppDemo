package com.example.yangchaoming.bappdemo.blur_image

import android.graphics.Outline
import android.os.Bundle
import android.renderscript.RenderScript
import android.view.View
import android.view.ViewOutlineProvider
import android.widget.PopupWindow
import androidx.appcompat.app.AppCompatActivity
import com.example.yangchaoming.bappdemo.R
import com.example.yangchaoming.bappdemo.blur_image.util.BlurUtil
import com.example.yangchaoming.bappdemo.blur_image.util.RSBlurProcessor
import kotlinx.android.synthetic.main.acitity_blur_image.*


class BlurImageActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.acitity_blur_image)


//        img.setOnClickListener {
//            val renderScript = RenderScript.create(this)
//            val pro =  RSBlurProcessor(renderScript)
//           val bit =  BlurUtil.getBitmapFromView(root)
//           val bit1 = pro.blur(bit,8f,1)
//            img.setImageBitmap(bit1)
//        }
//        PopupWindow

        img.setOnClickListener {
            val popUpClass =  PopUpClass1();
            popUpClass.showPopupWindow(it,1080,500);
        }


//        tv_rect.outlineProvider = object : ViewOutlineProvider(){
//            override fun getOutline(view: View?, outline: Outline?) {
//                outline?.setRoundRect(0, 0, view.getWidth(), view.getHeight(), view.getHeight() / 2);
//            }
//
//        }

    }
}