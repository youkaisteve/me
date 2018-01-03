import static org.junit.Assert.assertEquals;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import cn.youkai.core.sqlparser.dto.EFTable;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import cn.youkai.core.sqlparser.SqlParser;
import net.sf.jsqlparser.JSQLParserException;

import javax.sql.rowset.serial.SerialException;
import javax.sql.rowset.serial.SerialJavaObject;

/**
 * @author youkai
 * @date 2017/12/13.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SqlParser.class)
public class SqlParserTests {
    private String testSql =
            "SELECT t1.a as a1,t2.b,t2.c " +
                    "FROM tab1 t1 " +
                    "inner join tab2 t2 " +
                    "on t1.a = t2.b " +
                    "where t1.a = 11";

    @Test
    public void tableNamesTest() throws JSQLParserException {
        List<String> tableNames = SqlParser.getTableList(testSql);
        assertEquals("tab1", tableNames.get(0));
        assertEquals("tab2", tableNames.get(1));
    }

    @Test
    public void getBodyTest() throws JSQLParserException {
        List<String> body = SqlParser.getColumns(testSql);
        List<String> expected = new ArrayList<>();
        expected.add("t1.a");
        expected.add("t2.b");
        expected.add("t2.c");
        assertEquals(expected, body);
    }

    @Test
    public void print() throws JSQLParserException {
        SqlParser.printSelect(testSql);
    }

    @Test
    public void TableFinderTest() throws JSQLParserException {
        List<EFTable> tableList = SqlParser.extractDatabaseObjects(testSql);
        for (EFTable table : tableList) {
            System.out.println(table.toString());
        }
    }
}
