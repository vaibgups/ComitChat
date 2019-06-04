package com.example.comitchat.activity;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;

import androidx.annotation.NonNull;

import com.cometchat.pro.core.CometChat;
import com.cometchat.pro.exceptions.CometChatException;
import com.cometchat.pro.models.MediaMessage;
import com.cometchat.pro.models.MessageReceipt;
import com.cometchat.pro.models.TextMessage;
import com.example.comitchat.modal.UserRegister;
import com.example.comitchat.pojo.Message;
import com.example.comitchat.view.model.MessageEntityViewModel;
import com.google.android.material.tabs.TabLayout;

import androidx.lifecycle.ViewModelProviders;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.example.comitchat.R;
import com.example.comitchat.fragments.ChatFragment;
import com.example.comitchat.fragments.ContactFragment;

import com.example.comitchat.modal.user.register.RegisterUserResponse;
import com.example.comitchat.utility.Constant;
import com.example.comitchat.utility.PermissionClass;
import com.example.comitchat.adapter.viewpager.ViewPagerAdapter;
import com.google.gson.Gson;

public class MainActivity extends AppCompatActivity implements MessageEntityViewModel.InsertMessage {

    private static final String TAG = Constant.appName + MainActivity.class.getSimpleName();
    private Toolbar toolbar;
    private ViewPager viewPager;
    private ViewPagerAdapter viewPagerAdapter;
    private TabLayout tabLayout;
    private PermissionClass permissionClass;
    private RegisterUserResponse registerUserResponse;

    private Gson gson;
    private RequestQueue mRequestQueue;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private UserRegister userRegister;
    private Message message;
    private MessageEntityViewModel messageEntityViewModel;
    private String listenerID = "message_listener";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        gson = new Gson();
        permissionClass = new PermissionClass(this,this);
        permissionClass.contacts();
        getIntentData();
        messageEntityViewModel = ViewModelProviders.of(MainActivity.this).get(MessageEntityViewModel.class);


        sharedPreferences = getSharedPreferences(Constant.appName, MODE_PRIVATE);
        if (sharedPreferences.contains(RegisterUserResponse.class.getSimpleName())) {
            String userDetails = sharedPreferences.getString(RegisterUserResponse.class.getSimpleName(), "");
            if (userDetails != "") {
                userRegister = (UserRegister) gson.fromJson(userDetails, UserRegister.class);
            }
        }

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        viewPager = findViewById(R.id.viewPager);
        tabLayout = findViewById(R.id.tab);
        ChatFragment chatFragment = new ChatFragment();
        ContactFragment contactFragment = new ContactFragment();
        contactFragment.setIntentDataUserRegister(registerUserResponse);

        viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(),2);

        viewPagerAdapter.addFragment(chatFragment,"Chat");
        viewPagerAdapter.addFragment(contactFragment,"Contact");
        viewPager.setAdapter(viewPagerAdapter);

        tabLayout.setupWithViewPager(viewPager);

        Log.i(TAG, "onCreate: ");

    }

    private void getIntentData() {
        Intent intent = getIntent();
        if (intent.hasExtra(RegisterUserResponse.class.getSimpleName())){
            registerUserResponse = (RegisterUserResponse) intent.getSerializableExtra(RegisterUserResponse.class.getSimpleName());
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        switch (requestCode) {
            case Constant.REQUEST_CAMERA:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(this, "You have camera permission", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "You have not camera permission", Toast.LENGTH_SHORT).show();
                }
                break;
            case Constant.REQUEST_STORAGE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(this, "You have write external storage permission", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "You have not write external storage permission", Toast.LENGTH_SHORT).show();
                    break;
                }
            case Constant.REQUEST_CONTACTS:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(this, "You have contacts permission", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "You have not camera permission", Toast.LENGTH_SHORT).show();
                }
                break;


            case Constant.REQUEST_GROUP_PERMISSION:
                String result = "";
                int i = 0;
                for (String perm : permissions) {
                    String status = "";
                    if (grantResults[i] == PackageManager.PERMISSION_GRANTED)
                        status = "Granted";
                    else
                        status = "Decline";
                    result = result + "\n" + perm + " : " + status;
                    i++;

                }
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("Group Permission Details...");
                builder.setMessage(result);
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_activity_menu,menu);
        return true;
    }


    @Override
    protected void onResume() {
        super.onResume();

        CometChat.addMessageListener(userRegister.getUid(), new CometChat.MessageListener() {
            @Override
            public void onTextMessageReceived(TextMessage textMessage) {
//                CometChat.markMessageAsRead(textMessage);
                message = new Message();
                message.setMessage(textMessage.getText());
                message.setMyMsg(false);
                message.setEmail(textMessage.getSender().getEmail());
                message.setName(textMessage.getSender().getName());
                message.setFriendUid(textMessage.getSender().getUid());
                message.setMyUid(userRegister.getUid());
                if (message.getMyUid().equals(userRegister.getUid())) {
                    saveMessage(message);
//                    CometChat.markMessageAsRead(textMessage);

                }
                Log.d(TAG, "Text message received successfully: " + textMessage.toString());
            }
            @Override
            public void onMessageDelivered(MessageReceipt messageReceipt) {

                Log.e(TAG, "onMessageDelivered: " + messageReceipt.toString());
            }

            @Override
            public void onMessageRead(MessageReceipt messageReceipt) {
                Log.e(TAG, "onMessageRead: " + messageReceipt.toString());
            }

            @Override
            public void onMediaMessageReceived(MediaMessage mediaMessage) {
                Log.d(TAG, "Media message received successfully: " + mediaMessage.toString());
            }
        });
    }

    private void saveMessage(Message message) {
        com.example.comitchat.entity.Message messageEntity = new com.example.comitchat.entity.Message();
        try {
            String temp = gson.toJson(message);
            messageEntity = gson.fromJson(temp, com.example.comitchat.entity.Message.class);
            messageEntityViewModel.insertMessage(messageEntity,MainActivity.this);
        } catch (Exception e) {

        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.logout:
                sharedPreferences = getSharedPreferences(Constant.appName,MODE_PRIVATE);
                if (sharedPreferences.contains(RegisterUserResponse.class.getSimpleName())){
                    CometChat.logout(new CometChat.CallbackListener<String>() {
                        @Override
                        public void onSuccess(String s) {
                            editor = sharedPreferences.edit();
                            editor.clear();
                            editor.commit();
                            CometChat.removeMessageListener(listenerID);
                            Intent intent=new Intent(MainActivity.this, LoginActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                            finish();
                        }

                        @Override
                        public void onError(CometChatException e) {
                            Log.d(TAG, "onError: "+e.getMessage());
                        }
                    });
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void latestMessage(com.example.comitchat.entity.Message message) {
//        Toast.makeText(this, ""+message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        removeMessageListener();
    }

    private void removeMessageListener() {
        CometChat.removeMessageListener(listenerID);
    }


}
