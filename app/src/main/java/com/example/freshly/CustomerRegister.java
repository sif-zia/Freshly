package com.example.freshly;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.freshly.intentkeys.IntentKeys;
import com.example.freshly.room.database.FreshlyDB;
import com.example.freshly.room.entity.Customer;

public class CustomerRegister extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_customer_register);

        Button loginButton = findViewById(R.id.customer_login_button);
        Button switchButton = findViewById(R.id.switch_to_vendor_login);
        TextView signUpText = findViewById(R.id.signup_text);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Customer customer = validation();
                if(customer != null)
                    new insertCustomerTask().execute(customer);
            }
        });

        switchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent launchVendorRegisterActivity = new Intent(CustomerRegister.this, VendorRegister.class);
                startActivity(launchVendorRegisterActivity);
            }
        });


        signUpText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent launchCustomerLoginActivity = new Intent(CustomerRegister.this, CustomerLogin.class);
                startActivity(launchCustomerLoginActivity);
            }
        });
    }

    protected Customer validation() {
        EditText name = findViewById(R.id.name_input);
        EditText email = findViewById(R.id.email_input);
        EditText password = findViewById(R.id.customer_password_input);
        EditText confirmPassword = findViewById(R.id.confirm_password_input);
        RadioGroup genderRadioButtons = findViewById(R.id.gender_radio);

        // Name Validation
        String nameText = name.getText().toString();
        if(nameText.length() > 30)
        {
            Toast.makeText(CustomerRegister.this, "Name cannot be greater than 30!", Toast.LENGTH_LONG).show();
            return null;
        }
        if(!nameText.matches("^[a-zA-Z\\s]+$"))
        {
            Toast.makeText(CustomerRegister.this, "Name can only have Alphabets and Spaces!", Toast.LENGTH_LONG).show();
            return null;
        }

        // Email Validation
        String emailText = email.getText().toString();
        String emailRegex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
        if(!emailText.matches(emailRegex))
        {
            Toast.makeText(CustomerRegister.this, "Not a Valid Email!", Toast.LENGTH_LONG).show();
            return null;
        }

        // Password Validation
        String passwordText = password.getText().toString();
        String confirmPasswordText = confirmPassword.getText().toString();
        if(password.length() < 8)
        {
            Toast.makeText(CustomerRegister.this, "Password should have at least 8 characters!", Toast.LENGTH_LONG).show();
            return null;
        }
        if(!passwordText.equals(confirmPasswordText))
        {
            Toast.makeText(CustomerRegister.this, "Passwords do not match!", Toast.LENGTH_LONG).show();
            return null;
        }

        // Gender Validation
        int selectGenderId = genderRadioButtons.getCheckedRadioButtonId();

        if(selectGenderId == -1)
        {
            Toast.makeText(CustomerRegister.this, "No Gender Selected!", Toast.LENGTH_LONG).show();
            return null;
        }

        String genderText = "F";
        if(selectGenderId == R.id.radio_male) {
            genderText = "M";
        }


        return new Customer(nameText, emailText, passwordText, null, genderText);
    }

    protected class insertCustomerTask extends AsyncTask<Customer, Void, Long> {
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
        protected Long doInBackground(Customer... customers) {
            FreshlyDB freshlyDB = FreshlyDB.getInstance(CustomerRegister.this);

            boolean emailExists = freshlyDB.customerDao().isEmailExists(customers[0].email);

            if (emailExists)
                return (long) -2;

            return freshlyDB.customerDao().insert(customers[0]);
        }

        @Override
        protected void onPostExecute(Long l) {
            super.onPostExecute(l);

            if(l.intValue() == -1)
            {
                Toast.makeText(CustomerRegister.this, "Customer Registration Failed!", Toast.LENGTH_LONG).show();
                Button loginButton = findViewById(R.id.customer_login_button);
                Button switchButton = findViewById(R.id.switch_to_vendor_login);
                TextView signUpText = findViewById(R.id.signup_text);

                loginButton.setEnabled(true);
                switchButton.setEnabled(true);
                signUpText.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent launchCustomerLoginActivity = new Intent(CustomerRegister.this, CustomerLogin.class);
                        startActivity(launchCustomerLoginActivity);
                    }
                });
                return;
            }

            if (l.intValue() == -2) {
                Toast.makeText(CustomerRegister.this, "Account with this Email already exists!", Toast.LENGTH_LONG).show();
                Button loginButton = findViewById(R.id.customer_login_button);
                Button switchButton = findViewById(R.id.switch_to_vendor_login);
                TextView signUpText = findViewById(R.id.signup_text);

                loginButton.setEnabled(true);
                switchButton.setEnabled(true);
                signUpText.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent launchCustomerLoginActivity = new Intent(CustomerRegister.this, CustomerLogin.class);
                        startActivity(launchCustomerLoginActivity);
                    }
                });
                return;
            }


            Intent launchCustomerLoginActivity = new Intent(CustomerRegister.this, CustomerLogin.class);
            launchCustomerLoginActivity.putExtra(IntentKeys.LoginToast, "Customer Registered Successfully!");
            startActivity(launchCustomerLoginActivity);
        }
    }
}