package com.example.wangc.myapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.PopupWindow;

/**
 * Created by wangc on 2018/8/1
 * E-MAIL:274281610@QQ.COM
 */
public class PopTest extends PopupWindow {

    public PopTest(Context context){

        setContentView(LayoutInflater.from(context).inflate(R.layout.layout_pop,null));

    }
}
