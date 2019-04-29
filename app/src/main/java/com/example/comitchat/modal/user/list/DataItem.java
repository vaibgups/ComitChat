package com.example.comitchat.modal.user.list;

import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

@Generated("com.robohorse.robopojogenerator")
public class DataItem{

	@SerializedName("uid")
	private String uid;

	@SerializedName("createdAt")
	private int createdAt;

	@SerializedName("role")
	private String role;

	@SerializedName("name")
	private String name;

	@SerializedName("avatar")
	private String avatar;

	@SerializedName("status")
	private String status;

	@SerializedName("email")
	private String email;

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

	public void setRole(String role){
		this.role = role;
	}

	public String getRole(){
		return role;
	}

	public void setName(String name){
		this.name = name;
	}

	public String getName(){
		return name;
	}

	public void setAvatar(String avatar){
		this.avatar = avatar;
	}

	public String getAvatar(){
		return avatar;
	}

	public void setStatus(String status){
		this.status = status;
	}

	public String getStatus(){
		return status;
	}

	public void setEmail(String email){
		this.email = email;
	}

	public String getEmail(){
		return email;
	}

	@Override
 	public String toString(){
		return 
			"DataItem{" + 
			"uid = '" + uid + '\'' + 
			",createdAt = '" + createdAt + '\'' + 
			",role = '" + role + '\'' + 
			",name = '" + name + '\'' + 
			",avatar = '" + avatar + '\'' + 
			",status = '" + status + '\'' + 
			",email = '" + email + '\'' + 
			"}";
		}
}