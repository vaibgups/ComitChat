package com.example.comitchat.fragments;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.comitchat.R;
import com.example.comitchat.activity.OneToOneChatActivity;
import com.example.comitchat.adapter.recyclerview.ContactAdapter;
import com.example.comitchat.modal.ContactClass;

import com.example.comitchat.modal.user.list.DataItem;
import com.example.comitchat.modal.user.list.UserListResponse;
import com.example.comitchat.modal.user.register.RegisterUserResponse;
import com.example.comitchat.singleton.SingletonRequestQueue;
import com.example.comitchat.utility.Constant;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 */
public class ContactFragment extends Fragment implements ContactAdapter.OnClickListener {

    private static final String TAG = Constant.appName + ContactFragment.class.getSimpleName();
    
    private ContactClass contactClass;
    private List<ContactClass> contactClassList;
    private ContactAdapter contactAdapter;
    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;
    private RegisterUserResponse registerUserResponse;
    private SharedPreferences sharedPreferences;
    private Gson gson;
    private RequestQueue mRequestQueue;
    private UserListResponse userListResponse;


    public ContactFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_contact, container, false);
        contactClassList = new ArrayList<>();
        /*sharedPreferences = getContext().getSharedPreferences(RegisterUserResponse.class.getSimpleName(), MODE_PRIVATE);
        if (sharedPreferences.contains(RegisterUserResponse.class.getSimpleName())) {
            String tempUserList = sharedPreferences.getString(RegisterUserResponse.class.getSimpleName(),"");
            if (tempUserList != ""){

            }

        }*/
        init(view);
        getUserList();

        
        return view;
    }

    private void init(View view) {

        gson = new Gson();
        recyclerView = view.findViewById(R.id.contactFragmentRV);
        recyclerView.setHasFixedSize(true);
        linearLayoutManager = new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);



    }


    public void setIntentDataUserRegister(RegisterUserResponse registerUserResponse){
        this.registerUserResponse = registerUserResponse;
    }


    private void getUserList() {


        mRequestQueue = SingletonRequestQueue.getInstance(getContext()).getRequestQueue();
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET,
                Constant.GET_USER_LIST, null,
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        userListResponse = gson.fromJson(String.valueOf(response),UserListResponse.class);
                        Log.d(TAG, response.toString());
                        contactAdapter = new ContactAdapter(getContext(),userListResponse.getData(), ContactFragment.this);
                        recyclerView.setAdapter(contactAdapter);

                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());

            }
        }) {

            /**
             * Passing some request headers
             * */
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
//                headers.put("Content-Type", "application/json");
                headers.put("apikey", Constant.API_KEY);
                headers.put("appid", Constant.APP_ID);
                return headers;
            }

        };

// Adding request to request queue
        mRequestQueue.add(jsonObjReq);

    }


    @Override
    public void recyclerOnClickListener(int pos) {
        DataItem dataItem = userListResponse.getData().get(pos);
        startActivity(new Intent(getContext(), OneToOneChatActivity.class)
        .putExtra(DataItem.class.getSimpleName(),dataItem));
    }
}
