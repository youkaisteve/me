package cn.youkai.core.sqlparser.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * @author youkai
 */
@Getter
@Setter
public class ForeignKey extends EntityBase {
    private String foreignKeyName;
    private int tableSysNo;
    private int nativeColumnSysNo;
    private int foreignColumnSysNo;
}
