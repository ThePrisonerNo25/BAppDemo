package com.example.yangchaoming.bappdemo.miscellaneous;

import android.os.Bundle;
import androidx.annotation.Nullable;
import android.text.Editable;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.yangchaoming.bappdemo.R;

import java.util.ArrayList;
import java.util.List;

public class TestActivity extends AppCompatActivity {
    private static final String TAG = "TestActivity";
    private EditText ed;
    private EditText ed1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        initView();
    }

    private void initView() {
        final LineWrapContainer lwc = findViewById(R.id.lwc);
        lwc.setSingleSelected(true);
        Button add = findViewById(R.id.add);
        Button clear = findViewById(R.id.clear);

        final List<ItemBean> list=new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            ItemBean bean=new ItemBean("item #_"+i,false);
//            if(i%3==1)bean.setSelected(true);
            list.add(bean);
        }

        list.get(1).setTag("this is long string");
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (int i = 0; i <list.size() ; i++) {
                    list.get(i).setSelected(false);
                }
                lwc.setDatas(list);
            }
        });

        lwc.setItemClickListener(new LineWrapContainer.IitemclickListener() {
            @Override
            public void itemClicked(View view, int position) {
                Log.e(TAG, "itemClicked: "+position );
//                view.setSelected(true);
                boolean isSelected = list.get(position).isSelected;
                list.get(position).setSelected(isSelected?false:true);
                lwc.resetChildAtIndex(position);

            }
        });

        final EditText et = findViewById(R.id.et);
        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                List<String> list1=new ArrayList<>();
//                for (ItemBean bean:list) {
//                    if(bean.isSelected()){
//                        list1.add(bean.getTag());
//                    }
//                }
//
//                Log.e(TAG, "onClick: clear=="+list1.toString());

                Editable text = et.getText();
                if(text==null){
                    Log.e(TAG, "onClick: clear==Editable is null");
                }
                Log.e(TAG, "onClick: "+text);
                String s = text.toString().trim();
                if(TextUtils.isEmpty(s)){
                    Log.e(TAG, "onClick: clear==String is null");
                }

            }
        });





    }
}
