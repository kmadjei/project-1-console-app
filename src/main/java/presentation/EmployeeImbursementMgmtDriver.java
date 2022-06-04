package presentation;

import java.util.List;

import io.javalin.Javalin;
import model.EmployeePojo;
import model.ReimbursementPojo;
import service.MainService;
import service.MainServiceImpl;

public class EmployeeImbursementMgmtDriver {

	public static void main(String[] args) {

		MainService mainServ = new MainServiceImpl();
		
		// Initialize Restful server with cors enabled
		Javalin server = Javalin.create((config) -> config.enableCorsForAllOrigins());
		server.start(7474);
		
		server.get("/hello", (ctx)->{
			System.out.println("hello endpoint called....");
			ctx.result("hello returned from the endpoint");
		});
		
		//get all reimbursements by status
		server.get("/reimbursements/{status}", ctx -> {
			List<ReimbursementPojo> reimbursements = mainServ.getAllRequests(ctx.pathParam("status"));
			ctx.json(reimbursements);
		});
		
		//get all reimbursements for employee
		server.get("/reimbursements/emp/{emp_id}", ctx -> {
			int emp_id = Integer.parseInt(ctx.pathParam("emp_id"));
			System.out.println("/reimbursements/emp/{emp_id}...");
			List<ReimbursementPojo> reimbursements = mainServ.getEmployeeRequests(emp_id);
			ctx.json(reimbursements);
		});
		
		//get all reimbursements requests for employee by status
		server.get("/reimbursements/emp/{emp_id}/{status}", ctx -> {
			int emp_id = Integer.parseInt(ctx.pathParam("emp_id"));
			String status = ctx.pathParam("status");
			List<ReimbursementPojo> reimbursements = mainServ.viewMyRequests(emp_id, status);
			ctx.json(reimbursements);
		});
		
		//update reimbursement request status
		server.put("/reimbursements/status/update", ctx -> {
			System.out.println("Update --> /reimbursements/status/update");
			int rb_id = Integer.parseInt(ctx.formParam("rb_id"));
			String rb_status = ctx.formParam("rb_status");

			// submit parameters to DB			
			if(mainServ.updateRequestStatus(rb_id, rb_status)) {
				ctx.result("Reimbursement Status Updated Successfully!").status(200);
			} else {
				ctx.result("Reimbursement Status Update failed to submit!").status(417);
			}	
		});
		
		//update reimbursement request details
		server.put("/reimbursements/details/update", ctx -> {
			System.out.println("Update --> /reimbursements/details/update");
			int rb_id = Integer.parseInt(ctx.formParam("rb_id"));
			double rb_amount = Double.parseDouble(ctx.formParam("amount"));
					
			// submit parameters to DB			
			if(mainServ.updateRequestDetail(rb_id, rb_amount)) {
				ctx.result("Reimbursement Amount Updated Successfully!").status(200);
			} else {
				ctx.result("Reimbursement Amount Update failed to submit!").status(417);
			}	
		});
		
		//create reimbursement request
		server.post("/reimbursements", ctx -> {
			System.out.println("Generate --> /reimbursements/emp/{emp_id}...");
			// get form parameters
			int emp_id = Integer.parseInt(ctx.formParam("emp_id"));
			double amount = Double.parseDouble(ctx.formParam("amount"));
			
			// submit parameters to DB			
			if(mainServ.submitRequest(emp_id, amount)) {
				ctx.result("Reimbursement submitted Successfully!").status(201);
			} else {
				ctx.result("Reimbursement request failed to submit!").status(417);
			}		
		});
		
		// handle employee  login
		server.post("/login", ctx -> {
			System.out.println("login..");
			String email = ctx.formParam("email");
			String password = ctx.formParam("password");
			
			EmployeePojo user = mainServ.validateLogin(email, password);
			if (user == null) {
				ctx.result("No match found. Please try again.").status(404);
				
			} else {
				ctx.json(user);
			}
		});
		
		//clear out session on logout
		server.get("/logout", ctx -> {
			ctx.consumeSessionAttribute("emp_id");
		});
		
		//get specific employee details
		server.get("/employees/{emp_id}", ctx -> {
			int emp_id = ctx.sessionAttribute("emp_id");
			EmployeePojo emp = mainServ.getEmployee(emp_id);
			ctx.json(emp);
		});
		
		//update employee Info
		server.put("/employees/update", ctx -> {
			System.out.println("Update Employee...");
			// get formdata from client side
			int emp_id = Integer.parseInt(ctx.formParam("empID"));
			String fname = ctx.formParam("fname");
			String lname = ctx.formParam("lname"); 
			String email = ctx.formParam("email");
			System.out.println(emp_id + " " + lname);
			EmployeePojo emp = mainServ.updateEmployee(emp_id, fname, lname, email);
					
			if(emp != null) {
				ctx.status(200).json(emp);
			} else {
				ctx.result("Employee Information Update Failure.").status(417);
			}	
		});
		
		//get all employees
		server.get("employees", ctx -> {
			List<EmployeePojo> employees = mainServ.getAllEmployees();
			ctx.json(employees);
			
			if(employees != null) {
				ctx.status(200).json(employees);
			} else {
				ctx.result("No reults found.").status(404);
			}	
		});
	}

}
