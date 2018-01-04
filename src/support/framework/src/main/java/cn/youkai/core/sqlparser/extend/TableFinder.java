package cn.youkai.core.sqlparser.extend;

import cn.youkai.core.sqlparser.Consts;
import cn.youkai.core.sqlparser.dto.EFColumn;
import cn.youkai.core.sqlparser.dto.EFTable;
import net.sf.jsqlparser.expression.Alias;
import net.sf.jsqlparser.schema.Table;
import net.sf.jsqlparser.statement.select.*;

import java.util.*;
import java.util.logging.Logger;

/**
 * @author youkai
 */
public class TableFinder implements SelectItemVisitor, FromItemVisitor, SelectVisitor {
    private Logger logger = Logger.getLogger("TableFinder");

    VisitorContext visitorContext;

    public TableFinder() {
        visitorContext = new VisitorContext();
    }

    public VisitorContext getContext() {
        return visitorContext;
    }

    @Override
    public void visit(Table tableName) {
        logger.info("visit Table");
        EFTable table = new EFTable();
        table.setSysNo(UUID.randomUUID().toString());
        table.setTableName(tableName.getName());
        table.setAliasName(tableName.getAlias().getName());
        table.setColumns(new ArrayList<>());
        visitorContext.getTableList().add(table);
    }

    @Override
    public void visit(SubSelect subSelect) {
        logger.info("visit SubSelect");
    }

    @Override
    public void visit(SubJoin subjoin) {
        logger.info("visit SubJoin");
    }

    @Override
    public void visit(LateralSubSelect lateralSubSelect) {
        logger.info("visit LateralSubSelect");
    }

    @Override
    public void visit(ValuesList valuesList) {
        logger.info("visit ValuesList");
    }

    @Override
    public void visit(TableFunction tableFunction) {
        logger.info("visit TableFunction");
    }

    @Override
    public void visit(PlainSelect plainSelect) {
        logger.info("visit PlainSelect");
        plainSelect.getFromItem().accept(this);
        List<Join> joins = plainSelect.getJoins();
        if (null != joins) {
            joins.forEach(join -> {
                join.getRightItem().accept(this);
                join.getOnExpression().accept(new OnExpressionFinder(visitorContext));
            });
        }
        plainSelect.getSelectItems().forEach(selectItem -> selectItem.accept(this));
    }

    @Override
    public void visit(SetOperationList setOpList) {

    }

    @Override
    public void visit(WithItem withItem) {

    }

    @Override
    public void visit(AllColumns allColumns) {
        allColumns.accept(this);
    }

    @Override
    public void visit(AllTableColumns allTableColumns) {
        allTableColumns.accept(this);
    }

    @Override
    public void visit(SelectExpressionItem selectExpressionItem) {
        logger.info("visit SelectExpressionItem");
        Alias alias = selectExpressionItem.getAlias();
        String expression = selectExpressionItem.getExpression().toString();
        String actualAlias = "";
        if (null != alias) {
            if (alias.isUseAs()) {
                actualAlias = alias.toString().replace("AS", "").trim();
            } else {
                actualAlias = alias.toString();
            }
        }

        String tableAlias;
        String actualColumn = "";
        if (expression.contains(Consts.SELECT_EXPRESSION_SPLIT_STR)) {
            tableAlias = expression.split("\\.")[0];
            actualColumn = expression.split("\\.")[1];
        } else {
            tableAlias = null;
        }

        //第一个table为from table
        EFTable table = visitorContext.getTableList().get(0);
        if (null != tableAlias) {
            Optional<EFTable> optional = visitorContext.getTableList().stream()
                    .filter(t -> tableAlias.equalsIgnoreCase(t.getAliasName()))
                    .findFirst();
            if (optional.isPresent()) {
                table = optional.get();
            }
        }

        if (null == table.getColumns()) {
            table.setColumns(new ArrayList<>());
        }
        //如果已经存在该Column，则不需要再去添加了
        String finalActualColumn = actualColumn;
        Optional<EFColumn> columnOptional = table.getColumns().stream().filter(t -> finalActualColumn.equals(t.getColumnName())).findFirst();
        if (columnOptional.isPresent()) {
            return;
        }

        EFColumn newColumn = new EFColumn();
        newColumn.setSysNo(UUID.randomUUID().toString());
        newColumn.setColumnName(actualColumn);
        newColumn.setDisplayName(actualAlias.isEmpty() ? actualColumn : actualAlias);
        table.getColumns().add(newColumn);
    }
}
