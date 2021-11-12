package com.example.yangchaoming.bappdemo.splash

import android.app.DownloadManager
import android.content.IntentFilter
import android.database.Cursor
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.yangchaoming.bappdemo.BaseApplication
import com.example.yangchaoming.bappdemo.R
import com.example.yangchaoming.bappdemo.greendao.gen.DaoSession
import com.example.yangchaoming.bappdemo.splash.bean.SplashAdvertising
import java.io.File


class HomeActivity: AppCompatActivity() {
    val TAG = HomeActivity::javaClass.name
    lateinit var daoSession: DaoSession

    lateinit var myReceiver:CheckDownloadComplete

    var adsMap = HashMap<Long, SplashAdvertising>()
    lateinit var downloadManager:DownloadManager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.acitity_home)

        daoSession = (application as BaseApplication).daoSession

        downloadManager = this.getSystemService(AppCompatActivity.DOWNLOAD_SERVICE) as DownloadManager
        mockNetRequest()

        myReceiver = CheckDownloadComplete(object : CheckDownloadComplete.OnDownloadListener {
            override fun onDownloadSuccess(download_id: Long, filePath: String) {
                Log.e(TAG, "onDownloadSuccess: $download_id ----- filePath=$filePath")
                if(adsMap.containsKey(download_id)){
                    val splashAdvertising = adsMap[download_id] ?: return
                    val fileName = getFileName(filePath)
                    splashAdvertising.sourcePath = fileName
                    daoSession.splashAdvertisingDao.update(splashAdvertising)
                    adsMap.remove(download_id)
                }

            }

            override fun onDownloadFail(download_id: Long) {
                if(adsMap.containsKey(download_id)){
                    daoSession.splashAdvertisingDao.delete(adsMap[download_id])
                    Log.e(TAG, "onDownloadFail:${adsMap[download_id]} ")
                    adsMap.remove(download_id)
                }
            }
        })
        val filter = IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE)
        registerReceiver(myReceiver, filter);
    }


    private fun mockNetRequest(){
        val localList = daoSession.splashAdvertisingDao.queryBuilder().build().list()
        //      String id; //广告id ,
        //    String imgPopupUrl; //弹窗广告图路径（相对路径） ,
        //    Integer uploadFileTypeFile; //0 图片  1 视频
        //    String startTime;//展示开始时间（yyyy-MM-dd HH:mm:ss） ,
        //    String endTime;//展示结束时间（yyyy-MM-dd HH:mm:ss） ,
        //    String showStartTime;// 展示开始时间点（HH:mm） ,
        //    String showEndTime;// 展示结束时间点（HH:mm） ,
        //    Integer maxEveryday; //每人每天最多展示次数（弹框广告） ,
        //    Integer maxEveryone; //每个用户最多展示次数（弹框广告） ,
        //    Integer gotoType; //跳转类型（0 不跳转 1 内部跳转 2 跳H5 3外部小程序 4微盟H5） ,
        //    String  link; //  广告链接路径 ,
        //
        //    String lastShowTime; //最近一次展示时间 （yyyy-MM-dd HH:mm:ss）
        //    int oneDayShowedTimes;//一天已经展示了多少次
        //    int totalShowedTimes;//总共展示了多少次
        //    String sourcePath;//文件路径

        val  a = SplashAdvertising("id1", "https://ejoyst-cp-dev-test.oss-cn-shenzhen.aliyuncs.com/oms/1280%2B720%282%29.mp4",
                1,
                "2021-09-02 08:00:00", "2021-09-13 20:00:00",
                "08:00", "23:00",
                10, 30,
                0, "link",
                null, 0, 0, null,0)

        val  b = SplashAdvertising("id2", "https://ss2.bdstatic.com/70cFvnSh_Q1YnxGkpoWK1HF6hhy/it/u=1249633517,2092238595&fm=26&gp=0.jpg",
                0,
                "2021-09-02 08:00:00", "2021-09-13 20:00:00",
                "08:00", "23:00",
                4, 8,
                0, "link",
                null, 0, 0, null,0)

        val  c = SplashAdvertising("id3", "https://ejoyst-cp-dev-test.oss-cn-shenzhen.aliyuncs.com/oms/%5B%E9%98%B3%E5%85%89%E7%94%B5%E5%BD%B1-www.ygdy8.com%5D%E6%B5%B7%E8%B4%BC%E7%8E%8B-911.mp4",
                1,
                "2021-09-02 08:00:00", "2021-09-14 18:00:00",
                "08:00", "18:00",
                10, 30,
                0, "link",
                null, 0, 0, null,0)

        val  d = SplashAdvertising("id2", "https://img.xjh.me/mobile/bg/nature/63792823_p0.jpg",
                0,
                "2021-09-02 08:00:00", "2021-09-13 20:00:00",
                "08:00", "22:00",
                4, 8,
                0, "link",
                null, 0, 0, null,0)

        val  e = SplashAdvertising("id4", "https://img.xjh.me/mobile/bg/nature/63792823_p0.jpg",
                0,
                "2021-09-02 08:00:00", "2021-09-13 20:00:00",
                "08:00", "22:00",
                114, 18,
                0, "link",
                null, 0, 0, null,0)


//        val list = arrayListOf<SplashAdvertising>(a,b,c)
//        val list = arrayListOf<SplashAdvertising>(a, b,e)
//        val list = arrayListOf<SplashAdvertising>(a,d,e)
//        val list = arrayListOf<SplashAdvertising>(a,d,c,e)
//        val list = arrayListOf<SplashAdvertising>(c)
        val list = arrayListOf<SplashAdvertising>(e)
        if(list.isNullOrEmpty())return

        if(localList.isNullOrEmpty()){//本地数据没有
            Log.e(TAG, "mockNetRequest: 本地数据没有")
            daoSession.splashAdvertisingDao.insertInTx(list)
        }else{//本地有数据
            //获取需要展示的广告id集合
            val ids = list.map { it.id }.toSet()
            //需要删除的数据
            val needRemovedItems = localList.filter { it.id !in ids  }
            Log.e(TAG, "mockNetRequest: needRemovedItems = ${needRemovedItems.toString()}")
            if(!needRemovedItems.isNullOrEmpty()){
                val deleteIds = needRemovedItems.map { it.id }
                daoSession.splashAdvertisingDao.deleteByKeyInTx(deleteIds)
            }

            //需要添加的数据
            val localIds = localList.map { it.id }.toSet()
            val needAddNewItems = list.filter { it.id !in localIds }
            Log.e(TAG, "mockNetRequest: needAddItems = ${needAddNewItems.toString()}")

            daoSession.splashAdvertisingDao.insertInTx(needAddNewItems)

           val oldItems = list.filter { it.id in localIds }
            val changedItem  = arrayListOf<SplashAdvertising>()
            for (item in oldItems){
                val localOldItem = localList.find { it.id == item.id } ?: continue
                if(localOldItem.uploadFileTypeFile!=item.uploadFileTypeFile || localOldItem.imgPopupUrl != item.imgPopupUrl){
                    localOldItem.imgPopupUrl = item.imgPopupUrl
                    localOldItem.lastShowTime = null
                    localOldItem.oneDayShowedTimes = 0
                    localOldItem.totalShowedTimes = 0
                    localOldItem.sourcePath = ""
                }
                localOldItem.startTime = item.startTime
                localOldItem.endTime = item.endTime
                localOldItem.showStartTime = item.showStartTime
                localOldItem.showEndTime = item.showEndTime
                localOldItem.maxEveryday = item.maxEveryday
                localOldItem.maxEveryone = item.maxEveryone
                localOldItem.gotoType = item.gotoType
                localOldItem.link = item.link
                changedItem.add(localOldItem)

            }
            daoSession.splashAdvertisingDao.updateInTx(changedItem)

        }

        //下载相应的广告资源
        val newLocalList = daoSession.splashAdvertisingDao.queryBuilder().build().list()
        Log.e(TAG, "mockNetRequest: newLocalList = ${newLocalList.toString()}")
        //
        val needDownLoadSource = newLocalList.filter { it.sourcePath.isNullOrEmpty() }
        downLoadSource(needDownLoadSource)
    }

    private fun downLoadSource(needDownLoadSource: List<SplashAdvertising>) {
        for (item in needDownLoadSource){
          if(!item.imgPopupUrl.isNullOrEmpty()) {
              val fileName = getFileName(item.imgPopupUrl)
              val downLoadId = downLoad(item.imgPopupUrl, fileName,item)
              if(downLoadId != 0L){
                  adsMap[downLoadId] = item
                  item.sourceDownLoadId = downLoadId
              }
          }
        }
        daoSession.splashAdvertisingDao.updateInTx(needDownLoadSource)
    }

    private fun getFileName(imgPopupUrl: String): String {
        val decodeString = Uri.decode(imgPopupUrl)
        val split = decodeString.split("/".toRegex())
        return if (split.isNullOrEmpty()){
            ""
        }else{
            split[split.size - 1]
        }
    }

    private fun downLoad(url: String?, fileName: String, item: SplashAdvertising):Long{
        Log.e(TAG, "downLoad: $fileName")
//        return

        if(fileName.isNullOrEmpty())return 0
        val file = File(getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS), fileName)
        if (file.exists()) {
            if(item.sourceDownLoadId!=0L){
                val cursor: Cursor = downloadManager.query(DownloadManager.Query().setFilterById(item.sourceDownLoadId))
                if (cursor.moveToFirst()) {
                    val status: Int = cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_STATUS))
                    when (status) {
                        DownloadManager.STATUS_SUCCESSFUL ->{
                            Log.e(TAG, "downLoad: DownloadManager.STATUS_SUCCESSFUL " )
                            val fileName = getFileName(item.imgPopupUrl)
                            item.sourcePath = fileName
                            daoSession.splashAdvertisingDao.update(item)
                        }
                        DownloadManager.STATUS_FAILED ->{
                            Log.e(TAG, "downLoad: DownloadManager.STATUS_FAILED " )
                            daoSession.splashAdvertisingDao.delete(item)
                            if(adsMap.containsKey(item.sourceDownLoadId)) adsMap.remove(item.sourceDownLoadId)
                            file.delete()
                        }
                        DownloadManager.STATUS_RUNNING -> {
                            Log.e(TAG, "downLoad: DownloadManager.STATUS_RUNNING " )
                            adsMap[item.sourceDownLoadId] = item
                        }
                    }
                }
                cursor.close()
                return 0
            }else{
                file.delete()
            }
        }

        val request = DownloadManager.Request(Uri.parse(url))
        request.setDestinationInExternalFilesDir(this, Environment.DIRECTORY_DOWNLOADS, fileName)
        val fromFile = Uri.fromFile(file)
        Log.e(TAG, "downLoad: fromFile= $fromFile");
        request.setDestinationUri(fromFile)
        request.setTitle("怦怦健身教练")
        request.setDescription("正在下载文件：${fileName}")
        val downloadId =  downloadManager.enqueue(request)
        return downloadId
    }



    override fun onDestroy() {
//        Log.e(TAG, "onDestroy: ")
        super.onDestroy()
        unregisterReceiver(myReceiver)

    }
}