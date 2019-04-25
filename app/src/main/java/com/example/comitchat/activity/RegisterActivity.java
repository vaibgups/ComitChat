package com.example.comitchat.activity;

import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.comitchat.R;
import com.example.comitchat.modal.UserRegister;
import com.example.comitchat.modal.register.user.response.RegisterUserResponse;
import com.example.comitchat.singleton.SingletonRequestQueue;
import com.example.comitchat.utility.Constant;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity {

    private static final String TAG = Constant.appName + RegisterActivity.class.getSimpleName();

    private TextInputLayout textInput_first_name, textInput_last_name, textInput_email_id, textInput_mobile_number, textInput_select_gender;
    private TextInputEditText textInputEdit_name, textInputEdit_number, textInputEdit_emailId;
    private Button registerUser;
    private UserRegister userRegister;
    private RequestQueue mRequestQueue;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        init();
    }

    private void init() {

        textInput_first_name = findViewById(R.id.textInput_name);
        textInput_email_id = findViewById(R.id.textInput_emailId);
        textInput_mobile_number = findViewById(R.id.textInput_number);
        textInputEdit_name = findViewById(R.id.textInputEdit_name);
        textInputEdit_number = findViewById(R.id.textInputEdit_number);
        textInputEdit_emailId = findViewById(R.id.textInputEdit_emailId);
        registerUser = findViewById(R.id.btn_register);
        registerUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validateUserData();
            }
        });
    }

    private void validateUserData() {
        String name = "", number = "", email = "";
        name = textInputEdit_name.getText().toString();
        number = textInputEdit_number.getText().toString();
        email = textInputEdit_emailId.getText().toString();
        userRegister = new UserRegister();
        userRegister.setUid(number);
        userRegister.setName(name);
        userRegister.setEmail(email);
        postRegisterUser(userRegister);
    }

    private void postRegisterUser(UserRegister userRegister) {
        Gson gson = new Gson();
        String jsonObjectString = gson.toJson(userRegister);
        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(jsonObjectString);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        mRequestQueue = SingletonRequestQueue.getInstance(RegisterActivity.this).getRequestQueue();
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
                Constant.CREATE_USER, jsonObject,
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d(TAG, response.toString());
                        try {
                            JSONObject object = response.getJSONObject("data");
                            RegisterUserResponse registerUserResponse = gson.fromJson(String.valueOf(object),RegisterUserResponse.class);
                            Log.i(TAG, "onResponse: registerUserResponse \n"+registerUserResponse.toString() );
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "onErrorResponse: "+error.getMessage() );
                VolleyLog.d(TAG, "Error: " + error.getMessage());

            }
        }) {

            /**
             * Passing some request headers
             * */
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
//                headers.put("Content-Type", "application/json");
                headers.put("apikey", Constant.API_KEY);
                headers.put("appid", Constant.APP_ID);
                return headers;
            }

        };

// Adding request to request queue
        mRequestQueue.add(jsonObjReq);

    }


}
