package com.example.comitchat.activity;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;

import androidx.annotation.NonNull;
import com.google.android.material.tabs.TabLayout;
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

public class MainActivity extends AppCompatActivity {

    private static final String TAG = Constant.appName + MainActivity.class.getSimpleName();
    private Toolbar toolbar;
    private ViewPager viewPager;
    private ViewPagerAdapter viewPagerAdapter;
    private TabLayout tabLayout;
    private PermissionClass permissionClass;
    private RegisterUserResponse registerUserResponse;

    private RequestQueue mRequestQueue;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        permissionClass = new PermissionClass(this,this);
        permissionClass.contacts();
        getIntentData();

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
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.logout:
                sharedPreferences = getSharedPreferences(Constant.appName,MODE_PRIVATE);
                if (sharedPreferences.contains(RegisterUserResponse.class.getSimpleName())){
                    editor = sharedPreferences.edit();
                    editor.clear();
                    editor.commit();
                    startActivity(new Intent(MainActivity.this,LoginActivity.class));
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
