package com.example.yangchaoming.bappdemo.miscellaneous;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.yangchaoming.bappdemo.R;

import java.util.ArrayList;

public  class BaseFragment extends Fragment implements GroupListener {
    private String title;
    private RecyclerView mRecyclerView;
    private  ArrayList<PersonBean> dataSet;
    public static BaseFragment getInstance(String name){
        BaseFragment baseFragment=new BaseFragment();
        Bundle bundle = new Bundle();
        bundle.putString("title",name);
        baseFragment.setArguments(bundle);
        return baseFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle arguments = getArguments();
         title=arguments.getString("title");
    }



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_base, container, false);
        mRecyclerView=rootView.findViewById(R.id.recyclerView);
        LinearLayoutManager layoutManager=new LinearLayoutManager(getContext());
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        mRecyclerView.setLayoutManager(layoutManager);

        dataSet=new ArrayList<>();
        for (int i = 0; i < 50; i++) {
            PersonBean bean=null;
            if(i<10){
                 bean=new PersonBean("yang#"+i,"A");
            }else if(i<20){
                bean=new PersonBean("zhang#"+i,"B");
            }else if(i<30){
                bean=new PersonBean("wang#"+i,"C");
            }else if(i<40){
                bean=new PersonBean("li#"+i,"D");
            }else if(i<50){
                bean=new PersonBean("sun#"+i,"E");
            }
            dataSet.add(bean);
        }



        MyAdapter adapter=new MyAdapter(dataSet);
        mRecyclerView.setAdapter(adapter);
        mRecyclerView.addItemDecoration(new SectionDecoration(this));
        return rootView;

    }

    @Override
    public String getGroupName(int position) {

        return dataSet.get(position).getType();
    }

    public void moveToLetter(String letter) {
        int index=-1;
        for (int i = 0; i < dataSet.size(); i++) {
            PersonBean personBean = dataSet.get(i);
            if(TextUtils.equals(personBean.type,letter)){
                index=i;
                break;
            }
        }
        if(index!=-1) {
            LinearLayoutManager layoutManager = (LinearLayoutManager) mRecyclerView.getLayoutManager();
            layoutManager.scrollToPositionWithOffset(index,0);
        }
    }
}
