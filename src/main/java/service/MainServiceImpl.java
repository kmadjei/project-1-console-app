package service;

import java.util.List;

import dao.EmployeeDao;
import dao.EmployeeDaoImpl;
import dao.ReimbursementDao;
import dao.ReimbursementDaoImpl;
import model.EmployeePojo;
import model.ReimbursementPojo;

public class MainServiceImpl implements MainService {
	
	EmployeeDaoImpl employeeDao;
	ReimbursementDaoImpl reimbursementDao;
	
	public MainServiceImpl() {
		employeeDao = new EmployeeDaoImpl();
		reimbursementDao = new ReimbursementDaoImpl();
	}

	@Override
	public EmployeePojo validateLogin(String email, String password) {
		return employeeDao.validateLogin(email, password);
	}


	@Override
	public EmployeePojo getEmployee(int emp_id) {
		return employeeDao.getEmployee(emp_id);
	}

	@Override
	public EmployeePojo updateEmployee(int emp_id, String fname, String lname, String email) {
		return employeeDao.updateEmployee(emp_id, fname, lname, email);
	}
	
	@Override
	public List<ReimbursementPojo> getAllRequests() {
		return reimbursementDao.getAllRequests();
	}

	@Override
	public List<ReimbursementPojo> getAllRequestsByStatus(String status) {
		return reimbursementDao.getAllRequestsByStatus(status);
	}

	@Override
	public List<ReimbursementPojo> getEmployeeRequests(int emp_id) {
		return reimbursementDao.getEmployeeRequests(emp_id);
	}

	@Override
	public boolean updateRequestStatus(int rb_id, String newStatus) {
		return reimbursementDao.updateRequestStatus(rb_id, newStatus);
	}
	
	@Override
	public boolean updateRequestDetail(int rb_id, double newAmount) {
		return reimbursementDao.updateRequestDetail(rb_id, newAmount);
	}

	@Override
	public boolean submitRequest(int emp_id, double amount) {
		return reimbursementDao.submitRequest(emp_id, amount);
	}

	@Override
	public List<ReimbursementPojo> viewMyRequests(int emp_id, String status) {
		return reimbursementDao.viewMyRequests(emp_id, status);
	}

	@Override
	public List<EmployeePojo> getAllEmployees() {
		return employeeDao.getAllEmployees();
	}

}
