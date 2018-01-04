package cn.youkai.core.sqlparser;

/**
 * @author youkai
 */
public class Util {
    public static String[] getAliasExpression(String expression) {
        String[] strings = new String[2];
        if (expression.contains(Consts.SELECT_EXPRESSION_SPLIT_STR)) {
            strings[0] = expression.split("\\.")[0];
            strings[1] = expression.split("\\.")[1];
        } else {
            strings[0] = "";
            strings[1] = expression;
        }
        return strings;
    }
}
