package web.spring.boot.entity;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity(name = "T_DG_COLUMNS")
@Data
public class GeneralColumn {
    @Id
    @Column(name = "column_id", nullable = false)
    String columnId;

    @Column(name = "column_index", nullable = false)
    int columnIndex;

    @Column(name = "column_name", length=100, nullable = false)
    String columnName;

    @Column(name = "column_comment", length=200)
    String columnComment;

    @Column(name = "column_type", length=100, nullable = false)
    String columnType;

    @Column(name = "precision")
    int precision;

    @Column(name = "scale")
    int scale;

    @Column(name = "is_partition")
    boolean isPartition = false;

    @Column(name = "is_primary")
    boolean isPrimary = false;

    @Column(name = "update_time")
    LocalDateTime updateTime = LocalDateTime.now();

}
