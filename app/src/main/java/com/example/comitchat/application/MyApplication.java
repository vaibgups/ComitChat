package com.example.comitchat.application;

import android.app.Application;
import android.util.Log;

import com.cometchat.pro.core.CometChat;
import com.cometchat.pro.exceptions.CometChatException;
import com.example.comitchat.utility.Constant;

public class MyApplication extends Application {
    private static final String TAG = Constant.appName+MyApplication.class.getSimpleName();

    @Override
    public void onCreate() {
        super.onCreate();
        Log.i(TAG, "onCreate: ");
        CometChat.init(this, Constant.APP_ID, new CometChat.CallbackListener<String>() {
            @Override
            public void onSuccess(String successMessage) {
                Log.i(TAG, "Initialization completed successfully");
            }
            @Override
            public void onError(CometChatException e) {
                Log.i(TAG, "Initialization failed with exception: " + e.getMessage());
            }
        });

    }
}
