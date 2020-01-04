package com.example.yangchaoming.bappdemo.observe;

import android.view.View;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;

public class Cart  {
    private static HashMap<OnStateChangeListener,View> viewListeners=new HashMap<>();
    private static HashMap<Product,ArrayList<OnStateChangeListener>> productListeners=new LinkedHashMap<>();
    public interface OnStateChangeListener{
        void onStateChange(View v);
    }

    public static void setOnstateChangeListener(Product product,View view,OnStateChangeListener mStateListener){
        viewListeners.put(mStateListener,view);
        ArrayList<OnStateChangeListener> productStateChangeListenerList=new ArrayList<>();
        productStateChangeListenerList.add(mStateListener);
        productListeners.put(product,productStateChangeListenerList);
    }

    public static void notifyAllListeners(Product product){
        ArrayList<OnStateChangeListener> onStateChangeListeners = productListeners.get(product);

        for (OnStateChangeListener listener :onStateChangeListeners ) {
            View view = viewListeners.get(listener);
            listener.onStateChange(view);
        }
    }

}
