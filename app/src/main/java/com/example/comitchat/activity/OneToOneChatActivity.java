package com.example.comitchat.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.PorterDuff;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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
import com.example.comitchat.adapter.recyclerview.ChatScreenAdapter;
import com.example.comitchat.modal.UserRegister;
import com.example.comitchat.modal.user.list.DataItem;
import com.example.comitchat.modal.user.register.RegisterUserResponse;
import com.example.comitchat.pojo.Message;
import com.example.comitchat.utility.Constant;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

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
    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;
    private Message message;
    private List<Message> messageList;
    private ChatScreenAdapter chatScreenAdapter;
    private int newMessageCount;


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
        messageList = new ArrayList<Message>();
        chatScreenAdapter = new ChatScreenAdapter(messageList,OneToOneChatActivity.this);
        toolbarChatScreeName = findViewById(R.id.toolbar_chat_screen_name);
        toolbarChatScreeNumber = findViewById(R.id.toolbar_chat_screen_number);
        editTextChatMessage = findViewById(R.id.editTextChatMessage);
        ivBtnSend = findViewById(R.id.ivBtn_send);
        recyclerView = findViewById(R.id.rvChatMessages);
        linearLayoutManager = new LinearLayoutManager(OneToOneChatActivity.this,LinearLayoutManager.VERTICAL, false);        //        linearLayoutManager.setReverseLayout(true);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(chatScreenAdapter);
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

        message = new Message();
        messageText =  editTextChatMessage.getText().toString();
        editTextChatMessage.setText("");
        message.setMyMsg(true);
        message.setMessage(messageText);
        message.setUid(userRegister.getUid());
        message.setEmail(userRegister.getEmail());
        message.setName(userRegister.getName());
        messageList.add(message);
        chatScreenAdapter.notifyDataSetChanged();
        setScroll();
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

    private void setScroll() {
        if (chatScreenAdapter != null) {
            newMessageCount++;
            chatScreenAdapter.refreshMessage(messageList);

            if ((chatScreenAdapter.getItemCount() - 1) - linearLayoutManager.findLastVisibleItemPosition() < 5) {
                recyclerView.scrollToPosition(chatScreenAdapter.getItemCount() - 1);
                newMessageCount = 0;
            }
        }
    }


    @Override
    protected void onResume() {
        super.onResume();
        receivedMessage();
    }

    @Override
    protected void onPause() {
        super.onPause();
        CometChat.removeMessageListener(listenerID);
    }

    private void receivedMessage() {
        CometChat.addMessageListener(userRegister.getUid(), new CometChat.MessageListener() {
            @Override
            public void onTextMessageReceived(TextMessage textMessage) {
                message = new Message();
                message.setMessage(textMessage.getText());
                message.setMyMsg(false);
                message.setEmail(textMessage.getSender().getEmail());
                message.setName(textMessage.getSender().getName());
                message.setUid(textMessage.getSender().getUid());
                messageList.add(message);
                chatScreenAdapter.notifyDataSetChanged();
                setScroll();
                Log.d(TAG, "Text message received successfully: " + textMessage.toString());
            }
            @Override
            public void onMediaMessageReceived(MediaMessage mediaMessage) {
                Log.d(TAG, "Media message received successfully: " + mediaMessage.toString());
            }
        });
    }
}
