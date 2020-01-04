package com.example.yangchaoming.bappdemo.miscellaneous;

import android.os.Bundle;
import androidx.annotation.Nullable;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.yangchaoming.bappdemo.R;

public class TestActivity1 extends AppCompatActivity {
    private static final String TAG = "TestActivity";
    private EditText ed;
    private EditText ed1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test1);
        initView();
    }

    private void initView() {

        Button btn1 = findViewById(R.id.notify);
        Button back = findViewById(R.id.back);

        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    finish();
            }
        });
    }
}
