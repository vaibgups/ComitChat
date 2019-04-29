package com.example.comitchat.modal.user.register;

import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

@Generated("com.robohorse.robopojogenerator")
public class RegisterUserResponse implements Serializable {

	@SerializedName("uid")
	private String uid;

	@SerializedName("createdAt")
	private int createdAt;

	@SerializedName("name")
	private String name;

	@SerializedName("email")
	private String email;

	@SerializedName("status")
	private String status;

	public void setUid(String uid){
		this.uid = uid;
	}

	public String getUid(){
		return uid;
	}

	public void setCreatedAt(int createdAt){
		this.createdAt = createdAt;
	}

	public int getCreatedAt(){
		return createdAt;
	}

	public void setName(String name){
		this.name = name;
	}

	public String getName(){
		return name;
	}

	public void setEmail(String email){
		this.email = email;
	}

	public String getEmail(){
		return email;
	}

	public void setStatus(String status){
		this.status = status;
	}

	public String getStatus(){
		return status;
	}

	@Override
 	public String toString(){
		return 
			"RegisterUserResponse{" + 
			"uid = '" + uid + '\'' + 
			",createdAt = '" + createdAt + '\'' + 
			",name = '" + name + '\'' + 
			",email = '" + email + '\'' + 
			",status = '" + status + '\'' + 
			"}";
		}
}