package com.example.yangchaoming.bappdemo.popupwindow;

import android.app.Activity;
import android.content.Context;
import android.graphics.Rect;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.content.ContextCompat;
import androidx.core.view.ViewCompat;

import com.example.yangchaoming.bappdemo.R;

public class PopUpClass {
    private static final String TAG = "PopUpClass";
    //PopupWindow display method

    public void showPopupWindow(final View view,Activity activity) {


        //Create a View object yourself through inflater
        LayoutInflater inflater = (LayoutInflater) view.getContext().getSystemService(view.getContext().LAYOUT_INFLATER_SERVICE);
        View popupView = inflater.inflate(R.layout.pop_up_layout, null);

        //Specify the length and width through constants
        int width = LinearLayout.LayoutParams.MATCH_PARENT;
        int height = LinearLayout.LayoutParams.MATCH_PARENT;

        //Make Inactive Items Outside Of PopupWindow
        boolean focusable = true;

        //Create a window with our parameters
        final PopupWindow popupWindow = new PopupWindow(popupView, width, height, focusable);

        //Set the location of the window on the screen
        popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);

        //Initialize the elements of our window, install the handler

        TextView test2 = popupView.findViewById(R.id.titleText);
        test2.setText(R.string.textTitle);

        Button buttonEdit = popupView.findViewById(R.id.messageButton);
        buttonEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //As an example, display the message
                Toast.makeText(view.getContext(), "Wow, popup action button", Toast.LENGTH_SHORT).show();

            }
        });

        LinearLayout llContent = popupView.findViewById(R.id.ll_content);

        View viewTop = popupView.findViewById(R.id.view_top);
        View viewBottom = popupView.findViewById(R.id.view_bottom);

        llContent.measure(0,0);
//        viewBottom.measure(0,0);
        //llContent 的参数
//        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) llContent.getLayoutParams();
        int ll_measuredWidth = llContent.getMeasuredWidth();
        int ll_measuredHeight = llContent.getMeasuredHeight() ;


        //view 的参数
        int view_measuredHeight = view.getMeasuredHeight();
        int view_measuredWidth = view.getMeasuredWidth();
        int[] location = new int[2];
        view.getLocationInWindow(location);
        int view_left = location[0];
        int view_top = location[1] ;
        int view_bottom = view_top + view_measuredHeight;
        int view_right = view_left + view_measuredWidth;


        //屏幕的宽高
        int screenWidth = getScreenWidth(view.getContext());
        int screenHeight = getScreenHeight(view.getContext());


        //状态栏高度
        int statusBarHeight = getStatusBarHeight(activity);

        Log.e(TAG, "showPopupWindow:view_bottom= "+view_bottom +",--- screenHeight="+screenHeight +",---view_top="+view_top);

        //计算llContent应该显示的位置(正常情况)
        int result_left = view_left + (view_measuredWidth-ll_measuredWidth)/2;
        int result_right = view_right - (view_measuredWidth-ll_measuredWidth)/2;
        int result_top = view_bottom;
        int result_bottom =view_bottom + ll_measuredHeight;



        //llContent 应该移动的位置
        int del_x = result_left;
        int del_y = result_top ;//因为初始化是，少计算了StatusBar

        //左边界越界
        if(result_left<0){
            result_left = 0;
            result_right = ll_measuredWidth;

            del_x=result_left;
        }

        //右边界越界
        if(result_right>screenWidth){
            result_right = screenWidth;
            result_left =  screenWidth - ll_measuredWidth;

            del_x = result_left;
        }

//        //上边界越界
//        if(result_top<0){
//            result_top = view_bottom;
//            result_bottom = view_bottom +ll_measuredHeight;
//
//            del_y = result_top ;
//        }

        //下边界越界
        if(result_bottom>screenHeight){
            viewTop.setVisibility(View.GONE);
            viewBottom.setVisibility(View.VISIBLE);
            result_bottom = view_top;
            result_top = result_bottom - ll_measuredHeight;
            del_y = result_top;
        }

        //1068  1134

        llContent.setX(del_x);

        llContent.setY(del_y - statusBarHeight);

        //Handler for clicking on the inactive zone of the window

        popupView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                //Close the window when clicked
                popupWindow.dismiss();
                return true;
            }
        });
    }

    private static DisplayMetrics getMetrics(Context context) {
        return context.getResources().getDisplayMetrics();
    }

    /**
     * Get Screen Width
     */
    public static int getScreenWidth(Context context) {
        return getMetrics(context).widthPixels;
    }

    /**
     * Get Screen Height
     */
    public static int getScreenHeight(Context context) {
        return getMetrics(context).heightPixels;
    }


    public int getStatusBarHeight(Activity activity){
        Rect rectangle = new Rect();
        Window window = activity.getWindow();
        window.getDecorView().getWindowVisibleDisplayFrame(rectangle);
        int statusBarHeight = rectangle.top;
//        int contentViewTop =
//                window.findViewById(Window.ID_ANDROID_CONTENT).getTop();
//        int titleBarHeight= contentViewTop - statusBarHeight;
//        Log.e(TAG, "getStatusBarHeight: contentViewTop="+contentViewTop +" statusBarHeight="+statusBarHeight );
        return statusBarHeight;
    }
}
