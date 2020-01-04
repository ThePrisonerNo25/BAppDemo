package com.example.yangchaoming.bappdemo.video

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.database.Cursor
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.Lifecycle
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.yangchaoming.bappdemo.R
import com.example.yangchaoming.bappdemo.video.help.SimpleCallback
import com.example.yangchaoming.bappdemo.video.help.VideoCursorLoader
import com.example.yangchaoming.bappdemo.video.help.VideoLoadManager
import com.example.yangchaoming.bappdemo.video.videoTrim.VideoTrimActivity
import com.tbruyelle.rxpermissions2.RxPermissions


class VideoActivity : AppCompatActivity(), VideoContract.View {
    override fun getMLifecycle(): Lifecycle =lifecycle

    lateinit var adapter: VideoAdapter
    lateinit var recyclerView: RecyclerView
    lateinit var presenter: VideoPresenter

    companion object {
        const val MY_PERMISSIONS_REQUEST_READ_CONTACTS = 101
    }

    private var cursor: Cursor? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_video)
        initialData()
        recyclerView = findViewById(R.id.recycler_view)
        recyclerView.layoutManager = GridLayoutManager(this, 4)
        adapter = VideoAdapter(this,presenter.videoSubList)
        recyclerView.adapter = adapter



        val mVideoLoadManager = VideoLoadManager()
        mVideoLoadManager.setLoader(VideoCursorLoader())

        val rxPermissions =  RxPermissions(this);
        rxPermissions.request(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .subscribe { granted ->
                    if (granted) { // Always true pre-M
                        mVideoLoadManager.load(this, object : SimpleCallback {
                            override fun failure(o: Any?) = Unit

                            override fun success(obj: Any) {
                                cursor = obj as Cursor
                                val videoList = presenter.mapVideoSubsidiaryData(cursor!!)
                                adapter.resetData(videoList)
                            }
                        })
                    } else {
                        // Oups permission denied
                        Log.e("success", "false:");
                    }
                }

        adapter.listListener = object : VideoAdapter.VideoListListener{
            override fun checkItem(v: View, pos: Int) {
                val bean = presenter.videoSubList[pos]


            }

            override fun clickItem(v: View, pos: Int) {
                val bean = presenter.videoSubList[pos]
                val intent = Intent(this@VideoActivity, VideoTrimActivity::class.java)
                val bundle = Bundle()
                bundle.putString(VideoTrimActivity.VIDEO_PATH_KEY,bean.dataPath)
                intent.putExtras(bundle)
                startActivity(intent)
            }

        }
    }

    private fun initialData(){
       presenter= VideoPresenter(this,this)
    }



}