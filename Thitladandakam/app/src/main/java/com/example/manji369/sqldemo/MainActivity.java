package com.example.manji369.sqldemo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;


public class MainActivity extends Activity {
    EditText UserNameET, PasswordET;
    TextView txt;
    Handler handler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        UserNameET = (EditText)findViewById(R.id.etUserName);
        PasswordET = (EditText)findViewById(R.id.etPassword);
        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                String notif = msg.getData().getString("notify"); // You can change this according to your requirement.
                txt = (TextView) findViewById(R.id.textView2);
                txt.setText(notif);
                if(notif.equals("Success")) {
                    Intent intent = new Intent(MainActivity.this, Word.class);
                    startActivity(intent);
                }
            }
        };
    }
    public void OnLogin(View view){
        String username = UserNameET.getText().toString();
        String password = PasswordET.getText().toString();
        String type = "login";
        BackgroundWorker backgroundWorker = new BackgroundWorker(this,handler);
        backgroundWorker.execute(type,username,password);
    }


}