package test;

import java.util.ArrayList;

public class Corner {
	String cornerName;
	ArrayList<String> foods;
	public void insertFood(String food){
		foods.add(food);
	}
	public Corner(String cornerName){
		this.cornerName = cornerName;
		foods = new ArrayList<String>();
	}
	public String getCornerName() {
		return cornerName;
	}
	public void setCornerName(String cornerName) {
		this.cornerName = cornerName;
	}
	public ArrayList<String> getFoods() {
		return foods;
	}
	public void setFoods(ArrayList<String> foods) {
		this.foods = foods;
	}
}
