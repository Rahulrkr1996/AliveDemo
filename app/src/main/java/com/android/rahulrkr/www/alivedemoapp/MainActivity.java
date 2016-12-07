package com.android.rahulrkr.www.alivedemoapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.Image;
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
import android.widget.Toast;

import com.android.rahulrkr.www.alivedemoapp.Constants.LoginConstants;
import com.bumptech.glide.Glide;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private ImageButton home_settings, home_help;
    private ImageView home_bulb_image;
    private Switch home_bulb_toggle;
    private TextView home_bulb_text;
    private ImageView home_fan_image;
    private SeekBar home_fan_seekbar;
    private TextView home_fan_speed;

    private ImageView home_nav_header_image;
    private TextView home_nav_header_name;
    private GoogleApiClient mGoogleApiClient;

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

        home_settings = (ImageButton) findViewById(R.id.home_settings);
        home_help = (ImageButton) findViewById(R.id.home_help);
        home_bulb_image = (ImageView) findViewById(R.id.home_bulb_image);
        home_bulb_toggle = (Switch) findViewById(R.id.home_bulb_toggle_button);
        home_fan_image = (ImageView) findViewById(R.id.home_fan_image);
        home_fan_seekbar = (SeekBar) findViewById(R.id.home_fan_seekbar);
        home_fan_speed = (TextView) findViewById(R.id.home_fan_speed);

        View headerView = navigationView.inflateHeaderView(R.layout.nav_header_main);

        home_nav_header_image = (ImageView) headerView.findViewById(R.id.home_nav_header_img);
        home_nav_header_name = (TextView) headerView.findViewById(R.id.home_nav_header_name);

        SharedPreferences sharedPref = getSharedPreferences("userInfo", Context.MODE_PRIVATE);
        String name = sharedPref.getString("user_name", "");
//        String photo_url = sharedPref.getString("user_photo_url","");
//
//        home_nav_header_image.setImageBitmap(loadImageFromStorage(photo_url));
        home_nav_header_name.setText(name);

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

                if (isChecked == true) {
                    home_bulb_image.setImageResource(R.drawable.bulb_on);
                } else {
                    home_bulb_image.setImageResource(R.drawable.bulb_off);
                }
            }
        });

        home_bulb_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (home_bulb_toggle.isChecked()) {
                    home_bulb_toggle.setChecked(false);
                    home_bulb_image.setImageResource(R.drawable.bulb_off);
                } else {
                    home_bulb_toggle.setChecked(true);
                    home_bulb_image.setImageResource(R.drawable.bulb_on);
                }
            }
        });

        home_fan_seekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                home_fan_speed.setText(String.valueOf(progress * 20) + " %");
                if (progress == 0) {
                    home_fan_image.setImageResource(R.drawable.ic_home_fan);
                } else if (progress == 1) {
                    home_fan_image.setImageResource(R.drawable.ic_home_fan1);
                } else if (progress == 2) {
                    home_fan_image.setImageResource(R.drawable.ic_home_fan2);
                } else if (progress == 3) {
                    home_fan_image.setImageResource(R.drawable.ic_home_fan3);
                } else if (progress == 4) {
                    home_fan_image.setImageResource(R.drawable.ic_home_fan4);
                } else if (progress == 5) {
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
    protected void onStart() {
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();
        mGoogleApiClient.connect();
        super.onStart();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        } else if (id == R.id.action_help) {

        } else if (id == R.id.action_sign_out) {
            Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback(
                    new ResultCallback<Status>() {
                        @Override
                        public void onResult(Status status) {
                            Toast.makeText(getApplicationContext(), "Logged Out", Toast.LENGTH_SHORT).show();
                            Intent i = new Intent(getApplicationContext(), LoginActivity.class);
                            startActivity(i);
                            finish();
                        }
                    });
        } else if (id == R.id.action_revoke_access) {
            Auth.GoogleSignInApi.revokeAccess(mGoogleApiClient).setResultCallback(
                    new ResultCallback<Status>() {
                        @Override
                        public void onResult(Status status) {
                            Toast.makeText(getApplicationContext(), "Access Revoked", Toast.LENGTH_SHORT).show();
                            Intent i = new Intent(getApplicationContext(), LoginActivity.class);
                            startActivity(i);
                            finish();
                            // [END_EXCLUDE]
                        }
                    });
        }
        return super.onOptionsItemSelected(item);
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


//    private Bitmap loadImageFromStorage(String path) {
//        Bitmap b = null;
//        try {
//            File f=new File(path, "ProfilePic.jpg");
//            b = BitmapFactory.decodeStream(new FileInputStream(f));
//            return b;
//        }
//        catch (FileNotFoundException e)
//        {
//            // Setting default image
//            Drawable myDrawable = getResources().getDrawable(R.drawable.ic_profile_pic);
//            Bitmap anImage = ((BitmapDrawable) myDrawable).getBitmap();
//            e.printStackTrace();
//
//            return anImage;
//        }
//    }

}
