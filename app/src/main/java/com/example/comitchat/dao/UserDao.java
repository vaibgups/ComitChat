package com.example.comitchat.dao;


import com.example.comitchat.entity.UserTable;

import androidx.room.Dao;
import androidx.room.Insert;

@Dao
public interface UserDao {

    @Insert
    long insert(UserTable userTable);

}
