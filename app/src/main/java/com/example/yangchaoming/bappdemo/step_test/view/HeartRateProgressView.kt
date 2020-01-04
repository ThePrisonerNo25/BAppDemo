package com.example.yangchaoming.bappdemo.step_test.view

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.util.TypedValue
import android.view.View
import java.math.BigDecimal
import java.math.RoundingMode
import android.R.attr.start
import android.animation.ValueAnimator
import android.util.Log
import androidx.core.content.ContextCompat
import com.example.yangchaoming.bappdemo.R
import android.graphics.BitmapFactory
import android.graphics.Bitmap
import android.graphics.drawable.Drawable


class HeartRateProgressView @JvmOverloads constructor(context: Context, attributeSet: AttributeSet? =null, defStyleAttr: Int =0 )
    : View(context,attributeSet,defStyleAttr){
    val tag = "HeartRateProgressView";
    private var mRingPaint:Paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private var mTextPaint:Paint =Paint(Paint.ANTI_ALIAS_FLAG)
    private var mBpmTextPaint:Paint =Paint(Paint.ANTI_ALIAS_FLAG)

    var maxValue:Float=100*1000f
    var currentValue:Float=0f
    set(value) = run{
        field = if(value<0) 0f else value
        invalidate()
    }

    var textValue:Int=0
    set(value) =run{
        field =value
        invalidate()
    }

    private var offsetValue:Int=0
    private  var heartIcon:Bitmap?=null
    init {
        mRingPaint.style=Paint.Style.STROKE
        mRingPaint.strokeCap=Paint.Cap.ROUND
        mRingPaint.strokeWidth=dp2px(8f)
        mRingPaint.color=Color.parseColor("#ffffff")

        mTextPaint.textSize=sp2px(64f)
        mTextPaint.color=Color.parseColor("#ffffff")

        mBpmTextPaint.textSize=sp2px(20f)
        mBpmTextPaint.color=Color.parseColor("#ffffff")

        //测量 “0”的宽高 计算值的偏移量
        var rect= Rect()
        mTextPaint.getTextBounds("0",0,1,rect)
        offsetValue=rect.width()*2/3

        heartIcon=  drawableToBitmap(context.getDrawable(R.mipmap.ic_launcher_round))

    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
//        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        val width = View.MeasureSpec.getSize(widthMeasureSpec)
        val height = View.MeasureSpec.getSize(heightMeasureSpec)
        setMeasuredDimension(if (width > height) height else width, if (width > height) height else width)
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        if (canvas==null)return
        val width = canvas.width
        val height=canvas.height
        canvas.translate(width/2.0f,height/2.0f)

        //画数字
        val text = textValue.toString()
        val rect= Rect()
        mTextPaint.getTextBounds(text,0,text.length,rect)
        val visualDiff=dp2px(4f)//手动 调节人的视觉产生的 误差
        val x =-rect.width()-visualDiff +offsetValue
        val y=rect.height()/2.0f
        canvas.drawText(text,x,y,mTextPaint)

        //画心 画布的右上角
        val heartW=dp2px(34f)
        val heartH=dp2px(30f)
        val heartLeft =offsetValue+dp2px(8f)+visualDiff
        val heartRectF=RectF(heartLeft,-heartH+dp2px(4f),heartLeft+heartW,0f)
        if(heartIcon!=null)canvas.drawBitmap(heartIcon,null,heartRectF, null)

        //画 "bpm" 画布的右下角
        val bpmText:String ="bpm"

        val rectBpm= Rect()
        mBpmTextPaint.getTextBounds(bpmText,0,bpmText.length,rectBpm)

        val x_bpm=offsetValue+dp2px(8f)+visualDiff
        val y_bpm=rectBpm.height().toFloat()+dp2px(4f)
        canvas.drawText(bpmText,x_bpm,y_bpm,mBpmTextPaint)

        //画圆环
        var percent= currentValueToPercent(currentValue,maxValue)
        var paintStrokeWidth= mRingPaint.strokeWidth
        var circleBound= RectF(-width/2.0f + paintStrokeWidth,-height/2.0f + paintStrokeWidth,width/2.0f - paintStrokeWidth,height/2.0f - paintStrokeWidth)
        canvas.drawArc(circleBound!!,-90f,360*percent,false,mRingPaint)
    }

    private fun currentValueToPercent(currentValue:Float,maxValue:Float) : Float{
        if(maxValue==0f||currentValue==0f)return 0f;
        if(currentValue>=maxValue)return 1f
        val percent = currentValue.toDouble() / maxValue.toDouble()
        val bigDecimal = BigDecimal.valueOf(percent)
        return  bigDecimal.setScale(5, RoundingMode.HALF_UP).toFloat()
    }



//    fun setCurrentTime(value:Long,text:Int=0){
//        if(value<0)return
//        val animator = ValueAnimator.ofFloat(preValue.toFloat(), value.toFloat())
//        animator.addUpdateListener { animation ->
//            val currentStep = animation.animatedValue as Float//获取当前值
//
////            Log.e("setCurrentValueAnim", "setCurrentValueAnim: $currentStep");
//            setCurrentValue(currentStep.toLong(),text)
//        }
//        animator.duration = 1000
////        if(animator.isRunning)animator.cancel()
//        animator.start()
//    }

    private fun dp2px(value:Float) : Float{
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, value, context.resources.displayMetrics)
    }

    private fun sp2px(value:Float) : Float{
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, value, context.resources.displayMetrics)
    }

   private fun drawableToBitmap( drawable: Drawable):Bitmap? {
        if(drawable==null)return null
        val w = drawable.intrinsicWidth;
        val h = drawable.intrinsicHeight;
        Log.e(tag, "drawableToBitmap: ");
        val config = if(drawable!!.opacity != PixelFormat.OPAQUE) Bitmap.Config.ARGB_8888 else Bitmap.Config.RGB_565
        val bitmap = Bitmap.createBitmap(w, h, config);
        //注意，下面三行代码要用到，否则在View或者SurfaceView里的canvas.drawBitmap会看不到图
        val canvas =  Canvas(bitmap);
        drawable.setBounds(0, 0, w, h);
        drawable.draw(canvas);
        return bitmap;
    }

}