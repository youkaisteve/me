import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import cn.youkai.core.sqlparser.dto.EFForeignKey;
import cn.youkai.core.sqlparser.dto.EFTable;
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
            "SELECT t1.a as a1,t2.A,t2.B " +
                    "FROM tab1 t1 " +
                    "inner join tab2 t2 " +
                    "on t1.b = t2.C " +
                    "inner join tab3 t3 " +
                    "on t2.D = t3.x AND t1.a = t3.y " +
                    "where t1.a = 11 AND t3.y = 12";

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
}
