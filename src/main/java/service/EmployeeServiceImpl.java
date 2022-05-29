package service;

import java.util.List;

import dao.EmployeeDao;
import dao.EmployeeDaoImpl;
import model.EmployeePojo;


public class EmployeeServiceImpl implements EmployeeService{
	
	EmployeeDao employeeDao;
	
	
	public EmployeeServiceImpl() {
		employeeDao = new EmployeeDaoImpl();
	}
	
	public EmployeeDao getEmployeeDao() {
		return employeeDao;
	}
	
	public void setEmployeeDao(EmployeeDao employeeDao) {
		this.employeeDao = employeeDao;
	}

	@Override
	public EmployeePojo validateLogin(String email, String password) {
		return employeeDao.validateLogin(email, password);
	}

	@Override
	public EmployeePojo logout(int emp_id) {
		// TODO Auto-generated method stub
		return null;
	}



	@Override
	public EmployeePojo getEmployee(int emp_id) {
		return employeeDao.getEmployee(emp_id);
	}

	@Override
	public boolean updateEmployee(int emp_id, String changeColumn, String newInfo) {
		return employeeDao.updateEmployee(emp_id, changeColumn, newInfo);
	}



}
