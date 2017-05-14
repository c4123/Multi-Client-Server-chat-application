package aboullaite;

import java.net.Socket;

public class CurrentUser {
	private String setId;
	private String setPwd;
	private String setNick;
	private String setMajor;
	private String setImg;
	Socket socket;

	public CurrentUser(){}

	public CurrentUser(String setId, String setPwd)
	{
	    this.setId = setId;
	    this.setPwd = setPwd;
	    this.setNick = null;
	    this.setMajor = null;
	    this.setImg =  null;
	    this.socket = null;
	}

	public CurrentUser(String setId, String setPwd, String setNick, String setMajor, String setImg)
	{
	    this.setId = setId;
	    this.setPwd = setPwd;
	    this.setNick = setNick;
	    this.setMajor = setMajor;
	    this.setImg =  setImg;
	    this.socket = null;
	}
}
