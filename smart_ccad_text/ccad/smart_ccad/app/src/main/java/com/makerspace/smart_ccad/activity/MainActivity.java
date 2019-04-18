package com.makerspace.smart_ccad.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.amap.api.maps2d.model.LatLng;
import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.daimajia.slider.library.Tricks.ViewPagerEx;
import com.gyf.barlibrary.ImmersionBar;
import com.makerspace.smart_ccad.R;

import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.HashMap;

public class MainActivity extends BaseActivity implements ViewPagerEx.OnPageChangeListener, View.OnLongClickListener, View.OnClickListener {

    public SliderLayout sliderLayout;
    private Button push_messag;

    private View AirDefenseAlarm;
    private AlertDialog alert = null;

    private View Sos;
  private  boolean flag;

    @Override
    public void setCustomToolbar(Toolbar mToolbar) {
        /**
         *获取toolbar里的textview
         */
        for (int i = 0; i < mToolbar.getChildCount(); i++) {
            View view = mToolbar.getChildAt(i);
            if (view instanceof TextView) {
                ((TextView) view).setText("智慧人防");
            }
            if (view instanceof ImageView){
                ((ImageView) view).setVisibility(View.INVISIBLE);
            }
        }
    }

    @Override
    protected void initCustomView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_main);
//        EventBus.getDefault().register(this);
        //设置点击事件
        RelativeLayout one = findViewById(R.id.one);
        RelativeLayout two = findViewById(R.id.two);
        RelativeLayout three = findViewById(R.id.three);
        RelativeLayout four = findViewById(R.id.four);
        push_messag = findViewById(R.id.activity_main_push);

        one.setOnClickListener(this);
        two.setOnClickListener(this);
        three.setOnClickListener(this);
        four.setOnClickListener(this);
        push_messag.setOnLongClickListener(this);
        push_messag.setOnClickListener(this);
        initSlider();//初始化banner

    }



    /**
     * 初始化banner的方法
     */
    private void initSlider() {
        sliderLayout = (SliderLayout) findViewById(R.id.slide);
        HashMap<String, Integer> file_maps = new HashMap<String, Integer>();
        file_maps.put("国家安全是头等大事", R.mipmap.banner1);
        file_maps.put("人民防空 人人有责", R.mipmap.banner2);
        file_maps.put("加强国防教育", R.mipmap.banner3);


        for (String name : file_maps.keySet()) {
            TextSliderView textSliderView = new TextSliderView(this);
            textSliderView
                    .description(name)
                    .image(file_maps.get(name))
                    .setScaleType(BaseSliderView.ScaleType.Fit)
                    .setOnSliderClickListener(new BaseSliderView.OnSliderClickListener() {
                        @Override
                        public void onSliderClick(BaseSliderView slider) {

                        }
                    });

            //add your extra information
            textSliderView.bundle(new Bundle());
            textSliderView.getBundle()
                    .putString("extra", name);
            sliderLayout.addSlider(textSliderView);
        }
        sliderLayout.setPresetTransformer(SliderLayout.Transformer.Default);

        sliderLayout.setPresetIndicator(SliderLayout.PresetIndicators.Right_Bottom);
        sliderLayout.setCustomAnimation(new DescriptionAnimation());
        sliderLayout.setDuration(4000);
        sliderLayout.addOnPageChangeListener(this);

    }


    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

        Log.d("Slider Demo", "Page Changed: " + position);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    protected void onStop() {
        sliderLayout.stopAutoCycle();
        super.onStop();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onDefaultEvent(String message) {
        Log.d("onEventMainThread", "onEventMainThread: "+message);
    }

    @Override
    protected void onDestroy() {
        ImmersionBar.with(this).destroy();
//        EventBus.getDefault().unregister(this);
        try {
            MqttManager.getInstance().disConnect();
        } catch (MqttException e) {
            e.printStackTrace();
        }
        super.onDestroy();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.one:
                Intent intent = new Intent(this, NewsActivity.class);
                intent.putExtra("type", "1");
                startActivity(intent);
                break;

            case R.id.two:
                Intent intent2 = new Intent(this, ListssActivity.class);
                startActivity(intent2);
                break;

            case R.id.three:
                Intent intents = new Intent(this, Map3dActivity.class);
                startActivity(intents);

                break;
            case R.id.four:
                Sos();
                break;
            case R.id.activity_main_push:
                String  message = "0x222";
                if (flag) {
                    MqttManager.getInstance().publish("/jiaocheng/所有设备/大营一号水源井/Com4_大营一号水源井_Uab", 1, message.getBytes());
                    flag  = false;
                }
                break;

        }
    }

    /**
     * 拨打电话(拨号键)
     *
     * @param phoneStr
     */
    private void Call(String phoneStr) {

        Intent intent3 = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + phoneStr));
        intent3.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent3);

    }


    @Override
    public boolean onLongClick(View view) {
//        "/jiaocheng/所有设备/大营一号水源井/Com4_大营一号水源井_Uab"
        AirDefenseAlarm();

        MqttManager.getInstance().setCallBack(mqttCallbackBus);
        MqttManager.getInstance().creatConnect("", null, null, "");
        MqttManager.getInstance().subscribe("/jiaocheng/所有设备/大营一号水源井/Com4_大营一号水源井_Uab",1);

        String  message = "0x111";
        MqttManager.getInstance().publish("/jiaocheng/所有设备/大营一号水源井/Com4_大营一号水源井_Uab", 1,message.getBytes());
        flag = true;
        return false;

    }

    public MqttCallbackBus  mqttCallbackBus = new MqttCallbackBus(){

        @Override
        public void messageArrived(String topic, MqttMessage message) throws Exception {
//            super.messageArrived(topic, message);
            Log.d("1111111", "messageArrived: "+message.toString());

        }


    };


    private void AirDefenseAlarm() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);  //先得到构造器

        //加载自定义的那个View,同时设置下
        final LayoutInflater inflater = MainActivity.this.getLayoutInflater();
        AirDefenseAlarm = inflater.inflate(R.layout.dialog_air_defense_alarm, null, false);
        builder.setView(AirDefenseAlarm);
//        builder.setCancelable(false);//设置false点击空白不能取消
        alert = builder.create();
        //参数都设置完成了，创建并显示出来
        builder.create().show();
        LatLng latLng6 = new LatLng(37.856955, 112.578623);
        AirDefenseAlarm.findViewById(R.id.gogogo).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MainActivity.this, GoToActivity.class);
                intent.putExtra("lant", 37.856955);
                intent.putExtra("lont", 112.578623);
                startActivity(intent);
            }
        });
    }


    private void Sos() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);  //先得到构造器

        //加载自定义的那个View,同时设置下
        final LayoutInflater inflater = MainActivity.this.getLayoutInflater();
        Sos = inflater.inflate(R.layout.dialog_sos, null, false);
        builder.setView(Sos);
        builder.setCancelable(true);//设置false点击空白不能取消
        alert = builder.create();
        //参数都设置完成了，创建并显示出来
        builder.create().show();
    }
}
