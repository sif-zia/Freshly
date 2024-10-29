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

public class VendorRegister extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_vendor_register);

        Button loginButton = findViewById(R.id.customer_login_button);
        Button switchButton = findViewById(R.id.switch_to_vendor_login);
        TextView signUpText = findViewById(R.id.signup_text);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Vendor vendor = validation();
                if (vendor != null)
                    new insertVendorTask().execute(vendor);
            }
        });

        switchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent launchVendorRegisterActivity = new Intent(VendorRegister.this, CustomerRegister.class);
                launchVendorRegisterActivity.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(launchVendorRegisterActivity);
            }
        });


        signUpText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent launchCustomerLoginActivity = new Intent(VendorRegister.this, VendorLogin.class);
                launchCustomerLoginActivity.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(launchCustomerLoginActivity);
            }
        });
    }

    protected Vendor validation() {
        EditText username = findViewById(R.id.username_input);
        EditText password = findViewById(R.id.vendor_password_input);
        EditText confirmPassword = findViewById(R.id.confirm_password_input);
        EditText address = findViewById(R.id.address_input);
        EditText phoneNumber = findViewById(R.id.phone_input);

        // Username Validation
        String usernameText = username.getText().toString();
        if(usernameText.isEmpty()) {
            Toast.makeText(VendorRegister.this, "Username is Required!", Toast.LENGTH_LONG).show();
            return null;
        }
        if(usernameText.length() > 30)
        {
            Toast.makeText(VendorRegister.this, "Username cannot be greater than 30!", Toast.LENGTH_LONG).show();
            return null;
        }
        if(!usernameText.matches("^[a-zA-Z0-9_]+$"))
        {
            Toast.makeText(VendorRegister.this, "Username can only have Alphabets, Numbers and Underscore!", Toast.LENGTH_LONG).show();
            return null;
        }

        // Address Validation
        String addressText = address.getText().toString();
        if(addressText.isEmpty()) {
            Toast.makeText(VendorRegister.this, "Address is Required!", Toast.LENGTH_LONG).show();
            return null;
        }

        // Password Validation
        String passwordText = password.getText().toString();
        String confirmPasswordText = confirmPassword.getText().toString();
        if(password.length() < 8)
        {
            Toast.makeText(VendorRegister.this, "Password should have at least 8 characters!", Toast.LENGTH_LONG).show();
            return null;
        }
        if(!passwordText.equals(confirmPasswordText))
        {
            Toast.makeText(VendorRegister.this, "Passwords do not match!", Toast.LENGTH_LONG).show();
            return null;
        }

        // Phone Number Validation
        String phoneNumberText = phoneNumber.getText().toString();
        if (phoneNumberText.isEmpty()) {
            Toast.makeText(VendorRegister.this, "Phone Number is Required!", Toast.LENGTH_LONG).show();
            return null;

        }

        return new Vendor(usernameText, passwordText, addressText, null, phoneNumberText);
    }

    protected class insertVendorTask extends AsyncTask<Vendor, Void, Long> {
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
        protected Long doInBackground(Vendor... vendors) {
            FreshlyDB freshlyDB = FreshlyDB.getInstance(VendorRegister.this);

            boolean usernameExists = freshlyDB.vendorDao().doesUsernameExists(vendors[0].username);

            if (usernameExists)
                return (long) -2;

            return freshlyDB.vendorDao().insert(vendors[0]);
        }

        @Override
        protected void onPostExecute(Long l) {
            super.onPostExecute(l);

            Button loginButton = findViewById(R.id.customer_login_button);
            Button switchButton = findViewById(R.id.switch_to_vendor_login);
            TextView signUpText = findViewById(R.id.signup_text);

            loginButton.setEnabled(true);
            switchButton.setEnabled(true);
            signUpText.setEnabled(true);

            if(l.intValue() < 0)
            {
                if (l.intValue() == -1) {
                    Toast.makeText(VendorRegister.this, "Vendor Registration Failed!", Toast.LENGTH_LONG).show();
                }
                if (l.intValue() == -2) {
                    Toast.makeText(VendorRegister.this, "Vendor with this Username already exists!", Toast.LENGTH_LONG).show();
                }
                return;
            }

            Intent launchCustomerLoginActivity = new Intent(VendorRegister.this, VendorLogin.class);
            launchCustomerLoginActivity.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            launchCustomerLoginActivity.putExtra(IntentKeys.VENDOR_LOGIN_TOAST, "Vendor Registered Successfully!");
            startActivity(launchCustomerLoginActivity);
        }
    }
}