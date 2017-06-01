package aboullaite;

import java.io.Serializable;
import java.util.ArrayList;

import com.messages.User;

public class Data implements Serializable{
	private int type; 
	private String msg;
	private String sendor;
	private String receiver;
	private ArrayList<User> userList;
	
	public Data(int type, String msg, ArrayList<User> userList) {
		super();
		this.type = type;
		this.msg = msg;
		this.userList = userList;
	}
	

	@Override
	public String toString() {
		return "Data [type=" + type + ", msg=" + msg + ", userList=" + userList + "]";
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


	public String getSendor() {
		return sendor;
	}


	public void setSendor(String sendor) {
		this.sendor = sendor;
	}


	public String getReceiver() {
		return receiver;
	}


	public void setReceiver(String receiver) {
		this.receiver = receiver;
	}


	public ArrayList<User> getUserList() {
		return userList;
	}


	public void setUserList(ArrayList<User> userList) {
		this.userList = userList;
	}
	
	
}
