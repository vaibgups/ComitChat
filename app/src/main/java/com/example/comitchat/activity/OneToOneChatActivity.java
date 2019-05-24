package com.example.comitchat.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

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
import com.example.comitchat.view.model.MessageEntityViewModel;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class OneToOneChatActivity extends AppCompatActivity implements View.OnClickListener, MessageEntityViewModel.InsertMessage {

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
    private List<com.example.comitchat.entity.Message> messageListDB = new ArrayList<>();
    private ChatScreenAdapter chatScreenAdapter;
    private int newMessageCount;
    private MessageEntityViewModel messageEntityViewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_one_to_one_chat);
        Intent intent = getIntent();
        if (intent.hasExtra(DataItem.class.getSimpleName())) {
            dataItem = (DataItem) intent.getSerializableExtra(DataItem.class.getSimpleName());
            receiverID = dataItem.getUid();

        }
        init();

        sharedPreferences = getSharedPreferences(Constant.appName, MODE_PRIVATE);
        if (sharedPreferences.contains(RegisterUserResponse.class.getSimpleName())) {
            String userDetails = sharedPreferences.getString(RegisterUserResponse.class.getSimpleName(), "");
            if (userDetails != "") {
                userRegister = (UserRegister) gson.fromJson(userDetails, UserRegister.class);
            }
        }

    }

    private void init() {

        chatScreenAdapter = new ChatScreenAdapter(messageListDB, OneToOneChatActivity.this);
        messageEntityViewModel = ViewModelProviders.of(OneToOneChatActivity.this).get(MessageEntityViewModel.class);
        messageEntityViewModel.getFriendMessage(receiverID).observe(OneToOneChatActivity.this, new Observer<List<com.example.comitchat.entity.Message>>() {
            @Override
            public void onChanged(List<com.example.comitchat.entity.Message> messages) {
                if (messages != null) {
                    messageListDB = messages;
//                    chatScreenAdapter.refreshMessage(messageListDB);
                    setScroll();
                }

            }
        });

      /*  try {

            BeanUtils.copyProperties(message,message1.getValue().get(0));
            Log.i(TAG, "init: "+message1.getValue().get(0).toString());
            Log.i(TAG, "init: "+message.toString());
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }catch (Exception e){

        }*/
        gson = new Gson();
        toolbarChatScreeName = findViewById(R.id.toolbar_chat_screen_name);
        toolbarChatScreeNumber = findViewById(R.id.toolbar_chat_screen_number);
        editTextChatMessage = findViewById(R.id.editTextChatMessage);
        ivBtnSend = findViewById(R.id.ivBtn_send);
        recyclerView = findViewById(R.id.rvChatMessages);
        linearLayoutManager = new LinearLayoutManager(OneToOneChatActivity.this, RecyclerView.VERTICAL, false);        //        linearLayoutManager.setReverseLayout(true);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(chatScreenAdapter);
        setScroll();
        ivBtnSend.setOnClickListener(this);

        toolbarChatScreeName.setText(dataItem.getName());
        toolbarChatScreeNumber.setText(receiverID);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ivBtn_send: {

                sendMessage();
            }
        }
    }

    private void sendMessage() {

        message = new Message();
        messageText = editTextChatMessage.getText().toString();
        editTextChatMessage.setText("");
        message.setMyMsg(true);
        message.setMessage(messageText);
        message.setMyUid(userRegister.getUid());
        message.setFriendUid(receiverID);
        message.setEmail(userRegister.getEmail());
        message.setName(userRegister.getName());
        saveMessage(message);
//        chatScreenAdapter.notifyDataSetChanged();
        TextMessage textMessage = new TextMessage(receiverID, messageText, messageType, receiverType);

        try {
            JSONObject jsonObject = new JSONObject("{localMessageId:"+message.getId()+"}");
            textMessage.setMetadata(jsonObject);
        } catch (JSONException e) {
            e.printStackTrace();
        }
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

    private void saveMessage(Message message) {
        com.example.comitchat.entity.Message messageEntity = new com.example.comitchat.entity.Message();
        try {
            String temp = gson.toJson(message);
            messageEntity = gson.fromJson(temp, com.example.comitchat.entity.Message.class);
            messageEntityViewModel.insertMessage(messageEntity,OneToOneChatActivity.this);
        } catch (Exception e) {

        }
    }

    private void setScroll() {
        if (chatScreenAdapter != null) {
            newMessageCount++;
            chatScreenAdapter.refreshMessage(messageListDB);

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

    @Override
    protected void onDestroy() {
        super.onDestroy();
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
                message.setFriendUid(textMessage.getSender().getUid());
                message.setMyUid(userRegister.getUid());
                saveMessage(message);
                Log.d(TAG, "Text message received successfully: " + textMessage.toString());
            }

            @Override
            public void onMediaMessageReceived(MediaMessage mediaMessage) {
                Log.d(TAG, "Media message received successfully: " + mediaMessage.toString());
            }
        });
    }

    @Override
    public void latestMessage(com.example.comitchat.entity.Message message) {
        Log.i(TAG, "latestMessage: "+message.toString());
        Toast.makeText(this, ""+message.toString(), Toast.LENGTH_SHORT).show();

    }
}
