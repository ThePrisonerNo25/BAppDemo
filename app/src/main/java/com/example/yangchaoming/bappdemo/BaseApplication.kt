package com.example.yangchaoming.bappdemo

import android.app.Application
import android.database.sqlite.SQLiteDatabase
import com.example.yangchaoming.bappdemo.greendao.gen.DaoMaster
import com.example.yangchaoming.bappdemo.greendao.gen.DaoSession


open class BaseApplication : Application() {
    var instances: BaseApplication? = null

    lateinit var mHelper : DaoMaster.OpenHelper
    lateinit var db :SQLiteDatabase
    lateinit var daoSession: DaoSession
    lateinit var mDaoMaster: DaoMaster
    override fun onCreate() {
        super.onCreate()
        instances = this
        initDataBase()
    }

    private fun initDataBase(){
        // 通过 DaoMaster 的内部类 DevOpenHelper，你可以得到一个便利的 SQLiteOpenHelper 对象。
        // 可能你已经注意到了，你并不需要去编写「CREATE TABLE」这样的 SQL 语句，因为 greenDAO 已经帮你做了。
        // 注意：默认的 DaoMaster.DevOpenHelper 会在数据库升级时，删除所有的表，意味着这将导致数据的丢失。
        // 所以，在正式的项目中，你还应该做一层封装，来实现数据库的安全升级。
        mHelper = DaoMaster.DevOpenHelper(this, "demo_db", null)
        db = mHelper.writableDatabase
        mDaoMaster = DaoMaster(db)
        daoSession = mDaoMaster.newSession()
    }
}