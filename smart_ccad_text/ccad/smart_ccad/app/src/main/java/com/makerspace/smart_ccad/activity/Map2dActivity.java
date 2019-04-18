package com.makerspace.smart_ccad.activity;

import android.content.pm.PackageManager;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps.model.Poi;
import com.amap.api.maps2d.AMap;
import com.amap.api.maps2d.AMapOptions;
import com.amap.api.maps2d.CameraUpdateFactory;
import com.amap.api.maps2d.MapView;
import com.amap.api.maps2d.model.BitmapDescriptorFactory;
import com.amap.api.maps2d.model.CameraPosition;
import com.amap.api.maps2d.model.LatLng;
import com.amap.api.maps2d.model.Marker;
import com.amap.api.maps2d.model.MarkerOptions;
import com.amap.api.navi.AmapNaviPage;
import com.amap.api.navi.AmapNaviParams;
import com.amap.api.navi.AmapNaviType;
import com.amap.api.navi.INaviInfoCallback;
import com.amap.api.navi.model.AMapNaviLocation;
import com.makerspace.smart_ccad.R;

import java.util.ArrayList;

public class Map2dActivity extends BaseActivity implements AMapLocationListener, AMap.OnMyLocationChangeListener, INaviInfoCallback {

    //    //声明mlocationClient对象
//    public AMapLocationClient mlocationClient;
//    //声明mLocationOption对象
//    public AMapLocationClientOption mLocationOption = null;
    AMapLocationListener mapLocationListener;
    //声明AMapLocationClient类对象
    public AMapLocationClient mLocationClient = null;
    //声明AMapLocationClientOption对象
    public AMapLocationClientOption mLocationOption = null;
    public MapView mMapView;
    AMap aMap = null;

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
        setContentView(R.layout.activity_gao_de_map);

        LatLng centerBJPoint = new LatLng(37.846890, 112.577800);
        AMapOptions mapOptions = new AMapOptions();

        mapOptions.camera(new CameraPosition(centerBJPoint, 10f, 0, 0));
        mMapView = (MapView) findViewById(R.id.map);

        //在activity执行onCreate时执行mMapView.onCreate(savedInstanceState)，创建地图
        mMapView.onCreate(savedInstanceState);
        //检测系统是否打开开启了地理定位权限
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_COARSE_LOCATION}, 1);
        }
        if (aMap == null) {
            aMap = mMapView.getMap();
            aMap.moveCamera(CameraUpdateFactory.newLatLngZoom(centerBJPoint, 12f));
        }

        startLocations();
        DrawMarker();
    }

    /**
     * 覆盖物
     */
    private void DrawMarker() {
        ArrayList<LatLng> lats = new ArrayList<>();
        final ArrayList<String> title = new ArrayList<>();
        final ArrayList<String> desc = new ArrayList<>();
        final MarkerOptions markerOption = new MarkerOptions();
        //山西大医院
        LatLng latLng1 = new LatLng(37.776770, 112.554980);
        //山西省人民医院
        LatLng latLng2 = new LatLng(37.845830, 112.578170);
        //龙城大街
        LatLng latLng3 = new LatLng(37.774260, 112.620180);
        //太原和平公园
        LatLng latLng4 = new LatLng(37.836220, 112.518310);
        //山西省中医院
        LatLng latLng5 = new LatLng(37.850450, 112.566900);
        //二六四医院（公交站）
        LatLng latLng6 = new LatLng(37.856955, 112.578623);
        //山西省第二人民医院
        LatLng latLng7 = new LatLng(37.843530, 112.556560);
        //建南汽车站
        LatLng latLng8 = new LatLng(37.831075, 112.583263);
        LatLng latLng9 = new LatLng(37.836780, 112.545950);//太大图书馆
        LatLng latLng10 = new LatLng(37.925280, 112.510440);//葡萄园
        title.add("山西大医院");
        title.add("山西省人民医院");
        title.add("龙城大街");
        title.add("太原和平公园");
        title.add("山西省中医院");
        title.add("二六四医院（公交站）");
        title.add("山西省第二人民医院");
        title.add("建南汽车站");
        title.add("太大图书馆");
        title.add("葡萄园");

        desc.add("山西大医院:37.776770, 112.554980");
        desc.add("山西省人民医院:37.845830,112.578170");
        desc.add("龙城大街:37.774490,112.569840");
        desc.add("太原和平公园:37.836220,112.518310");

        lats.add(latLng1);
        lats.add(latLng2);
        lats.add(latLng3);
        lats.add(latLng4);
        lats.add(latLng5);
        lats.add(latLng6);
        lats.add(latLng7);
        lats.add(latLng8);
        lats.add(latLng9);
        lats.add(latLng10);

        for (int i = 0; i < lats.size(); i++) {
            markerOption.position(lats.get(i));
//            markerOption.title(title.get(i)).snippet(desc.get(i));
            markerOption.title(title.get(i));
            markerOption.draggable(false);//设置Marker不可拖动
            if (title.get(i).contains("医院")) {
                markerOption.icon(BitmapDescriptorFactory.fromBitmap(BitmapFactory
                        .decodeResource(getResources(), R.mipmap.yiyuan)));
            } else {
                markerOption.icon(BitmapDescriptorFactory.fromBitmap(BitmapFactory
                        .decodeResource(getResources(), R.mipmap.fangkong)));
            }
            aMap.addMarker(markerOption);
        }

        aMap.setOnMarkerClickListener(new AMap.OnMarkerClickListener() {

            @Override
            public boolean onMarkerClick(Marker marker) {
                marker.showInfoWindow();
                return true;
            }
        });

        aMap.setOnInfoWindowClickListener(new AMap.OnInfoWindowClickListener() {
            @Override
            public void onInfoWindowClick(Marker marker) {
                if (marker.isInfoWindowShown()) {
                    marker.hideInfoWindow();//隐藏infowindow窗口
                }

                Poi start = new Poi("三元桥", new com.amap.api.maps.model.LatLng(39.96087, 116.45798), "");
                /**终点传入的是北京站坐标,但是POI的ID "B000A83M61
                 * "对应的是北京西站，所以实际算路以北京西站作为终点**/
                Poi end = new Poi("北京站", new com.amap.api.maps.model.LatLng(39.904556, 116.427231), "B000A83M61");
                AmapNaviPage.getInstance().showRouteActivity(Map2dActivity.this, new AmapNaviParams(start, null, end, AmapNaviType.DRIVER), Map2dActivity.this);

//                Intent intent = new Intent(Map2dActivity.this,ToSafePlaceActivity.class);
//                startActivity(intent);

            }
        });
    }

    /**
     * 定位
     */
    private void startLocations() {

//        mlocationClient = new AMapLocationClient(this);
//        //初始化定位参数
//        mLocationOption = new AMapLocationClientOption();
//        //设置定位监听
//        mlocationClient.setLocationListener(this);
//        //设置定位模式为高精度模式，Battery_Saving为低功耗模式，Device_Sensors是仅设备模式
//        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
//        //设置定位间隔,单位毫秒,默认为2000ms
//        mLocationOption.setInterval(2000);
//        mLocationOption.setNeedAddress(true);
//        //设置是否允许模拟位置,默认为true，允许模拟位置
//        mLocationOption.setMockEnable(true);
//        //设置定位参数
//        mlocationClient.setLocationOption(mLocationOption);
//        启动定位
//        mlocationClient.startLocation();


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
//        mapLocationListener = new AMapLocationListener() {
//            @Override
//            public void onLocationChanged(AMapLocation amapLocation) {
//                if (amapLocation != null) {
//                    if (amapLocation.getErrorCode() == 0) {
//                        List<NaviLatLng> naviLatLng1 = new ArrayList<>();
//                        //可在其中解析amapLocation获取相应内容。
//                        //获取纬度
//                        double latitude = amapLocation.getLatitude();
//                        //获取经度
//                        double longitude = amapLocation.getLongitude();
//                        System.out.println("经纬度"+latitude+ longitude);
//                    } else {
//                        //定位失败时，可通过ErrCode（错误码）信息来确定失败的原因，errInfo是错误信息，详见错误码表。
//                        Log.e("AmapError", "location Error, ErrCode:"
//                                + amapLocation.getErrorCode() + ", errInfo:"
//                                + amapLocation.getErrorInfo());
//                        Toast.makeText(Map2dActivity.this, "定位失败", Toast.LENGTH_SHORT);
//                    }
//                }
//            }
//
//        };
        //设置定位回调监听
        mLocationClient.setLocationListener(this);
        //给定位客户端对象设置定位参数
        mLocationClient.setLocationOption(mLocationOption);
        //启动定位
        mLocationClient.startLocation();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //在activity执行onDestroy时执行mMapView.onDestroy()，销毁地图
        mMapView.onDestroy();
        if (mLocationClient != null) {
            mLocationClient.stopLocation();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        //在activity执行onResume时执行mMapView.onResume ()，重新绘制加载地图
        mMapView.onResume();

    }

    @Override
    protected void onPause() {
        super.onPause();
        //在activity执行onPause时执行mMapView.onPause ()，暂停地图的绘制
        mMapView.onPause();
        if (mLocationClient != null) {
            mLocationClient.stopLocation();
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        //在activity执行onSaveInstanceState时执行mMapView.onSaveInstanceState (outState)，保存地图当前的状态
        mMapView.onSaveInstanceState(outState);
    }

    @Override
    public void onLocationChanged(AMapLocation aMapLocation) {

        if (aMapLocation != null) {
            if (aMapLocation.getErrorCode() == 0) {
                //可在其中解析amapLocation获取相应内容。
                double latitude = aMapLocation.getLatitude();//获取纬度
                double longitude = aMapLocation.getLongitude();//获取经度
                System.out.println("经纬度" + "lat"+latitude +"lon"+ longitude );

            } else {
                //定位失败时，可通过ErrCode（错误码）信息来确定失败的原因，errInfo是错误信息，详见错误码表。
                Log.e("AmapError", "location Error, ErrCode:"
                        + aMapLocation.getErrorCode() + ", errInfo:"
                        + aMapLocation.getErrorInfo());
            }
        }
    }

    @Override
    public void onMyLocationChange(Location location) {

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