package com.aiwan.ace.aiwan.Imservice.callback;

/**
 * Created by ACE on 2016/4/1.
 */
public interface IMListener<T> {
    public abstract void onSuccess(T response);

    public abstract void onFaild();

    public abstract void onTimeout();
}
