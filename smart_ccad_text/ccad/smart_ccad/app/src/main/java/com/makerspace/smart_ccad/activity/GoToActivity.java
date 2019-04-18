package com.makerspace.smart_ccad.activity;


import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.navi.AMapNavi;
import com.amap.api.navi.AMapNaviListener;
import com.amap.api.navi.AMapNaviView;
import com.amap.api.navi.AMapNaviViewListener;
import com.amap.api.navi.enums.NaviType;
import com.amap.api.navi.model.AMapLaneInfo;
import com.amap.api.navi.model.AMapModelCross;
import com.amap.api.navi.model.AMapNaviCameraInfo;
import com.amap.api.navi.model.AMapNaviCross;
import com.amap.api.navi.model.AMapNaviInfo;
import com.amap.api.navi.model.AMapNaviLocation;
import com.amap.api.navi.model.AMapNaviTrafficFacilityInfo;
import com.amap.api.navi.model.AMapServiceAreaInfo;
import com.amap.api.navi.model.AimLessModeCongestionInfo;
import com.amap.api.navi.model.AimLessModeStat;
import com.amap.api.navi.model.NaviInfo;
import com.amap.api.navi.model.NaviLatLng;
import com.autonavi.tbt.TrafficFacilityInfo;
import com.gyf.barlibrary.ImmersionBar;
import com.makerspace.smart_ccad.R;

import java.util.ArrayList;
import java.util.List;

public class GoToActivity extends Activity implements AMapNaviViewListener, AMapNaviListener {
    AMapNavi mAMapNavi;
    AMapNaviView mAMapNaviView;

    AMapLocationListener mapLocationListener;
    //声明AMapLocationClient类对象
    public AMapLocationClient mLocationClient = null;
    //声明AMapLocationClientOption对象
    public AMapLocationClientOption mLocationOption = null;
    //网络获取的经纬度
    public double lant;
    public double lont;
    public static double longitude;
    public static double latitude;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_go_to);

        lant = getIntent().getDoubleExtra("lant", 0);
        lont = getIntent().getDoubleExtra("lont", 0);


        mAMapNaviView = (AMapNaviView) findViewById(R.id.navi_view);
        mAMapNaviView.onCreate(savedInstanceState);

        //获取 AMapNaviView 实例setAMapNaviViewListener
        mAMapNaviView.setAMapNaviViewListener(this);

        //获取AMapNavi实例
        mAMapNavi = AMapNavi.getInstance(this);
        //添加监听回调，用于处理算路成功
        mAMapNavi.addAMapNaviListener(this);


    }


    @Override
    protected void onResume() {
        super.onResume();
        if (mAMapNaviView!=null) {
            mAMapNaviView.onResume();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mAMapNaviView!=null) {
            mAMapNaviView.onPause();
        }
        if (mLocationClient != null) {
            mLocationClient.stopLocation();
        }
    }

    @Override
    protected synchronized void onDestroy() {
        super.onDestroy();
        ImmersionBar.with(this).destroy();
        if (mAMapNaviView!=null) {
            mAMapNaviView.onDestroy();
        }
        if (mLocationClient!=null) {
            mLocationClient.onDestroy();//销毁定位客户端，同时销毁本地定位服务。
        }
    }

    @Override
    public void onNaviSetting() {
    }

    @Override
    public void onNaviCancel() {
    }

    @Override
    public boolean onNaviBackClick() {
        return false;
    }

    @Override
    public void onNaviMapMode(int i) {
    }

    @Override
    public void onNaviTurnClick() {
    }

    @Override
    public void onNextRoadClick() {
    }

    @Override
    public void onScanViewButtonClick() {
    }

    @Override
    public void onLockMap(boolean b) {
    }

    @Override
    public void onNaviViewLoaded() {
    }

    @Override
    public void onInitNaviFailure() {
    }

    //导航初始化成功时的回调函数
    @Override
    public void onInitNaviSuccess() {
        //初始化AMapLocationClientOption对象
        mLocationOption = new AMapLocationClientOption();
        //设置定位模式为AMapLocationMode.Hight_Accuracy，高精度模式。
        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        mLocationOption.setInterval(3000);
        //设置是否返回地址信息（默认返回地址信息）
        mLocationOption.setNeedAddress(true);
        //设置是否允许模拟位置,默认为true，允许模拟位置
        mLocationOption.setMockEnable(true);
        //初始化定位
        mLocationClient = new AMapLocationClient(getApplicationContext());
        mapLocationListener = new AMapLocationListener() {
            @Override
            public void onLocationChanged(AMapLocation amapLocation) {
                if (amapLocation != null) {
                    if (amapLocation.getErrorCode() == 0) {
                        List<NaviLatLng> naviLatLng1 = new ArrayList<>();
                        //可在其中解析amapLocation获取相应内容。
                        //获取纬度
                        latitude = amapLocation.getLatitude();
                        //获取经度
                        longitude = amapLocation.getLongitude();
                        naviLatLng1.add(new NaviLatLng(latitude, longitude));
                        System.out.println("获取的经纬度" + longitude + "" + latitude + "");
                        int strategy = 0;
                        try {
                            strategy = mAMapNavi.strategyConvert(true, false, false, false, false);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                        List<NaviLatLng> naviLatLngs = new ArrayList<>();
                        NaviLatLng naviLatLng = new NaviLatLng(39.92, 116.53);
                        naviLatLngs.add(naviLatLng);
                        mAMapNavi.calculateDriveRoute(naviLatLng1, naviLatLngs, null, strategy);
                    } else {
                        //定位失败时，可通过ErrCode（错误码）信息来确定失败的原因，errInfo是错误信息，详见错误码表。
                        Log.e("AmapError", "location Error, ErrCode:"
                                + amapLocation.getErrorCode() + ", errInfo:"
                                + amapLocation.getErrorInfo());
                        Toast.makeText(GoToActivity.this, "定位失败", Toast.LENGTH_SHORT);
                    }
                }
            }

        };
        //设置定位回调监听
        mLocationClient.setLocationListener(mapLocationListener);
        //给定位客户端对象设置定位参数
        mLocationClient.setLocationOption(mLocationOption);
        //启动定位
        mLocationClient.startLocation();

        Log.e("latitude", latitude + longitude + "");
//        mAMapNavi.calculateWalkRoute( new NaviLatLng(latitude, longitude),new NaviLatLng(39.92, 116.53));
    }

    @Override
    public void onStartNavi(int i) {

    }

    @Override
    public void onTrafficStatusUpdate() {

    }

    @Override
    public void onLocationChange(AMapNaviLocation aMapNaviLocation) {

    }

    @Override
    public void onGetNavigationText(int i, String s) {

    }

    @Override
    public void onGetNavigationText(String s) {

    }

    @Override
    public void onEndEmulatorNavi() {

    }

    @Override
    public void onArriveDestination() {

    }

    @Override
    public void onCalculateRouteFailure(int i) {

    }

    @Override
    public void onReCalculateRouteForYaw() {

    }

    @Override
    public void onReCalculateRouteForTrafficJam() {

    }

    @Override
    public void onArrivedWayPoint(int i) {

    }

    @Override
    public void onGpsOpenStatus(boolean b) {

    }

    @Override
    public void onNaviInfoUpdate(NaviInfo naviInfo) {

    }

    @Override
    public void onNaviInfoUpdated(AMapNaviInfo aMapNaviInfo) {

    }

    @Override
    public void updateCameraInfo(AMapNaviCameraInfo[] aMapNaviCameraInfos) {

    }

    @Override
    public void updateIntervalCameraInfo(AMapNaviCameraInfo aMapNaviCameraInfo, AMapNaviCameraInfo aMapNaviCameraInfo1, int i) {

    }

    @Override
    public void onServiceAreaUpdate(AMapServiceAreaInfo[] aMapServiceAreaInfos) {

    }

    @Override
    public void showCross(AMapNaviCross aMapNaviCross) {

    }

    @Override
    public void hideCross() {

    }

    @Override
    public void showModeCross(AMapModelCross aMapModelCross) {

    }

    @Override
    public void hideModeCross() {

    }

    @Override
    public void showLaneInfo(AMapLaneInfo[] aMapLaneInfos, byte[] bytes, byte[] bytes1) {

    }

    @Override
    public void showLaneInfo(AMapLaneInfo aMapLaneInfo) {

    }

    @Override
    public void hideLaneInfo() {

    }

    //在路线规划成功 onCalculateRouteSuccess() 的回调函数中，开启实时导航。
    @Override
    public void onCalculateRouteSuccess(int[] ints) {

        if(mAMapNavi!=null) {
            mAMapNavi.startNavi(NaviType.GPS);
        }

    }

    @Override
    public void notifyParallelRoad(int i) {

    }

    @Override
    public void OnUpdateTrafficFacility(AMapNaviTrafficFacilityInfo aMapNaviTrafficFacilityInfo) {

    }

    @Override
    public void OnUpdateTrafficFacility(AMapNaviTrafficFacilityInfo[] aMapNaviTrafficFacilityInfos) {

    }

    @Override
    public void OnUpdateTrafficFacility(TrafficFacilityInfo trafficFacilityInfo) {

    }

    @Override
    public void updateAimlessModeStatistics(AimLessModeStat aimLessModeStat) {

    }

    @Override
    public void updateAimlessModeCongestionInfo(AimLessModeCongestionInfo aimLessModeCongestionInfo) {

    }

    @Override
    public void onPlayRing(int i) {

    }


    public void getNowLocation() {
        //初始化AMapLocationClientOption对象
        mLocationOption = new AMapLocationClientOption();
        //设置定位模式为AMapLocationMode.Hight_Accuracy，高精度模式。
        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        mLocationOption.setInterval(3000);
        //设置是否返回地址信息（默认返回地址信息）
        mLocationOption.setNeedAddress(true);
        //设置是否允许模拟位置,默认为true，允许模拟位置
        mLocationOption.setMockEnable(true);
        //初始化定位
        mLocationClient = new AMapLocationClient(getApplicationContext());
        mapLocationListener = new AMapLocationListener() {
            @Override
            public void onLocationChanged(AMapLocation amapLocation) {
                if (amapLocation != null) {
                    if (amapLocation.getErrorCode() == 0) {
                        //可在其中解析amapLocation获取相应内容。
                        //获取纬度
                        latitude = amapLocation.getLatitude();
                        //获取经度
                        longitude = amapLocation.getLongitude();
                        System.out.println("获取的经纬度" + longitude + "" + latitude + "");

                    } else {
                        //定位失败时，可通过ErrCode（错误码）信息来确定失败的原因，errInfo是错误信息，详见错误码表。
                        Log.e("AmapError", "location Error, ErrCode:"
                                + amapLocation.getErrorCode() + ", errInfo:"
                                + amapLocation.getErrorInfo());
                        Toast.makeText(GoToActivity.this, "定位失败", Toast.LENGTH_SHORT);
                    }
                }
            }

        };
        //设置定位回调监听
        mLocationClient.setLocationListener(mapLocationListener);
        //给定位客户端对象设置定位参数
        mLocationClient.setLocationOption(mLocationOption);
        //启动定位
        mLocationClient.startLocation();
    }

}
