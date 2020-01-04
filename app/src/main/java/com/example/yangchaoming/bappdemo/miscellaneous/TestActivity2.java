package com.example.yangchaoming.bappdemo.miscellaneous;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.yangchaoming.bappdemo.R;

public class TestActivity2 extends AppCompatActivity {
    private static final String TAG = "TestActivity";
    private EditText ed;
    private EditText ed1;

    private String i="1000";
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test2);
        initView();
    }

    private void initView() {

        Button btn1 = findViewById(R.id.btn_1);
        TextView tvdes = findViewById(R.id.tv_des);

        tvdes.setText(i);

        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(TestActivity2.this,TestActivity1.class);
                startActivity(intent);
            }
        });
//        DatePicker
//        NumberPicker


    }
}
