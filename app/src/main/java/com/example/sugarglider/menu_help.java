package com.example.sugarglider;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

public class menu_help extends AppCompatActivity {
    Dialog dia_wx;
    Dialog dia_zfb;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_help);
        Button wx = (Button)findViewById(R.id.wx);
        Button zfb = (Button)findViewById(R.id.zfb);
        wx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dia_wx.show();
            }
        });
        zfb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dia_zfb.show();
            }
        });
        dia_wx = new Dialog(menu_help.this, R.style.edit_AlertDialog_style);
        dia_zfb = new Dialog(menu_help.this, R.style.edit_AlertDialog_style);
        dia_wx.setContentView(R.layout.wx_dialog);
        dia_zfb.setContentView(R.layout.zfb_dialog);
        dia_wx.setCanceledOnTouchOutside(true);
        dia_zfb.setCanceledOnTouchOutside(true);
        Window w = dia_wx.getWindow();
        WindowManager.LayoutParams lp = w.getAttributes();
        lp.width = 1000;
        lp.height = 1300;
        dia_wx.onWindowAttributesChanged(lp);
        Window k = dia_zfb.getWindow();
        WindowManager.LayoutParams kp = k.getAttributes();
        kp.width = 1000;
        kp.height = 1300;
        dia_wx.onWindowAttributesChanged(kp);
    }
}
