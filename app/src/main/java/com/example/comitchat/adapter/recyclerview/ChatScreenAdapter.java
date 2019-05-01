package com.example.comitchat.adapter.recyclerview;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

public class ChatScreenAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {



    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    class LeftViewHolder extends RecyclerView.ViewHolder{

        public LeftViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }

    class RightViewHolder extends RecyclerView.ViewHolder{

        public RightViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
