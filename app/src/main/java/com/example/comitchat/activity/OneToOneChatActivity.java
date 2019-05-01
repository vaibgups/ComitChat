package com.example.comitchat.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.cometchat.pro.constants.CometChatConstants;
import com.cometchat.pro.core.CometChat;
import com.cometchat.pro.exceptions.CometChatException;
import com.cometchat.pro.models.MediaMessage;
import com.cometchat.pro.models.TextMessage;
import com.example.comitchat.R;
import com.example.comitchat.modal.UserRegister;
import com.example.comitchat.modal.user.list.DataItem;
import com.example.comitchat.modal.user.register.RegisterUserResponse;
import com.example.comitchat.utility.Constant;
import com.google.gson.Gson;

public class OneToOneChatActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = Constant.appName + OneToOneChatActivity.class.getSimpleName();

    private String messageType = CometChatConstants.MESSAGE_TYPE_TEXT;
    private String receiverType = CometChatConstants.RECEIVER_TYPE_USER;

    private DataItem dataItem;
    private String receiverID, messageText;

    private TextView toolbarChatScreeName, toolbarChatScreeNumber;
    private EditText editTextChatMessage;
    private ImageButton ivBtnSend;
    private String listenerID;
    private SharedPreferences sharedPreferences;
    private UserRegister userRegister;
    private Gson gson;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_one_to_one_chat);
        init();
        Intent intent = getIntent();
        if (intent.hasExtra(DataItem.class.getSimpleName())){
            dataItem = (DataItem) intent.getSerializableExtra(DataItem.class.getSimpleName());
            receiverID = dataItem.getUid();
            toolbarChatScreeName.setText(dataItem.getName());
            toolbarChatScreeNumber.setText(receiverID);
        }

        sharedPreferences = getSharedPreferences(Constant.appName,MODE_PRIVATE);
        if (sharedPreferences.contains(RegisterUserResponse.class.getSimpleName())){
           String userDetails = sharedPreferences.getString(RegisterUserResponse.class.getSimpleName(),"");
           if (userDetails != ""){
               userRegister = (UserRegister) gson.fromJson(userDetails,UserRegister.class);
           }
        }



    }

    private void init() {

        gson = new Gson();
        toolbarChatScreeName = findViewById(R.id.toolbar_chat_screen_name);
        toolbarChatScreeNumber = findViewById(R.id.toolbar_chat_screen_number);
        editTextChatMessage = findViewById(R.id.editTextChatMessage);
        ivBtnSend = findViewById(R.id.ivBtn_send);
        ivBtnSend.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ivBtn_send:
            {

                sendMessage();
            }
        }
    }

    private void sendMessage() {

        messageText =  editTextChatMessage.getText().toString();
        TextMessage textMessage = new TextMessage(receiverID, messageText, messageType, receiverType);

        CometChat.sendMessage(textMessage, new CometChat.CallbackListener<TextMessage>() {
            @Override
            public void onSuccess(TextMessage textMessage) {
                Log.d(TAG, "Message sent successfully: " + textMessage.toString());
            }
            @Override
            public void onError(CometChatException e) {
                Log.d(TAG, "Message sending failed with exception: " + e.getMessage());
            }
        });
    }


    @Override
    protected void onResume() {
        super.onResume();
        receivedMessage();
    }

    private void receivedMessage() {
        CometChat.addMessageListener(userRegister.getUid(), new CometChat.MessageListener() {
            @Override
            public void onTextMessageReceived(TextMessage textMessage) {
                Log.d(TAG, "Text message received successfully: " + textMessage.toString());
            }
            @Override
            public void onMediaMessageReceived(MediaMessage mediaMessage) {
                Log.d(TAG, "Media message received successfully: " + mediaMessage.toString());
            }
        });
    }
}
