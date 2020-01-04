package com.example.yangchaoming.bappdemo.miscellaneous;

import android.content.Context;
import android.graphics.Rect;
import android.os.AsyncTask;
import androidx.annotation.NonNull;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.PopupWindow;
import android.widget.TextView;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.yangchaoming.bappdemo.R;

import java.util.ArrayList;

public class OptionsPopupWindow  extends PopupWindow implements View.OnClickListener {
    private static final String TAG = "OptionsPopupWindow";
    private final ArrayList<OptionsBean> datas;
    private final OptionAdapter optionAdapter;
    private View popupView;
    private Context context;


    public OptionsPopupWindow(Context context) {
        this.context=context;
        popupView = LayoutInflater.from(context).inflate(R.layout.options_popupwindow, null);
        TextView negative = popupView.findViewById(R.id.tv_negative);
        TextView positive = popupView.findViewById(R.id.tv_positive);
        RecyclerView recyclerView=popupView.findViewById(R.id.recyclerView);

        this.setContentView(popupView);
        this.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        this.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);

        GridLayoutManager layoutManager=new GridLayoutManager(context,2);
        recyclerView.setLayoutManager(layoutManager);

        datas = new ArrayList<OptionsBean>();
        datas.add(new OptionsBean("test#1",false));
        datas.add(new OptionsBean("test#2",true));

        optionAdapter = new OptionAdapter(datas);
        final int space = Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 10, context.getResources().getDisplayMetrics()));
        recyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
                super.getItemOffsets(outRect, view, parent, state);
                int itemCount = parent.getChildAdapterPosition(view);
                int i=itemCount%2;
                if(i==0){
                    outRect.set(space,0,space/2,space);
                }else {
                    outRect.set(space/2,0,space,space);
                }
                if(itemCount==0||itemCount==1){
                    outRect.top=space;
                }
            }
        });

        recyclerView.setAdapter(optionAdapter);


        negative.setOnClickListener(this);
        positive.setOnClickListener(this);




        optionAdapter.setListener(new OptionAdapter.IoptionsItemClicked() {
            @Override
            public void onItemCliked(View view, int position) {
                Log.e(TAG, "onItemCliked: position"+position );
                OptionsBean optionsBean = datas.get(position);
                optionsBean.setSelected(optionsBean.isSelected()?false:true);
//                optionAdapter.notifyDataSetChanged();
                optionAdapter.notifyItemChanged(position);
            }
        });
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_negative:
//                OptionsPopupWindow.this.dismiss();
                MyAsyncTask myAsyncTask = new MyAsyncTask();
                myAsyncTask.execute(datas);
                break;
            case R.id.tv_positive:
                dismissWindow();
                break;
        }
    }


    class  MyAsyncTask extends AsyncTask<ArrayList<OptionsBean>,Void,ArrayList<OptionsBean>>{

        @Override
        protected ArrayList<OptionsBean> doInBackground(ArrayList<OptionsBean>... arrayLists) {
//            Log.e(TAG, "doInBackground: "+arrayLists[0].toString() );
            ArrayList<OptionsBean> arrayList = arrayLists[0];
            for (int i = 0; i <10 ; i++) {
                int j=i%3;
               boolean isSelected=j==0?true:false;
                arrayList.add(new OptionsBean("this item#"+i,isSelected));
            }
            return arrayList;
        }

        @Override
        protected void onPostExecute(ArrayList<OptionsBean> optionsBeans) {
//            super.onPostExecute(optionsBeans);
            optionAdapter.updata(optionsBeans);

        }
    }

    public void showWindow(View view){
        OptionsPopupWindow.this.showAsDropDown(view,0,0);
        Animation animation = AnimationUtils.loadAnimation(context, R.anim.options_popup_enter);
        popupView.startAnimation(animation);
    }

    public void dismissWindow(){
        Animation animation = AnimationUtils.loadAnimation(context, R.anim.options_popup_exit);
        popupView.startAnimation(animation);
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                OptionsPopupWindow.this.dismiss();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }
}
