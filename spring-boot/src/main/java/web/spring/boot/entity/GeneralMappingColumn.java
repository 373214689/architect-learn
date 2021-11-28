package web.spring.boot.entity;


import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "T_DG_COLUMN_MAPPINGS")
public class GeneralMappingColumn {
    @Id
    @Column(name = "column_mapping_id", nullable=false, unique = true)
    String columnMappingId;

    String columnMappingName; // 列映射名

    String columnMappingAlias; // 列映射别名

    int columnMappingType; // 列映射类型。0: 原表列, 1: 映射列

    String mappingId;

    String srcTableIds; // 源表

    String srcColumnIds; // 源列

    String tgtTableIds; // 目标表

    String tgtColumnIds; // 目标列

    String relateFunctions; // 关联函数

    String columnType; // 列类型

    String columnExpr; // 列表达式

    LocalDateTime updateTime;


}
