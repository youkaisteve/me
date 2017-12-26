package cn.youkai.core.sqlparser.extend;

import net.sf.jsqlparser.schema.Table;
import net.sf.jsqlparser.statement.select.*;

import java.util.List;

/**
 * @author youkai
 * @date 2017/12/14.
 */
public class TableColumnFinder implements SelectItemVisitor {
    private String columnsNames;

    public String getColumnsNames() {
        return columnsNames;
    }

    @Override
    public void visit(AllColumns allColumns) {

    }

    @Override
    public void visit(AllTableColumns allTableColumns) {

    }

    @Override
    public void visit(SelectExpressionItem selectExpressionItem) {
        selectExpressionItem.accept(this);
        this.columnsNames = selectExpressionItem.toString();
    }
}
