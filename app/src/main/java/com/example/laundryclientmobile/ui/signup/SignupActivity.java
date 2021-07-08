package com.example.laundryclientmobile.ui.signup;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.laundryclientmobile.MainActivity;
import com.example.laundryclientmobile.R;
import com.example.laundryclientmobile.apiconnection.Customer;
import com.example.laundryclientmobile.apiconnection.RequestHandler;
import com.example.laundryclientmobile.apiconnection.SharedPrefManager;
import com.example.laundryclientmobile.apiconnection.URLs;
import com.example.laundryclientmobile.ui.extra.PreSelectShopActivity;
import com.example.laundryclientmobile.ui.login.LoginActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class SignupActivity extends AppCompatActivity {

    EditText editTextFullCustomerName, editTextCustomerPhoneNumber, editTextCustomerEmail, editTextCustomerAddress, editTextCustomerUsername, editTextCustomerPassword1, editTextCustomerPassword2;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
         super.onCreate(savedInstanceState);
         setContentView(R.layout.activity_signup);

         //if the user is already logged in we will directly start the profile activity
         if (SharedPrefManager.getInstance(this).isLoggedIn()) {
            finish();
            startActivity(new Intent(this, MainActivity.class));
            return;
         }

         editTextFullCustomerName = findViewById(R.id.editTextFullName);
         editTextCustomerPhoneNumber = findViewById(R.id.editTextCustomerPhoneNumber);
         editTextCustomerEmail = findViewById(R.id.editTextEmailAddress);
         editTextCustomerAddress = findViewById(R.id.editTextCustomerAddress);
         editTextCustomerUsername = findViewById(R.id.editTextCustomerUsername);
         editTextCustomerPassword1 = findViewById(R.id.editTextPassword1);
         editTextCustomerPassword2 = findViewById(R.id.editTextPassword2);
         progressBar = findViewById(R.id.progressBarSignUp);

         findViewById(R.id.buttonRegister).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //if user pressed on button register
                //here we will register the user to server
                registerUser();
            }
        });

        findViewById(R.id.buttonAlreadyHaveAnAccount).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //if user pressed on login
                //we will open the login screen
                finish();
                startActivity(new Intent(SignupActivity.this, LoginActivity.class));
            }
        });
    }

    private void registerUser() {
        final String customer_name = editTextFullCustomerName.getText().toString().trim();
        final String customer_phone_number = "";
        final String customer_email = editTextCustomerEmail.getText().toString().trim();
        final String customer_address = "";
        final String customer_username = "";
        final String customer_password = editTextCustomerPassword1.getText().toString().trim();
        final String customer_password2 = editTextCustomerPassword2.getText().toString().trim();

        System.out.println(customer_name);
        //first we will do the validations

        if (TextUtils.isEmpty(customer_name)) {
            editTextFullCustomerName.setError("Please enter username");
            editTextFullCustomerName.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(customer_email)) {
            editTextCustomerEmail.setError("Please enter your email");
            editTextCustomerEmail.requestFocus();
            return;
        }

        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(customer_email).matches()) {
            editTextCustomerEmail.setError("Enter a valid email");
            editTextCustomerEmail.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(customer_password)) {
            editTextCustomerPassword1.setError("Enter a password");
            editTextCustomerPassword1.requestFocus();
            return;
        }

        if (customer_password == customer_password2){
            editTextCustomerPassword2.setError("Password not Match");
            editTextCustomerPassword2.requestFocus();
            return;
        }

        //if it passes all the validations

        class RegisterUser extends AsyncTask<Void, Void, String> {

            private ProgressBar progressBar;

            @Override
            protected String doInBackground(Void... voids) {
                //creating request handler object
                RequestHandler requestHandler = new RequestHandler();

                //creating request parameters
                HashMap<String, String> params = new HashMap<>();
                params.put("customer_name", customer_name);
                params.put("customer_phone_number", customer_phone_number);
                params.put("customer_email", customer_email);
                params.put("customer_address", customer_address);
                params.put("customer_username", customer_username);
                params.put("customer_password", customer_password);

                //returing the response
                return requestHandler.sendPostRequest(URLs.URL_REGISTER, params);
            }

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                //displaying the progress bar while user registers on the server
                progressBar = findViewById(R.id.progressBarSignUp);
                progressBar.setVisibility(View.VISIBLE);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                //hiding the progressbar after completion
                progressBar.setVisibility(View.GONE);

                try {
                    //converting response to json object
                    JSONObject obj = new JSONObject(s);

                    //if no error in response
                    if (!obj.getBoolean("error")) {
                        Toast.makeText(getApplicationContext(), obj.getString("message"), Toast.LENGTH_SHORT).show();

                        //getting the user from the response
                        JSONObject userJson = obj.getJSONObject("customers");

                        //creating a new user object
                        Customer customer = new Customer(
                                userJson.getInt("id"),
                                userJson.getString("customer_name"),
                                userJson.getString("customer_phone_number"),
                                userJson.getString("customer_email"),
                                userJson.getString("customer_address"),
                                userJson.getString("customer_username"),
                                userJson.getString("customer_password")
                        );

                        //storing the user in shared preferences
                        SharedPrefManager.getInstance(getApplicationContext()).userLogin(customer);

                        //starting the profile activity
                        finish();
                        startActivity(new Intent(getApplicationContext(), PreSelectShopActivity.class));
                    } else {
                        Toast.makeText(getApplicationContext(), "Some error occurred", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }

        //executing the async task
        RegisterUser ru = new RegisterUser();
        ru.execute();
    }
}