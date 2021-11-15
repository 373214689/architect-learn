package web.spring.boot.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Data
@Entity(name = "T_DG_TABLE_MAPPINGS")
public class GeneralTableMapping {
    @Id
    @Column(name = "mapping_id", nullable=false, unique = true)
    String mappingId;

    @Column(name = "mapping_type", nullable=false)
    int mappingType; // 映射类型。from, join,

    @Column(name = "src_table_id", nullable=false)
    String srcTableId; // 来源表

    int srcTableType;

    @Column(name = "tgt_table_id", nullable=false)
    String tgtTableId; // 目标表

    int tgtTableType;

    String whereCondition;

    String joinCondition;
    @Column(name = "update_time", nullable=false)
    LocalDateTime updateTime;
}
