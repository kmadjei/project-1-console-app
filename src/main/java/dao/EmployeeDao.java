package dao;

import java.util.List;

import model.EmployeePojo;
import model.ReimbursementPojo;

public interface EmployeeDao {
    
    // employees and manager
    EmployeePojo validateLogin(String email, String password);
    EmployeePojo logout(int emp_id);
    
    // employees only
    
    EmployeePojo getEmployee(int emp_id);
    boolean updateEmployee(int emp_id, String changeColumn, String newInfo);
    

    

}