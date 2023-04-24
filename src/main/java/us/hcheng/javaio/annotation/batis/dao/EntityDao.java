package us.hcheng.javaio.annotation.batis.dao;

import us.hcheng.javaio.annotation.batis.util.JavaDBNameUtil;
import us.hcheng.javaio.annotation.batis.util.DBUtil;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.*;

public class EntityDao implements IEntityDao {
    @Override
    public <T> boolean create(T t) {
        boolean ret = false;
        ResultSet rs = null;
        Class<?> klass = t.getClass();
        StringBuilder sql = new StringBuilder(String.join(" ",
                "INSERT INTO", JavaDBNameUtil.tableName(klass), "("));
        List<Field> fields = JavaDBNameUtil.getFields(klass, false);

        for (int i = 0, len = fields.size(); i < len; i++) {
            sql.append(JavaDBNameUtil.fieldName(fields.get(i)));
            if (i != len - 1)
                sql.append(',');
        }
        sql.append(") VALUES (");

        for (int i = 0, len = fields.size(); i < len; i++)
            sql.append(i == len - 1 ? "?)" : "?,");

        try (Connection con = DBUtil.getConnection();
             PreparedStatement pst = con.prepareStatement( sql.toString() )) {
            for (int i = 0, len = fields.size(); i < len; i++) {
                Method m = klass.getMethod(JavaDBNameUtil.accesserName(fields.get(i).getName(), true));
                pst.setObject(i + 1, m.invoke(t));
            }
            ret = pst.executeUpdate() == 1;
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        } finally {
            DBUtil.closeRS(rs);
        }

        return ret;
    }

    @Override
    public <T> T queryById(Class<T> klass, int id) {
        Object ret = null;
        ResultSet rs = null;
        StringBuilder sql = new StringBuilder(String.join(" ",
                "SELECT * FROM", JavaDBNameUtil.tableName(klass), "WHERE id = ?"));

        try (Connection con = DBUtil.getConnection();
             PreparedStatement pst = con.prepareStatement( sql.toString() )) {
            pst.setObject(1, id);
            rs = pst.executeQuery();
            if (rs.next()) {
                ret = klass.getConstructor().newInstance();
                for (Field f : JavaDBNameUtil.getFields(klass, true)) {
                    Method m = klass.getMethod(JavaDBNameUtil.accesserName(f.getName(), false), f.getType());
                    m.invoke(ret, rs.getObject(JavaDBNameUtil.fieldName(f)));
                }
            }
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        } finally {
            DBUtil.closeRS(rs);
        }

        return (T) ret;
    }

    @Override
    public <T> boolean update(T t) {
        boolean ret;
        Class<?> klass = t.getClass();
        List<Field> fields = JavaDBNameUtil.getFields(klass, false);
        StringBuilder sql = new StringBuilder(String.join(" ",
                "UPDATE", JavaDBNameUtil.tableName(klass), "SET "));

        for (int i = 0, len = fields.size(); i < len; i++)
            sql.append(String.join("", JavaDBNameUtil.fieldName(fields.get(i)),
                    i == len - 1 ? " = ? " : " = ?, "));
        sql.append(" WHERE id = ?");

        try (Connection con = DBUtil.getConnection();
             PreparedStatement pst = con.prepareStatement( sql.toString() )) {

            for (int i = 0, len = fields.size(); i < len; i++) {
                Method m = klass.getMethod(JavaDBNameUtil.accesserName(fields.get(i).getName(), true));
                pst.setObject(i + 1, m.invoke(t));
            }

            Method m = klass.getMethod(JavaDBNameUtil.accesserName("id", true));
            pst.setObject(fields.size() + 1, m.invoke(t));
            ret = pst.executeUpdate() == 1;
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }

        return ret;
    }

    @Override
    public <T> boolean delete(T t) {
        boolean ret;
        Class<?> klass = t.getClass();

        StringBuilder sql = new StringBuilder(String.join(" ",
                "DELETE FROM", JavaDBNameUtil.tableName(klass), "WHERE id = ?"));

        try (Connection con = DBUtil.getConnection();
             PreparedStatement pst = con.prepareStatement( sql.toString() )){
            Object val = klass.getMethod(JavaDBNameUtil.accesserName("id", true)).invoke(t);
            pst.setObject(1, val);
            ret = pst.executeUpdate() == 1;
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }

        return ret;
    }

}
