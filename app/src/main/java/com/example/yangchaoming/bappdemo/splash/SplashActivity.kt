package com.example.yangchaoming.bappdemo.splash

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.os.Handler
import android.transition.Fade
import android.view.View
import android.widget.Button
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.app.ActivityOptionsCompat
import com.bumptech.glide.Glide
import com.example.yangchaoming.bappdemo.BaseApplication
import com.example.yangchaoming.bappdemo.R
import com.example.yangchaoming.bappdemo.greendao.gen.DaoSession
import com.example.yangchaoming.bappdemo.splash.bean.SplashAdvertising
import com.google.android.exoplayer2.*
import com.google.android.exoplayer2.source.MediaSource
import com.google.android.exoplayer2.source.ProgressiveMediaSource
import com.google.android.exoplayer2.ui.AspectRatioFrameLayout
import com.google.android.exoplayer2.ui.PlayerView
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory
import com.google.android.exoplayer2.util.Util
import kotlinx.android.synthetic.main.fragment_camera2_basic.view.*
import java.io.File
import java.text.SimpleDateFormat
import java.util.*

class SplashActivity : AppCompatActivity(){

    private  var  player: SimpleExoPlayer? =null
    lateinit var playerView: PlayerView
    lateinit var imageView: ImageView
    lateinit var btnSkip: Button
    lateinit var btnVolume: Button

    private var playWhenReady :Boolean?= true;
    private var currentWindow :Int? = 0;
    private var playbackPosition : Long?= 0L;

    private var showAds :SplashAdvertising? = null  //是否显示广告
    private var adsIsVideo = false //广告是否为视频
    lateinit var daoSession: DaoSession

    private var currentvolume :Float = 0f //用户音量

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        playerView = findViewById(R.id.video)
        imageView = findViewById(R.id.img)
        btnSkip = findViewById(R.id.btn_skip)
        btnVolume = findViewById(R.id.btn_volume)

        btnSkip.setOnClickListener {
            handler.removeCallbacks(runnable)
            toHome()
        }

        btnVolume.setOnClickListener {
            if(player?.volume == 0f){
                player?.volume = currentvolume
            }else{
                player?.volume = 0f
            }

        }

        daoSession = (application as BaseApplication).daoSession
        //是否需要显示广告
//
        showAdertising()
    }

    private fun showAdertising() {
        showAds = needShowAdvertising()
        if(showAds == null){
            toHome()
            return
        }
        adsIsVideo = showAds?.uploadFileTypeFile == 1 //uploadFileTypeFile; //0 图片  1 视频
        if (!adsIsVideo) {
            playerView.visibility = View.INVISIBLE
            val file = File(getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS), showAds!!.sourcePath?:"")
            if (file.exists()) {
                Glide.with(this).load(file).into(imageView)
                updateAdsInDB()

                handler.postDelayed(runnable, 3000)
            } else {
                daoSession.delete(showAds)
                toHome()
            }
        }else{
            imageView.visibility = View.INVISIBLE
            playerView.visibility = View.VISIBLE
        }
    }

    private val handler = Handler()
    private val  runnable = Runnable {
        toHome()
    }

    private fun toHome() {
        finish()
        val intent = Intent(this, HomeActivity::class.java)
        startActivity(intent)
    }

    override fun finish() {
        super.finish()
        overridePendingTransition(0,R.anim.anim_fade_out)
    }

    private fun needShowAdvertising():SplashAdvertising?{
        var needShowAds : SplashAdvertising?= null
        val list = daoSession.splashAdvertisingDao.queryBuilder().build().list()
        if(list.isNullOrEmpty()){
            return needShowAds
        }else {
            val currentTimeMillis = System.currentTimeMillis()
            val showedList = arrayListOf<SplashAdvertising>()
            val notShowedList = arrayListOf<SplashAdvertising>()
            for (i in 0 until list.size) {
                val ad = list[i]
                //是否在有效期
                val isValid = inValidTime(currentTimeMillis, ad)
                if (isValid) {
                    //今天是否展示过
                    val showed = adHasShowToday(ad, currentTimeMillis)
                    if (showed) { //今天是否展示过（展示过的放一边）
                        showedList.add(ad)
                    } else {//今天未展示的（展示次数较少的，否则随机）
                        notShowedList.add(ad)
                    }
                } else {
                    continue
                }
            }

            if (notShowedList.isNotEmpty()) {
                val list = notShowedList.filter { it.totalShowedTimes < it.maxEveryone&&it.oneDayShowedTimes< it.maxEveryday}
                if(!list.isNullOrEmpty()){
                    val sortedBy = list.sortedBy { it.oneDayShowedTimes }
                    needShowAds =  sortedBy[0]
                }
            }

            if(needShowAds == null && showedList.isNotEmpty()){
                val list = showedList.filter { it.totalShowedTimes < it.maxEveryone&&it.oneDayShowedTimes< it.maxEveryday }
                if(!list.isNullOrEmpty()){
                    val sortedBy = list.sortedBy { it.oneDayShowedTimes }
                    needShowAds =  sortedBy[0]
                }
            }
        }
        return needShowAds
    }

    private fun adHasShowToday(ad: SplashAdvertising?, currentTimeMillis: Long):Boolean {
        if(ad == null)return false
        return if(ad.lastShowTime.isNullOrEmpty()){//（yyyy-MM-dd HH:mm:ss）
            false
        }else{
            val sdf2 = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
            val lastShowTimeDate = sdf2.parse(ad.lastShowTime)

            val sdf3 = SimpleDateFormat("yyyy-MM-dd")
            val currentDate =  Date(currentTimeMillis)
            val currentDateString = sdf3.format(currentDate)
            val todayDate = sdf3.parse(currentDateString)
            lastShowTimeDate.time >= todayDate.time
        }
    }


    private fun inValidTime(currentTimeMillis: Long,ad: SplashAdvertising?):Boolean {
        if(ad == null)return false
        // String startTime;//展示开始时间（yyyy-MM-dd HH:mm:ss） ,
        //    String endTime;//展示结束时间（yyyy-MM-dd HH:mm:ss） ,
        //    String showStartTime;// 展示开始时间点（HH:mm） ,
        //    String showEndTime;// 展示结束时间点（HH:mm） ,

        val startTime =  ad.startTime
        val endTime =  ad.endTime
        val showStartTime =  ad.showStartTime
        val showEndTime =  ad.showEndTime

        val sdf0 = SimpleDateFormat("yyyy-MM-dd")

        val currentDate = Date(currentTimeMillis)
        val currentDateString = sdf0.format(currentDate)

        val showStartTimeDateString  = "$currentDateString $showStartTime" //yyyy-MM-dd HH:mm
        val showEndTimeDateString  = "$currentDateString $showEndTime" //yyyy-MM-dd HH:mm

        val sdf1 = SimpleDateFormat("yyyy-MM-dd HH:mm")
        val showStartTimeDate = sdf1.parse(showStartTimeDateString)
        val showEndTimeDate = sdf1.parse(showEndTimeDateString)

        if(currentTimeMillis >= showStartTimeDate.time && currentTimeMillis<= showEndTimeDate.time){//属于展示时间段(时刻)
            val sdf2 = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
            val startTimeDate = sdf2.parse(startTime)
            val endTimeDate = sdf2.parse(endTime)
            if(currentTimeMillis >= startTimeDate.time && currentTimeMillis <= endTimeDate.time){//属于展示时间段(日期)
                return true
            }
        }
        return false
    }


    private fun initializePlayer(){
        if(showAds == null)return
        if(!adsIsVideo)return
        val sourcePath = showAds!!.sourcePath
        if(sourcePath.isNullOrEmpty()){
            daoSession.splashAdvertisingDao.delete(showAds)
            toHome()
            return
        }
        val file = File(getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS),sourcePath)
        if(!file.exists()){
            daoSession.splashAdvertisingDao.delete(showAds)
            toHome()
            return
        }

        player = ExoPlayerFactory.newSimpleInstance(this);
        playerView.player = player;
//        val mp4Url ="https://ejoyst-cp-pro.oss-cn-shenzhen.aliyuncs.com/mc/%E7%88%B1%E5%89%AA%E8%BE%91-%E6%88%91%E7%9A%84%E8%A7%86%E9%A2%912.mp4"
//        val uri = Uri.parse(mp4Url)
        val buildMediaSource = buildMediaSource(Uri.fromFile(file))
        player?.playWhenReady = playWhenReady == false;
        player?.seekTo(currentWindow ?: 0, playbackPosition ?: 0);
        player?.prepare(buildMediaSource, false, false);
        player?.playWhenReady = true;
        //获取当前音量:int currentvolume = player.getVolume(); 静音: player.setVolume(0f); 取消静音: player.setVolume(currentVolume);
        currentvolume = player?.volume ?:0f
        player?.volume = 0f

        //视频铺满全屏
//        playerView.resizeMode = AspectRatioFrameLayout.RESIZE_MODE_FILL;
//        player?.videoScalingMode = C.VIDEO_SCALING_MODE_SCALE_TO_FIT_WITH_CROPPING;



        player?.removeListener(playerListener)
        player?.addListener(playerListener)

    }

    val playerListener = object : Player.EventListener {
        override fun onPlayerStateChanged(playWhenReady: Boolean, playbackState: Int) {
//            super.onPlayerStateChanged(playWhenReady, playbackState)
            when(playbackState){
                    Player.STATE_READY -> {
                        btnVolume.visibility = View.VISIBLE
                        updateAdsInDB()
                    }
                 Player.STATE_ENDED -> {
                     btnVolume.visibility = View.GONE
                     toHome()
                 }
            }
        }

        override fun onPlayerError(error: ExoPlaybackException?) {
//            super.onPlayerError(error)
            daoSession.delete(showAds)
            toHome()
        }
    }

    private fun updateAdsInDB() {
        if (showAds == null) return

        val sdf3 = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
        val currentDate = Date(System.currentTimeMillis())
        val currentDateString = sdf3.format(currentDate)

        val substring = currentDateString.substring(0, 10)
        if(showAds!!.lastShowTime.isNullOrEmpty() || showAds!!.lastShowTime!!.substring(0, 10) != substring){
            showAds!!.oneDayShowedTimes = 1
        }else{
            showAds!!.oneDayShowedTimes += 1
        }

        showAds!!.lastShowTime = currentDateString
        showAds!!.totalShowedTimes += 1
        daoSession.splashAdvertisingDao.update(showAds)
    }

    private fun buildMediaSource(uri: Uri): MediaSource {
        val dataSourceFactory = DefaultDataSourceFactory(this, "yijian")
        return ProgressiveMediaSource.Factory(dataSourceFactory)
                .createMediaSource(uri)
    }

    private fun releasePlayer() {
        if (player != null) {
            playWhenReady = player?.playWhenReady;
            playbackPosition = player?.currentPosition;
            currentWindow = player?.currentWindowIndex;
            player!!.release()
            player = null
        }
    }

    public override fun onStart() {
        super.onStart()
        if (Util.SDK_INT >= 24) {
            initializePlayer()
        }
    }

    public override fun onResume() {
        super.onResume()
//        hideSystemUi()
        if (Util.SDK_INT < 24 || player == null) {
            initializePlayer()

        }
    }

    public override fun onPause() {
        super.onPause()
        if (Util.SDK_INT < 24) {
            releasePlayer()
        }
    }

    public override fun onStop() {
        super.onStop()
        if (Util.SDK_INT >= 24) {
            releasePlayer()
        }
    }




}