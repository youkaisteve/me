package cn.youkai.core.sqlparser.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * @author youkai
 */
@Getter
@Setter
public class Column extends EntityBase {
    private int tableSysNo;
    private String columnName;
    private String columnType;
    private String length;
    private int precision;
    private String comment;
    private Object defaultValue;
    private boolean allowNull;
    private boolean primaryKey;
}
