package com.example.httpframework.callback;

import java.io.InputStream;

public interface CallbackListener {

    void onSuccess(InputStream inputStream);

    void onFailure();

}
