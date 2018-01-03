package cn.youkai.core.sqlparser.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * @author youkai
 */
@Getter
@Setter
public class EFForeignKey extends EFEntityBase {
    private String foreignKeyName;
    private String tableSysNo;
    private String nativeColumnSysNo;
    private String foreignColumnSysNo;
}
