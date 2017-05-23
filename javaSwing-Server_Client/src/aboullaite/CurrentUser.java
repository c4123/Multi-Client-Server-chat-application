package aboullaite;

public class CurrentUser {
	private String id;
	private String nickname;
		
	public CurrentUser(String id, String nickname) {
		super();
		this.id = id;
		this.nickname = nickname;
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

	@Override
	public String toString() {
		return "CurrentUser [id=" + id + ", nickname=" + nickname + "]";
	}
}
