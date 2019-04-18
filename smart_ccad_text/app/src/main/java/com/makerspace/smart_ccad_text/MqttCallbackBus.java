package com.makerspace.smart_ccad_text;


import android.util.Log;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

/**
 * @version V1.0 <描述当前版本功能>.
 * @filename: com.makerspace.smart_ccad.activity.MqttCallbackBus.java
 * @Author 天地  on 2017/12/06.
 * @Org 山西创客空间科技有限公司.
 * @Description: ${TODO} .
 * @Motto 大梦谁先觉, 贫僧我自知..
 */
public class MqttCallbackBus implements MqttCallback {
    private static final String TAG = "MqttCallbackBus";
    @Override
    public void connectionLost(Throwable cause) {
        Log.d(TAG,cause.getMessage());
    }

    @Override
    public void messageArrived(String topic, MqttMessage message) throws Exception {
        Log.d(TAG,topic + "====" + message.toString());
//        EventBus.getDefault().post(message);
    }

    @Override
    public void deliveryComplete(IMqttDeliveryToken token) {
        try {
            Log.d(TAG,token.getMessage().toString());
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }
}
