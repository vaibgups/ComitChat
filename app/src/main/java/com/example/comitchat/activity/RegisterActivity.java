package com.example.comitchat.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.comitchat.R;
import com.example.comitchat.modal.UserRegister;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class RegisterActivity extends AppCompatActivity {


    private TextInputLayout textInput_first_name, textInput_last_name, textInput_email_id, textInput_mobile_number, textInput_select_gender;
    private TextInputEditText textInputEdit_name, textInputEdit_number, textInputEdit_emailId;
    private Button registerUser;

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
        textInputEdit_number= findViewById(R.id.textInputEdit_number);
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
        UserRegister userRegister = new UserRegister();
        userRegister.setUid(number);
        userRegister.setName(name);
        userRegister.setEmail(email);



    }



}
