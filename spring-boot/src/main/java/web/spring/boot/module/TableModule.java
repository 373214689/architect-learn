package web.spring.boot.module;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
public class TableModule {
    String tableId;
    String databaseId;
    String tableName;
    String tableComment;
    String tableSpace;
    int table_type = 0; // 0 - 标准表, 1 - 视图, 2 - 引用
    boolean isIncrement = false;
    boolean isDictionary = false;
    LocalDateTime updateTime = LocalDateTime.now();
    // 列信息
    List<ColumnModule> columns = new ArrayList<>();
}
