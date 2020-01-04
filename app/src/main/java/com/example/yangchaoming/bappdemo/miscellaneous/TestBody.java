package com.example.yangchaoming.bappdemo.miscellaneous;

import android.database.DataSetObserver;

import java.util.Observable;

public class TestBody extends Observable {


    public void setMessage(Object msg) {
        // 告诉观察者对象，所观察的内容发生改变
//        observerable.setChanged();
//        // 刷新信息，或者信息传递
//        observerable.notifyObservers(msg);
    }

}
