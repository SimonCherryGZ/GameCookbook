package com.simoncherry.gamecookbook.Bean;


public class FoodListBean{
	private String id;
	
	private String food_name;
	private String food_effect;
	private int img_index;
	private int food_rank;
	private String food_material;
	private String food_step;
	private boolean isHighLight = false;
	
	
	public void setId(String id){
		this.id = id;
	}
	
	public String getId(){
		return this.id;
	}
	
	public void setFoodMaterial(String material){
		this.food_material = material;
	}
	
	public String getFoodMaterial(){
		return this.food_material;
	}
	
	public void setFoodStep(String step){
		this.food_step = step;
	}
	
	public String getFoodStep(){
		return this.food_step;
	}
	
	
	public void setFoodName(String name){
		this.food_name = name;
	}
	
	public String getFoodName(){
		return this.food_name;
	}
	
	public void setFoodEffect(String effect){
		this.food_effect = effect;
	}
	
	public String getFoodEffect(){
		return this.food_effect;
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