package com.example.wangc.myapplication;

import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Layout;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.PopupWindow;

public class PopWindowActivity extends AppCompatActivity implements View.OnClickListener{
    Button button1;
    Button button2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pop_window);

//        StatusBarUtil.setImgTransparent(this);//状态栏透明

         button1 = findViewById(R.id.btn1);
         button2 = findViewById(R.id.btn2);

        button1.setOnClickListener(this);
        button2.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn1:

                int result = 0;
                int resourceId = this.getResources().getIdentifier("status_bar_height", "dimen", "android");
                if (resourceId > 0) {
                    result = this.getResources().getDimensionPixelSize(resourceId);
                }
                Log.e("+++","status:"+result);

                Display display = getWindowManager().getDefaultDisplay();
                int height = display.getHeight();
                Log.e("+++","button"+height);
                PopupWindow popupWindow = new PopupWindow(
                ViewGroup.LayoutParams.MATCH_PARENT, height-Utils.dip2px(this,40+Utils.px2dip(this,result)));
                popupWindow.setContentView(LayoutInflater.from(this).inflate(R.layout.layout_pop,null));
                popupWindow.setBackgroundDrawable(new ColorDrawable(0x00000000));
                popupWindow.setOutsideTouchable(true);

                popupWindow.showAsDropDown(button1);

                break;
            case R.id.btn2:
                break;
        }
    }
}
