package us.hcheng.javaio.learnhspedu.mhl.service;

import us.hcheng.javaio.learnhspedu.mhl.dao.EmployeeDao;
import us.hcheng.javaio.learnhspedu.mhl.entity.Employee;

public class EmployeeService {
    private EmployeeDao employeeDao;

    public EmployeeService() {
        employeeDao = new EmployeeDao();
    }

    public Employee getEmployeeByIdAndPwd(String empId, String pwd) {
        return employeeDao.queryOne("select * from employee where empId = ? and pwd = password(?)", Employee.class, empId, pwd);
    }

}
