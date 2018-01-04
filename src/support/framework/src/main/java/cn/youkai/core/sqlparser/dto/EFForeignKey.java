package cn.youkai.core.sqlparser.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * @author youkai
 */
@Getter
@Setter
public class EFForeignKey extends EFEntityBase {
    private String nativeTableSysNo;
    private String nativeTableName;
    private String nativeColumnSysNo;
    private String nativeColumnName;
    private String foreignTableSysNo;
    private String foreignTableName;
    private String foreignColumnSysNo;
    private String foreignColumnName;

    @Override
    public String toString() {
        String lineSeprator = System.getProperty("line.separator");
        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append(String.format("-------------------------ForeignKey(%s)-------------------------%s", this.getSysNo(), lineSeprator));
        stringBuilder.append(String.format("NativeTable(%s)%s", this.getNativeTableName(), lineSeprator));
        stringBuilder.append(String.format("NativeColumn(%s)%s", this.getNativeColumnName(), lineSeprator));
        stringBuilder.append(String.format("ForeignTable(%s)%s", this.getForeignTableName(), lineSeprator));
        stringBuilder.append(String.format("ForeignColumn(%s)%s", this.getForeignColumnName(), lineSeprator));

        return stringBuilder.toString();
    }
}
