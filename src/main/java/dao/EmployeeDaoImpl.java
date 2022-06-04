package dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import model.EmployeePojo;

public class EmployeeDaoImpl {

	//@Override
	public EmployeePojo validateLogin(String email, String password) {
		try(Connection conn = DBUtil.makeConnection();) {
			
	
			Statement stmt = conn.createStatement();
			String query = "SELECT * FROM employee_details WHERE email = '" + email + "' AND password = '"
					+ password + "'";
			ResultSet resultSet = stmt.executeQuery(query);
			
			if(resultSet.next()) {
				
				return new EmployeePojo(resultSet.getInt(1), resultSet.getString(2), resultSet.getInt(3), resultSet.getString(4),
						resultSet.getString(5), resultSet.getString(6));
			}

		} catch(SQLException e) {
			e.printStackTrace();		
		}
		
		return null;
	}


	//@Override
	public EmployeePojo getEmployee(int emp_id) {
		try {
			Connection conn = DBUtil.makeConnection();
			Statement stmt = conn.createStatement();
			String query = "SELECT * FROM employee_details WHERE emp_id =" + emp_id + ";";
			ResultSet resultSet = stmt.executeQuery(query);
			
			if(!resultSet.isBeforeFirst()) {
				return null;
			} else {
				resultSet.next();
				return new EmployeePojo(resultSet.getInt(1), resultSet.getString(2), resultSet.getInt(3), resultSet.getString(4),
						resultSet.getString(5), resultSet.getString(6));
			}
			
		} catch(SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	//@Override
	public EmployeePojo updateEmployee(int emp_id, String fname, String lname, String email) {
		try(Connection conn = DBUtil.makeConnection();) {
			
			Statement stmt = conn.createStatement();
			
			String query = "UPDATE employee_details SET email ='" + email + "', "
					+ "fname ='" + fname + "', lname ='" + lname + "' WHERE emp_id =" + emp_id + " RETURNING *;";
			System.out.println(query);
			 ResultSet resultSet = stmt.executeQuery(query);
			 EmployeePojo emp = null; 
			 
			 while (resultSet.next()) {
				 emp = new EmployeePojo(resultSet.getInt(1), resultSet.getString(2), resultSet.getInt(3), resultSet.getString(4),
							resultSet.getString(5), resultSet.getString(6));
			 }

			 return emp;
			
		} catch(SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	//@Override
	public List<EmployeePojo> getAllEmployees() {
		try {
			List<EmployeePojo> employees = new ArrayList<EmployeePojo>();
			Connection conn = DBUtil.makeConnection();
			Statement stmt = conn.createStatement();
			String query = "SELECT * FROM employee_details;";
			ResultSet resultSet = stmt.executeQuery(query);
			while(resultSet.next()) {
				employees.add(new EmployeePojo(resultSet.getInt(1), resultSet.getString(2), resultSet.getInt(3), resultSet.getString(4),
						resultSet.getString(5), resultSet.getString(6)));
			}
			return employees;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}


}
