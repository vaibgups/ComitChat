package com.example.comitchat;

import android.Manifest;
import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.comitchat.fragments.ChatFragment;
import com.example.comitchat.fragments.ContactFragment;
import com.example.comitchat.utility.Constant;
import com.example.comitchat.utility.PermissionClass;
import com.example.comitchat.utility.PermissionUtil;
import com.example.comitchat.viewpager.adapter.ViewPagerAdapter;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = Constant.appName + MainActivity.class.getSimpleName();
    private Toolbar toolbar;
    private ViewPager viewPager;
    private ViewPagerAdapter viewPagerAdapter;
    private TabLayout tabLayout;
    private PermissionClass permissionClass;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        permissionClass = new PermissionClass(this,this);
        permissionClass.contacts();
        newContactUpdateInDB();


        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        viewPager = findViewById(R.id.viewPager);
        tabLayout = findViewById(R.id.tab);
        ChatFragment chatFragment = new ChatFragment();
        ContactFragment contactFragment = new ContactFragment();

        viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(),2);
        viewPagerAdapter.addFragment(chatFragment,"Chat");
        viewPagerAdapter.addFragment(contactFragment,"Contact");
        viewPager.setAdapter(viewPagerAdapter);

        tabLayout.setupWithViewPager(viewPager);

        Log.i(TAG, "onCreate: ");

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
                    newContactUpdateInDB();
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



    public void newContactUpdateInDB() {
        ContentResolver cr = getApplicationContext().getContentResolver();
        ArrayList<String> temp = new ArrayList<String>();

        StringBuilder contacts = new StringBuilder();
        Cursor phones = cr.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null, null, null);
        // use the cursor to access the contacts
        while (phones.moveToNext()) {
            String name = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
            String phoneNumber = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
            String phnnumber;
            phnnumber = phoneNumber.replaceAll("[\\s\\-()]", "");
            phnnumber = phnnumber.replace("+91", "");
            phnnumber = phnnumber.replaceFirst("^0+(?!$)", "");
            if (name == "") {
                name = "+91" + phnnumber;
            }
            Log.i(TAG, "newContactUpdateInDB: " + name);

           /* if(oldContacts.contains(phnnumber)) {
                temp.add(phnnumber);
            }else{
                oldContacts.add(phnnumber);
                int muteUnmuteFlag = 1;  // this muteUnmuteFlag 1 is use for ring the phone
                int blockUnblockFlag = 1; // this blockUnblockFlag 1 is use for block the particular
                // contacts so that block contact does not send and received msg


                // imSecureDBAdapter.contactsUpdate(name, phnnumber, null, null);
                imSecureDBAdapter.saveRecord(name, phnnumber, null, null,muteUnmuteFlag,blockUnblockFlag);
            }
            imSecureDBAdapter.upDateContactsNameFromPhoneContactsBook(name,phnnumber);
        }
        oldContacts.removeAll(temp);
        for(String tempName : oldContacts){
            imSecureDBAdapter.upDateContactsNameFromPhoneContactsBook(tempName,tempName);
        }*/

        }


    }








}
