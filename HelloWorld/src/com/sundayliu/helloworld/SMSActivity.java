package com.sundayliu.helloworld;

import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class SMSActivity extends Activity
{
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sms);
        
        Button buttonSendSMS = (Button)findViewById(R.id.btn_send_sms);
        buttonSendSMS.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                EditText mobileText = (EditText)findViewById(R.id.sms_number);
                EditText contentText = (EditText)findViewById(R.id.sms_content);
                String mobile = mobileText.getText().toString();
                String content = contentText.getText().toString();
                SmsManager smsManager = SmsManager.getDefault();
                List<String> texts = smsManager.divideMessage(content);
                for (String text:texts){
                    smsManager.sendTextMessage(mobile, null, text, null, null);
                }
                Toast.makeText(SMSActivity.this, R.string.success, Toast.LENGTH_LONG).show();
            }
        });
    }
}
