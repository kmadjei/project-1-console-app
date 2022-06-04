package service;

import java.util.List;

import model.EmployeePojo;
import model.ReimbursementPojo;

public interface MainService {
	EmployeePojo validateLogin(String email, String password);
	EmployeePojo getEmployee(int emp_id);
	List<EmployeePojo> getAllEmployees();
	List<ReimbursementPojo> getAllRequests();
	List<ReimbursementPojo> getEmployeeRequests(int emp_id);
	boolean updateRequestDetail(int rb_id, double newAmount);
	boolean updateRequestStatus(int rb_id, String newStatus);
	boolean submitRequest(int emp_id, double amount);
	List<ReimbursementPojo> viewMyRequests(int emp_id, String status);
	EmployeePojo updateEmployee(int emp_id, String fname, String lname, String email);
	List<ReimbursementPojo> getAllRequestsByStatus(String status);
}
