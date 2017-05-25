package aboullaite;

import java.io.Serializable;

public class LoginData implements Serializable{
	private String id;
	private String passwd;
	private int type; //0�̸� ȸ������. 1�̸� �α���
	
	public LoginData(String id, String passwd, int type){
		this.id = id;
		this.passwd = passwd;
		this.type = type;
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getPasswd() {
		return passwd;
	}
	public void setPasswd(String passwd) {
		this.passwd = passwd;
	}
	public void setType(int type){
		this.type = type;
	}
	public int getType(){
		return type;
	}

	
}
