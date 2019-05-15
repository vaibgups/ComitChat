package com.example.comitchat.dao;

import com.example.comitchat.entity.Message;

import androidx.room.Dao;
import androidx.room.Insert;

@Dao
public interface MessageDao {

    @Insert
    void insert(Message message);
}
