package com.android.rahulrkr.www.alivedemoapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class Login extends AppCompatActivity {

    private EditText login_username,login_password;
    private Button login_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login2);

        login_username = (EditText)findViewById(R.id.login_username);
        login_password = (EditText)findViewById(R.id.login_password);
        login_button = (Button)findViewById(R.id.login_button);

        login_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /** To-Do */
                authenticateUser();
                Intent i = new Intent(Login.this,MainActivity.class);
                startActivity(i);
                finish();
            }
        });

    }

    private void authenticateUser() {
    /** To-Do */
    }
}
