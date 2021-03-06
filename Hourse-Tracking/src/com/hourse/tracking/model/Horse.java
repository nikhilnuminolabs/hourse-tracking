package com.hourse.tracking.model;

public class Horse {

	private String name ;
	
	private Integer odds ;

	public Horse(String name, Integer odds) {
		super();
		this.name = name;
		this.odds = odds;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getOdds() {
		return odds;
	}

	public void setOdds(Integer odds) {
		this.odds = odds;
	}
	
	
}
