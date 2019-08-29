package com.example.httpframework.request;

import com.example.httpframework.callback.CallbackListener;

public interface IHttpRequest {

    void setUrl(String url);

    void setData(byte[] data);

    void setListener(CallbackListener callbackListener);

    void execute();

}
