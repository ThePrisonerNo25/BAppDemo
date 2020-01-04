package com.example.yangchaoming.bappdemo.fitness_game.fitness_list

import android.graphics.Rect
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.yangchaoming.bappdemo.R
import com.example.yangchaoming.bappdemo.utils.CommonUtil
import kotlinx.android.synthetic.main.activity_fitness_moment.*

class FitnessMomentActivity : AppCompatActivity(){

    lateinit var adapter: FitnessMomentAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fitness_moment)
        initializeData()
        recycler_view.layoutManager =  LinearLayoutManager(this,RecyclerView.VERTICAL,false)
        recycler_view.addItemDecoration(object : RecyclerView.ItemDecoration(){
            override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
                super.getItemOffsets(outRect, view, parent, state)
                val childAdapterPosition = parent.getChildAdapterPosition(view)
                if(childAdapterPosition == 0){
                    outRect.top = CommonUtil.dp2px(20f,this@FitnessMomentActivity)
                }else if(childAdapterPosition+1 == parent.adapter?.itemCount){
                    outRect.bottom = CommonUtil.dp2px(24f,this@FitnessMomentActivity)
                }else{
                    outRect.top = CommonUtil.dp2px(24f,this@FitnessMomentActivity)
                }
            }
        })

        adapter = FitnessMomentAdapter(this, ArrayList())
        recycler_view.adapter = adapter

        adapter.listener = object : FitnessMomentAdapter.Listener {
            override fun watchClick(v: View, position: Int) {

            }

            override fun unwatchClick(v: View, position: Int) {

            }

            override fun commentClick(v: View, position: Int) {

            }

            override fun likesClick(v: View, position: Int) {

            }

            override fun iamgeClick(v: View, childPosition: Int, parentPosition: Int) {

            }
        }

    }

    private fun initializeData(){

    }

}