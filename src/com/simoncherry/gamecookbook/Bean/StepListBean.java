package com.simoncherry.gamecookbook.Bean;


public class StepListBean{
	private String step_text;
	private int step_index;
	
	public void setStepText(String text){
		this.step_text = text;
	}
	
	public String getStepText(){
		return this.step_text;
	}
	
	public void setStepIndex(int index){
		this.step_index = index;
	}
	
	public int getStepIndex(){
		return this.step_index;
	}
}