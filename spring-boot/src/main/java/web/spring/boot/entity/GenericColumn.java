package web.spring.boot.entity;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "T_DM_COLUMNS")
public class GenericColumn {
    @Id
    @Column(name = "column_id", nullable = false)
    private String columnId;
    @Column(name = "column_index", nullable = false)
    private int columnIndex;
    @Column(name = "column_name", length=100, nullable = false)
    private String columnName;
    @Column(name = "column_comment", length=200)
    private String columnComment;
    @Column(name = "column_type", length=100, nullable = false)
    private String columnType;
    @Column(name = "is_partition")
    private boolean isPartition = false;
    @Column(name = "is_primary")
    private boolean isPrimary = false;
    @Column(name = "update_time")
    private LocalDateTime updateTime = LocalDateTime.now();

}
