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
		
		//update reimbursement request
		server.put("/reimbursements", ctx -> {
			int rb_id = Integer.parseInt(ctx.formParam("rb_id"));
			String rb_status = ctx.formParam("rb_status");
			mainServ.updateRequest(rb_id, rb_status);
		});
		
		//create reimbursement request
		server.post("/reimbursements", ctx -> {
			System.out.println("Generate --> /reimbursements/emp/{emp_id}...");
			// get form parameters
			int emp_id = Integer.parseInt(ctx.formParam("emp_id"));
			double amount = Double.parseDouble(ctx.formParam("amount"));
			
			// submit parameters to DB			
			if(mainServ.submitRequest(emp_id, amount)) {
				ctx.result("Reimbursement Submitted Successfully!").status(201);
			} else {
				ctx.result("Reimbursement request failed to submit!").status(417);
			}		
		});
		
		// handle employee  login
		server.post("/login", ctx -> {
			String email = ctx.formParam("email");
			String password = ctx.formParam("password");
			System.out.println("nooo login..");
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
		//update employee, should convert to user form and default to old values
//		server.put("/employees/{emp_id}", ctx -> {
//			int emp_id = Integer.parseInt(ctx.pathParam("emp_id"));
//			mainServ
//		});
//		
		//get all employees
		server.get("employees", ctx -> {
			List<EmployeePojo> employees = mainServ.getAllEmployees();
			ctx.json(employees);
		});
	}

}
