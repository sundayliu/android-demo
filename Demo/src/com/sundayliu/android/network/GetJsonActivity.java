package com.sundayliu.android.network;

import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.sundayliu.android.http.HttpHandler;
import com.sundayliu.demo.R;

public class GetJsonActivity extends Activity implements View.OnClickListener{
    EditText etResponse;
    TextView tvIsConnected;
    private Button btnRequest;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_network);
 
        // get reference to the views
        etResponse = (EditText) findViewById(R.id.et_response);
        tvIsConnected = (TextView) findViewById(R.id.tv_is_connected);
 
        // check if you are connected or not
        if(isConnected()){
            tvIsConnected.setBackgroundColor(0xFF00CC00);
            tvIsConnected.setText("You are conncted");
        }
        else{
            tvIsConnected.setText("You are NOT conncted");
        }
 
        // show response on the EditText etResponse 
        //etResponse.setText(GET("http://hmkcode.com/examples/index.php"));
        
        //new HttpAsyncTask().execute("http://hmkcode.com/examples/index.php");
        btnRequest = (Button) findViewById(R.id.btn_request);
        btnRequest.setOnClickListener(this);
 
    }
    
    public void onClick(View v){
        new HttpHandler() {
            @Override
            public HttpUriRequest getHttpRequestMethod() {

                return new HttpGet("http://hmkcode.appspot.com/rest/controller/get.json");

                // return new HttpPost(url)
            }
            @Override
            public void onResponse(String result) {
                Toast.makeText(getBaseContext(), "Received!", Toast.LENGTH_LONG).show();
                try{
                    JSONObject json = new JSONObject(result);
                    JSONArray articles = json.getJSONArray("articleList"); // get articles array
                    articles.length(); // --> 2
                    articles.getJSONObject(0); // get first article in the array
                    articles.getJSONObject(0).names(); // get first article keys [title,url,categories,tags]
                    articles.getJSONObject(0).getString("url"); // return an article url
                    etResponse.setText(json.toString(1));
                }catch(JSONException e){
                    etResponse.setText(result);
                }

            }

        }.execute();
    }
 
    
    // check network connection
    public boolean isConnected(){
        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
            if (networkInfo != null && networkInfo.isConnected()) 
                return true;
            else
                return false;   
    }
}
