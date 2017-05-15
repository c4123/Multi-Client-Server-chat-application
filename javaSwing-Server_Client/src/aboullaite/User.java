package aboullaite;

import java.net.Socket;

public class User {
	private String id;
	private String nickname;
	private Socket socket;

	public User(){}

	public User(String id, String nickname,Socket socket)
	{
		this.id = id;
		this.nickname = nickname;
		this.socket = socket;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public Socket getSocket() {
		return socket;
	}

	public void setSocket(Socket socket) {
		this.socket = socket;
	}


}
