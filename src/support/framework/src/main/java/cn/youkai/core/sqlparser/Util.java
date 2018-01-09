package cn.youkai.core.sqlparser;

import cn.youkai.core.sqlparser.enums.EFDataType;

/**
 * @author youkai
 */
public class Util {
    public static String[] getAliasExpression(String expression) {
        String[] strings = new String[2];
        if (expression.contains(Consts.SELECT_EXPRESSION_SPLIT_STR)) {
            strings[0] = expression.split("\\" + Consts.SELECT_EXPRESSION_SPLIT_STR)[0];
            strings[1] = expression.split("\\" + Consts.SELECT_EXPRESSION_SPLIT_STR)[1];
        } else {
            strings[0] = "";
            strings[1] = expression;
        }
        return strings;
    }

    /**
     * 获取字段类型
     *
     * @param colStr
     * @return
     */
    public static EFDataType getColumnDataType(String colStr) {
        String dataTypeStr = colStr.split("_")[0];
        if (dataTypeStr.length() > 0) {
            dataTypeStr = dataTypeStr.substring(0, 1);
        }
        switch (dataTypeStr) {
            case "I":
                return EFDataType.INT;
            case "V":
                return EFDataType.VARCHAR;
            case "T":
                return EFDataType.TEXT;
            case "B":
                return EFDataType.BIT;
            case "D":
                return EFDataType.DATETIME;
            default:
                return EFDataType.VARCHAR;
        }
    }

    public static int getColumnLength(String colStr) {
        String dataTypeStr = colStr.split("_")[0];
        boolean containLength = dataTypeStr.length() > 1;
        if (containLength) {
            return Integer.parseInt(dataTypeStr.substring(1));
        } else {
            switch (dataTypeStr) {
                case "I":
                    return 11;
                case "V":
                    return 50;
                case "B":
                    return 1;
                default:
                    return 0;
            }
        }
    }

    /**
     * 获取字段名
     *
     * @param colStr
     * @return
     */
    public static String getColumnName(String colStr) {
        return colStr.split(Consts.COLUMN_SPLIT_STR)[1];
    }
}
