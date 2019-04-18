package com.makerspace.smart_ccad_text;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static final int SEND_MESSAGE = 111;
    private static final int SEND_REST = 222;
    private static final int SEND_STOP = 333;
    int time = 5;
    public ImageView jiantou;
    public TextView textView2;


    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            switch (msg.what) {
                case SEND_MESSAGE:
                    textView2.setVisibility(View.VISIBLE);
                    mp.start();

                    break;

                case SEND_STOP:
//                    mp.stop();
                    textView2.setVisibility(View.GONE);
                    break;
            }

        }
    };
    public MediaPlayer mp;
    private boolean flag;
    private int count;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("sos", "onCreate: ");
        setContentView(R.layout.activity_main);
        textView2 = (TextView) findViewById(R.id.text123);
        textView2.requestFocus();
        MqttManager.getInstance().setCallBack(mqttCallbackBus);
        MqttManager.getInstance().creatConnect("", null, null, "");
        MqttManager.getInstance().subscribe("/jiaocheng/所有设备/大营一号水源井/Com4_大营一号水源井_Uab", 1);
//        handler.sendEmptyMessage(SEND_MESSAGE);
        Log.d("sos", "onCreate: " + MqttManager.getInstance());
        mp = MediaPlayer.create(this, R.raw.sos);

        mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(final MediaPlayer mp) {
                if (flag) {
                    mp.start();
                }
            }
        });
    }

    public MqttCallbackBus mqttCallbackBus = new MqttCallbackBus() {
        @Override
        public void messageArrived(String topic, MqttMessage message) throws Exception {
//            super.messageArrived(topic, message);
            String s = message.toString();
            Log.e("sos", "" + s);
            if (s.equals("0x111")) {
                handler.sendEmptyMessage(SEND_MESSAGE);
                flag = true;
            } else if (s.equals("0x222")) {
                handler.sendEmptyMessage(SEND_STOP);
                flag = false;
            }
        }

        @Override
        public void deliveryComplete(IMqttDeliveryToken token) {
//            super.deliveryComplete(token);
            try {
                Log.d("sos", "deliveryComplete: " + token.getMessage().toString());
            } catch (MqttException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void connectionLost(Throwable cause) {
//            super.connectionLost(cause);
            Log.d("sos", "connectionLost: " + cause.getLocalizedMessage());
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mp.stop();
        flag = false;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

        }
    }
}
