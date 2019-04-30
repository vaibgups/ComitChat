package com.example.comitchat.activity;

import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.comitchat.R;
import com.example.comitchat.fragments.ChatFragment;
import com.example.comitchat.fragments.ContactFragment;

import com.example.comitchat.modal.user.register.RegisterUserResponse;
import com.example.comitchat.singleton.SingletonRequestQueue;
import com.example.comitchat.utility.Constant;
import com.example.comitchat.utility.PermissionClass;
import com.example.comitchat.adapter.viewpager.ViewPagerAdapter;

import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = Constant.appName + MainActivity.class.getSimpleName();
    private Toolbar toolbar;
    private ViewPager viewPager;
    private ViewPagerAdapter viewPagerAdapter;
    private TabLayout tabLayout;
    private PermissionClass permissionClass;
    private RegisterUserResponse registerUserResponse;

    private RequestQueue mRequestQueue;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        permissionClass = new PermissionClass(this,this);
        permissionClass.contacts();
        getIntentData();
//        test();

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




    private void test() {

        mRequestQueue = SingletonRequestQueue.getInstance(MainActivity.this).getRequestQueue();
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
                "http://demoarea.1akal.in/IMD_NCS/MIS/api/mobile_service/rise/", null,
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d(TAG, response.toString());


                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "onErrorResponse: " + error.getMessage());
                VolleyLog.d(TAG, "Error: " + error.getMessage());

            }
        }) {

            /**
             * Passing some request headers
             * */

        };

// Adding request to request queue
        mRequestQueue.add(jsonObjReq);

    }





}
