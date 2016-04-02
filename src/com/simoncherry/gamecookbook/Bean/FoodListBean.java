package com.simoncherry.gamecookbook.Bean;


public class FoodListBean{
	private String food_name;
	private int img_index;
	private int food_rank;
	private boolean isHighLight = false;
	
	public void setFoodName(String name){
		this.food_name = name;
	}
	
	public String getFoodName(){
		return this.food_name;
	}
	
	public void setFoodImgIndex(int index){
		this.img_index = index;
	}
	
	public int getFoodImgIndex(){
		return this.img_index;
	}
	
	public void setFoodRank(int rank){
		this.food_rank = rank;
	}
	
	public int getFoodRank(){
		return this.food_rank;
	}
	
	public void setHighLight(boolean isHighLight){
		this.isHighLight = isHighLight;
	}
	
	public boolean getHighLight(){
		return this.isHighLight;
	}
}