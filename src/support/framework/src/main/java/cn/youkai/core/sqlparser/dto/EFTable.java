package cn.youkai.core.sqlparser.dto;

import com.sun.org.apache.xml.internal.serialize.LineSeparator;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Properties;

/**
 * @author youkai
 */
@Getter
@Setter
public class EFTable extends EFEntityBase {
    private String database;
    private String tableName;
    private String comment;
    private String aliasName;

    private List<EFColumn> columns;

    @Override
    public String toString() {
        String lineSeprator = System.getProperty("line.separator");
        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append(String.format("====================%s(%s)=====================%s", this.getTableName(), this.getSysNo(), lineSeprator));

        stringBuilder.append(String.format("COLUMNS:%s", lineSeprator));
        if (null != columns) {
            for (EFColumn column : columns) {
                stringBuilder.append(String.format("Name:%s,DisplayName:%s%s", column.getColumnName(), column.getDisplayName(), lineSeprator));
            }
        }
        stringBuilder.append(String.format("====================%s(%s)=====================%s", this.getTableName(), this.getSysNo(), lineSeprator));

        return stringBuilder.toString();
    }
}
