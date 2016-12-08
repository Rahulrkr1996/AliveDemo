package com.android.rahulrkr.www.alivedemoapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class SignUp extends AppCompatActivity {

    private EditText signup_first_name, signup_last_name, signup_password, signup_re_password, signup_email, signup_user_name;
    private Button signup_signup_button, signup_login_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        signup_first_name = (EditText) findViewById(R.id.signup_first_name);
        signup_last_name = (EditText) findViewById(R.id.signup_last_name);
        signup_email = (EditText) findViewById(R.id.signup_email);
        signup_user_name = (EditText) findViewById(R.id.signup_user_name);
        signup_password = (EditText) findViewById(R.id.signup_password);
        signup_re_password = (EditText) findViewById(R.id.signup_re_password);

        signup_signup_button = (Button) findViewById(R.id.signup_signup_button);
        signup_login_button = (Button) findViewById(R.id.signup_login_button);


        signup_re_password.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (signup_re_password.length() != 0) {
                    if (!(signup_re_password.equals(signup_password.toString())))
                        Toast.makeText(SignUp.this, "Password fields don't match!!! ", Toast.LENGTH_SHORT).show();
                }
            }
        });

        signup_login_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(SignUp.this,Login.class);
                startActivity(i);
            }
        });

        signup_signup_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /** To-Do */
                sign_up();

                Toast.makeText(SignUp.this, "SignUp Successfull!!", Toast.LENGTH_SHORT).show();
                Intent i = new Intent(SignUp.this,LoginActivity.class);
                startActivity(i);
                finish();
            }
        });
    }

    private void sign_up() {
        /** To-Do */
    }
}
