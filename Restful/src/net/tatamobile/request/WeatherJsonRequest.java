package net.tatamobile.request;

import org.json.JSONObject;

import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;

public class WeatherJsonRequest extends JsonObjectRequest {
    public WeatherJsonRequest(int method, 
            String url, 
            JSONObject jsonRequest,
            Response.Listener<JSONObject> listener, 
            Response.ErrorListener errorListener) {
        super(method, url, jsonRequest, listener, errorListener);
    }
    
    private Priority mPriority;
    
    public void setPriority(Priority priority) {
        mPriority = priority;
    }
    
    @Override
    public Priority getPriority() {
        return mPriority == null ? Priority.NORMAL : mPriority;
    }
}
