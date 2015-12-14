package com.sundayliu.android.http;

import org.apache.http.client.methods.HttpUriRequest;

import com.sundayliu.android.task.HttpAsyncTask;

public abstract class HttpHandler {
    public abstract HttpUriRequest getHttpRequestMethod();

    public abstract void onResponse(String result);

    public void execute(){
        new HttpAsyncTask(this).execute();
    } 
}
