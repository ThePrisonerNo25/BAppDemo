package com.example.yangchaoming.bappdemo.widget

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import com.example.yangchaoming.bappdemo.R

class ButtonAttention @JvmOverloads constructor(context: Context,
                                                attrs: AttributeSet? = null,
                                                defStyle: Int = 0):ConstraintLayout(context,attrs,defStyle){
   lateinit var itemView: ViewGroup
    lateinit var tvName : TextView
    lateinit var ivIcon : ImageView
    init {
        LayoutInflater.from(context)
                .inflate(R.layout.btn_attention, this,true)
        initializeView()
    }

    private fun initializeView() {
        itemView = findViewById(R.id.root)
        tvName = findViewById(R.id.tv_text)
        ivIcon = findViewById(R.id.iv_icon)

       onSelected(isSelected)
    }


    override fun setSelected(selected: Boolean) {
        onSelected(selected)
        super.setSelected(selected)
    }

    private fun onSelected(selected: Boolean){
        if(selected){
            tvName.text = "关注"
            tvName.setTextColor(ContextCompat.getColor(this.context,R.color.light_green))
            ivIcon.setImageDrawable(ContextCompat.getDrawable(this.context,R.mipmap.girl))

        }else{
            tvName.text = "已关注"
            tvName.setTextColor(ContextCompat.getColor(this.context,R.color.text_color_3))
            ivIcon.setImageDrawable(ContextCompat.getDrawable(this.context,R.mipmap.girl1))
        }
    }
}