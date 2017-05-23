package aboullaite;

import java.io.Serializable;
import java.util.ArrayList;

public class Data implements Serializable{
	private int type; //1이면 메세지 2이면 유저리스트
	private String msg;
	private ArrayList<CurrentUser> userList;
	
	public Data(int type, String msg, ArrayList<CurrentUser> userList) {
		super();
		this.type = type;
		this.msg = msg;
		this.userList = userList;
	}
	
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public ArrayList<CurrentUser> getUserList() {
		return userList;
	}
	public void setUserList(ArrayList<CurrentUser> userList) {
		this.userList = userList;
	}
	
	@Override
	public String toString() {
		return "Data [type=" + type + ", msg=" + msg + ", userList=" + userList + "]";
	}
	
	
}
