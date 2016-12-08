package com.android.rahulrkr.www.alivedemoapp;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.rahulrkr.www.alivedemoapp.Constants.LoginConstants;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.OptionalPendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URL;

public class LoginActivity extends AppCompatActivity implements
        GoogleApiClient.OnConnectionFailedListener {

    private static final String TAG = "SignInActivity";
    private static final int RC_SIGN_IN = 9001;

    private GoogleApiClient mGoogleApiClient;
    private ProgressDialog mProgressDialog;
    private SignInButton login_google_sign_in;

    private TextView login_signUp,login_login;

    private String name, email;
    private Uri photo_url;

    private Bitmap profilePicture = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // [START configure_signin]
        // Configure sign-in to request the user's ID, email address, and basic
        // profile. ID and basic profile are included in DEFAULT_SIGN_IN.
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        // [END configure_signin]

        // [START build_client]
        // Build a GoogleApiClient with access to the Google Sign-In API and the
        // options specified by gso.
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this /* FragmentActivity */, this /* OnConnectionFailedListener */)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();
        // [END build_client]

        login_google_sign_in = (SignInButton) findViewById(R.id.login_google_sign_in);
        login_google_sign_in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signIn();
            }
        });

        login_signUp = (TextView) findViewById(R.id.login_signUp);
        login_login = (TextView) findViewById(R.id.login_login);

        login_signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(LoginActivity.this,SignUp.class);
                startActivity(i);
            }
        });

        login_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(LoginActivity.this,Login.class);
                startActivity(i);
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();

        OptionalPendingResult<GoogleSignInResult> opr = Auth.GoogleSignInApi.silentSignIn(mGoogleApiClient);
        if (opr.isDone()) {
            // If the user's cached credentials are valid, the OptionalPendingResult will be "done"
            // and the GoogleSignInResult will be available instantly.
            Log.d(TAG, "Got cached sign-in");
            GoogleSignInResult result = opr.get();
            handleSignInResult(result);

        } else {
            // If the user has not previously signed in on this device or the sign-in has expired,
            // this asynchronous branch will attempt to sign in the user silently.  Cross-device
            // single sign-on will occur in this branch.
            showProgressDialog();
            opr.setResultCallback(new ResultCallback<GoogleSignInResult>() {
                @Override
                public void onResult(GoogleSignInResult googleSignInResult) {
                    hideProgressDialog();
                    handleSignInResult(googleSignInResult);
                }
            });
        }
    }

    // [START onActivityResult]
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleSignInResult(result);
        }
    }
    // [END onActivityResult]

    // [START handleSignInResult]
    private void handleSignInResult(GoogleSignInResult result) {
        Log.d(TAG, "handleSignInResult:" + result.isSuccess());
        if (result.isSuccess()) {
            // Signed in successfully, show authenticated UI.
            GoogleSignInAccount acct = result.getSignInAccount();
            name = acct.getDisplayName();
            email = acct.getEmail();
            photo_url = acct.getPhotoUrl();
            String root = Environment.getExternalStorageDirectory().toString();

            SharedPreferences sharedPref = getSharedPreferences("userInfo", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPref.edit();
            editor.putString("user_name", name);
            editor.putString("user_email", email);
            editor.putString("user_photo_url", root + "/Alive Homes/ProfileImage/");
            editor.apply();
            Toast.makeText(this, "User Details Saved", Toast.LENGTH_SHORT).show();

//            if (photo_url != null) {
//                // Setting default image
//              //  new LoadImage().execute(acct.getPhotoUrl().toString());
//            } else {
//                // Setting default image
//                Drawable myDrawable = getResources().getDrawable(R.drawable.ic_profile_pic);
//                Bitmap anImage = ((BitmapDrawable) myDrawable).getBitmap();
//                saveInGallery(anImage);
//            }

            Intent i = new Intent(this, MainActivity.class);
            startActivity(i);
            finish();
        } else {

            // Signed out, show unauthenticated UI.
        }
    }
    // [END handleSignInResult]

    // [START signIn]
    private void signIn() {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }
    // [END signIn]

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        // An unresolvable error has occurred and Google APIs (including Sign-In) will not
        // be available.
        Toast.makeText(this, "Connection Error!! Please check Internet Connection...", Toast.LENGTH_SHORT).show();
        Log.d(TAG, "onConnectionFailed:" + connectionResult);
    }

    private void showProgressDialog() {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(this);
            mProgressDialog.setIndeterminate(true);
        }

        mProgressDialog.show();
    }

    private void hideProgressDialog() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.hide();
        }
    }

//    public void saveInGallery(Bitmap img) {
//        File myDir = new File(Environment.getExternalStorageDirectory()
//                + "/Android/data/"
//                + getApplicationContext().getPackageName()
//                + "/Files");
//
////        if(!myDir.exists()){
////            if (! myDir.mkdirs()){
////                return null;
////            }
////        }
//
//
//        String fname = "ProfilePic" + ".jpg";
//        File file = new File(myDir, fname);
//
//        /////////////////////////////////////
//
//        if (file.exists())
//            file.delete();
//        try {
//            FileOutputStream out = new FileOutputStream(file);
//            img.compress(Bitmap.CompressFormat.JPEG, 90, out);
//            out.flush();
//            out.close();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    private class LoadImage extends AsyncTask<String, String, Bitmap> {
//        ProgressDialog pDialog;
//
//        @Override
//        protected void onPreExecute() {
//            super.onPreExecute();
//            pDialog = new ProgressDialog(LoginActivity.this);
//            pDialog.setMessage("Saving Profile Picture ....");
//            pDialog.show();
//
//        }
//
//        protected Bitmap doInBackground(String... args) {
//            try {
//                profilePicture = BitmapFactory.decodeStream((InputStream) new URL(args[0]).getContent());
//
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//            return profilePicture;
//        }
//
//        protected void onPostExecute(Bitmap image) {
//
//            if (image != null) {
//                saveInGallery(image);
//                pDialog.dismiss();
//            } else {
//                pDialog.dismiss();
//                Toast.makeText(LoginActivity.this, "Image Does Not exist or Network Error", Toast.LENGTH_SHORT).show();
//            }
//        }
//    }
}
