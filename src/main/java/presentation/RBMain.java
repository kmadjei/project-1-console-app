package presentation;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import model.EmployeePojo;
import model.ReimbursementPojo;
import service.EmployeeService;
import service.EmployeeServiceImpl;
import service.ReimbursementService;
import service.ReimbursementServiceImpl;

public class RBMain {

	public static void main(String[] args){
		
		Scanner scan = new Scanner(System.in);
		
		EmployeeService employeeService = new EmployeeServiceImpl();
		ReimbursementService rbService = new ReimbursementServiceImpl();
		
		System.out.println("***Employee Reimbursement System***");
		System.out.println();
		
		
		int loginOption = 0;
		String ch = "y";
		
		System.out.println("1. Manager login");
		System.out.println("2. Employee login");
		System.out.println("3. Exit");
		System.out.println();
		
		loginOption = scan.nextInt();
		scan.nextLine();
		
		switch(loginOption) {
		
		case 1:
			
			System.out.println("Enter manager email: ");
			String mgrEmail = scan.nextLine();
			System.out.println();
			
			System.out.println("Enter manager password: ");
			String mgrPassword = scan.nextLine();
			System.out.println();
			
			EmployeePojo mgrPojo = employeeService.validateLogin(mgrEmail, mgrPassword);
			System.out.println();
			
			if(mgrPojo != null) {
				
				System.out.println("Welcome, "+mgrPojo.getFname()+" "+mgrPojo.getLname());
				System.out.println();
				
				while("y".equals(ch)) {
					
					System.out.println("Select an option: ");
					System.out.println();
					System.out.println("1. Get employee information"); // employee and manager
					System.out.println("2. Get all requests"); // manager
					System.out.println("3. Get employee requests"); // manager
					System.out.println("4. Update reimbursement request"); // manager
					System.out.println("5. Exit");
					System.out.println();
				
				
					int option = 0;
					option = scan.nextInt();
					scan.nextLine();
					
					EmployeePojo employeePojo = new EmployeePojo();
					
					switch(option) {
					case 1:
						System.out.println("Enter employee ID: ");
						int empID1 = scan.nextInt();
						scan.nextLine();
						
						employeePojo = employeeService.getEmployee(empID1);
						
						System.out.println(employeePojo.toString());
						System.out.println();
						
						System.out.println("Continue? (y/n)");
						ch = scan.nextLine();
						
						System.out.println();
						break;
						
					case 2:
						List<ReimbursementPojo> allRbs = new ArrayList<ReimbursementPojo>();
						
						System.out.println("Enter reimbursement status (pending, approved, or denied): ");
						String requestStatus = scan.nextLine();
						System.out.println();
						
						allRbs = rbService.getAllRequests(requestStatus);
						
						for(ReimbursementPojo rb : allRbs) {
							System.out.println(rb.toString());
							System.out.println();
						}
						System.out.println();
						
						System.out.println("Continue? (y/n)");
						ch = scan.nextLine();
						
						System.out.println();
						break;
						
					case 3:
						List<ReimbursementPojo> empRbs = new ArrayList<ReimbursementPojo>();
						
						System.out.println("Enter employee ID: ");
						int requestID = scan.nextInt();
						scan.nextLine();
						System.out.println();
						
						empRbs = rbService.getEmployeeRequests(requestID);
						
						for(ReimbursementPojo rb: empRbs) {
							System.out.println(rb.toString());
							System.out.println();
						}
						System.out.println();
						
						System.out.println("Continue? (y/n)");
						ch = scan.nextLine();
						
						System.out.println();
						break;
						
					case 4:
						ReimbursementPojo updateRb = new ReimbursementPojo();
						
						System.out.println("Enter reimbursement ID: ");
						int updateRequestID = scan.nextInt();
						scan.nextLine();
						System.out.println();
						
						System.out.println("Enter new status (approved or denied): ");
						String newStatus = scan.nextLine();
						System.out.println();
						
						updateRb = rbService.updateRequest(updateRequestID, newStatus);
						
						System.out.println(updateRb.toString());
						System.out.println();
						
						System.out.println("Continue? (y/n)");
						ch = scan.nextLine();
						
						System.out.println();
						break;
						
					case 5:
						System.exit(0);
					
					}
					
				}
			}
			
			else {
				System.exit(0);
			}
			
			break;
			
		case 2:
			
			System.out.println("Enter employee email: ");
			String empEmail = scan.nextLine();
			System.out.println();
			
			System.out.println("Enter employee password: ");
			String empPassword = scan.nextLine();
			System.out.println();
			
			EmployeePojo empPojo = employeeService.validateLogin(empEmail, empPassword);
			System.out.println();
			
			if(empPojo != null) {
				System.out.println("Welcome, "+empPojo.getFname()+" "+empPojo.getLname());
				System.out.println();
				
				while("y".equals(ch)) {
				
					System.out.println("Select an option: ");
					System.out.println();
					System.out.println("1. Get employee information"); // employee and manager
					System.out.println("2. Submit request "); // employee
					System.out.println("3. View my requests"); // employee
					System.out.println("4. Update employee information"); // employee
					System.out.println("5. Exit");
					System.out.println();
					
					int option = 0;
					option = scan.nextInt();
					scan.nextLine();
					
					EmployeePojo employeePojo = new EmployeePojo();
					
					switch(option) {
					case 1:
						System.out.println("Enter employee ID: ");
						int empID1 = scan.nextInt();
						System.out.println();
						
						employeePojo = employeeService.getEmployee(empID1);
						
						System.out.println(employeePojo.toString());
						System.out.println();
						
						System.out.println("Continue? (y/n)");
						ch = scan.nextLine();
						
						System.out.println();
						break;
						
					case 2:
						System.out.println("Enter employee ID: ");
						int empID5 = scan.nextInt();
						scan.nextLine();
						System.out.println();
						
						System.out.println("Enter reimbursement request amount: ");
						double rbAmount = scan.nextDouble();
						System.out.println();
						
						boolean submitRequest = rbService.submitRequest(empID5, rbAmount);
						System.out.println("Request submitted.");
						System.out.println();
						
						System.out.println("Continue? (y/n)");
						ch = scan.nextLine();
						
						System.out.println();
						break;
						
					case 3:
						List<ReimbursementPojo> myRbs = new ArrayList<ReimbursementPojo>();
						
						System.out.println("Enter employee ID: ");
						int empID6 = scan.nextInt();
						scan.nextLine();
						System.out.println();
						
						System.out.println("Enter reimbursement status (pending, approved, or denied): ");
						String empRequestStatus = scan.nextLine();
						System.out.println();
						
						myRbs = rbService.viewMyRequests(empID6, empRequestStatus);
						
						for(ReimbursementPojo rb : myRbs) {
							System.out.println(rb.toString());
							System.out.println();
						}
						
						System.out.println("Continue? (y/n)");
						ch = scan.nextLine();
						
						System.out.println();
						break;
						
					case 4:
						String proceed = "y";
						while("y".equals(proceed)) {
							
							System.out.println("Enter employee ID: ");
							int empID7 = scan.nextInt();
							scan.nextLine();
							System.out.println();
							
							System.out.println("What information would you like to change? (fname, lname, password, or email): ");
							String changeColumn = scan.nextLine();
							System.out.println();
							
							System.out.println("Please enter the new information for "+changeColumn+": ");
							String newInfo = scan.nextLine();
							System.out.println();
							
							boolean updateEmp = employeeService.updateEmployee(empID7, changeColumn, newInfo);
							
							System.out.println("Update more information? (y/n)");
							proceed = scan.nextLine();
							System.out.println();
						}
						
						System.out.println("Continue? (y/n)");
						ch = scan.nextLine();
						
						System.out.println();
						break;
					case 5:
						System.exit(0);
						
						
					}
				}
			}
			else {
				System.exit(0);
			}
		case 3:
			System.exit(0);
		}
		
		scan.close();

	} 

}
