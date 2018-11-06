package com.example.sugarglider;

import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import org.litepal.LitePal;
import org.litepal.crud.DataSupport;

import java.util.Calendar;

public class Add_Activity extends AppCompatActivity {
    private EditText name;
    private TextView name_view;
    private TextView date_view;
    private TextView day_view;
    private String in_name;
    private String in_mYear;
    private String in_mMonth;
    private String in_mDay;
    private DatePicker date;
    private int mYear,mMonth,mDay,type;
    ContentValues values = new ContentValues();
    private boolean f = false;
    RadioButton surplus_button;
    RadioButton total_button;
    RadioButton birthday_button;
    int k;
    long birthday;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null){
            actionBar.hide();
        }
        LitePal.getDatabase();//创建数据库
        date = (DatePicker) findViewById(R.id.date);
        date_view = (TextView) findViewById(R.id.date_view);
        FloatingActionButton submit_button = (FloatingActionButton) findViewById(R.id.submit_button);
        FloatingActionButton back_button = (FloatingActionButton) findViewById(R.id.back_button);
        final Events events = new Events();
        name = (EditText) findViewById(R.id.name);
        name_view = (TextView) findViewById(R.id.name_view);
        day_view = (TextView)findViewById(R.id.day_view);
        surplus_button = (RadioButton)findViewById(R.id.surplus_button);
        total_button = (RadioButton)findViewById(R.id.total_button);
        birthday_button = (RadioButton)findViewById(R.id.birthday_button);


        Intent intent =getIntent();
        String flag = intent.getStringExtra("flag");
        final int back_id = intent.getIntExtra("back_id",0);
        String back_name = intent.getStringExtra("back_name");
        final String back_mYear = intent.getStringExtra("back_mYear");
        final String back_mMonth = intent.getStringExtra("back_mMonth");
        final String back_mDay = intent.getStringExtra("back_mDay");
        final int back_type = intent.getIntExtra("type",0);
        final String beforedate =num_operate.getToday();
        final String mmdd = num_operate.getTodaytwo();
        final int yy = Integer.parseInt(num_operate.getTodayyear());
        final int mm = Integer.parseInt(num_operate.getTodaymonth());
        final int dd = Integer.parseInt(num_operate.getTodayday());
        Calendar c = Calendar.getInstance();

        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);
        if (flag.equals("1")){//第一种情况...............................................
            date.init(mYear, mMonth, mDay, new DatePicker.OnDateChangedListener() {
                @Override
                public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                    mYear = year;
                    mMonth = monthOfYear+1;
                    mDay = dayOfMonth;
                    in_mYear = String.valueOf(mYear);
                    in_mMonth = String.valueOf(mMonth);
                    in_mDay = String.valueOf(mDay);
                    //先拼接年月日字符串
                    String yearmonthday = in_mYear+"-"+in_mMonth+"-"+in_mDay;
                    //未来天数与目前天数的差值
                    long minus_day = num_operate.dateDiff(beforedate, yearmonthday, "yyyy-MM-dd");
                    long total_day = num_operate.dateDiff(yearmonthday,beforedate,"yyyy-MM-dd");
                    long diff = Math.abs(num_operate.dateDiff(mmdd,in_mMonth+"-"+in_mDay,"MM-dd"));
                    if(((yy+1)%4 == 0)&&((yy+1)%100 != 0)||((yy+1)%400 == 0)){ k = 366; }//判断生日天数
                    else { k = 365; }
                    if(mm == mMonth){
                        if(dd >= mDay){ birthday = diff; }
                        else{ birthday = k-diff; }
                    }
                    else if(mm>mMonth){ birthday = k-diff; }
                    else{ birthday = diff; }//判断生日天数

                    f = true;//判定是否滑动日期选择器
                    date_view.setText(in_mYear+"-"+in_mMonth+"-"+ in_mDay);//实时显示预览日期
                    if(surplus_button.isChecked()){
                        day_view.setText(minus_day+"");//实时显示预览天数
                    }
                    if(total_button.isChecked()){
                        day_view.setText(total_day+"");
                    }
                    if (birthday_button.isChecked()){
                        day_view.setText(birthday+"");
                    }
                }
            });

            //submit按钮:判空并保存及跳转
            submit_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    switch (v.getId()) {
                        case R.id.submit_button:
                            in_name = name.getText().toString();
                            if(in_name.equals("")){
                                Toast.makeText(Add_Activity.this,"请输入名称",Toast.LENGTH_SHORT).show();
                            }else if(!surplus_button.isChecked()&&!total_button.isChecked()&&!birthday_button.isChecked()){
                                Toast.makeText(Add_Activity.this,"请选择事件类型",Toast.LENGTH_SHORT).show();
                            }
                            else{
                                if(surplus_button.isChecked()){
                                    type = 1;
                                }
                                if(total_button.isChecked()){
                                    type = 2;
                                }
                                if (birthday_button.isChecked()){
                                    type = 3;
                                }
                                events.setName(in_name);
                                events.setType(type);
                                if(f){
                                    events.setmYear(in_mYear);
                                    events.setmMonth(in_mMonth);
                                    events.setmDay(in_mDay);
                                }else{
                                    events.setmYear(mYear+"");
                                    events.setmMonth((mMonth+1)+"");
                                    events.setmDay(mDay+"");
                                }
                                Toast.makeText(Add_Activity.this,"添加成功",Toast.LENGTH_SHORT).show();
                                events.save();
                                Intent intent = new Intent(Add_Activity.this,MainActivity.class);
                                startActivity(intent);
                                break;
                            }
                        default:
                            break;
                    }
                }
            });

        } else {//第二种情况..............................................................................
            //控件设置取出来的数据
            name.setText(back_name);//传入之前的文本
            name_view.setText(back_name);//预览名字
            date_view.setText(back_mYear+"-"+back_mMonth+"-"+back_mDay);//预览年月日
            if(back_type==1){
                surplus_button.setChecked(true);
            }
            if(back_type==2){
                total_button.setChecked(true);
            }
            if(back_type==3){
                birthday_button.setChecked(true);
            }
            date.init(Integer.parseInt(back_mYear), Integer.parseInt(back_mMonth)-1,Integer.parseInt(back_mDay), new DatePicker.OnDateChangedListener() {
                @Override
                public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                    mYear = year;
                    mMonth = monthOfYear+1;
                    mDay = dayOfMonth;
                    in_mYear = String.valueOf(mYear);
                    in_mMonth = String.valueOf(mMonth);
                    in_mDay = String.valueOf(mDay);
                    //先拼接年月日字符串
                    String yearmonthday = in_mYear+"-"+in_mMonth+"-"+in_mDay;
                    String beforedate =num_operate.getToday();
                    long minus_day = num_operate.dateDiff(beforedate, yearmonthday, "yyyy-MM-dd");
                    long total_day = num_operate.dateDiff( yearmonthday,beforedate, "yyyy-MM-dd");
                    long diff = Math.abs(num_operate.dateDiff(mmdd,in_mMonth+"-"+in_mDay,"MM-dd"));
                    if(((yy+1)%4 == 0)&&((yy+1)%100 != 0)||((yy+1)%400 == 0)){ k = 366; }//判断生日天数
                    else { k = 365; }
                    if(mm == mMonth){
                        if(dd >= mDay){ birthday = diff; }
                        else{ birthday = k-diff; }
                    }
                    else if(mm>mMonth){ birthday = k-diff; }
                    else{ birthday = diff; }//判断生日天数
                    if(surplus_button.isChecked()){
                        day_view.setText(minus_day+"");//实时显示预览天数
                    }
                    if(total_button.isChecked()){
                        day_view.setText(total_day+"");
                    }
                    if(birthday_button.isChecked()){
                        day_view.setText(birthday+"");
                    }
                    date_view.setText(in_mYear+"-"+in_mMonth+"-"+ in_mDay);//实时显示预览日期
                    f=true;
                }
            });
            long minus_day = num_operate.dateDiff(beforedate, back_mYear+"-"+back_mMonth+"-"+back_mDay, "yyyy-MM-dd");
            long total_day = num_operate.dateDiff( back_mYear+"-"+back_mMonth+"-"+back_mDay,beforedate, "yyyy-MM-dd");
            long diff = Math.abs(num_operate.dateDiff(mmdd,back_mMonth+"-"+back_mDay,"MM-dd"));

            if(((yy+1)%4 == 0)&&((yy+1)%100 != 0)||((yy+1)%400 == 0)){ k = 366; }//判断生日天数
            else { k = 365; }
            if(mm == Integer.parseInt(back_mMonth)){
                if(dd >= Integer.parseInt(back_mDay)){ birthday = diff; }
                else{ birthday = k-diff; }
            }
            else if(mm>Integer.parseInt(back_mMonth)){ birthday = k-diff; }
            else{ birthday = diff; }//判断生日天数

            if(back_type==1){
                day_view.setText(minus_day+"");
            }
            if(back_type==2){
                day_view.setText(total_day+"");
            }
            if(back_type==3){
                day_view.setText(birthday+"");
            }
            //提交之后,执行数据库的update
            submit_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String input_name = name.getText().toString();
                    if(input_name.equals("")){
                        Toast.makeText(Add_Activity.this,"请输入名称",Toast.LENGTH_SHORT).show();
                    }else if(!surplus_button.isChecked()&&!total_button.isChecked()&&!birthday_button.isChecked()){
                        Toast.makeText(Add_Activity.this,"请选择事件类型",Toast.LENGTH_SHORT).show();
                    }
                    else {
                        int back_typ = 0;
                        if (surplus_button.isChecked()){
                            back_typ = 1;
                        }
                        if (total_button.isChecked()){
                            back_typ = 2;
                        }
                        if(birthday_button.isChecked()){
                            back_typ = 3;
                        }
                        values.put("type",back_typ);
                        values.put("name",input_name);
                        if(f){
                            values.put("mYear",in_mYear);
                            values.put("mMonth",in_mMonth);
                            values.put("mDay",in_mDay);
                        }else{
                            values.put("mYear",back_mYear);
                            values.put("mMonth",back_mMonth);
                            values.put("mDay", back_mDay);
                        }
                        Toast.makeText(Add_Activity.this,"修改成功",Toast.LENGTH_SHORT).show();
                        DataSupport.update(Events.class,values,back_id);
                        Intent intent = new Intent(Add_Activity.this,MainActivity.class);
                        startActivity(intent);


                    }
                }
            });
        }

        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Add_Activity.this,MainActivity.class);
                startActivity(intent);
            }
        });
        //实时显示预览名称
        name.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
            @Override
            public void afterTextChanged(Editable s) {
                name_view.setText(name.getText());
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}






