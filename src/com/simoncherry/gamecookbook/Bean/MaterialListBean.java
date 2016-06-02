package com.simoncherry.gamecookbook.Bean;


public class MaterialListBean{
	private String material_name;
	private int img_index;
	private String material_weight;
	private String material_unit;
	
	public void setMaterialName(String name){
		this.material_name = name;
	}
	
	public String getMaterialName(){
		return this.material_name;
	}
	
	public void setMaterialImgIndex(int index){
		this.img_index = index;
	}
	
	public int getMaterialImgIndex(){
		return this.img_index;
	}
	
	public void setMaterialWeight(String weight){
		this.material_weight = weight;
	}
	
	public String getMaterialWeight(){
		return this.material_weight;
	}
	
	public void setMaterialUnit(String unit){
		this.material_unit = unit;
	}
	
	public String getMaterialUnit(){
		return this.material_unit;
	}
	
}