package com.example.comitchat.pojo;

public class Message {


    private String uid;
    private String name;
    private String email;
    private boolean isMyMsg;
    private String message = "";

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
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
