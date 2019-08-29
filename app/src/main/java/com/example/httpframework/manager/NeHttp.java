package com.example.httpframework.manager;

import com.example.httpframework.callback.CallbackListener;
import com.example.httpframework.callback.IJsonDataListener;
import com.example.httpframework.callback.JsonCallBackListener;
import com.example.httpframework.request.IHttpRequest;
import com.example.httpframework.request.JsonHttpRequest;


public class NeHttp {
    public static <T,M>void sendJsonRequest(String url, T requestData, Class<M> response, IJsonDataListener listener){
        IHttpRequest httpRequest = new JsonHttpRequest();
        CallbackListener callbackListener = new JsonCallBackListener<>(response,listener);
        HttpTask ht = new HttpTask(url,requestData,httpRequest,callbackListener);
        ThreadPoolManager.getInstance().addTask(ht);
    }
}
