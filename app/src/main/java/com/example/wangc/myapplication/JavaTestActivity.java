package com.example.wangc.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class JavaTestActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_java_test);

        final List lists = new ArrayList();
        for (int i = 0; i < 6; i++) {
            lists.add(i);
        }

        for (int i = 0; i < lists.size(); i++) {
            final int finalI1 = i;
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(1*1000);
                        Log.e("+++","上传："+lists.get(finalI1));
                        lists.remove(finalI1);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }).start();

        }

        if (lists.size()==0){
            Log.e("+++","提交订单");
        }
    }
}
