package com.example.comitchat.db;

import android.content.Context;
import android.util.Log;

import com.example.comitchat.dao.MessageDao;
import com.example.comitchat.dao.UserDao;
import com.example.comitchat.entity.Message;
import com.example.comitchat.entity.UserTable;
import com.example.comitchat.utility.Constant;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {Message.class,UserTable.class}, version = 1, exportSchema = false)
public abstract class CometChatDB extends RoomDatabase {

    private static final String TAG = Constant.appName + CometChatDB.class.getSimpleName();
    public abstract MessageDao messageDao();

    public abstract UserDao userDao();

    private static volatile CometChatDB cometChatDB;

  public static CometChatDB getCometChatDB(Context context){
        Log.i(TAG, "getCometChatDB: ");
        if (cometChatDB == null){
            synchronized (CometChatDB.class){
                cometChatDB = Room.databaseBuilder(context, CometChatDB.class,"comet_chat_db").build();
                Log.i(TAG, "getCometChatDB: new Object is created");
            }
        }
        return cometChatDB;
    }
}
