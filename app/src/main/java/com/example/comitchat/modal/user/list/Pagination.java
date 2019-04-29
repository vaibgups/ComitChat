package com.example.comitchat.modal.user.list;

import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

@Generated("com.robohorse.robopojogenerator")
public class Pagination{

	@SerializedName("per_page")
	private int perPage;

	@SerializedName("total")
	private int total;

	@SerializedName("count")
	private int count;

	@SerializedName("total_pages")
	private int totalPages;

	@SerializedName("current_page")
	private int currentPage;

	public void setPerPage(int perPage){
		this.perPage = perPage;
	}

	public int getPerPage(){
		return perPage;
	}

	public void setTotal(int total){
		this.total = total;
	}

	public int getTotal(){
		return total;
	}

	public void setCount(int count){
		this.count = count;
	}

	public int getCount(){
		return count;
	}

	public void setTotalPages(int totalPages){
		this.totalPages = totalPages;
	}

	public int getTotalPages(){
		return totalPages;
	}

	public void setCurrentPage(int currentPage){
		this.currentPage = currentPage;
	}

	public int getCurrentPage(){
		return currentPage;
	}

	@Override
 	public String toString(){
		return 
			"Pagination{" + 
			"per_page = '" + perPage + '\'' + 
			",total = '" + total + '\'' + 
			",count = '" + count + '\'' + 
			",total_pages = '" + totalPages + '\'' + 
			",current_page = '" + currentPage + '\'' + 
			"}";
		}
}