package web.spring.boot.entity;


import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "T_DG_TABLES")
public class GeneralTable {

    @Id
    @Column(name = "table_id", nullable=false, unique = true)
    String tableId;

    @Column(name = "database_id", nullable=false)
    String databaseId;

    @Column(name = "table_name", length=100, nullable = false)
    String tableName;

    @Column(name = "table_comment", length=200)
    String tableComment;

    @Column(name = "table_space")
    String tableSpace;

    @Column(name = "table_type", nullable = false)
    int table_type = 0; // 0 - 标准表, 1 - 视图, 2 - 引用

    @Column(name = "is_increment", nullable = false)
    boolean isIncrement = false;

    @Column(name = "is_dictionary", nullable = false)
    boolean isDictionary = false;

    @Column(name = "update_time")
    LocalDateTime updateTime = LocalDateTime.now();


}
