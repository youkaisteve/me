package cn.youkai.core.sqlparser.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * @author youkai
 */
@Getter
@Setter
public class EFColumn extends EFEntityBase {
    private String tableSysNo;
    private String columnName;
    private String displayName;
    private String columnType;
    private String length;
    private int precision;
    private String comment;
    private Object defaultValue;
    private boolean allowNull;
    private boolean primaryKey;
}
