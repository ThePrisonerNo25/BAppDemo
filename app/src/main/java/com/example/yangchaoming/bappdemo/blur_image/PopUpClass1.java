package com.example.yangchaoming.bappdemo.blur_image;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.cardview.widget.CardView;

import com.example.yangchaoming.bappdemo.R;

public class PopUpClass1 {

    //PopupWindow display method

    public void showPopupWindow(final View view,int width,int height) {


        //Create a View object yourself through inflater
        LayoutInflater inflater = (LayoutInflater) view.getContext().getSystemService(view.getContext().LAYOUT_INFLATER_SERVICE);
        View popupView = inflater.inflate(R.layout.pop_up_layout1, null);

        //Specify the length and width through constants
//        int width = LinearLayout.LayoutParams.WRAP_CONTENT;
//        int height = LinearLayout.LayoutParams.WRAP_CONTENT;

        //Make Inactive Items Outside Of PopupWindow
        boolean focusable = true;

        //Create a window with our parameters
        final PopupWindow popupWindow = new PopupWindow(popupView, width, height, focusable);

        //Set the location of the window on the screen
//        popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);
        popupWindow.showAsDropDown(view);

        //Initialize the elements of our window, install the handler

        CardView cardView = popupView.findViewById(R.id.card_view);
        cardView.setBackgroundResource(R.drawable.card_view_bg);

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

}
