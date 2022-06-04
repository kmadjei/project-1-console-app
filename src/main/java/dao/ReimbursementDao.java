package dao;

import java.util.List;

import model.ReimbursementPojo;

public interface ReimbursementDao {
	
	List<ReimbursementPojo> getAllRequests();
	List<ReimbursementPojo> getEmployeeRequests(int emp_id);
	boolean updateRequestStatus(int rb_id, String newStatus);
	boolean submitRequest(int emp_id, double amount);
	List<ReimbursementPojo> viewMyRequests(int emp_id, String status);
	List<ReimbursementPojo> getAllRequestsByStatus(String status);
	boolean updateRequestDetail(int rb_id, double newAmount);
}
