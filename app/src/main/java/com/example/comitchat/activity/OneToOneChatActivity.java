package com.example.comitchat.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.cometchat.pro.constants.CometChatConstants;
import com.cometchat.pro.core.CometChat;
import com.cometchat.pro.exceptions.CometChatException;
import com.cometchat.pro.models.TextMessage;
import com.example.comitchat.R;
import com.example.comitchat.modal.user.list.DataItem;
import com.example.comitchat.utility.Constant;

public class OneToOneChatActivity extends AppCompatActivity {

    private static final String TAG = Constant.appName + OneToOneChatActivity.class.getSimpleName();

    private String messageType = CometChatConstants.MESSAGE_TYPE_TEXT;
    private String receiverType = CometChatConstants.RECEIVER_TYPE_USER;

    private DataItem dataItem;
    private String receiverID, messageText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_one_to_one_chat);
        Intent intent = getIntent();
        if (intent.hasExtra(DataItem.class.getSimpleName())){
            dataItem = (DataItem) intent.getSerializableExtra(DataItem.class.getSimpleName());
            receiverID = dataItem.getUid();
        }



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
}
