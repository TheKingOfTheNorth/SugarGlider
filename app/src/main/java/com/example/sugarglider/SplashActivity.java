package com.example.sugarglider;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

public class SplashActivity extends AppCompatActivity {
    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case StaticClass.HANDLER_SPLASH:

                    startActivity(new Intent(SplashActivity.this,MainActivity.class));

                    finish();
                    break;
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash2);
        ImageView imageView=findViewById(R.id.splashh);
        Glide.with(SplashActivity.this)
                .load(R.mipmap.splash_default)
                .into(imageView);
        handler.sendEmptyMessageDelayed(StaticClass.HANDLER_SPLASH,2000);
    }
}
