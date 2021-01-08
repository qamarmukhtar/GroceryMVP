package com.techqamar.myapplication;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.techqamar.myapplication.CommonUtils.Urls;
import com.techqamar.myapplication.CommonUtils.Utils;
import com.techqamar.myapplication.CommonUtils.VolleySingleton;


import org.json.JSONObject;

public class Register_activity extends AppCompatActivity {

    Button btnRegister;
    private EditText edtname, edtnumber, edtemail, edtAddress, edtCity, edtPostalcode;
    public String check, compare_string;
//    TextView loginText;
    private String email, id, username, address,phone;
    String ADDRESS = null;

    private RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_activity);

        SharedPreferences sh = Register_activity.this.getSharedPreferences("profiledata", Context.MODE_PRIVATE);
        String profData = sh.getString("Loginresponse", "");
        System.out.println("profData"+profData);

        if (profData != null && !profData.equals("")) {
            finish();
            Intent intent = new Intent(Register_activity.this, MainItemList.class);
            startActivity(intent);
        }

        requestQueue = Volley.newRequestQueue(Register_activity.this);//Creating the RequestQueue

        edtname = findViewById(R.id.editTextName);
        edtnumber = findViewById(R.id.editTextMobile);
        edtemail = findViewById(R.id.editTextEmail);
        edtAddress = findViewById(R.id.editTextAddress);
        edtCity = findViewById(R.id.editTextCity);
        edtPostalcode = findViewById(R.id.editTextPostalcode);
//        loginText = findViewById(R.id.loginText);

        edtname.addTextChangedListener(nameWatcher);
        edtnumber.addTextChangedListener(numberWatcher);
        edtemail.addTextChangedListener(emailWatcher);
        edtAddress.addTextChangedListener(AddressWatcher);
        edtCity.addTextChangedListener(CityWatcher);
        edtPostalcode.addTextChangedListener(PostalcodeWatcher);

        btnRegister = findViewById(R.id.cirRegisterButton);
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                if (validateName() && validateEmail() && validateAddress() && validateCity() && validateNumber() && validatePincode()) {
//
                    ADDRESS = (edtAddress.getText().toString() +"\n"+ edtCity.getText().toString() +"\n"+ edtPostalcode.getText().toString());

                    CheckCredentials(edtemail.getText().toString(), ADDRESS, edtname.getText().toString(), edtnumber.getText().toString());
//                }

            }
        });
//        loginText.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent i = new Intent(Register_activity.this, MainItemList.class);
//                startActivity(i);
//                finish();
//            }
//        });
    }


    private void CheckCredentials(final String userName, final String Address, final String name, final String phoneno) {

        String urlForLogin = String.format(Urls.REGISTRATION, name, userName, phoneno, Address);

        Log.e("url", "url" + urlForLogin);


        StringRequest stringRequest = new StringRequest(Request.Method.GET, urlForLogin,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject Object = new JSONObject(response);
                            compare_string = Object.getString("message");
                            System.out.println("compare_string" + compare_string);
                            System.out.println("Output" + response);
                            JSONObject jsonObject = Object.getJSONObject("user");
//                            branchid = Object.getString("branchid");
                            System.out.println("Output" + response);
//                            compare_string = Object.getString("message");
                            id = jsonObject.getString("_id");
                            username = jsonObject.getString("name");
                            System.out.println("username" + username);
                            email = jsonObject.getString("email_id");
                            System.out.println("email" + email);
                            phone = jsonObject.getString("mobile_no");
                            System.out.println("phoneno" + phone);
                            address = jsonObject.getString("address");
                            System.out.println("phoneno" + address);


                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                        if (compare_string.equals("Invalid API Call")) {
                            btnRegister.setVisibility(View.VISIBLE);
//                            spinKitView.setVisibility(View.INVISIBLE);
                            Toast.makeText(Register_activity.this, "Check all Fields Username / Password", Toast.LENGTH_SHORT).show();
                        } else {

                            Log.e("RES.......", "..............................." + response);

                            Toast.makeText(Register_activity.this, "Registration  Successful", Toast.LENGTH_SHORT).show();
                            SharedPreferences sharedpreferences = Register_activity.this.getSharedPreferences("profiledata", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedpreferences.edit();
                            editor.putString("Loginresponse", response);
                            editor.putString("id", id);
                            editor.putString("username", username);
                            editor.putString("phoneno", phoneno);
                            editor.putString("email", email);
                            editor.putString("address", address);
                            editor.commit();

                            Intent i = new Intent(Register_activity.this, MainItemList.class);
                            startActivity(i);
                            finish();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                btnRegister.setVisibility(View.VISIBLE);
//                spinKitView.setVisibility(View.INVISIBLE);
                Log.e("ERROR LoginActivity", error.toString());
            }
        });

        Utils.setVolleyRetryPolicy(stringRequest);
        VolleySingleton.getInstance(Register_activity.this).addToRequestQueue(stringRequest, "Login");
    }

    private boolean validateNumber() {

        check = edtnumber.getText().toString();
        Log.e("inside number", check.length() + " ");
        if (check.length() > 10) {
            return false;
        } else if (check.length() < 10) {
            return false;
        }
        return true;
    }

    private boolean validatePincode() {

        check = edtnumber.getText().toString();
        Log.e("inside number", check.length() + " ");
        if (check.length() > 6) {
            return false;
        } else if (check.length() < 6) {
            return false;
        }
        return true;
    }

    private boolean validateCity() {

//        check = edtcnfpass.getText().toString();
//
//        return check.equals(edtAddress.getText().toString());

        check = edtCity.getText().toString();

        if (check.length() < 4 || check.length() > 20) {
            return false;
        }
        return true;
    }

    private boolean validateAddress() {


        check = edtAddress.getText().toString();

        if (check.length() < 4 || check.length() > 20) {
            return false;
        }
//        else if (!check.matches("^[A-za-z0-9@]+")) {
//            return false;
//        }
        return true;
    }

    private boolean validateEmail() {

        check = edtemail.getText().toString();

        if (check.length() < 4 || check.length() > 40) {
            return false;
        } else if (!check.matches("^[A-za-z0-9.@]+")) {
            return false;
        } else if (!check.contains("@") || !check.contains(".")) {
            return false;
        }

        return true;
    }


    private boolean validateName() {

        check = edtname.getText().toString();

        return !(check.length() < 4 || check.length() > 20);

    }

    //TextWatcher for Name -----------------------------------------------------

    TextWatcher nameWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            //none
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            //none
        }

        @Override
        public void afterTextChanged(Editable s) {

            check = s.toString();

            if (check.length() < 4 || check.length() > 20) {
                edtname.setError("Name Must consist of 4 mngf   Z FX |to 20 characters");
            }
        }

    };

    //TextWatcher for Email -----------------------------------------------------

    TextWatcher emailWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            //none
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            //none
        }

        @Override
        public void afterTextChanged(Editable s) {

            check = s.toString();

            if (check.length() < 4 || check.length() > 40) {
                edtemail.setError("Email Must consist of 4 to 20 characters");
            } else if (!check.matches("^[A-za-z0-9.@]+")) {
                edtemail.setError("Only . and @ characters allowed");
            } else if (!check.contains("@") || !check.contains(".")) {
                edtemail.setError("Enter Valid Email");
            }

        }

    };

    //TextWatcher for pass -----------------------------------------------------

    TextWatcher AddressWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            //none
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            //none
        }

        @Override
        public void afterTextChanged(Editable s) {

            check = s.toString();

            if (check.length() < 8 || check.length() > 40) {
                edtAddress.setError("Address Must consist of 8 to 20 characters");
            }
//            else if (!check.matches("^[A-za-z0-9@]+")) {
//                edtemail.setError("Only @ special character allowed");
//            }
        }

    };

    //TextWatcher for repeat Password -----------------------------------------------------

    TextWatcher CityWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            //none
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            //none
        }

        @Override
        public void afterTextChanged(Editable s) {

            check = s.toString();

            if (check.length() < 2 || check.length() > 20) {
                edtCity.setError("City Name not empty");
            }
        }

    };


    //TextWatcher for Pin code -----------------------------------------------------

    TextWatcher numberWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            //none
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            //none
        }

        @Override
        public void afterTextChanged(Editable s) {

            check = s.toString();

            if (check.length() > 10) {
                edtnumber.setError("Number cannot be grated than 10 digits");
            } else if (check.length() < 10) {
                edtnumber.setError("Number should be 10 digits");
            }
        }

    };
    //TextWatcher for Mobile -----------------------------------------------------

    TextWatcher PostalcodeWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            //none
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            //none
        }

        @Override
        public void afterTextChanged(Editable s) {

            check = s.toString();

            if (check.length() > 6) {
                edtPostalcode.setError("Pin Code Number cannot be grated than 6 digits");
            } else if (check.length() < 6) {
                edtPostalcode.setError("Number should be 6 digits");
            }
        }

    };
}