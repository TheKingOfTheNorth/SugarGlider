package com.example.sugarglider;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.PopupMenu;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;
import com.tencent.bugly.Bugly;
import com.tencent.bugly.beta.Beta;
import com.tencent.bugly.beta.UpgradeInfo;
import org.litepal.crud.DataSupport;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private List<Events> eventList = new ArrayList<>();
    private Date_Adapter adapter;
    private DrawerLayout mDrawerLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Bugly.init(getApplicationContext(), "05217db31f", false);
        Beta.checkUpgrade(false,false);
        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mDrawerLayout = (DrawerLayout)findViewById(R.id.drawer_layout);
        NavigationView navView = (NavigationView)findViewById(R.id.nav_view);
        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.ic_menu);//导航图标
        }

        initDate();
        RecyclerView recyclerView = (RecyclerView)findViewById(R.id.recycler_view) ;
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new Date_Adapter(eventList,MainActivity.this);
        recyclerView.setAdapter(adapter);
        FloatingActionButton add_button = (FloatingActionButton) findViewById(R.id.add_button);
        add_button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,Add_Activity.class);
                intent.putExtra("flag","1");
                startActivity(intent);
            }
        });
        navView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.application:
                        Intent intent = new Intent(MainActivity.this,menu_application.class);
                        startActivity(intent);
                        mDrawerLayout.closeDrawers();
                        break;
                    case R.id.update:
                        UpgradeInfo upgradeInfo = Beta.getUpgradeInfo();
                        if (upgradeInfo == null) {
                            Toast.makeText(MainActivity.this,"当前已是最新版本!",Toast.LENGTH_SHORT).show();
                        }else {
                            /***** 检查更新 *****/
                            Beta.checkUpgrade();
                        }
                        mDrawerLayout.closeDrawers();
                        break;
                    case R.id.connection:
                        intent = new Intent(MainActivity.this,menu_connection.class);
                        startActivity(intent);
                        mDrawerLayout.closeDrawers();
                        break;
                    case R.id.help:
                        intent = new Intent(MainActivity.this,menu_help.class);
                        startActivity(intent);
                        mDrawerLayout.closeDrawers();
                        break;
                        default:
                            mDrawerLayout.closeDrawers();
                }
                return true;
            }
        });
        adapter.setOnItemClickListener(new Date_Adapter.OnItemOnClickListener() {
            @Override
            public void onItemOnClick(View view, int pos) {
            }
            @Override
            public void onItemLongOnClick(View view, int pos) {
                showPopMenu(view,pos);
            }
        });
    }

    private void initDate() {
        eventList = DataSupport.findAll(Events.class);
        }

    public void showPopMenu(View view,final int pos){
        PopupMenu popupMenu = new PopupMenu(this,view);
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener(){
            public boolean onMenuItemClick(MenuItem item){
                adapter.removeItem(pos);
                return false;
            }
        });
        popupMenu.setOnDismissListener(new PopupMenu.OnDismissListener(){
            @Override
            public void onDismiss(PopupMenu menu){

            }
        });
        popupMenu.show();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        initDate();
        RecyclerView recyclerView = (RecyclerView)findViewById(R.id.recycler_view) ;
        LinearLayoutManager layoutManager = new LinearLayoutManager(MainActivity.this);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new Date_Adapter(eventList,MainActivity.this);
        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(new Date_Adapter.OnItemOnClickListener() {
            @Override
            public void onItemOnClick(View view, int pos) {

            }

            @Override
            public void onItemLongOnClick(View view, int pos) {
                showPopMenu(view,pos);
            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.toolbar,menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                break;
                default:
        }
        return true;
    }
}

