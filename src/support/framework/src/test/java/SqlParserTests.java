import java.util.List;

import cn.youkai.core.sqlparser.dto.EFForeignKey;
import cn.youkai.core.sqlparser.dto.EFTable;
import cn.youkai.core.sqlparser.enums.EFDataType;
import cn.youkai.core.sqlparser.extend.VisitorContext;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import cn.youkai.core.sqlparser.SqlParser;
import net.sf.jsqlparser.JSQLParserException;

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
        for (EFTable table : tableList) {
            System.out.println(table.toString());
        }

        List<EFForeignKey> foreignKeyList = context.getForeignKeys();
        for (EFForeignKey foreignKey : foreignKeyList) {
            System.out.println(foreignKey.toString());
        }
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
