package net.tatamobile.application;

import android.app.Application;
import com.android.volley.*;
import com.android.volley.toolbox.Volley;

public class MarsWeather extends Application {
    public static final String TAG = MarsWeather.class.getName();
    private RequestQueue mRequestQueue;
    private static MarsWeather mInstance;
    
    public void onCreate(){
        super.onCreate();
        mInstance = this;
        mRequestQueue = Volley.newRequestQueue(getApplicationContext());
    }
    
    public static synchronized MarsWeather getInstance(){
        return mInstance;
    }
    
    public RequestQueue getRequestQueue(){
        return mRequestQueue;
    }
    
    public <T> void add(Request<T> req){
        req.setTag(TAG);
        mRequestQueue.add(req);
    }
    
    public void cancel(){
        mRequestQueue.cancelAll(TAG);
    }
}
