package web.spring.boot.entity;


import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "T_DG_CONDITION")
public class GeneralCondition {

    @Id
    @Column(name = "condition_id", nullable=false, unique = true)
    String conditionId;

    int conditionType; // and, or, not

    String tableId;

    String columnId;

    int operatorId; // 操作符， =, <, <=, >, >=, <>, BETWEEN, LIKE, IN

    int operatorType; // 值, 子查询,

    String value;
}
