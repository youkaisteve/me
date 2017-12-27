import cn.youkai.core.sqlparser.SqlParser;

import static org.junit.Assert.*;

import net.sf.jsqlparser.JSQLParserException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

/**
 * @author youkai
 * @date 2017/12/13.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SqlParser.class)
public class SqlParserTests {
    @Test
    public void tableNamesTest() throws JSQLParserException {
        List<String> tableNames = SqlParser.getTableList("SELECT t1.a,t2.b,t2.c FROM tab1 t1 inner join tab2 t2 on t1.a = t2.b");
        assertEquals("tab1", tableNames.get(0));
        assertEquals("tab2", tableNames.get(1));
    }

    @Test
    public void getBodyTest() throws JSQLParserException {
        List<String> body = SqlParser.getColumns("SELECT t1.a,t2.b,t2.c FROM tab1 t1 inner join tab2 t2 on t1.a = t2.b");
        List<String> expected = new ArrayList();
        expected.add("t1.a");
        expected.add("t2.b");
        expected.add("t2.c");
        assertEquals(expected, body);
    }

    @Test
    public void print() throws JSQLParserException {
        SqlParser.printSelect("SELECT t1.a,t2.b,t2.c FROM tab1 t1 inner join tab2 t2 on t1.a = t2.b where t1.a = 11");
    }

    private void getNextLngLat(double lng, double lat, double distance) {
        
    }

}
