package cn.youkai.core.sqlparser.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * @author youkai
 */
@Getter
@Setter
public class Table extends EntityBase {
    private String database;
    private String tableName;
    private String comment;
}
