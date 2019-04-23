package com.example.comitchat.fragments;


import android.content.ContentResolver;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.comitchat.R;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class ContactFragment extends Fragment {

    private static final String TAG = R.string.app_name + ContactFragment.class.getSimpleName();


    public ContactFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_contact, container, false);
        return view;
    }


    public void newContactUpdateInDB() {
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
