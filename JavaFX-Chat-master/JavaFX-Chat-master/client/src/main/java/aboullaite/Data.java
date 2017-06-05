package aboullaite;

import java.io.Serializable;
import java.util.ArrayList;

public class Data implements Serializable{
	private int type; 
	private String msg;
	private String sendorEmail;
	private String sendorNickName;
	private String receiverEmail;
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





	public ArrayList<User> getUserList() {
		return userList;
	}


	public void setUserList(ArrayList<User> userList) {
		this.userList = userList;
	}


	public String getSendorEmail() {
		return sendorEmail;
	}


	public void setSendorEmail(String sendorEmail) {
		this.sendorEmail = sendorEmail;
	}


	public String getSendorNickName() {
		return sendorNickName;
	}


	public void setSendorNickName(String sendorNickName) {
		this.sendorNickName = sendorNickName;
	}


	public String getReceiverEmail() {
		return receiverEmail;
	}


	public void setReceiverEmail(String receiverEmail) {
		this.receiverEmail = receiverEmail;
	}
	
	
}
