package com.example.yangchaoming.bappdemo.exoplayer

import android.annotation.SuppressLint
import android.content.pm.ActivityInfo
import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.yangchaoming.bappdemo.R
import com.google.android.exoplayer2.ExoPlayerFactory
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.source.MediaSource
import com.google.android.exoplayer2.source.ProgressiveMediaSource
import com.google.android.exoplayer2.ui.PlayerView
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory
import com.google.android.exoplayer2.util.Util
import kotlinx.android.synthetic.main.activity_exoplayer.*
import kotlinx.android.synthetic.main.exo_playback_control_view.*


class ExoPlayerActivity : AppCompatActivity(){

    private  var  player: SimpleExoPlayer? =null
    lateinit var playerView: PlayerView
    private var fullscreen : Boolean = false

//    private lateinit var mediaSource: MediaSource

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_exoplayer)
        playerView = findViewById(R.id.video_view)

        iv_full_screen.setOnClickListener(View.OnClickListener {
            if (fullscreen) {
//                fullscreenButton.setImageDrawable(ContextCompat.getDrawable(this@MainActivity, R.drawable.ic_fullscreen_open))
                window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_VISIBLE
                if (supportActionBar != null) {
                    supportActionBar!!.show()
                }
                requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
                val params = playerView.layoutParams as ConstraintLayout.LayoutParams
                params.width = ViewGroup.LayoutParams.MATCH_PARENT
                params.height = (210 * applicationContext.resources.displayMetrics.density).toInt()
                playerView.layoutParams = params
                fullscreen = false
            } else {
//                fullscreenButton.setImageDrawable(ContextCompat.getDrawable(this@MainActivity, R.drawable.ic_fullscreen_close))
                window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_FULLSCREEN
                        or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                        or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION)
                if (supportActionBar != null) {
                    supportActionBar!!.hide()
                }
                requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
                val params = playerView.layoutParams as ConstraintLayout.LayoutParams
                params.width = ViewGroup.LayoutParams.MATCH_PARENT
                params.height = ViewGroup.LayoutParams.MATCH_PARENT
                playerView.layoutParams = params
                fullscreen = true
            }
        })

//        mediaSource = initializeSource()
//        initializePlayer(mediaSource!!)
        initializePlayer()
        btn_player.setOnClickListener {
//            if(playerView.isControllerVisible){
//                playerView.hideController()
//            }else{
//                playerView.showController()
//            }
//            player?.playWhenReady = player?.isPlaying != true
//            initializePlayer()
        }

        player?.addListener(object : Player.EventListener{

        })

    }

    var mHandler = Handler()
    var shouldListeningProgress = true
    var updateProgressAction  = Runnable {
        updateProgress()
    }

    private fun updateProgress(){
        val position = if (player == null) 0 else player?.currentPosition ?: 0
//        Log.e("updateProgress", "updateProgress: $position");
//        if(shouldListeningProgress){
//            if(position<20000){
//                mHandler.postDelayed(updateProgressAction,1000)
//            }else{
//                Log.e("updateProgress", "请购买会员");
//                releasePlayer()
//            }
//        }

    }


    private fun buildMediaSource(uri: Uri): MediaSource {
        val dataSourceFactory = DefaultDataSourceFactory(this, "exoplayer-codelab")
        return ProgressiveMediaSource.Factory(dataSourceFactory)
                .createMediaSource(uri)
    }

    public override fun onStart() {
        super.onStart()
        if (Util.SDK_INT >= 24) {
//            initializePlayer(mediaSource)
            initializePlayer()
        }
    }

    public override fun onResume() {
        super.onResume()
//        hideSystemUi()
        if (Util.SDK_INT < 24 || player == null) {
//            initializePlayer(mediaSource)
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

    private var playWhenReady :Boolean?= true;
    private var currentWindow :Int? = 0;
    private var playbackPosition : Long?= 0L;

//
//    private fun initializeSource():MediaSource{
//        val mp3Url ="https://storage.googleapis.com/exoplayer-test-media-0/play.mp3"
//        val mp4Url ="http://gslb.miaopai.com/stream/oxX3t3Vm5XPHKUeTS-zbXA__.mp4"
//        val uri = Uri.parse(mp4Url)
//        return buildMediaSource(uri)
//    }

    private fun initializePlayer(mediaSource:MediaSource){
        player = ExoPlayerFactory.newSimpleInstance(this);
        playerView.setPlayer(player);

        player?.playWhenReady = playWhenReady == false;
        player?.seekTo(currentWindow ?: 0, playbackPosition?: 0);
        player?.prepare(mediaSource, false, false);

    }

    private fun initializePlayer(){
        player = ExoPlayerFactory.newSimpleInstance(this);
        playerView.setPlayer(player);
//        val mp4Url ="http://gslb.miaopai.com/stream/oxX3t3Vm5XPHKUeTS-zbXA__.mp4"
        val mp4Url ="https://ejoyst-cp-pro.oss-cn-shenzhen.aliyuncs.com/mc/%E7%88%B1%E5%89%AA%E8%BE%91-%E6%88%91%E7%9A%84%E8%A7%86%E9%A2%912.mp4"
        val uri = Uri.parse(mp4Url)
        val buildMediaSource = buildMediaSource(uri)
        player?.playWhenReady = playWhenReady == false;
        player?.seekTo(currentWindow ?: 0, playbackPosition?: 0);
        player?.prepare(buildMediaSource, false, false);
        shouldListeningProgress = true
        updateProgress()
    }

    private fun releasePlayer() {
        if (player != null) {
            playWhenReady = player?.playWhenReady;
            playbackPosition = player?.currentPosition;
            currentWindow = player?.currentWindowIndex;
            player!!.release()
            player = null
        }
        shouldListeningProgress =false
    }



    @SuppressLint("InlinedApi")
    private fun hideSystemUi() {
        playerView.systemUiVisibility = (View.SYSTEM_UI_FLAG_LOW_PROFILE
                or View.SYSTEM_UI_FLAG_FULLSCREEN
                or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION)
    }

//     fun getApplicationName() :String{
//       var  packageManager: PackageManager? = null;
//        var  applicationInfo : ApplicationInfo? = null;
//        try {
//            packageManager = applicationContext.packageManager;
//            applicationInfo = packageManager.getApplicationInfo(packageName, 0);
//        } catch (e:PackageManager.NameNotFoundException ) {
//            applicationInfo = null;
//        }
//         return packageManager?.getApplicationLabel(applicationInfo) ?: "yijianApplication";
//    }
}