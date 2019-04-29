package com.example.comitchat.modal.user.list;

import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

@Generated("com.robohorse.robopojogenerator")
public class Meta{

	@SerializedName("cursor")
	private Cursor cursor;

	@SerializedName("pagination")
	private Pagination pagination;

	public void setCursor(Cursor cursor){
		this.cursor = cursor;
	}

	public Cursor getCursor(){
		return cursor;
	}

	public void setPagination(Pagination pagination){
		this.pagination = pagination;
	}

	public Pagination getPagination(){
		return pagination;
	}

	@Override
 	public String toString(){
		return 
			"Meta{" + 
			"cursor = '" + cursor + '\'' + 
			",pagination = '" + pagination + '\'' + 
			"}";
		}
}