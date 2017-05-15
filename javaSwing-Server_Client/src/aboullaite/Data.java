package aboullaite;

import java.io.Serializable;

public class Data implements Serializable{
	private int type;
	private String fromUser;
	private String toUser;
	private String msg;
	private String passwd;
	private CurrentUser[] users;
	
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public String getFromUser() {
		return fromUser;
	}
	public void setFromUser(String fromUser) {
		this.fromUser = fromUser;
	}
	public String getToUser() {
		return toUser;
	}
	public void setToUser(String toUser) {
		toUser = toUser;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public String getPasswd() {
		return passwd;
	}
	public void setPasswd(String passwd) {
		this.passwd = passwd;
	}	
	public void setUsers(CurrentUser[] users){
		this.users = users;
	}
	public CurrentUser[] getUsers(){
		return users;
	}
}
