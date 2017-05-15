package aboullaite;

import java.io.Serializable;

public class LoginData implements Serializable{
	private String id;
	private String passwd;
	
	public LoginData(String id, String passWd){
		this.id = id;
		this.passwd = passwd;
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

	
}
