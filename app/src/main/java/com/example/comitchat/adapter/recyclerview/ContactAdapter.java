package com.example.comitchat.adapter.recyclerview;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.comitchat.R;
import com.example.comitchat.modal.ContactClass;

import java.util.List;

public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.MyViewHolder> {


    private Context context;
    private ContactClass contactClass;
    private List<ContactClass> contactClassList;

    public ContactAdapter(Context context, List<ContactClass> contactClassList) {
        this.context = context;
        this.contactClassList = contactClassList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view = LayoutInflater.from(context).inflate(R.layout.contacts_view_layout,viewGroup,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
        contactClass = contactClassList.get(i);
        myViewHolder.contactNameTextView.setText(contactClass.getName());
        myViewHolder.contactNumberTextView.setText(contactClass.getPhoneNumber());
    }

    @Override
    public int getItemCount() {
        return contactClassList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{

        TextView contactNameTextView, contactNumberTextView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            contactNameTextView = itemView.findViewById(R.id.contact_name);
            contactNumberTextView = itemView.findViewById(R.id.contact_number);
        }
    }



}
