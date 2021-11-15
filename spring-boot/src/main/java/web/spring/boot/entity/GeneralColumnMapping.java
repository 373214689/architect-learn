package web.spring.boot.entity;


import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Data
@Entity(name = "T_DG_COLUMN_MAPPINGS")
public class GeneralColumnMapping  {
    @Id
    @Column(name = "mapping_id", nullable=false, unique = true)
    String columnMappingId;

    String tableMappingId;

    String srcTableId;

    String srcColumnId;

    String tgtTableIds;

    String tgtColumnIds;

    String relateFunctions;

    LocalDateTime updateTime;

}
