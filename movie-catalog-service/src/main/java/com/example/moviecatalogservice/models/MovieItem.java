package com.example.moviecatalogservice.models;

public class MovieItem {

	private String name;
	private String desc;
	
	public MovieItem() {
		//required for JSONMapping of the response
	}
	public MovieItem(String name, String desc) {
		super();
		this.name = name;
		this.desc = desc;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	
	
}
