package web.spring.boot.entity;

import lombok.Data;

@Data
public class GeneralMappingJoin {

    String joinId;

    String  mappingId;

    int joinType; // 0: JOIN, 1: LEFT JOIN, 2: RIGHT JOIN, 3: OUTER JOIN

    String joinTableId;

    String joinTableName;

    String joinTableAlias;

    int joinTableType;

    String mappingColumnId;
}
