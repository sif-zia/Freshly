package com.example.freshly;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.freshly.keys.IntentKeys;
import com.example.freshly.room.database.FreshlyDB;
import com.example.freshly.room.entity.Vendor;

public class VendorLogin extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_vendor_login);

        TextView signUpText = findViewById(R.id.signup_text);
        Button loginButton = findViewById(R.id.customer_login_button);
        Button switchButton = findViewById(R.id.switch_to_vendor_login);

        EditText username = findViewById(R.id.username_input);
        EditText password = findViewById(R.id.customer_password_input);

        Intent intent = getIntent();
        String ToastText = intent.getStringExtra(IntentKeys.CUSTOMER_LOGIN_TOAST) ;
        if ( ToastText != null) {
            Toast.makeText(VendorLogin.this, ToastText, Toast.LENGTH_LONG).show();
        }

        signUpText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent launchVendorSignUp = new Intent(VendorLogin.this, VendorRegister.class);
                launchVendorSignUp.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(launchVendorSignUp);
            }
        });

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String usernameText = username.getText().toString();
                String passwordText = password.getText().toString();

                new loginTask().execute(new Vendor(usernameText, passwordText, null, null, null));
            }
        });

        switchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent launchVendorLoginActivity = new Intent(VendorLogin.this, CustomerLogin.class);
                launchVendorLoginActivity.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(launchVendorLoginActivity);
            }
        });
    }


    protected class loginTask extends AsyncTask<Vendor, Void, Integer> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Button loginButton = findViewById(R.id.customer_login_button);
            Button switchButton = findViewById(R.id.switch_to_vendor_login);
            TextView signUpText = findViewById(R.id.signup_text);

            loginButton.setEnabled(false);
            switchButton.setEnabled(false);
            signUpText.setEnabled(false);
        }

        @Override
        protected Integer doInBackground(Vendor... vendors) {

            FreshlyDB freshlyDB = FreshlyDB.getInstance(VendorLogin.this);

            if(!freshlyDB.vendorDao().doesUsernameExists(vendors[0].username))
                return -2;

            if(!freshlyDB.vendorDao().loginVendor(vendors[0].username, vendors[0].password))
                return -1;

            return 0;
        }

        @Override
        protected void onPostExecute(Integer loginSuccessful) {
            super.onPostExecute(loginSuccessful);

            Button loginButton = findViewById(R.id.customer_login_button);
            Button switchButton = findViewById(R.id.switch_to_vendor_login);
            TextView signUpText = findViewById(R.id.signup_text);

            loginButton.setEnabled(true);
            switchButton.setEnabled(true);
            signUpText.setEnabled(true);

            if(loginSuccessful < 0) {
                if(loginSuccessful == -1) {
                    Toast.makeText(VendorLogin.this, "Invalid Password!", Toast.LENGTH_LONG).show();
                }

                if(loginSuccessful == -2) {
                    Toast.makeText(VendorLogin.this, "Invalid Username!", Toast.LENGTH_LONG).show();
                }
                return;
            }


            Intent launchCustomerMainActivity = new Intent(VendorLogin.this, MainActivity.class);
            launchCustomerMainActivity.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(launchCustomerMainActivity);
        }
    }
}