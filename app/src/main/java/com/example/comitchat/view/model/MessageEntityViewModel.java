package com.example.comitchat.view.model;

import android.app.Application;
import android.os.AsyncTask;
import android.util.Log;

import com.example.comitchat.dao.MessageDao;
import com.example.comitchat.db.CometChatDB;
import com.example.comitchat.entity.Message;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

public class MessageEntityViewModel extends AndroidViewModel {

    private static final String TAG = MessageEntityViewModel.class.getSimpleName();

    private MessageDao messageDao;
    private CometChatDB cometChatDB;

    public MessageEntityViewModel(@NonNull Application application) {
        super(application);
        cometChatDB = CometChatDB.getCometChatDB(application);
        messageDao = cometChatDB.messageDao();
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        Log.i(TAG, "onCleared: AndroidViewModel Destroy");
    }

    public void insertMessage(Message message) {
        new InsertMessageAsync(messageDao).execute(message);
    }

    public LiveData<List<Message>> getFriendMessage(String friend) {
        return messageDao.getMessage(friend);
//        new GetFriendMessageAsync(messageDao).execute(friend);

    }

    private class InsertMessageAsync extends AsyncTask<Message, Void, Void> {

        MessageDao messageDao;

        public InsertMessageAsync(MessageDao messageDao) {
            this.messageDao = messageDao;
        }

        @Override
        protected Void doInBackground(Message... messages) {
            long id = messageDao.insert(messages[0]);
            Log.i(TAG, "doInBackground: message inserted successful");
            Log.i(TAG, "doInBackground: id " + id);
            Log.i(TAG, "doInBackground: " + messages[0].toString());


            return null;
        }
    }

    private class GetFriendMessageAsync extends AsyncTask<String,Void,Message[]>{

        MessageDao messageDao;
        public GetFriendMessageAsync(MessageDao messageDao) {
            this.messageDao = messageDao;
        }

        @Override
        protected Message[] doInBackground(String... strings) {

//            messageDao.getMessage(strings[0]);
            return null;
        }

        @Override
        protected void onPostExecute(Message[] messages) {
            return;
        }
    }
}
