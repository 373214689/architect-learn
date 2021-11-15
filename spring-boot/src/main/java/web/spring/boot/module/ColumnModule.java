package web.spring.boot.module;


import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ColumnModule {
    String columnId;
    int columnIndex;
    String columnName;
    String columnComment;
    String columnType;
    int precision;
    int scale;
    boolean isPartition = false;
    boolean isPrimary = false;
    LocalDateTime updateTime = LocalDateTime.now();
}
