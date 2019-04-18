package com.makerspace.smart_ccad.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebView;

import com.gyf.barlibrary.ImmersionBar;
import com.makerspace.smart_ccad.R;

public class ViewPagerWebActivity extends AppCompatActivity {


    @Override
    protected synchronized void onDestroy() {
        super.onDestroy();
        ImmersionBar.with(this).destroy();
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_view_pager_web);
        ImmersionBar.with(this).fitsSystemWindows(true).init();


        String url = getIntent().getStringExtra("url");
        WebView viewById = (WebView) findViewById(R.id.webview);

        viewById.loadUrl(url);
    }


}
