package us.hcheng.javaio.learnhspedu.chapters.chapter25.daodesign.dao;


import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.ColumnListHandler;
import org.apache.commons.dbutils.handlers.MapHandler;
import org.apache.commons.dbutils.handlers.MapListHandler;
import us.hcheng.javaio.learnhspedu.chapters.chapter25.daodesign.domain.HspUser;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public class HspUserDao extends BasicDao<HspUser> {

    public Map<String, Object> queryMapHandler(String sql, Object... params) {
        try {
            return new QueryRunner(ds).query(sql, new MapHandler(), params);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Map<String, Object>> queryMapListHandler(String sql, Object... params) {
        try {
            return new QueryRunner(ds).query(sql, new MapListHandler(), params);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
