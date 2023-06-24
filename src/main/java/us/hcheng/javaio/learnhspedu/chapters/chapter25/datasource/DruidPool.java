package us.hcheng.javaio.learnhspedu.chapters.chapter25.datasource;

import us.hcheng.javaio.learnhspedu.chapters.chapter25.util.DBUtil;

public class DruidPool {

    public static void main(String[] args) {
        DBUtil.timeIt(DBUtil.DSUtil.getDataSource(), 5000 * 100);   // TimeMillis: 234
    }

}
