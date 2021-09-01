package com.example.yangchaoming.bappdemo.b_app_demo.viewpage2

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class DemoCollectionAdapter(activity: AppCompatActivity):FragmentStateAdapter(activity) {

    override fun getItemCount(): Int {
        return 5
    }

    override fun createFragment(position: Int): Fragment {
        val fragment = DemoObjectFragment()
        fragment.arguments = Bundle().apply {
            // Our object is just an integer :-P
            putInt(DemoObjectFragment.ARG_OBJECT, position + 1)
        }
        return fragment
    }

}