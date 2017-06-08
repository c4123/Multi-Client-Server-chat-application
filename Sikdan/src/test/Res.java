package test;

import java.util.ArrayList;

public class Res {
	private String resName;
	private	ArrayList<Corner> corners;
	public void insertCorner(Corner corner){
		corners.add(corner);
	}
	public Res(){
		corners = new ArrayList<Corner>();
	}
	public String getResName() {
		return resName;
	}
	public void setResName(String resName) {
		this.resName = resName;
	}
	public ArrayList<Corner> getCorners() {
		return corners;
	}
	public void setCorners(ArrayList<Corner> corners) {
		this.corners = corners;
	}
}
