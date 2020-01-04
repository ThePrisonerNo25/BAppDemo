package com.example.yangchaoming.bappdemo.miscellaneous;

import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.example.yangchaoming.bappdemo.R;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements SideBar.OnTouchingLetterChangedListener, View.OnClickListener, PopupWindow.OnDismissListener {
    private static final String TAG = "MainActivity";
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private SideBar sideBar;
    private ArrayList<Fragment> fragments;
    private TextView options;
    private RelativeLayout mask;
    private ImageView ivBack;
    private RelativeLayout head;
    private OptionsPopupWindow window;

    private int fragmentIndex=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        intView();

        setUp();
    }

    private void setUp() {
        fragments = new ArrayList<>();
        BaseFragment instance1 = BaseFragment.getInstance("fragment-1");
        BaseFragment instance2 = BaseFragment.getInstance("fragment-2");
        BaseFragment instance3 = BaseFragment.getInstance("fragment-3");
        fragments.add(instance1);
        fragments.add(instance2);
        fragments.add(instance3);



        ArrayList<String> titles= new ArrayList<>();
        titles.add("所有会员");
        titles.add("正式学员");
        titles.add("意向学员");

        viewPager.setAdapter(new MyFragmentPagerAdaper(getSupportFragmentManager(),this, fragments,titles));
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.addOnTabSelectedListener(new TabLayout.BaseOnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                fragmentIndex=tab.getPosition();

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

    }

    private void intView() {
        tabLayout=findViewById(R.id.tab_layout);
        viewPager=findViewById(R.id.view_pager);
        sideBar=findViewById(R.id.sidebar);
        options=findViewById(R.id.tv_options);
        ivBack = findViewById(R.id.iv_back);
        head = findViewById(R.id.rl_head);
        mask = findViewById(R.id.mask);
        window=new OptionsPopupWindow(this);


        sideBar.setOnTouchingLetterChangedListener(this);
        window.setOnDismissListener(this);
        options.setOnClickListener(this);
        ivBack.setOnClickListener(this);
    }




    //字母表变化
    @Override
    public void onTouchingLetterChanged(String s) {

    }

    //字母表选中
    @Override
    public void onTouchingUpLetter(String s) {
        Log.e(TAG, "onTouchingUpLetter: "+s );
        Toast toast = new Toast(this);
        View view=LayoutInflater.from(this).inflate(R.layout.letter_toast,null);
        TextView name = view.findViewById(R.id.tv_letter);
        name.setText(s);
        //设置Toast要显示的位置，水平居中并在底部，X轴偏移0个单位，Y轴偏移70个单位，
        toast.setGravity(Gravity.CENTER,0,0);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setView(view);
        toast.show();
//        fragments.get(0)
        BaseFragment fragment = (BaseFragment) fragments.get(fragmentIndex);
        fragment.moveToLetter(s);

    }


    private  int dpTopx(int dp){
        return (int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,dp,this.getResources().getDisplayMetrics());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_options:
                showOptions();
                break;
            case R.id.iv_back:
                Toast.makeText(MainActivity.this,"back or not back",Toast.LENGTH_SHORT).show();
                break;

        }
    }

    //显示筛选
    private void showOptions() {
        if(!window.isShowing()){
            showMask();
            window.showWindow(head);
        }else {
            window.dismissWindow();
        }
    }

    //popupwindow 消失
    @Override
    public void onDismiss() {
        hideMask();
    }

    public void showMask(){
        mask.setVisibility(View.VISIBLE);
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.mask_show);
        mask.startAnimation(animation);

    }

    public void hideMask(){
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.mask_hide);
        mask.startAnimation(animation);
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                mask.setVisibility(View.GONE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }
}
