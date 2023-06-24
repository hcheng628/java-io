package us.hcheng.javaio.learnhspedu.chapters.chapter25.daodesign.service;

import us.hcheng.javaio.learnhspedu.chapters.chapter25.daodesign.dao.HspUserDao;
import us.hcheng.javaio.learnhspedu.chapters.chapter25.daodesign.domain.HspUser;

import java.util.List;
import java.util.Map;

public class HspUserService {
    private HspUserDao hspUserDao;

    public HspUserService() {
        hspUserDao = new HspUserDao();
    }

    public boolean createHspUser(HspUser user) {
        String sql = "insert into hsp_user values(?, ?, ?)";
        return hspUserDao.execute(sql, user.getId(), user.getName(), user.getPwd()) > 0;
    }

    public HspUser queryHspUserById(int id) {
        String sql = "select * from hsp_user where id = ?";
        return hspUserDao.queryOne(sql, HspUser.class, id);
    }

    public String getHspUserNameById(int id) {
        String sql = "select name from hsp_user where id = ?";
        Object res = hspUserDao.queryScalar(sql, id);
        return res != null ? res.toString() : null;
    }

    public boolean deleteHspUser(int id) {
        String sql = "delete from hsp_user where id = ?";
        return hspUserDao.execute(sql, id) > 0;
    }

    public Map<String, Object> getWeakPwdUsersOne() {
        String sql = "select * from hsp_user where length(pwd) < 10";
        return hspUserDao.queryMapHandler(sql);
    }

    public List<Map<String, Object>> getWeakPwdUsers() {
        String sql = "select * from hsp_user where length(pwd) < 10";
        return hspUserDao.queryMapListHandler(sql);
    }

}
