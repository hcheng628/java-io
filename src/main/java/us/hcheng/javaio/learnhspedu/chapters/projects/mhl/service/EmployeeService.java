package us.hcheng.javaio.learnhspedu.chapters.projects.mhl.service;

import us.hcheng.javaio.learnhspedu.chapters.projects.mhl.dao.EmployeeDao;
import us.hcheng.javaio.learnhspedu.chapters.projects.mhl.entity.Employee;

public class EmployeeService {
    private EmployeeDao employeeDao;

    public EmployeeService() {
        employeeDao = new EmployeeDao();
    }

    public Employee getEmployeeByIdAndPwd(String empId, String pwd) {
        return employeeDao.queryOne("select * from employee where empId = ? and pwd = password(?)", Employee.class, empId, pwd);
    }

}
