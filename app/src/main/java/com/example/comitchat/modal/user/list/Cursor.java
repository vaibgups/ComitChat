package com.example.comitchat.modal.user.list;

import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

@Generated("com.robohorse.robopojogenerator")
public class Cursor{

	@SerializedName("field")
	private String field;

	@SerializedName("affix")
	private String affix;

	@SerializedName("value")
	private int value;

	public void setField(String field){
		this.field = field;
	}

	public String getField(){
		return field;
	}

	public void setAffix(String affix){
		this.affix = affix;
	}

	public String getAffix(){
		return affix;
	}

	public void setValue(int value){
		this.value = value;
	}

	public int getValue(){
		return value;
	}

	@Override
 	public String toString(){
		return 
			"Cursor{" + 
			"field = '" + field + '\'' + 
			",affix = '" + affix + '\'' + 
			",value = '" + value + '\'' + 
			"}";
		}
}