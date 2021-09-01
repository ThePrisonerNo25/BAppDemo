package com.example.yangchaoming.bappdemo.video.videoTrim

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Bitmap
import android.media.MediaMetadataRetriever
import android.media.MediaPlayer
import android.net.Uri
import android.os.Handler
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.view.animation.LinearInterpolator
import android.widget.*
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.yangchaoming.bappdemo.R
import io.reactivex.*
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers
import io.reactivex.subscribers.DisposableSubscriber
import org.reactivestreams.Subscriber
import org.reactivestreams.Subscription
import java.util.function.Consumer


class VideoTrimView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : FrameLayout(context, attrs, defStyleAttr) {
    val TAG: String = this.javaClass.name

    var mContext: Context = context
    var videoContent: RelativeLayout
    var mVideoView: ZVideoView
    var framesRecyclerView: RecyclerView
    var seekBarLayout: LinearLayout
    var mRedProgressIcon: ImageView
    var btnCancel: Button
    var btnConfirm: Button

    var mDuration: Int = -1 //视频时长

    var isPause: Boolean = false

    private var mRangeSeekBarView: RangeSeekBarView? = null

    private var mLeftProgressPos: Long = 0
    private var mRightProgressPos: Long = 0

    private var mThumbsTotalCount: Int = 0

    private var deviceWidth = context.resources.displayMetrics.widthPixels

    private var mAverageMsPx: Float = 0.toFloat()//每毫秒所占的px
    private var averagePxMs: Float = 0.toFloat()//每px所占用的ms毫秒

    private var isSeeking: Boolean = false
    private var scrollPos: Long = 0

    private var mSourceUri: Uri? = null

    private var mVideoThumbAdapter: VideoTrimmerAdapter

    private var lastScrollX: Int = 0
    private val mScaledTouchSlop: Int = 0
    private var isOverScaledTouchSlop: Boolean = false

    private var mRedProgressAnimator: ValueAnimator? = null
    private val mAnimationHandler = Handler()

    private val  recyclerViewPadding = dpToPx(35)
    private var mRedProgressBarPos: Long = 0

    private val disposable = CompositeDisposable()

    companion object {
        const val MIN_SHOOT_DURATION = 3000L// 最小剪辑时间3s
        const val MAX_COUNT_RANGE = 10//seekBar的区域内一共有多少张图片
        const val VIDEO_MAX_TIME = 10//
        const val MAX_SHOOT_DURATION = VIDEO_MAX_TIME * 1000L;//视频最多剪切多长时间10s
    }

    init {
        LayoutInflater.from(context).inflate(R.layout.video_trimmer_view, this, true)
        videoContent = findViewById(R.id.layout_surface_view)
        mVideoView = findViewById(R.id.video_loader)
        framesRecyclerView = findViewById(R.id.video_frames_recyclerView)
        seekBarLayout = findViewById(R.id.seek_bar_layout)
        mRedProgressIcon = findViewById(R.id.positionIcon)
        btnCancel = findViewById(R.id.btn_cancel)
        btnConfirm = findViewById(R.id.btn_confirm)

        framesRecyclerView.layoutManager = LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false)
        mVideoThumbAdapter = VideoTrimmerAdapter(mContext)
        framesRecyclerView.adapter = mVideoThumbAdapter


        setUpListener()
    }

    val mOnScrollListener = object : RecyclerView.OnScrollListener() {
        override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
            super.onScrollStateChanged(recyclerView, newState)
//            Log.d(TAG, "newState = $newState")
            if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                mRangeSeekBarView!!.setStartEndTime(mLeftProgressPos, mRightProgressPos)
                mRangeSeekBarView!!.invalidate()
                mRedProgressBarPos = mLeftProgressPos
                seekTo(mLeftProgressPos)
                playVideo()
            }
        }

        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)
            val recyclerViewPadding = recyclerViewPadding
            isSeeking = false
            val scrollX = calcScrollXDistance()
            //达不到滑动的距离
            if (Math.abs(lastScrollX - scrollX) < mScaledTouchSlop) {
                isOverScaledTouchSlop = false
                return
            }
            isOverScaledTouchSlop = true
            //初始状态,why ? 因为默认的时候有35dp的空白！
            if (scrollX == -recyclerViewPadding) {
                scrollPos = 0
            } else {
                isSeeking = true
                scrollPos = (mAverageMsPx * (recyclerViewPadding + scrollX)).toLong()
                mLeftProgressPos = mRangeSeekBarView!!.selectedMinValue + scrollPos
                mRightProgressPos = mRangeSeekBarView!!.selectedMaxValue + scrollPos
            }
            lastScrollX = scrollX
        }
    }


    private fun setUpListener() {
        mVideoView.setOnPreparedListener(MediaPlayer.OnPreparedListener { mp ->

            mp.setVideoScalingMode(MediaPlayer.VIDEO_SCALING_MODE_SCALE_TO_FIT)
            videoPrepared(mp!!)
        })
        mVideoView.setOnCompletionListener {
            seekTo(mLeftProgressPos)
            playVideo()
        }

        btnCancel.setOnClickListener {
            videoTrimListener?.canCel()
        }

        btnConfirm.setOnClickListener {
//            Log.e("setUpListener", "setUpListener: ${mSourceUri?.path}")
            videoTrimListener?.trimVideo(mSourceUri!!.path!!,mLeftProgressPos,mRightProgressPos)
        }
    }

    fun initVideoByURI(videoURI: Uri) {
        mSourceUri = videoURI
        mVideoView.setVideoURI(videoURI)
        mVideoView.requestFocus()
    }

    private fun videoPrepared(mp: MediaPlayer) {
        val lp = mVideoView.layoutParams
        val videoProportion = mp.videoWidth.toFloat() / mp.videoHeight.toFloat()

        if (videoProportion < 1) { //竖屏
            lp.width = (videoContent.width * videoProportion).toInt()
            lp.height = videoContent.height
        } else { // 横屏
            lp.width = videoContent.width
            lp.height = (lp.width / videoProportion).toInt()
        }
        mVideoView.layoutParams = lp
        mDuration = mVideoView.duration

        seekTo(mRedProgressBarPos.toInt().toLong())

        initRangeSeekBarView()
        startShootVideoThumbs(mContext, mSourceUri!!, mThumbsTotalCount, 0, mDuration.toLong())
        playVideo()
    }

    private fun startShootVideoThumbs(context: Context, videoUri: Uri, totalThumbsCount: Int, startPosition: Long, endPosition: Long) {
        val mediaMetadataRetriever = MediaMetadataRetriever()
        mediaMetadataRetriever.setDataSource(context, videoUri)
        val interval = (endPosition - startPosition) / (totalThumbsCount - 1)
        val thumb_width = (deviceWidth - recyclerViewPadding * 2) / VIDEO_MAX_TIME
        val thumb_height = dpToPx(50)
        disposable.add(Observable.create(ObservableOnSubscribe<Bitmap> { e ->
            for (i in 0 until totalThumbsCount) {
                val frameTime = startPosition + interval * i
                var bitmap = mediaMetadataRetriever.getFrameAtTime(frameTime * 1000, MediaMetadataRetriever.OPTION_CLOSEST_SYNC)
                        ?: continue
                bitmap = Bitmap.createScaledBitmap(bitmap, thumb_width, thumb_height, false);
                e.onNext(bitmap)
//                Log.e("onNext", "onNext: fori");
            }
            e.onComplete()
        }).subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableObserver<Bitmap>() {
                    override fun onError(e: Throwable) {

                    }
                    override fun onComplete() {
                        mediaMetadataRetriever.release()
                    }
                    override fun onNext(t: Bitmap) {
//                        Log.e("onNext", "onNext: ");
//                        list.add(t)
                        mVideoThumbAdapter.addBitmaps(t)
                    }
                }))

    }

    private fun initRangeSeekBarView() {
        if (mRangeSeekBarView != null) return
        var rangeWidth = 0
        mLeftProgressPos = 0
        if (mDuration <= MAX_SHOOT_DURATION) {
            mThumbsTotalCount = MAX_COUNT_RANGE
            rangeWidth = deviceWidth - recyclerViewPadding*2
            mRightProgressPos = mDuration.toLong()
        } else {
            mThumbsTotalCount = (mDuration * 1.0f / (MAX_SHOOT_DURATION * 1.0f) * MAX_COUNT_RANGE).toInt()
            rangeWidth = (deviceWidth - recyclerViewPadding*2) / MAX_COUNT_RANGE * mThumbsTotalCount
            mRightProgressPos = MAX_SHOOT_DURATION
        }

        framesRecyclerView.addItemDecoration(SpacesItemDecoration2(recyclerViewPadding, mThumbsTotalCount));

        mRangeSeekBarView = RangeSeekBarView(mContext, mLeftProgressPos, mRightProgressPos)

        mRangeSeekBarView!!.selectedMinValue = mLeftProgressPos;
        mRangeSeekBarView!!.selectedMaxValue = mRightProgressPos;
        mRangeSeekBarView!!.setStartEndTime(mLeftProgressPos, mRightProgressPos);
        mRangeSeekBarView!!.setMinShootTime(MIN_SHOOT_DURATION);
        mRangeSeekBarView!!.isNotifyWhileDragging = true;
        mRangeSeekBarView!!.setOnRangeSeekBarChangeListener(mOnRangeSeekBarChangeListener);

        seekBarLayout.addView(mRangeSeekBarView);

        framesRecyclerView.addOnScrollListener(mOnScrollListener)

        mAverageMsPx = mDuration * 1.0f / rangeWidth * 1.0f;
        averagePxMs = ( (deviceWidth - recyclerViewPadding*2)* 1.0f / (mRightProgressPos - mLeftProgressPos));

    }

    private val mOnRangeSeekBarChangeListener = RangeSeekBarView.OnRangeSeekBarChangeListener { bar, minValue, maxValue, action, isMin, pressedThumb ->
//        Log.d(TAG, "-----minValue----->>>>>>$minValue")
//        Log.d(TAG, "-----maxValue----->>>>>>$maxValue")
        mLeftProgressPos = minValue + scrollPos
        mRedProgressBarPos = mLeftProgressPos
        mRightProgressPos = maxValue + scrollPos
//        Log.d(TAG, "-----mLeftProgressPos----->>>>>>$mLeftProgressPos")
//        Log.d(TAG, "-----mRightProgressPos----->>>>>>$mRightProgressPos")
        when (action) {
            MotionEvent.ACTION_DOWN -> {
                isSeeking = false
                pauseVideo()
            }
            MotionEvent.ACTION_MOVE -> {
                isSeeking = true
                seekTo((if (pressedThumb === RangeSeekBarView.Thumb.MIN) mLeftProgressPos else mRightProgressPos).toInt().toLong())
            }
            MotionEvent.ACTION_UP -> {
                isSeeking = false
                seekTo(mLeftProgressPos.toInt().toLong())
                playVideo()
            }
            else -> {
            }
        }

        mRangeSeekBarView!!.setStartEndTime(mLeftProgressPos, mRightProgressPos)
    }


    /**
     * 水平滑动了多少px
     */
    private fun calcScrollXDistance(): Int {
        val layoutManager = framesRecyclerView.layoutManager as LinearLayoutManager
        val position = layoutManager.findFirstVisibleItemPosition()
        val firstVisibleChildView = layoutManager.findViewByPosition(position) ?: return 0
        val itemWidth = firstVisibleChildView.width
        return position * itemWidth - firstVisibleChildView.left
    }


    private fun seekTo(msec: Long) {
        mVideoView.seekTo(msec.toInt())
//        Log.d(TAG, "seekTo = $msec")
    }

    fun pauseVideo() {
        mRedProgressBarPos = mVideoView.currentPosition.toLong()
        mVideoView.pause()
        pauseRedProgressAnimation()
    }

    fun playVideo() {
        mRedProgressBarPos = mVideoView.currentPosition.toLong()
        mVideoView.start()
        playingRedProgressAnimation()
    }

    private val mAnimationRunnable = Runnable { updateVideoProgress() }

    private fun updateVideoProgress() {
        val currentPosition = mVideoView.currentPosition
//        Log.d(TAG, "updateVideoProgress currentPosition = $currentPosition")
        if (currentPosition >= mRightProgressPos) {
            mRedProgressIcon.visibility = View.GONE
            mRedProgressBarPos = mLeftProgressPos
            playingRedProgressAnimation()
//            onVideoPause()
        } else {
            mAnimationHandler.post(mAnimationRunnable)
        }
    }

    private fun playingAnimation() {
        if (mRedProgressIcon.visibility == View.GONE) {
            mRedProgressIcon.visibility = View.VISIBLE
        }
        val params = mRedProgressIcon.layoutParams as ConstraintLayout.LayoutParams
        val start = (recyclerViewPadding + (mRedProgressBarPos - scrollPos) * averagePxMs).toInt()
        val end = (recyclerViewPadding + (mRightProgressPos - scrollPos) * averagePxMs).toInt()
        mRedProgressAnimator = ValueAnimator.ofInt(start, end).setDuration(mRightProgressPos - scrollPos - (mRedProgressBarPos - scrollPos))
        mRedProgressAnimator!!.interpolator = LinearInterpolator()
        mRedProgressAnimator!!.addUpdateListener(ValueAnimator.AnimatorUpdateListener { animation ->
            params.leftMargin = animation.animatedValue as Int
            mRedProgressIcon.layoutParams = params
//            Log.d(TAG, "----onAnimationUpdate--->>>>>>>$mRedProgressBarPos")
        })
        mRedProgressAnimator!!.start()
    }

    private fun playingRedProgressAnimation() {
        pauseRedProgressAnimation()
        playingAnimation()
        mAnimationHandler.post(mAnimationRunnable)
    }

    private fun pauseRedProgressAnimation() {
        mRedProgressIcon.clearAnimation()
        if (mRedProgressAnimator != null && mRedProgressAnimator!!.isRunning) {
            mAnimationHandler.removeCallbacks(mAnimationRunnable)
            mRedProgressAnimator!!.cancel()
        }
    }


    fun onDestory() {
        pauseRedProgressAnimation()
        mVideoView.stopPlayback()
        disposable.clear()
    }


    fun dpToPx(dp: Int): Int {
        return (dp * this.resources.displayMetrics.density + 0.5f).toInt()
    }

    interface VideoTrimListener{
        fun canCel()
        fun trimVideo(path:String,startTime:Long,endTime:Long)
    }

    var videoTrimListener: VideoTrimListener? = null
}
