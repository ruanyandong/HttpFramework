package com.example.httpframework;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.example.httpframework.callback.IJsonDataListener;
import com.example.httpframework.manager.NeHttp;
import com.example.httpframework.model.ResponseClass;

/**
 * 笔记：打造网络访问框架
 *      1、能同时接收多个请求
 *      2、重试机制
 *      3、队列，存放所有的网络请求
 *      4、脱离第三方框架
 */
public class MainActivity extends AppCompatActivity {

    private String url = "http://v.juhe.cn/historyWeather/citys?province_id=28&key=bb52107206585ab074f5e59a8c73875b";
    private String url1 = "http://xxxx";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sendRequest();
    }

    private void sendRequest() {
        NeHttp.sendJsonRequest(url, null, ResponseClass.class, new IJsonDataListener<ResponseClass>() {
            @Override
            public void onSuccess(ResponseClass o) {
                Log.d("ruanyandong", "onSuccess: "+o.toString());
            }
        });
    }
}
