package cn.youkai.core.sqlparser;

import cn.youkai.core.sqlparser.dto.EFColumn;
import cn.youkai.core.sqlparser.dto.EFEntityBase;
import cn.youkai.core.sqlparser.dto.EFTable;
import cn.youkai.core.sqlparser.extend.TableFinder;
import cn.youkai.core.sqlparser.extend.VisitorContext;
import net.sf.jsqlparser.JSQLParserException;
import net.sf.jsqlparser.expression.Alias;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.parser.CCJSqlParserUtil;
import net.sf.jsqlparser.schema.Table;
import net.sf.jsqlparser.statement.Statement;
import net.sf.jsqlparser.statement.create.table.ColDataType;
import net.sf.jsqlparser.statement.create.table.ColumnDefinition;
import net.sf.jsqlparser.statement.create.table.CreateTable;
import net.sf.jsqlparser.statement.select.*;
import net.sf.jsqlparser.util.TablesNamesFinder;

import java.util.ArrayList;
import java.util.List;

/**
 * @author youkai
 * @date 2017/12/13.
 */
public class SqlParser {
    public static void printSelect(String sqlStr) throws JSQLParserException {
        Statement statement = CCJSqlParserUtil.parse(sqlStr);
        SelectBody selectBody = ((Select) statement).getSelectBody();
        PlainSelect plainSelect = (PlainSelect) selectBody;

        FromItem fromItem = plainSelect.getFromItem();
        System.out.println("----------------FromItem---------------");

        System.out.print("toString:");
        System.out.println(fromItem.toString());
        System.out.print("getAlias:");
        System.out.println(fromItem.getAlias());
        System.out.print("getPivot:");
        System.out.println(fromItem.getPivot());

        List<Join> joins = plainSelect.getJoins();
        System.out.println("----------------Join---------------");
        for (Join join : joins) {
            System.out.print("toString:");
            System.out.println(join.toString());
            System.out.print("getUsingColumns:");
            System.out.println(join.getUsingColumns());
            System.out.print("getOnExpression:");
            System.out.println(join.getOnExpression());
            System.out.print("getRightItem:");
            FromItem fromItemJoin = join.getRightItem();
            System.out.println(String.format("%s,alias:%s", fromItemJoin, fromItemJoin.getAlias()));
        }

        List<SelectItem> selectItems = plainSelect.getSelectItems();
        System.out.println("----------------SelectItem---------------");
        for (SelectItem item : selectItems) {
            if (item instanceof SelectExpressionItem) {
                System.out.println(((SelectExpressionItem) item).getExpression());
                System.out.println(((SelectExpressionItem) item).getAlias());
            }
        }

        Expression whereExpression = plainSelect.getWhere();
        System.out.println(whereExpression);
    }

    public static List<String> getTableList(String sqlStr) throws JSQLParserException {
        Statement statement = CCJSqlParserUtil.parse(sqlStr);
        Select selectStatement = (Select) statement;
        TablesNamesFinder tablesNamesFinder = new TablesNamesFinder();
        return tablesNamesFinder.getTableList(selectStatement);
    }

    public static List<String> getColumns(String sqlStr) throws JSQLParserException {
        List<String> columns = new ArrayList<>();
        Statement statement = CCJSqlParserUtil.parse(sqlStr);
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

    public static VisitorContext extractDatabaseObjects(String sqlStr) throws JSQLParserException {
        Statement statement = CCJSqlParserUtil.parse(sqlStr);
        SelectBody selectBody = ((Select) statement).getSelectBody();
        PlainSelect plainSelect = (PlainSelect) selectBody;

        TableFinder tableFinder = new TableFinder();
        tableFinder.visit(plainSelect);

        return tableFinder.getContext();
    }

    public static String getCreateSql(EFTable efTable) {
        Table table = new Table();
        table.setName(efTable.getTableName());
        table.setAlias(new Alias(efTable.getAliasName()));

        CreateTable createTable = new CreateTable();
        createTable.setTable(table);
        createTable.setIfNotExists(true);

        List<ColumnDefinition> columnDefinitions = new ArrayList<>();
        for (EFColumn efColumn : efTable.getColumns()) {
            ColumnDefinition tempColumnDefinition = new ColumnDefinition();
            tempColumnDefinition.setColumnName(efColumn.getColumnName());

            ColDataType tempColDataType = new ColDataType();
            tempColDataType.setDataType(efColumn.colDataType());
            tempColumnDefinition.setColDataType(tempColDataType);

            List<String> tempColumnSpecStrings = new ArrayList<>();
            tempColumnSpecStrings.add(efColumn.getComment());
            tempColumnDefinition.setColumnSpecStrings(tempColumnSpecStrings);
            columnDefinitions.add(tempColumnDefinition);
        }

        createTable.setColumnDefinitions(columnDefinitions);

        return createTable.toString();
    }
}
