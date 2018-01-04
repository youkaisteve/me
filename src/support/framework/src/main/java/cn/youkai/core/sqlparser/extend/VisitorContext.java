package cn.youkai.core.sqlparser.extend;

import cn.youkai.core.sqlparser.dto.EFForeignKey;
import cn.youkai.core.sqlparser.dto.EFTable;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
 * @author youkai
 */
@Getter
@Setter
public class VisitorContext {
    public VisitorContext(){
        tableList = new ArrayList<>();
        foreignKeys = new ArrayList<>();
    }
    private List<EFTable> tableList;
    private List<EFForeignKey> foreignKeys;
}
