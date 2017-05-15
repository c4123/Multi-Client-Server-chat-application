package aboullaite;

import java.net.Socket;

public class CurrentUser {
	private String id;
	private String nickname;
	private String major;
	private Socket socket;

	public CurrentUser(){}

	public CurrentUser(String id, String nickname,String major,Socket socket)
	{
		this.id = id;
		this.nickname = nickname;
		this.major = major;
		this.socket = socket;
	}
}
