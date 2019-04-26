package com.example.comitchat.fragments;


import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.comitchat.R;
import com.example.comitchat.adapter.recyclerview.ContactAdapter;
import com.example.comitchat.modal.ContactClass;
import com.example.comitchat.modal.register.user.response.RegisterUserResponse;
import com.example.comitchat.utility.Constant;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class ContactFragment extends Fragment {

    private static final String TAG = Constant.appName + ContactFragment.class.getSimpleName();
    
    private ContactClass contactClass;
    private List<ContactClass> contactClassList;
    private ContactAdapter contactAdapter;
    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;
    private RegisterUserResponse registerUserResponse;


    public ContactFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_contact, container, false);
        contactClassList = new ArrayList<>();
        newContactUpdateInDB();
        init(view);
        Log.i(TAG, "onCreateView: registerUserResponse "+registerUserResponse.toString());

        
        return view;
    }

    private void init(View view) {

        recyclerView = view.findViewById(R.id.contactFragmentRV);
        contactAdapter = new ContactAdapter(getContext(),contactClassList);
        recyclerView.setHasFixedSize(true);
        linearLayoutManager = new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(contactAdapter);


    }


    public void newContactUpdateInDB() {
        Log.i(TAG, "newContactUpdateInDB: ");
        ContentResolver cr = getContext().getContentResolver();
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
            contactClass = new ContactClass();
            contactClass.setName(name);
            contactClass.setPhoneNumber(phoneNumber);
            contactClassList.add(contactClass);
            

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

    public void setIntentDataUserRegister(RegisterUserResponse registerUserResponse){
        this.registerUserResponse = registerUserResponse;
    }

  /*  private void getIntentData() {
        Bundle bundle = getArguments();
        Intent intent =
        if (intent.hasExtra(RegisterUserResponse.class.getSimpleName())){
            registerUserResponse = (RegisterUserResponse) intent.getSerializableExtra(RegisterUserResponse.class.getSimpleName());
        }
    }*/




}
