package com.example.yangchaoming.bappdemo.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.yangchaoming.bappdemo.R

class Tab2Fragment : Fragment(){

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        Log.e("onCreateView", "Tab2Fragment : ");
        val view = inflater.inflate(R.layout.fragment_tab1, container, false)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        Log.e("onViewCreated", "Tab2Fragment : ");
        super.onViewCreated(view, savedInstanceState)
        view.findViewById<TextView>(R.id.tv_label).apply {
            text="If You Forget Me"
//            background = ContextCompat.getDrawable(context, R.color.colorPrimary)
        }

    }
}