package com.example.yangchaoming.bappdemo.tooltip

import android.graphics.Color
import android.os.Bundle
import android.text.SpannableString
import android.text.Spanned
import android.text.style.ForegroundColorSpan
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import com.example.yangchaoming.bappdemo.R
import com.tomergoldst.tooltips.ToolTip
import com.tomergoldst.tooltips.ToolTipsManager
import kotlinx.android.synthetic.main.activity_tooltip.*

class TooltipActivity : AppCompatActivity(), ToolTipsManager.TipListener {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tooltip)

       val mToolTipsManager =  ToolTipsManager(this);
        mToolTipsManager.findAndDismiss(tooltip)
        val text = "it is now  16:42 "
        val mAlign = ToolTip.ALIGN_CENTER;
        val  builder =  ToolTip.Builder(this, tooltip,content , text, ToolTip.POSITION_ABOVE);
        builder.setAlign(mAlign)


        tooltip.setOnClickListener {
            mToolTipsManager.show(builder.build())
        }
//        SpannableString spannableString = new SpannableString("如果我是陈奕迅");
//        ForegroundColorSpan foregroundColorSpan = new ForegroundColorSpan(Color.GREEN);
//        spannableString.setSpan(foregroundColorSpan, 4, 7, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
//        mTextView.setText(spannableString);
        val text1 = "王浩高级私人教练"
//        val text1 = ""
        val text2 = "Viva初级瑜伽教练"
//        val text2 = ""
        val text4 ="易健王大雷:我也想知道！这个抓拍是真的无"

        val spannableString = SpannableString("${text1}回复${text2}：$text4 ")
        val foregroundColorSpan =  ForegroundColorSpan(Color.GREEN);
        val foregroundColorSpan1 =  ForegroundColorSpan(Color.GREEN);
        spannableString.setSpan(foregroundColorSpan, 0, text1.length, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        spannableString.setSpan(foregroundColorSpan1,text1.length+2,text1.length+2+text2.length,Spanned.SPAN_INCLUSIVE_EXCLUSIVE)
//        tv_content.text = "如果我是:$spannableString"
        tv_content.text = spannableString
    }

    override fun onTipDismissed(view: View?, anchorViewId: Int, byUser: Boolean) {

    }
}