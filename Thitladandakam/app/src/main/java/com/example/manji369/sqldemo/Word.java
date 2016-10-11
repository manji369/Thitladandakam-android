package com.example.manji369.sqldemo;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class Word extends AppCompatActivity {
    TextView random_word;
    Handler handler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_word);
        random_word = (TextView) findViewById(R.id.textView_randword);
        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                String notif = msg.getData().getString("word"); // You can change this according to your requirement.
                random_word.setText(notif);
            }
        };
        String type = "load";
        BackgroundWorker backgroundWorker = new BackgroundWorker(this,handler);
        backgroundWorker.execute(type);
    }

    public void onRefresh(View view){
        String type = "load";
        BackgroundWorker backgroundWorker = new BackgroundWorker(this,handler);
        backgroundWorker.execute(type);
    }
}
