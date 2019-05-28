package com.example.comitchat.fragments;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
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
import com.example.comitchat.modal.UserRegister;
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

import static android.content.Context.MODE_PRIVATE;


/**
 * A simple {@link Fragment} subclass.
 */
public class ContactFragment extends Fragment implements ContactAdapter.OnClickListener {

    private static final String TAG = Constant.appName + ContactFragment.class.getSimpleName();

    private List<DataItem> dataItemList;
    private ContactAdapter contactAdapter;
    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;
    private RegisterUserResponse registerUserResponse;
    private SharedPreferences sharedPreferences;
    private Gson gson;
    private RequestQueue mRequestQueue;
    private UserListResponse userListResponse;
    private UserRegister userRegister;


    public ContactFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_contact, container, false);
        dataItemList = new ArrayList<>();
        init(view);
        sharedPreferences = getContext().getSharedPreferences(Constant.appName, MODE_PRIVATE);
        if (sharedPreferences.contains(RegisterUserResponse.class.getSimpleName())) {
            String userDetails = sharedPreferences.getString(RegisterUserResponse.class.getSimpleName(), "");
            if (userDetails != "") {
                userRegister = (UserRegister) gson.fromJson(userDetails, UserRegister.class);
            }
        }
        getUserList();
        return view;
    }

    private void init(View view) {

        gson = new Gson();
        recyclerView = view.findViewById(R.id.contactFragmentRV);
        recyclerView.setHasFixedSize(true);
        linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);


    }


    public void setIntentDataUserRegister(RegisterUserResponse registerUserResponse) {
        this.registerUserResponse = registerUserResponse;
    }


    private void getUserList() {


        mRequestQueue = SingletonRequestQueue.getInstance(getContext()).getRequestQueue();
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET,
                Constant.GET_USER_LIST, null,
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        userListResponse = gson.fromJson(String.valueOf(response), UserListResponse.class);
                        DataItem removeRequired = null ;
                        for (DataItem dataItem : userListResponse.getData()) {
                            if(dataItem.getUid().equals(userRegister.getUid())){
                                removeRequired = dataItem;
                            }
                        }
                        Log.i(TAG, "onResponse: before remove"+userListResponse.getData().size());
                        userListResponse.getData().remove(removeRequired);
                        Log.i(TAG, "onResponse: after remove"+userListResponse.getData().size());

                        Log.d(TAG, response.toString());
                        contactAdapter = new ContactAdapter(getContext(), userListResponse.getData(), ContactFragment.this);
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
                .putExtra(DataItem.class.getSimpleName(), dataItem));
    }
}
