package com.example.yangchaoming.bappdemo.b_app_work.behavior

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.View
import androidx.coordinatorlayout.widget.CoordinatorLayout
import com.example.yangchaoming.bappdemo.R

class BehaviorWorkBody @JvmOverloads constructor(val context: Context? = null, attrs: AttributeSet? = null) : CoordinatorLayout.Behavior<View>(context,attrs){

    override fun layoutDependsOn(parent: CoordinatorLayout, child: View, dependency: View): Boolean {
        return dependency.id == R.id.middle_card
    }

    override fun onDependentViewChanged(parent: CoordinatorLayout, child: View, dependency: View): Boolean {
        var offset = dependency.bottom
        child.translationY = offset.toFloat();
        Log.e("onDependentViewChanged", "onDependentViewChanged: $offset");
        return true;
    }
}