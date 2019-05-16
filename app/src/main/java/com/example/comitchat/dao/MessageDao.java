package com.example.comitchat.dao;

import com.example.comitchat.entity.Message;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

@Dao
public interface MessageDao {

    @Insert
    long insert(Message message);

    @Query("Select * from Message where friendUid = :friendUid")
    Message[] getMessage(String friendUid);

}
