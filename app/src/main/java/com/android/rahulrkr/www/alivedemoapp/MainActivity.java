package com.android.rahulrkr.www.alivedemoapp;

import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private ImageButton home_settings,home_help;
    private ImageView home_bulb_image;
    private Switch home_bulb_toggle;
    private TextView home_bulb_text;
    private ImageView home_fan_image;
    private SeekBar home_fan_seekbar;
    private TextView home_fan_speed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        home_settings = (ImageButton)findViewById(R.id.home_settings);
        home_help = (ImageButton)findViewById(R.id.home_help);
        home_bulb_image = (ImageView)findViewById(R.id.home_bulb_image);
        home_bulb_toggle = (Switch)findViewById(R.id.home_bulb_toggle_button);
        home_fan_image = (ImageView)findViewById(R.id.home_fan_image);
        home_fan_seekbar = (SeekBar)findViewById(R.id.home_fan_seekbar);
        home_fan_speed = (TextView)findViewById(R.id.home_fan_speed);

        home_settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /** To Do */
            }
        });

        home_help.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /** To Do */
            }
        });

        home_bulb_toggle.setChecked(false);
        home_bulb_toggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                home_bulb_toggle.setChecked(isChecked);

                if(isChecked==true){
                    home_bulb_image.setImageResource(R.drawable.bulb_on);
                }else {
                    home_bulb_image.setImageResource(R.drawable.bulb_off);
                }
            }
        });

        home_bulb_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               if(home_bulb_toggle.isChecked()){
                   home_bulb_toggle.setChecked(false);
                   home_bulb_image.setImageResource(R.drawable.bulb_off);
               }else{
                   home_bulb_toggle.setChecked(true);
                   home_bulb_image.setImageResource(R.drawable.bulb_on);
               }
            }
        });

        home_fan_seekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                home_fan_speed.setText(String.valueOf(progress*20)+" %");
                if(progress==0){
                    home_fan_image.setImageResource(R.drawable.ic_home_fan);
                }else if(progress==1){
                    home_fan_image.setImageResource(R.drawable.ic_home_fan1);
                }else if(progress==2){
                    home_fan_image.setImageResource(R.drawable.ic_home_fan2);
                }else if(progress==3){
                    home_fan_image.setImageResource(R.drawable.ic_home_fan3);
                }else if(progress==4){
                    home_fan_image.setImageResource(R.drawable.ic_home_fan4);
                }else if(progress==5){
                    home_fan_image.setImageResource(R.drawable.ic_home_fan5);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
