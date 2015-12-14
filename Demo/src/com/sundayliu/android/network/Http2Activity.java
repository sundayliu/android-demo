package com.sundayliu.android.network;


import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;


import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import android.os.Bundle;
import android.widget.Button;
//import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.view.View;

import com.sundayliu.android.http.HttpHandler;
import com.sundayliu.demo.R;

public class Http2Activity extends Activity implements View.OnClickListener{

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

                return new HttpGet("http://hmkcode.com/examples/index.php");

                // return new HttpPost(url)
            }
            @Override
            public void onResponse(String result) {
                Toast.makeText(getBaseContext(), "Received!", Toast.LENGTH_LONG).show();
                etResponse.setText(result);
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
