package dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import model.EmployeePojo;


public class EmployeeDaoImpl implements EmployeeDao{

	@Override
	public EmployeePojo validateLogin(String email, String password) {
		EmployeePojo employeePojo = null;
		Statement stmt;
		try {
			Connection conn = DBUtil.makeConnection();
			stmt = conn.createStatement();
			String query = "SELECT * FROM employee_details WHERE email='"+email+"' AND password='"+password+"';";
			ResultSet rs = stmt.executeQuery(query);
			if(rs.next()) {
				System.out.println("Login successful");
				employeePojo = new EmployeePojo(rs.getInt(1), rs.getString(2), rs.getInt(3), rs.getString(4),
						rs.getString(5), rs.getString(6));
			}
			else {
				System.out.println("Login failed");
			}
			
		} catch(SQLException e) {
			
		}
		return employeePojo;
	}

	@Override
	public EmployeePojo logout(int emp_id) {
		
		return null;
	}

	
	
	@Override
	public EmployeePojo getEmployee(int emp_id){
		Statement stmt;
		EmployeePojo employeePojo = null;
		try {
			Connection conn = DBUtil.makeConnection();
			stmt = conn.createStatement();
			String query = "SELECT * FROM employee_details WHERE emp_id=" + emp_id + ";";
			ResultSet rs = stmt.executeQuery(query);
			
			if (rs.next()) {
				employeePojo = new EmployeePojo(rs.getInt(1), rs.getString(2), rs.getInt(3), rs.getString(4),
												rs.getString(5), rs.getString(6));
			}
			
		} catch(SQLException e) {
			e.printStackTrace();
		}
		
		return employeePojo;
	}

	
	
	@Override
	public boolean updateEmployee(int emp_id, String changeColumn, String newInfo) {
		
		Statement stmt;
		Statement stmt2;
		
		try {
			Connection conn = DBUtil.makeConnection();
			stmt = conn.createStatement();
			String query = "SELECT * FROM employee_details WHERE emp_id=" + emp_id + ";";
			ResultSet rs = stmt.executeQuery(query);
			
			if (rs.next()) {
				if("fname".equals(changeColumn)) {
					stmt2 = conn.createStatement();
					String query2 = "UPDATE employee_details SET fname='"+newInfo+"' WHERE emp_id="+emp_id+";";
					int rowsAffected = stmt2.executeUpdate(query2);
					if(rowsAffected==1) {
						System.out.println("Information updated.");
						return true;
					}
				}
				else if("lname".equals(changeColumn)) {
					stmt2 = conn.createStatement();
					String query2 = "UPDATE employee_details SET lname='"+newInfo+"' WHERE emp_id="+emp_id+";";
					int rowsAffected = stmt2.executeUpdate(query2);
					if(rowsAffected==1) {
						System.out.println("Information updated.");
						return true;
					}
				}
				else if("email".equals(changeColumn)) {
					stmt2 = conn.createStatement();
					String query2 = "UPDATE employee_details SET email='"+newInfo+"' WHERE emp_id="+emp_id+";";
					int rowsAffected = stmt2.executeUpdate(query2);
					if(rowsAffected==1) {
						System.out.println("Information updated.");
						return true;
					}
				}
				else if("password".equals(changeColumn)) {
					stmt2 = conn.createStatement();
					String query2 = "UPDATE employee_details SET password='"+newInfo+"' WHERE emp_id="+emp_id+";";
					int rowsAffected = stmt2.executeUpdate(query2);
					if(rowsAffected==1) {
						System.out.println("Information updated.");
						return true;
					}
				}
				
				
			}
			
		} catch(SQLException e) {
			e.printStackTrace();
		}
		
		return false;
	}



	
	

	
}
