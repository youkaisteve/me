import java.util.List;

import cn.youkai.core.sqlparser.dto.EFColumn;
import cn.youkai.core.sqlparser.dto.EFForeignKey;
import cn.youkai.core.sqlparser.dto.EFTable;
import cn.youkai.core.sqlparser.enums.EFDataType;
import cn.youkai.core.sqlparser.extend.VisitorContext;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import cn.youkai.core.sqlparser.SqlParser;
import net.sf.jsqlparser.JSQLParserException;

import static cn.youkai.core.sqlparser.Consts.*;

/**
 * @author youkai
 * @date 2017/12/13.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SqlParser.class)
public class SqlParserTests {
    private String testSql =
            "SELECT t1.I_a as a1,t2.T_A,t2.D_B " +
                    "FROM tab1 t1 " +
                    "inner join tab2 t2 " +
                    "on t1.B_b = t2.B_C " +
                    "inner join tab3 t3 " +
                    "on t2.V100_D = t3.V_x AND t1.I_a = t3.I_y " +
                    "where t1.I_a = 11 AND t3.I_y = 12";

    @Test
    public void print() throws JSQLParserException {
        SqlParser.printSelect(testSql);
    }

    @Test
    public void TableFinderTest() throws JSQLParserException {
        VisitorContext context = SqlParser.extractDatabaseObjects(testSql);
        List<EFTable> tableList = context.getTableList();

        /* tab1 */
        EFTable tab = tableList.get(0);
        Assert.assertEquals("tab1", tab.getTableName());
        Assert.assertEquals("t1", tab.getAliasName());

        /* tab1.cols */
        EFColumn tab1Col = tab.getColumns().get(0);
        Assert.assertEquals("b", tab1Col.getColumnName());
        Assert.assertEquals(EFDataType.BIT, tab1Col.getColumnType());
        Assert.assertEquals(DEFAULT_LENGTH_BIT, tab1Col.getLength());

        tab1Col = tab.getColumns().get(1);
        Assert.assertEquals("a", tab1Col.getColumnName());
        Assert.assertEquals(EFDataType.INT, tab1Col.getColumnType());
        Assert.assertEquals(DEFAULT_LENGTH_INT, tab1Col.getLength());

        /* tab2 */
        tab = tableList.get(1);
        Assert.assertEquals("tab2", tab.getTableName());
        Assert.assertEquals("t2", tab.getAliasName());

        /* tab2.cols */
        tab1Col = tab.getColumns().get(0);
        Assert.assertEquals("C", tab1Col.getColumnName());
        Assert.assertEquals(EFDataType.BIT, tab1Col.getColumnType());
        Assert.assertEquals(DEFAULT_LENGTH_BIT, tab1Col.getLength());

        tab1Col = tab.getColumns().get(1);
        Assert.assertEquals("D", tab1Col.getColumnName());
        Assert.assertEquals(EFDataType.VARCHAR, tab1Col.getColumnType());
        Assert.assertEquals(100, tab1Col.getLength());

        tab1Col = tab.getColumns().get(2);
        Assert.assertEquals("A", tab1Col.getColumnName());
        Assert.assertEquals(EFDataType.TEXT, tab1Col.getColumnType());
        Assert.assertEquals(DEFAULT_LENGTH, tab1Col.getLength());

        tab1Col = tab.getColumns().get(3);
        Assert.assertEquals("B", tab1Col.getColumnName());
        Assert.assertEquals(EFDataType.DATETIME, tab1Col.getColumnType());
        Assert.assertEquals(DEFAULT_LENGTH, tab1Col.getLength());

        /* tab3 */
        tab = tableList.get(2);
        Assert.assertEquals("tab3", tab.getTableName());
        Assert.assertEquals("t3", tab.getAliasName());

        /* tab3.cols */
        tab1Col = tab.getColumns().get(0);
        Assert.assertEquals("x", tab1Col.getColumnName());
        Assert.assertEquals(EFDataType.VARCHAR, tab1Col.getColumnType());
        Assert.assertEquals(DEFAULT_LENGTH_VARCHAR, tab1Col.getLength());

        tab1Col = tab.getColumns().get(1);
        Assert.assertEquals("y", tab1Col.getColumnName());
        Assert.assertEquals(EFDataType.INT, tab1Col.getColumnType());
        Assert.assertEquals(DEFAULT_LENGTH_INT, tab1Col.getLength());

        List<EFForeignKey> foreignKeyList = context.getForeignKeys();
        /* FK tab1.b = tab2.C*/
        EFForeignKey foreignKey = foreignKeyList.get(0);
        Assert.assertEquals("tab1", foreignKey.getNativeTableName());
        Assert.assertEquals("b", foreignKey.getNativeColumnName());
        Assert.assertEquals("tab2", foreignKey.getForeignTableName());
        Assert.assertEquals("C", foreignKey.getForeignColumnName());

        /* FK tab2.D = tab3.X*/
        foreignKey = foreignKeyList.get(1);
        Assert.assertEquals("tab2", foreignKey.getNativeTableName());
        Assert.assertEquals("D", foreignKey.getNativeColumnName());
        Assert.assertEquals("tab3", foreignKey.getForeignTableName());
        Assert.assertEquals("x", foreignKey.getForeignColumnName());

        /* FK tab1.a = tab3.y*/
        foreignKey = foreignKeyList.get(2);
        Assert.assertEquals("tab1", foreignKey.getNativeTableName());
        Assert.assertEquals("a", foreignKey.getNativeColumnName());
        Assert.assertEquals("tab3", foreignKey.getForeignTableName());
        Assert.assertEquals("y", foreignKey.getForeignColumnName());
    }

    @Test
    public void TableSetterTest() throws JSQLParserException {
        VisitorContext context = SqlParser.extractDatabaseObjects(testSql);
        List<EFTable> tableList = context.getTableList();
        for (EFTable table : tableList) {
            table.getColumns().forEach(c -> {
                c.setComment("null");
            });
            System.out.println(SqlParser.getCreateSql(table));
        }
    }
}
