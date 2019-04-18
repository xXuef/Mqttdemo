package com.makerspace.smart_ccad.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.makerspace.smart_ccad.R;

public abstract class BaseActivity extends AppCompatActivity {

    private Toolbar mToolbar;
    private LinearLayout mDectorView = null;//根布局
    private FrameLayout mContentView = null;//activity内容布局

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (mDectorView == null) {
            initDectorView();
        }
        setSupportActionBar(mToolbar);
        if (getSupportActionBar() != null) {
//            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//            getSupportActionBar().setDisplayShowTitleEnabled(false);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeButtonEnabled(true);
        }
        //如果已经创建就先把内容清空，再添加
        if (mContentView != null) {
            mContentView.removeAllViews();
        }
        initCustomView(savedInstanceState);
    }

    /**
     *  重写setContentView方法，使mContentView成为子activity界面的父布局
     * @param layoutResID
     */
    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        getLayoutInflater().inflate(layoutResID,mContentView);
        super.setContentView(mDectorView);
    }
    /**
     * 菜单监听
     *
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == android.R.id.home) {
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    /**
     * 初始化根布局
     */
    private void initDectorView() {
        mDectorView = new LinearLayout(this);
        mDectorView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT));
        mDectorView.setOrientation(LinearLayout.VERTICAL);
        initToolBar();
        mContentView = new FrameLayout(this);
        mContentView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT));
        mDectorView.addView(mContentView);
    }
    /**
     * 初始化toolbar
     */
    private void initToolBar() {
        View view = getLayoutInflater().inflate(R.layout.toolbar,mDectorView);
        mToolbar = (Toolbar) view.findViewById(R.id.toolbar);
        mToolbar.setTitle("");

        mToolbar.setNavigationIcon(R.drawable.sel_back);

        mToolbar.setTitleTextColor(Color.WHITE);
//        mToolbar.setNavigationIcon(android.R.drawable.sym_def_app_icon);
//         setSupportActionBar(mToolbar);

        setCustomToolbar(mToolbar);
    }

    /**
     * 让子activity自己定义toolbar
     * @param mToolbar
     */
    public abstract void setCustomToolbar(Toolbar mToolbar);
    protected abstract void initCustomView(Bundle savedInstanceState);

}
