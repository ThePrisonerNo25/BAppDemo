package com.example.yangchaoming.bappdemo.demo1.widget

import android.app.Activity
import android.content.Context
import android.graphics.Color
import android.graphics.Rect
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import android.util.Log
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.FrameLayout
import android.widget.LinearLayout
import android.widget.TextView
import com.example.yangchaoming.bappdemo.R
import com.example.yangchaoming.bappdemo.demo1.DynamicUtil


class SimpleEditView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : LinearLayout(context, attrs, defStyleAttr){


    private lateinit var layoutPanel: LinearLayout
    private lateinit var flNull: FrameLayout
    private lateinit var etContent: EditText
    private lateinit var tvCommit: TextView


    init {
        val view = LayoutInflater.from(context).inflate(R.layout.view_edit_panel, this, false);
        layoutPanel = view.findViewById(R.id.layout_edit_panel)
        layoutPanel.visibility=View.VISIBLE
        flNull = view.findViewById(R.id.fl_null)
        etContent = view.findViewById(R.id.et_content)
        tvCommit = view.findViewById(R.id.tv_commit)
        tvCommit.setOnClickListener{v->run{
            if (etContent.text.isNotEmpty()) editPanelListenter?.commitMessage(etContent.text?.toString())
            etContent.setText("")
            dismissPanel()

        }}

        etContent.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {

                if (s.toString().isNotEmpty()) {
                    tvCommit.background=context.getDrawable(R.drawable.shape_yellow_solid_4)
                    tvCommit.setTextColor(Color.parseColor("#ffffff"))
                }else{
                    tvCommit.background=context.getDrawable(R.drawable.shape_gray_stoke_4)
                    tvCommit.setTextColor(Color.parseColor("#999999"))
                }
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

        })

//        flNull.setOnTouchListener(object :View.OnTouchListener{
//            override fun onTouch(v: View?, event: MotionEvent?): Boolean {
//                if(isVisiableForLast){
//                    dismissPanel()
//                    return true
//                }
//
//                return false
//            }
//
//        })

        etContent.setOnClickListener{ showPanel() }
        addView(view)
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
         if(isVisiableForLast){
//             Log.e("onTouchEvent","onTouchEvent")
             dismissPanel()
             return true
         }
        return super.onTouchEvent(event)
    }

    var isVisiableForLast=false
    private fun addOnSoftKeyBoardVisibleListener(activity: Activity) {
        val decorView = activity.window.decorView
        decorView.viewTreeObserver.addOnGlobalLayoutListener {
            val rect = Rect()
            decorView.getWindowVisibleDisplayFrame(rect)
            //计算出可见屏幕的高度
            var displayHight = (rect.bottom - rect.top)
            //获得屏幕整体的高度
            var hight = decorView.height;
            //获得键盘高度
            var keyboardHeight = hight - displayHight - DynamicUtil.calcStatusBarHeight(context);
            var visible = displayHight*1.0  / hight < 0.8;
//            if(isVisiableForLast!=visible){
//                Log.e("showPanel","visible $visible")
//            }

            isVisiableForLast = visible;
        }
    }


    fun showPanel() {
//        showOrHideAnimation(true)
        if(isVisiableForLast) return
        etContent.setText("")
        showSoftKeyBoard()
    }


    fun dismissPanel() {
//        showOrHideAnimation(false)
        hideSoftKeyBoard()
    }

//    private fun showOrHideAnimation(isShow: Boolean) {
//        val animation = TranslateAnimation(Animation.RELATIVE_TO_PARENT, 0.0f,
//                Animation.RELATIVE_TO_PARENT, 0.0f, Animation.RELATIVE_TO_PARENT, if (isShow) 1.0f else 0.0f,
//                Animation.RELATIVE_TO_PARENT, if (isShow) 0.0f else 1.0f)
//        animation.duration = 200
//        layoutPanel.startAnimation(animation)
//    }

    private fun showSoftKeyBoard() {
        val inputMethodManager = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        if (inputMethodManager != null && etContent != null) {
            etContent.post {
                etContent.requestFocus()
                inputMethodManager.showSoftInput(etContent, 0)
            }
        }
    }

    private fun hideSoftKeyBoard() {
        val inputMethodManager = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        if (inputMethodManager != null && etContent != null) {
            inputMethodManager.hideSoftInputFromWindow(etContent.windowToken, 0)
        }
    }

    interface EditPanelListener{
        fun commitMessage(mesagge:String?)
    }

    var  editPanelListenter:EditPanelListener? = null;

    fun setEditHintText(str :String?){
        etContent.hint=if(str.isNullOrEmpty()) "" else "回复$str:"
    }


    fun setActivityContext(activity: Activity){
        addOnSoftKeyBoardVisibleListener(activity)
    }
}