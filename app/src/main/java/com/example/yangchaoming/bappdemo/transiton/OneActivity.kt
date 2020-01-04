package com.example.yangchaoming.bappdemo.transiton

import android.app.ActivityOptions
import android.content.Intent
import android.os.Bundle
import android.transition.Explode
import android.util.Log
import android.util.Pair
import android.view.View
import android.view.ViewTreeObserver
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.example.yangchaoming.bappdemo.R
import kotlinx.android.synthetic.main.activity_one.*

class OneActivity : AppCompatActivity(){

    var list = ArrayList<String>()
    companion object{
        const val IMAGE_ITEM_POSITION = "imageItemPosition"
        const val IMAGE_ITEM_TRANSITION = "imageItemTransition"
        const val IMAGE_ITEM_ID = "imageItemTransitionId"
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_one)
        val explode =  Explode()
        window.exitTransition = explode
        mockData()
        initView()
    }

    fun initView(){
        recycler_view.layoutManager = GridLayoutManager(this,4)

        val oneAdapter = OneAdapter(this, list)
        recycler_view.adapter = oneAdapter
        oneAdapter.listener = object : OneAdapter.OneAdapterListener{
            override fun ItemClick(v: View, pos: Int) {

                val activityOptions = getActivityOptions(v, v.transitionName)
                val imageActivityIntent = getImageActivityIntent(v,pos)
                startActivity(imageActivityIntent,activityOptions.toBundle())
            }
        }
    }

    var pos_ = -1
    fun getImageActivityIntent(view: View, pos: Int): Intent{
        val intent = Intent(this@OneActivity,ImageActivity::class.java)
        intent.putExtra(IMAGE_ITEM_TRANSITION,""+view.id+pos)
        intent.putExtra(IMAGE_ITEM_POSITION,pos)
        intent.putExtra(IMAGE_ITEM_ID,""+view.id)

        pos_= pos
        return intent
    }

    override fun onActivityReenter(resultCode: Int, data: Intent?) {
        super.onActivityReenter(resultCode, data)
        Log.e("onActivityReenter", "onActivityReenter: ");
        postponeEnterTransition()
//        recycler_view.viewTreeObserver.addOnPreDrawListener {
//            recycler_view.viewTreeObserver.removeOnPreDrawListener(@addOnPreDrawListener)
//            startPostponedEnterTransition()
//            return@addOnPreDrawListener true
//        }
        recycler_view.viewTreeObserver.addOnPreDrawListener(object : ViewTreeObserver.OnPreDrawListener{
            override fun onPreDraw(): Boolean {
                recycler_view.viewTreeObserver.removeOnPreDrawListener(this)
                startPostponedEnterTransition()
                return true
            }

        })
        recycler_view.scrollToPosition(pos_+2)

    }

    fun getActivityOptions(v:View,transitionName:String):ActivityOptions{
        val pair = Pair.create(v, transitionName)

//        return null
        val activityOptions = ActivityOptions.makeSceneTransitionAnimation(this, pair)
        return  activityOptions
    }

    fun mockData(){
        for (i in 0 until 100){
            list.add("item#"+i)
        }
    }

}