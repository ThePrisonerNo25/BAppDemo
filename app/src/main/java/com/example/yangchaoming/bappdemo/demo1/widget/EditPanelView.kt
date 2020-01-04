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
import android.view.animation.Animation
import android.view.animation.TranslateAnimation
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.FrameLayout
import android.widget.LinearLayout
import android.widget.TextView
import com.example.yangchaoming.bappdemo.R
import com.example.yangchaoming.bappdemo.demo1.DynamicUtil
import com.example.yangchaoming.bappdemo.demo1.OnKeyBoardStateListener

class EditPanelView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : LinearLayout(context, attrs, defStyleAttr), OnKeyBoardStateListener {


    private  var layoutPanel: LinearLayout
    private  var flNull: FrameLayout
    private  var etContent: EditText
    private  var tvCommit: TextView


    var isKeyBoardShow = false
    var mKeyBoardHeight: Int = -1
    var mDisplayHeight: Int = -1


    init {
        val view = LayoutInflater.from(context).inflate(R.layout.view_edit_panel, this, false);
        layoutPanel = view.findViewById(R.id.layout_edit_panel)
        flNull = view.findViewById(R.id.fl_null)
        etContent = view.findViewById(R.id.et_content)
        tvCommit = view.findViewById(R.id.tv_commit)
        tvCommit.setOnClickListener{v->run{
            if (etContent.text.isNotEmpty()) editPanelListenter?.commitMessage(etContent.text?.toString())
            dismiss()
        }}

        etContent.addTextChangedListener(object :TextWatcher{
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
        addView(view)
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        if (isShowing()) {
            dismiss()
        }
        return super.onTouchEvent(event)
    }

    var isVisiableForLast = false
    private fun addOnSoftKeyBoardVisibleListener(activity: Activity, listener: OnKeyBoardStateListener) {
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

            if (visible != isVisiableForLast) {
                listener.onSoftKeyBoardState(visible, keyboardHeight, (displayHight as Int) - dp2px(50f))
            }
            isVisiableForLast = visible;
        }

    }

    fun showEditPanel() {
        if(isVisiableForLast)return
        layoutPanel?.visibility=View.VISIBLE
        showOrHideAnimation(true)
        showSoftKeyBoard()
    }

    private fun dp2px(dpValue: Float): Int {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dpValue, context.resources.displayMetrics).toInt()
    }

    override fun onSoftKeyBoardState(visible: Boolean, keyboardHeight: Int, displayHeight: Int) {
//        Log.e("onSoftKeyBoardState","onSoftKeyBoardState ")
        isKeyBoardShow = visible
        if (visible) {
//            Log.e("onSoftKeyBoardState","keyboardHeight + $keyboardHeight")
            mKeyBoardHeight = keyboardHeight
            mDisplayHeight = displayHeight
            editPanelListenter?.softKeyBoardShow(displayHeight)
        }
    }

    fun isShowing(): Boolean {
//        return layoutPanel != null && layoutPanel.visibility == View.VISIBLE
        return  layoutPanel.visibility == View.VISIBLE
    }

    fun dismiss() {
        etContent.setText("")
        showOrHideAnimation(false)
        layoutPanel.visibility=View.GONE
        hideSoftKeyBoard()
    }

    private fun showOrHideAnimation(isShow: Boolean) {
        val animation = TranslateAnimation(Animation.RELATIVE_TO_PARENT, 0.0f,
                Animation.RELATIVE_TO_PARENT, 0.0f, Animation.RELATIVE_TO_PARENT, if (isShow) 1.0f else 0.0f,
                Animation.RELATIVE_TO_PARENT, if (isShow) 0.0f else 1.0f)
        animation.duration = 200
        layoutPanel.startAnimation(animation)
    }

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
       fun softKeyBoardShow(mDisplayHeight:Int)
        fun commitMessage(mesagge:String?)
    }

    var  editPanelListenter:EditPanelListener? = null;

    fun setEditHintText(str :String?){

        etContent.hint=if(str.isNullOrEmpty()) "" else "回复$str:"
    }

    fun setActivityContext(activity: Activity){
        addOnSoftKeyBoardVisibleListener(activity, this)
    }
}