package com.example.yangchaoming.bappdemo.tooltip

import android.os.Bundle
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
    }

    override fun onTipDismissed(view: View?, anchorViewId: Int, byUser: Boolean) {

    }
}