package aboullaite;

import java.io.Serializable;

public class User implements Serializable {
    private String id;
    private String nickname;


    public User(){}

    public User(String id, String nickname)
    {
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



}
