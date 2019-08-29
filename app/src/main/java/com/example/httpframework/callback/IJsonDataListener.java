package com.example.httpframework.callback;

public interface IJsonDataListener<T> {
    void onSuccess(T t);
}
