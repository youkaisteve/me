package cn.youkai.core.sqlparser.dto;

import cn.youkai.core.sqlparser.enums.EFDataType;
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
    private EFDataType columnType;
    private int length;
    private int precision;
    private String comment;
    private Object defaultValue;
    private boolean allowNull;
    private boolean primaryKey;

    public String colDataType() {
        switch (this.columnType) {
            case INT:
                return this.length == 0 ? "INT" : "INT(" + this.length + ")";
            case VARCHAR:
                return this.length == 0 ? "VARCHAR" : "VARCHAR(" + this.length + ")";
            case TEXT:
                return "TEXT";
            case BIT:
                return "BIT";
            case DATETIME:
                return "DATETIME";
            default:
                return "VARCHAR";
        }
    }
}
