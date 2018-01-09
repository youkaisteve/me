package cn.youkai.core.sqlparser.enums;

/**
 * @author youkai
 */

public enum EFDataType {
    INT(1), VARCHAR(2), TEXT(3), BIT(4), DATETIME(5);

    // 定义私有变量

    private int nCode;

    // 构造函数，枚举类型只能为私有
    private EFDataType(int _nCode) {
        this.nCode = _nCode;
    }

    @Override
    public String toString() {
        return String.valueOf(this.nCode);
    }
}
