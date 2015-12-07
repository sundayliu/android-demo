package com.sundayliu.helloworld;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class PhoneActivity extends Activity
{
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.phone);
        Button button = (Button)findViewById(R.id.btn_dial_phone);
        button.setOnClickListener(new View.OnClickListener() {
            
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                EditText phonenoText = (EditText)findViewById(R.id.phone_number);
                String phoneno = phonenoText.getText().toString();
                if ((phoneno != null) && (!"".equals(phoneno.trim())))
                {
                    Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:"+phoneno));
                    startActivity(intent);
                }
            }
        });
    }
}
