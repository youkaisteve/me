package cn.youkai.core.sqlparser.extend;

import cn.youkai.core.sqlparser.Util;
import cn.youkai.core.sqlparser.dto.EFColumn;
import cn.youkai.core.sqlparser.dto.EFForeignKey;
import cn.youkai.core.sqlparser.dto.EFTable;
import lombok.Getter;
import lombok.Setter;
import net.sf.jsqlparser.expression.*;
import net.sf.jsqlparser.expression.operators.arithmetic.*;
import net.sf.jsqlparser.expression.operators.conditional.AndExpression;
import net.sf.jsqlparser.expression.operators.conditional.OrExpression;
import net.sf.jsqlparser.expression.operators.relational.*;
import net.sf.jsqlparser.schema.Column;
import net.sf.jsqlparser.statement.select.SubSelect;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

@Getter
@Setter
public class ExpressionFinder implements ExpressionVisitor {
    private Logger logger = Logger.getLogger("OnExpressionFinder");

    VisitorContext _visitorContext;

    public ExpressionFinder(VisitorContext visitorContext) {
        _visitorContext = visitorContext;
    }

    @Override
    public void visit(NullValue nullValue) {

    }

    @Override
    public void visit(Function function) {

    }

    @Override
    public void visit(SignedExpression signedExpression) {

    }

    @Override
    public void visit(JdbcParameter jdbcParameter) {

    }

    @Override
    public void visit(JdbcNamedParameter jdbcNamedParameter) {

    }

    @Override
    public void visit(DoubleValue doubleValue) {

    }

    @Override
    public void visit(LongValue longValue) {

    }

    @Override
    public void visit(HexValue hexValue) {

    }

    @Override
    public void visit(DateValue dateValue) {

    }

    @Override
    public void visit(TimeValue timeValue) {

    }

    @Override
    public void visit(TimestampValue timestampValue) {

    }

    @Override
    public void visit(Parenthesis parenthesis) {

    }

    @Override
    public void visit(StringValue stringValue) {

    }

    @Override
    public void visit(Addition addition) {

    }

    @Override
    public void visit(Division division) {

    }

    @Override
    public void visit(Multiplication multiplication) {

    }

    @Override
    public void visit(Subtraction subtraction) {

    }

    @Override
    public void visit(AndExpression andExpression) {
        andExpression.getLeftExpression().accept(this);
        andExpression.getRightExpression().accept(this);
    }

    @Override
    public void visit(OrExpression orExpression) {
        orExpression.getLeftExpression().accept(this);
        orExpression.getRightExpression().accept(this);
    }

    @Override
    public void visit(Between between) {

    }

    /**
     * ex - "t1.a = t2.b"
     * 遇到t1,则去识别表名或者表别名为t1的表，使用t1去找到表别名为t1的表，如果没有，则找到表名为t1的表，为其添加Column
     *
     * @param equalsTo
     */
    @Override
    public void visit(EqualsTo equalsTo) {
        logger.log(Level.FINE,"visit EqualsTo");
        List<String[]> tableColumnExpression = new ArrayList<>();
        String leftExpression = equalsTo.getLeftExpression().toString();
        String rightExpression = equalsTo.getRightExpression().toString();

        EFForeignKey efForeignKey = new EFForeignKey();
        efForeignKey.setSysNo(UUID.randomUUID().toString());

        tableColumnExpression.add(Util.getAliasExpression(leftExpression));
        tableColumnExpression.add(Util.getAliasExpression(rightExpression));

        int index = 0;
        for (String[] item : tableColumnExpression) {
            EFTable table = null;
            if (!item[0].isEmpty()) {
                Optional<EFTable> findTable = this._visitorContext.getTableList().stream().filter(t -> item[0].equals(t.getAliasName())).findFirst();
                if (findTable.isPresent()) {
                    table = findTable.get();
                }
            } else {
                EFTable tempTable = this._visitorContext.getTableList().get(0);
                if (null != tempTable) {
                    if (tempTable.getAliasName().equals(item[0])) {
                        table = tempTable;
                    }
                }
            }

            if (null != table) {
                String colName = Util.getColumnName(item[1]);
                Optional<EFColumn> findColumn = table.getColumns().stream().filter(c -> colName.equals(c.getColumnName())).findFirst();
                EFColumn newColumn;
                if (!findColumn.isPresent()) {
                    newColumn = new EFColumn();
                    newColumn.setSysNo(UUID.randomUUID().toString());
                    newColumn.setColumnName(colName);
                    newColumn.setLength(Util.getColumnLength(item[1]));
                    newColumn.setColumnType(Util.getColumnDataType(item[1]));
                    table.getColumns().add(newColumn);
                } else {
                    newColumn = findColumn.get();
                }

                if (index == 0) {
                    efForeignKey.setNativeTableSysNo(table.getSysNo());
                    efForeignKey.setNativeTableName(table.getTableName());
                    efForeignKey.setNativeColumnSysNo(newColumn.getSysNo());
                    efForeignKey.setNativeColumnName(newColumn.getColumnName());
                } else {
                    efForeignKey.setForeignTableSysNo(table.getSysNo());
                    efForeignKey.setForeignTableName(table.getTableName());
                    efForeignKey.setForeignColumnSysNo(newColumn.getSysNo());
                    efForeignKey.setForeignColumnName(newColumn.getColumnName());
                }
            }
            index++;
        }
        this._visitorContext.getForeignKeys().add(efForeignKey);
    }

    @Override
    public void visit(GreaterThan greaterThan) {

    }

    @Override
    public void visit(GreaterThanEquals greaterThanEquals) {

    }

    @Override
    public void visit(InExpression inExpression) {

    }

    @Override
    public void visit(IsNullExpression isNullExpression) {

    }

    @Override
    public void visit(LikeExpression likeExpression) {

    }

    @Override
    public void visit(MinorThan minorThan) {

    }

    @Override
    public void visit(MinorThanEquals minorThanEquals) {

    }

    @Override
    public void visit(NotEqualsTo notEqualsTo) {

    }

    @Override
    public void visit(Column tableColumn) {

    }

    @Override
    public void visit(SubSelect subSelect) {

    }

    @Override
    public void visit(CaseExpression caseExpression) {

    }

    @Override
    public void visit(WhenClause whenClause) {

    }

    @Override
    public void visit(ExistsExpression existsExpression) {

    }

    @Override
    public void visit(AllComparisonExpression allComparisonExpression) {

    }

    @Override
    public void visit(AnyComparisonExpression anyComparisonExpression) {

    }

    @Override
    public void visit(Concat concat) {

    }

    @Override
    public void visit(Matches matches) {

    }

    @Override
    public void visit(BitwiseAnd bitwiseAnd) {

    }

    @Override
    public void visit(BitwiseOr bitwiseOr) {

    }

    @Override
    public void visit(BitwiseXor bitwiseXor) {

    }

    @Override
    public void visit(CastExpression cast) {

    }

    @Override
    public void visit(Modulo modulo) {

    }

    @Override
    public void visit(AnalyticExpression aexpr) {

    }

    @Override
    public void visit(WithinGroupExpression wgexpr) {

    }

    @Override
    public void visit(ExtractExpression eexpr) {

    }

    @Override
    public void visit(IntervalExpression iexpr) {

    }

    @Override
    public void visit(OracleHierarchicalExpression oexpr) {

    }

    @Override
    public void visit(RegExpMatchOperator rexpr) {

    }

    @Override
    public void visit(JsonExpression jsonExpr) {

    }

    @Override
    public void visit(JsonOperator jsonExpr) {

    }

    @Override
    public void visit(RegExpMySQLOperator regExpMySQLOperator) {

    }

    @Override
    public void visit(UserVariable var) {

    }

    @Override
    public void visit(NumericBind bind) {

    }

    @Override
    public void visit(KeepExpression aexpr) {

    }

    @Override
    public void visit(MySQLGroupConcat groupConcat) {

    }

    @Override
    public void visit(RowConstructor rowConstructor) {

    }

    @Override
    public void visit(OracleHint hint) {

    }

    @Override
    public void visit(TimeKeyExpression timeKeyExpression) {

    }

    @Override
    public void visit(DateTimeLiteralExpression literal) {

    }

    @Override
    public void visit(NotExpression aThis) {

    }
}
