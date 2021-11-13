package web.spring.boot.entity;


import lombok.Data;

import java.sql.Timestamp;

@Data
public class User {
    private int userId;

    private String username;

    private String password;

    private Timestamp createTime;

    private Timestamp lasLoginTime;

    private String lastLoginIp;

    public User(int userId, String username, String password, Timestamp createTime, Timestamp lasLoginTime, String lastLoginIp)
    {
        this.userId = userId;
        this.username = username;
        this.password = password;
        this.createTime = createTime;
        this.lasLoginTime = lasLoginTime;
        this.lastLoginIp = lastLoginIp;
    }

    public int getUserId() {
        return userId;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public Timestamp getCreateTime() {
        return createTime;
    }

    public Timestamp getLasLoginTime() {
        return lasLoginTime;
    }

    public String getLastLoginIp() {
        return lastLoginIp;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    public void setLasLoginTime(Timestamp lasLoginTime) {
        this.lasLoginTime = lasLoginTime;
    }

    public void setLastLoginIp(String lastLoginIp) {
        this.lastLoginIp = lastLoginIp;
    }
}
