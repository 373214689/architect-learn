package web.spring.boot.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "T_DM_DATABASES")
public class GenericDatabase {
    @Id
    @Column(name = "database_id", nullable=false, unique = true)
    String databaseId;

    @Column(name = "database_name", nullable=false)
    String databaseName;

    @Column(name = "database_comment", nullable=false)
    String databaseComment;

    @Column(name = "owner", nullable=false)
    String owner;

    @Column(name = "update_time")
    LocalDateTime updateTime = LocalDateTime.now();

}
