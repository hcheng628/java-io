package us.hcheng.javaio.thread.classloader.chapter5;

import java.sql.DriverManager;
import java.sql.SQLException;

public class AppMySQL {

    public static void main(String[] args) throws ClassNotFoundException, SQLException {
//        Class.forName("org.mariadb.jdbc.Driver");
//        Class.forName("com.mysql.jdbc.Driver");


        DriverManager.getConnection("jdbc:mariadb://localhost:3306/global");
        System.out.println("Cheng");
    }

}
