package web.spring.boot.entity;


import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
@Table(name = "T_DM_TABLES")
public class GenericTable {
    @Id
    @Column(name = "table_id", nullable = false)
    private String tableId;
    @Column(name = "database_id",  unique = true, nullable = false)
    private String databaseId;
    @Column(name = "table_name",  unique = true, length = 200, nullable = false)
    private String tableName;
    @Column(name = "table_comment", length=200)
    private String tableComment;
    @Column(name = "table_space")
    private String tableSpace;
    @Column(name = "table_type", nullable = false)
    private int table_type = 0; // 0 - 标准表, 1 - 视图, 2 - 引用
    @Column(name = "is_increment", nullable = false)
    private boolean isIncrement = false;
    @Column(name = "is_dictionary", nullable = false)
    private boolean isDictionary = false;
    @Column(name = "update_time")
    private LocalDateTime updateTime = LocalDateTime.now();
    @Transient
    private List<GenericColumn> columns;

}
