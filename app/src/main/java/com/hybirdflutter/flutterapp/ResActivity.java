package com.hybirdflutter.flutterapp;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.FrameLayout;

import com.hybirdflutter.commincation.ChannelConst;

import io.flutter.facade.Flutter;
import io.flutter.view.FlutterView;

/**
 * author：yuxinfeng on 2019/4/15 14:44
 * email：yuxinfeng@corp.netease.com
 */
public class ResActivity extends AppCompatActivity {

    private FlutterView view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();
        onCreateFlutterView();
    }

    private void onCreateFlutterView() {
        // 获取 Flutter view
        view = Flutter.createView(ResActivity.this, getLifecycle(), ChannelConst.RES_ROUTE);
        // 创建 LayoutParams
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);
        // 添加 flutterView 到当前 Activity
        addContentView(view, layoutParams);
    }
}
