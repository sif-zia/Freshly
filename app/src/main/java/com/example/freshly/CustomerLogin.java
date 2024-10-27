package com.example.freshly;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.freshly.intentkeys.IntentKeys;
import com.example.freshly.room.database.FreshlyDB;
import com.example.freshly.room.entity.Customer;


public class CustomerLogin extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_customer_login);

        TextView signUpText = findViewById(R.id.signup_text);
        Button loginButton = findViewById(R.id.customer_login_button);

        EditText email = findViewById(R.id.email);
        EditText password = findViewById(R.id.password);

        Intent intent = getIntent();
        String ToastText = intent.getStringExtra(IntentKeys.LoginToast) ;
        if ( ToastText != null) {
            Toast.makeText(CustomerLogin.this, ToastText, Toast.LENGTH_LONG).show();
        }

        signUpText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent launchCustomerSignUp = new Intent(CustomerLogin.this, CustomerRegister.class);
                startActivity(launchCustomerSignUp);
            }
        });

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String emailText = email.getText().toString();
                String passwordText = password.getText().toString();

                new loginTask().execute(new Customer(null, emailText, passwordText, null, null));
                String TAG = "Freshly.CustomerLogin.onCreate";
                Log.d(TAG, "Email: " + emailText + " Password: " + passwordText);
            }
        });
    }


    protected class loginTask extends AsyncTask<Customer, Void, Integer> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Button loginButton = findViewById(R.id.customer_login_button);
            Button switchButton = findViewById(R.id.switch_to_vendor_login);
            TextView signUpText = findViewById(R.id.signup_text);

            loginButton.setEnabled(false);
            switchButton.setEnabled(false);
            signUpText.setOnClickListener(null);
        }

        @Override
        protected Integer doInBackground(Customer... customers) {

            FreshlyDB freshlyDB = FreshlyDB.getInstance(CustomerLogin.this);

            if(!freshlyDB.customerDao().isEmailExists(customers[0].email))
                return -2;

            if(!freshlyDB.customerDao().loginCustomer(customers[0].email, customers[0].password))
                return -1;

            return 0;
        }

        @Override
        protected void onPostExecute(Integer loginSuccessful) {
            super.onPostExecute(loginSuccessful);

            if(loginSuccessful < 0) {
                Button loginButton = findViewById(R.id.customer_login_button);
                Button switchButton = findViewById(R.id.switch_to_vendor_login);
                TextView signUpText = findViewById(R.id.signup_text);

                loginButton.setEnabled(true);
                switchButton.setEnabled(true);
                signUpText.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent launchCustomerLoginActivity = new Intent(CustomerLogin.this, CustomerRegister.class);
                        startActivity(launchCustomerLoginActivity);
                    }
                });

                if(loginSuccessful == -1) {
                   Toast.makeText(CustomerLogin.this, "Invalid Password!", Toast.LENGTH_LONG).show();
                }

                if(loginSuccessful == -2) {
                    Toast.makeText(CustomerLogin.this, "Invalid Email!", Toast.LENGTH_LONG).show();

                }
                return;
            }


            Intent launchCustomerMainActivity = new Intent(CustomerLogin.this, MainActivity.class);
            startActivity(launchCustomerMainActivity);
        }
    }
}