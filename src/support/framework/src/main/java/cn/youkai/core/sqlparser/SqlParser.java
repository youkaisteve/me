package cn.youkai.core.sqlparser;

import cn.youkai.core.sqlparser.extend.TableColumnFinder;
import net.sf.jsqlparser.JSQLParserException;
import net.sf.jsqlparser.expression.Alias;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.parser.CCJSqlParserUtil;
import net.sf.jsqlparser.statement.Statement;
import net.sf.jsqlparser.statement.select.*;
import net.sf.jsqlparser.util.TablesNamesFinder;

import java.util.ArrayList;
import java.util.List;

/**
 * @author youkai
 * @date 2017/12/13.
 */
public class SqlParser {
    private String statement;

    public void setStatement(String statement) {
        this.statement = statement;
    }

    public List<String> getTableList() throws JSQLParserException {
        Statement statement = CCJSqlParserUtil.parse(this.statement);
        Select selectStatement = (Select) statement;
        TablesNamesFinder tablesNamesFinder = new TablesNamesFinder();
        List<String> tables = tablesNamesFinder.getTableList(selectStatement);
        return tables;
    }

    public List<String> getColumns() throws JSQLParserException {
        List<String> columns = new ArrayList<>();
        Statement statement = CCJSqlParserUtil.parse(this.statement);
        SelectBody selectBody = ((Select) statement).getSelectBody();
        List<SelectItem> selectItems = ((PlainSelect) selectBody).getSelectItems();
        if (selectItems != null) {
            for (SelectItem item : selectItems) {
                if (item instanceof AllColumns) {
                    String column = item.toString();
                    columns.add(column);
                }
                if (item instanceof AllTableColumns) {
                    columns.add(item.toString());
                }
                if (item instanceof SelectExpressionItem) {
                    Alias alias = ((SelectExpressionItem) item).getAlias();
                    Expression expression = ((SelectExpressionItem) item).getExpression();
                    if (alias != null) {
                        String column = alias.getName();
                        columns.add(column);
                    } else if (expression != null) {
                        columns.add(expression.toString());
                    }
                }
            }
        }

        return columns;
    }
}
