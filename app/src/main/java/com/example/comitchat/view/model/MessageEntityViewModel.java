package com.example.comitchat.view.model;

import android.app.Application;
import android.os.AsyncTask;
import android.util.Log;

import com.example.comitchat.dao.MessageDao;
import com.example.comitchat.db.CometChatDB;
import com.example.comitchat.entity.Message;



import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

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

    public void insertMessage(Message message){
        new InsertMessageAsync(messageDao).execute(message);
    }



    private class InsertMessageAsync extends AsyncTask<Message, Void, Void> {

        MessageDao messageDao;

        public InsertMessageAsync(MessageDao messageDao) {
            this.messageDao = messageDao;
        }

        @Override
        protected Void doInBackground(Message... messages) {
            messageDao.insert(messages[0]);
            Log.i(TAG, "doInBackground: message inserted successful");
            return null;
        }
    }
}
