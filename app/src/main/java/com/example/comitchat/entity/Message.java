package com.example.comitchat.entity;

import org.json.JSONObject;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Message {

    @PrimaryKey(autoGenerate = true)
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
    @NonNull
    private String message;
    private String messageID;
    @NonNull
    private long sentAt;
    private long deliveredAt;
    private long readAt;
    private String metadata;


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

    public String getMessageID() {
        return messageID;
    }

    public void setMessageID(String messageID) {
        this.messageID = messageID;
    }

    public long getSentAt() {
        return sentAt;
    }

    public void setSentAt(long sentAt) {
        this.sentAt = sentAt;
    }

    public long getDeliveredAt() {
        return deliveredAt;
    }

    public void setDeliveredAt(long deliveredAt) {
        this.deliveredAt = deliveredAt;
    }

    public long getReadAt() {
        return readAt;
    }

    public void setReadAt(long readAt) {
        this.readAt = readAt;
    }

    public String getMetadata() {
        return metadata;
    }

    public void setMetadata(String metadata) {
        this.metadata = metadata;
    }

    @Override
    public String toString() {
        return "Message{" +
                        "id = '" + id + '\'' +
                        "myuid = '" + myUid + '\'' +
                        "friendUid = '" + friendUid + '\'' +
                        "name = '" + name + '\'' +
                        "email = '" + email + '\'' +
                        "message = '" + message + '\'' +
                        "messageID = '" + messageID + '\'' +
                        "sentAt = '" + sentAt + '\'' +
                        "deliveredAt = '" + deliveredAt + '\'' +
                        "readAt = '" + readAt + '\'' +
                        "metadata = '" + metadata + '\'' +
                        "}";
    }
}
