package com.makerspace.smart_ccad.activity;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Poi;
import com.amap.api.navi.AmapNaviPage;
import com.amap.api.navi.AmapNaviParams;
import com.amap.api.navi.AmapNaviType;
import com.amap.api.navi.INaviInfoCallback;
import com.amap.api.navi.model.AMapNaviLocation;
import com.makerspace.smart_ccad.R;

public class ToSafePlaceActivity extends BaseActivity implements INaviInfoCallback {

    @Override
    public void setCustomToolbar(Toolbar mToolbar) {
        /**
         *获取toolbar里的textview
         */
        for (int i = 0; i < mToolbar.getChildCount(); i++) {
            View view = mToolbar.getChildAt(i);
            if (view instanceof TextView) {
                ((TextView) view).setText("避难场所");
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void initCustomView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_to_safe_place);

        Poi start = new Poi("三元桥", new LatLng(39.96087, 116.45798), "");
        /**终点传入的是北京站坐标,但是POI的ID "B000A83M61
         * "对应的是北京西站，所以实际算路以北京西站作为终点**/
        Poi end = new Poi("北京站", new LatLng(39.904556, 116.427231), "B000A83M61");
        AmapNaviPage.getInstance().showRouteActivity(this, new AmapNaviParams(start, null, end, AmapNaviType.DRIVER), this);

    }

    /**
     * 导航初始化失败时的回调函数
     **/
    @Override
    public void onInitNaviFailure() {

    }

    /**
     * 导航播报信息回调函数。
     **/
    @Override
    public void onGetNavigationText(String s) {

    }

    /**
     * 当GPS位置有更新时的回调函数。
     *
     * @param aMapNaviLocation 当前自车坐标位置
     **/
    @Override
    public void onLocationChange(AMapNaviLocation aMapNaviLocation) {

    }

    /**
     * 到达目的地后回调函数。
     **/
    @Override
    public void onArriveDestination(boolean b) {

    }

    /**
     * 启动导航后的回调函数
     **/
    @Override
    public void onStartNavi(int i) {

    }

    /**
     * 算路成功回调
     */
    @Override
    public void onCalculateRouteSuccess(int[] ints) {

    }

    /**
     * 步行或者驾车路径规划失败后的回调函数
     **/
    @Override
    public void onCalculateRouteFailure(int i) {

    }

    /**
     * 停止语音回调，收到此回调后用户可以停止播放语音
     **/
    @Override
    public void onStopSpeaking() {

    }

    @Override
    public void onReCalculateRoute(int i) {

    }

    @Override
    public void onExitPage(int i) {

    }
}
