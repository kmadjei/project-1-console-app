package service;

import java.util.List;


import dao.ReimbursementDao;
import dao.ReimbursementDaoImpl;
import model.ReimbursementPojo;

public class ReimbursementServiceImpl implements ReimbursementService{
	
	ReimbursementDao reimbursementDao;
	
	
	public ReimbursementServiceImpl() {
		reimbursementDao = new ReimbursementDaoImpl();
	}
	
	public ReimbursementDao getReimbursemenDao() {
		return reimbursementDao;
	}
	
	public void setReimbursementDao(ReimbursementDao ReimbursementDao) {
		this.reimbursementDao = ReimbursementDao;
	}
	
	

	@Override
	public List<ReimbursementPojo> getAllRequests(String status) {
		List<ReimbursementPojo> allRbs = this.reimbursementDao.getAllRequests(status);
		return allRbs;
	}

	
	
	@Override
	public List<ReimbursementPojo> getEmployeeRequests(int emp_id) {
		List<ReimbursementPojo> empRbs = this.reimbursementDao.getEmployeeRequests(emp_id);
		return empRbs;
	}
	
	

	@Override
	public ReimbursementPojo updateRequest(int rb_id, String newStatus) {
		ReimbursementPojo updateRb = this.reimbursementDao.updateRequest(rb_id, newStatus);
		return updateRb;
	}

	@Override
	public boolean submitRequest(int emp_id, double amount) {
		return reimbursementDao.submitRequest(emp_id, amount);
	}

	@Override
	public List<ReimbursementPojo> viewMyRequests(int emp_id, String status) {
		List<ReimbursementPojo> empRbs = this.reimbursementDao.viewMyRequests(emp_id, status);
		return empRbs;
	}

}
