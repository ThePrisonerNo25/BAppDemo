package com.example.yangchaoming.bappdemo.fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentTransaction
import com.example.yangchaoming.bappdemo.R
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.activity_frag.*


class FragActivity : FragmentActivity(), View.OnClickListener {

    private val disposables = CompositeDisposable()
    lateinit var tab1: Fragment
    lateinit var tab2: Fragment
    lateinit var tab3: Fragment
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_frag)
        btn_1.setOnClickListener(this)
        btn_2.setOnClickListener(this)
        btn_3.setOnClickListener(this)

        initFragment()
//        btn_1.performClick()
    }

    private fun initFragment() {
        tab1 = Tab1Fragment()
        tab2 = Tab2Fragment()
        tab3 = Tab3Fragment()
    }

    override fun onClick(v: View?) {
        supportFragmentManager?.beginTransaction()?.apply {
            when (v!!.id) {
                R.id.btn_1 -> {
                    replace(R.id.content, tab1)
                }
                R.id.btn_2 -> {
                    replace(R.id.content, tab2)
                }
                R.id.btn_3 -> {
                    replace(R.id.content, tab3)
                }
            }
        }.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                .commit()


//        val d = Single.just("Hello World")
//                .delay(10, TimeUnit.SECONDS, Schedulers.io())
//                .subscribeWith<SingleObserver<String>>(object : DisposableSingleObserver<String>() {
//                    public override fun onStart() {
//                        println("Started")
//                    }
//
//                    override fun onSuccess(value: String) {
//                        println("Success: $value")
//                    }
//
//                    override fun onError(error: Throwable) {
//                        error.printStackTrace()
//                    }
//                })
    }

}