package com.example.comitchat.adapter.recyclerview;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.comitchat.R;
import com.example.comitchat.pojo.Message;
import com.example.comitchat.utility.Constant;

import java.util.List;

public class ChatScreenAdapter extends RecyclerView.Adapter<ChatScreenAdapter.RightViewHolder> {

    private static final String TAG = Constant.appName + ChatScreenAdapter.class.getSimpleName();


    private static final int RIGHT_TEXT_MESSAGE = 334;

    private static final int LEFT_TEXT_MESSAGE = 734;


    private List<Message> messageList;

    private Context context;

    public ChatScreenAdapter(List<Message> messageList, Context context) {
        this.messageList = messageList;
        this.context = context;
    }

    @NonNull
    @Override
    public RightViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View rightTextMessageView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.right_chat_bubble, viewGroup, false);
        return new RightViewHolder(rightTextMessageView);
       /* switch (i) {

            case RIGHT_TEXT_MESSAGE: {

                View rightTextMessageView = LayoutInflater.from(viewGroup.getContext())
                        .inflate(R.layout.right_chat_bubble, viewGroup, false);
                return new RightViewHolder(rightTextMessageView);
            }
            case LEFT_TEXT_MESSAGE: {
                View leftTextMessageView = LayoutInflater.from(viewGroup.getContext())
                        .inflate(R.layout.left_chat_bubble, viewGroup, false);
                return new LeftViewHolder(leftTextMessageView);
            }
            default: {
                return null;
            }
        }
*/
    }

    @Override
    public void onBindViewHolder(@NonNull RightViewHolder viewHolder, int i) {


        Message message = messageList.get(i);

        if (message.isMyMsg()) {

            RightViewHolder rightTextMessageView = (RightViewHolder) viewHolder;
            rightTextMessageView.textView.setText(message.getMessage());
        }/*else if (!message.isMyMsg()){
            LeftViewHolder leftViewHolder = (LeftViewHolder) viewHolder;
            leftViewHolder.textView.setText(message.getMessage());
        }*/
    }

    @Override
    public int getItemCount() {
        return messageList.size();
    }

    public void refreshMessage(List<Message> messageList){
        this.messageList = messageList;
        notifyDataSetChanged();
    }
    class LeftViewHolder extends RecyclerView.ViewHolder {

        TextView textView;


        public LeftViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.leftSideTextView);
        }
    }

    class RightViewHolder extends RecyclerView.ViewHolder {

        TextView textView;

        public RightViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.rightSideTextView);
        }
    }
}
