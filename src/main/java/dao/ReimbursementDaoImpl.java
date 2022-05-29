package dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import model.ReimbursementPojo;

public class ReimbursementDaoImpl implements ReimbursementDao{
	
	@Override
	public List<ReimbursementPojo> getAllRequests(String status) {
		
		Statement stmt;
		List<ReimbursementPojo> reimbursementPojos = new ArrayList<ReimbursementPojo>();
		
		try {
			Connection conn = DBUtil.makeConnection();
			stmt = conn.createStatement();
			String query = "SELECT * FROM reimbursement_details WHERE rb_status='" + status +"';";
			ResultSet rs = stmt.executeQuery(query);
			
			while(rs.next()) {
				ReimbursementPojo reimbursementPojo = new ReimbursementPojo(rs.getInt(1), rs.getString(2),
														rs.getDouble(3), rs.getString(4), rs.getInt(5));
				
				reimbursementPojos.add(reimbursementPojo);
			}
			
		}catch(SQLException e) {
			e.printStackTrace();
		}
		
		return reimbursementPojos;
	}
	
	

	@Override
	public List<ReimbursementPojo> getEmployeeRequests(int emp_id) {

		Statement stmt;
		List<ReimbursementPojo> reimbursementPojos = new ArrayList<ReimbursementPojo>();
		
		try {
			Connection conn = DBUtil.makeConnection();
			stmt = conn.createStatement();
			String query = "SELECT * FROM reimbursement_details WHERE emp_id=" + emp_id +";";
			ResultSet rs = stmt.executeQuery(query);
			
			while(rs.next()) {
				ReimbursementPojo reimbursementPojo = new ReimbursementPojo(rs.getInt(1), rs.getString(2),
														rs.getDouble(3), rs.getString(4), rs.getInt(5));
				
				reimbursementPojos.add(reimbursementPojo);
			}
			
		}catch(SQLException e) {
			e.printStackTrace();
		}
		
		return reimbursementPojos;
		
	}
	
	

	@Override
	public ReimbursementPojo updateRequest(int rb_id, String newStatus) {
		
		Statement stmt;
		Statement stmt2;
		Statement stmt3;
		ReimbursementPojo rbUpdatePojo = null;
		
		try {
			Connection conn = DBUtil.makeConnection();
			stmt = conn.createStatement();
			String query = "SELECT * FROM reimbursement_details WHERE rb_id=" + rb_id +";";
			ResultSet rs = stmt.executeQuery(query);
			
			if(rs.next()) {
				stmt2 = conn.createStatement();
				String query2 = "UPDATE reimbursement_details SET rb_status='" + newStatus + "' WHERE rb_id=" + rb_id +";";
				int rowsAffected = stmt2.executeUpdate(query2);
				
				if(rowsAffected == 1) {
					stmt3 = conn.createStatement();
					String query3 = "SELECT * FROM reimbursement_details WHERE rb_id=" + rb_id +";";
					ResultSet rs2 = stmt3.executeQuery(query3);
					
					if(rs2.next()) {
						rbUpdatePojo = new ReimbursementPojo(rs2.getInt(1), rs2.getString(2),
								rs2.getDouble(3), rs2.getString(4), rs2.getInt(5));
					}
					
				}

			}
			
		}catch(SQLException e) {
			e.printStackTrace();
		}
		
		return rbUpdatePojo;
		
		
	}



	@Override
	public boolean submitRequest(int emp_id, double amount) {
		
		Statement stmt;
		try {
			Connection conn = DBUtil.makeConnection();
			stmt = conn.createStatement();
			String query = "INSERT INTO reimbursement_details(rb_status, rb_amount, rb_timestamp, emp_id) VALUES ('pending', "
					+ amount + ", current_timestamp, " + emp_id + ");";
							
			int rowsAffected = stmt.executeUpdate(query);
			if(rowsAffected == 1) {
				
				return true;
			}
			
			
			
		}catch(SQLException e) {
			e.printStackTrace();
		}
		
		return false;
		
	}



	@Override
	public List<ReimbursementPojo> viewMyRequests(int emp_id, String status) {

		Statement stmt;
		List<ReimbursementPojo> reimbursementPojos = new ArrayList<ReimbursementPojo>();
		
		try {
			Connection conn = DBUtil.makeConnection();
			stmt = conn.createStatement();
			String query = "SELECT * FROM reimbursement_details WHERE emp_id=" + emp_id +" AND rb_status= '" + status + "';";
			ResultSet rs = stmt.executeQuery(query);
			
			while(rs.next()) {
				ReimbursementPojo reimbursementPojo = new ReimbursementPojo(rs.getInt(1), rs.getString(2),
														rs.getDouble(3), rs.getString(4), rs.getInt(5));
				
				reimbursementPojos.add(reimbursementPojo);
			}
			
		}catch(SQLException e) {
			e.printStackTrace();
		}
		
		return reimbursementPojos;
		
	}
		
	
	
	
	
	

}
