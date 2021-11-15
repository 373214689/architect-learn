package web.spring.boot.module;

import lombok.Data;

@Data
public class TableMappingModule {
    String mappingId;
    int mappingType; // 映射类型。from, join,
    String srcTableId; // 来源表
    String srcTableName;
    int srcTableType;
    String tgtTableId; // 目标表
    String tgtTableName;
    int tgtTableType;
    String whereCondition;
    String joinCondition;
    //
    TableModule srcTable;
    TableModule tgtTable;
}
