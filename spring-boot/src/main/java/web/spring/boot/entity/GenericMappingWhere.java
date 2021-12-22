package web.spring.boot.entity;

import lombok.Data;

@Data
public class GenericMappingWhere {

    String whereId;

    String mappingId;

    String columnMappingId; // 列

    int columnMappingType; // 列映射类型。0: 原表列, 1: 映射列

    int logicType;

    String whenMappingId; // 条件

    int whenMappingType; // 条件列映射类型。0: 原表列, 1: 映射列

    String thenMappingId; // 条件满足时取值

    int thenMappingType; //条件满足列映射类型。0: 原表列, 1: 映射列

    String elseMappingId; // 条件不满足时取值

    int elseMappingType; //条件满足列映射类型。0: 原表列, 1: 映射列
}
