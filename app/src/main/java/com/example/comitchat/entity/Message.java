package com.example.comitchat.entity;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Message {

    @PrimaryKey
    @NonNull
    private int id;
    @NonNull
    private String myUid;
    @NonNull
    private String friendUid;
    private String name;
    private String email;
    @NonNull
    private boolean isMyMsg;
    private String message;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @NonNull
    public String getMyUid() {
        return myUid;
    }

    public void setMyUid(@NonNull String myUid) {
        this.myUid = myUid;
    }

    @NonNull
    public String getFriendUid() {
        return friendUid;
    }

    public void setFriendUid(@NonNull String friendUid) {
        this.friendUid = friendUid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isMyMsg() {
        return isMyMsg;
    }

    public void setMyMsg(boolean myMsg) {
        isMyMsg = myMsg;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
