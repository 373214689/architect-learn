package web.spring.boot.entity;


import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "t_users")
public class GeneralUser {
    @Id
    @Column(name = "id")
    private int userId;
    @Column(name = "username")
    private String username;
    @Column(name = "password")
    private String password;
    @Column(name = "create_time")
    private LocalDateTime createTime;
    @Column(name = "last_login_time")
    private LocalDateTime lasLoginTime;
    @Column(name = "last_login_ip")
    private String lastLoginIp;
}
