package com.example.comitchat.activity;

import android.content.Intent;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import com.cometchat.pro.core.CometChat;
import com.cometchat.pro.exceptions.CometChatException;
import com.cometchat.pro.models.User;
import com.example.comitchat.R;
import com.example.comitchat.utility.Constant;

public class LoginActivity extends AppCompatActivity {

    private static final String TAG = Constant.appName+LoginActivity.class.getSimpleName();

    private TextInputLayout textInputLayoutUid;

    private TextInputEditText textInputEditTextUid;

    private Button login,signUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        init();
    }

    private void init() {
        textInputLayoutUid = findViewById(R.id.input_layout_uid);
        textInputEditTextUid = findViewById(R.id.guid);
        login = findViewById(R.id.btn_login);
        signUp = findViewById(R.id.btn_sing_up);
        login.setOnClickListener(view -> login());
        signUp.setOnClickListener(view -> signUp());
        Log.i(TAG, "init: ");
    }




    private void login() {
        String uid = textInputEditTextUid.getText().toString().trim();

        if (!uid.isEmpty()) {
            Toast.makeText(LoginActivity.this, getString(R.string.wait), Toast.LENGTH_SHORT).show();
//            loginActivityPresenter.Login(uid);
            Login(uid);
        } else {

            Toast.makeText(LoginActivity.this, getString(R.string.enter_uid_toast), Toast.LENGTH_SHORT).show();
        }

    }

    private void signUp() {
        startActivity(new Intent(this,RegisterActivity.class));
    }

    public void Login(String uid) {

        Log.i(TAG, "Login: with user id : "+uid);
        CometChat.login(uid, Constant.API_KEY, new CometChat.CallbackListener<User>() {

            @Override
            public void onSuccess(User user) {
                Log.i(TAG, "onSuccess: "+user.toString());
                    startActivity(new Intent(LoginActivity.this,MainActivity.class));
                    finish();
            }

            @Override
            public void onError(CometChatException e) {
                Log.e(TAG, "onError: "+e.getMessage() );
                e.printStackTrace();
            }

        });
    }

}
