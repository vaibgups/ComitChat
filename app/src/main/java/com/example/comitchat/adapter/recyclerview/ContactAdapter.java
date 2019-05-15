package com.example.comitchat.adapter.recyclerview;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.comitchat.R;
import com.example.comitchat.modal.user.list.DataItem;

import java.util.List;

public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.MyViewHolder> {


    private Context context;
//    private ContactClass contactClass;
    private DataItem contactClass;
    private  List<DataItem>  contactClassList;
    private OnClickListener onClickListener;

    public ContactAdapter(Context context, List<DataItem> contactClassList, OnClickListener onClickListener) {
        this.context = context;
        this.contactClassList = contactClassList;
        this.onClickListener = onClickListener;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view = LayoutInflater.from(context).inflate(R.layout.contacts_view_layout,viewGroup,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int pos) {
        contactClass = contactClassList.get(pos);
        myViewHolder.contactNameTextView.setText(contactClass.getName());
        myViewHolder.contactNumberTextView.setText(contactClass.getUid());



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
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onClickListener.recyclerOnClickListener(getAdapterPosition());
                }
            });




        }


    }

    public interface OnClickListener{
        void recyclerOnClickListener(int pos);
    }


}
