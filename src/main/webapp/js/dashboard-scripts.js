class Authentication {
    /*
    constructor() {
        console.log("Constructor called")
        //this.auth = sessionStorage.getItem("authenticated");
        //this.auth = sessionStorage.getItem("authenticated");
        this.
    } */

    // checks for authenticated user in sessionStorage
    static checkAuth(auth) {
        // let auth = sessionStorage.getItem("authenticated");
        console.log("checkAuth...");
        let jsonAuth = JSON.parse(auth); // convert to Javascript Obj
        console.log(jsonAuth.emp_id);
        if (jsonAuth != null){
            console.log("auth dash-->")
            // after validating user 
            // get user info from Session
            return jsonAuth;
        } else {
            // redirect to login  / index
            window.location.replace("/");
        }
        return null;
    }
}




class DashboardEvents {
    
    constructor(employee) {
        this.employee = employee;
        this.buildEmployeeInfoHTML(employee);
    }



    async getAllReimbursementRequest (employee) {
        
        try {
            console.log("getAllReimbursementRequest.....");
            let response = await fetch(`http://127.0.0.1:7474/reimbursements/emp/${employee.emp_id}`);

            if(!response.ok) {
                throw new Error("Sorry! Currently experiencing network issues");
            }

            // handle data on client side
            let reimbursements = await response.json();
            this.buildRequestHTML(reimbursements);

        } catch (err) {
            console.error(err.message);
        }
         
    }

    // construct Reimbursement list
    buildRequestHTML(reimbursements ) {
        console.log("Reimb HTML.....");
        reimbursements.map((item) => {
            let date = new Date(item.rb_timestamp);
            let dateFormat = (date.getDate() +
                        "/"+(date.getMonth()+1)+
                        "/"+date.getFullYear()+
                        " "+date.getHours()+
                        ":"+date.getMinutes()+
                        ":"+date.getSeconds());

            let status;

            if (item.rb_status === "pending") {
                status = "text-warning";
            } else if (item.rb_status === "resolved") {
                status = "text-success";
            } else if (item.rb_status === "rejected") {
                status = "text-danger";
            }
            
            let html = `
            <div class="mb-4">
                <h4 class="small font-weight-bold 
                d-flex align-items-center justify-content-between">
                    <span class="id">ID: ${item.rb_id}</span>
                    <span class="status ${status}">Status: ${item.rb_status}</span> 
                    <span class="amount">20%</span>
                </h4>
                <div class="d-flex align-items-center justify-content-between">
                    <h4 class="small font-weight-bold">Date: ${dateFormat}</h4>
                    <button class="btn btn-primary btn-sm">Update</button>
                </div>
            </div>
            <div class="dropdown-divider"></div>
            `;

            //update html body for Reimbursements
            document.getElementById("reimbursements").innerHTML += html;

        });
    }

    // construct employee Info Card
    buildEmployeeInfoHTML(emp) {
        console.log("employee HTML");
        let htmlInfo = `
        <div class="d-flex align-items-center justify-content-between">
            <b>Employee ID</b>
            <b>${emp.emp_id}</b>
        </div>
        <div class="d-flex align-items-center justify-content-between">
            <b>Job Code</b>
            <b>${emp.job_code}</b>
        </div>
        <div class="d-flex align-items-center justify-content-between">
            <b>First Name</b>
            <b>${emp.fname}</b>
        </div>
        <div class="d-flex align-items-center justify-content-between">
            <b>Last Name</b>
            <b>${emp.lname}</b>
        </div>
        <div class="d-flex align-items-center justify-content-between">
            <b>Email</b>
            <b>${emp.email}</b>
        </div>
        <button class="btn btn-primary float-right mt-4">Update</button>
        `;

        document.getElementById("employeeInfo").innerHTML = htmlInfo;

        // Nav display name
        document.getElementById("navName").innerHTML = `${emp.fname} ${emp.lname}`;


        // display search bar to search for an employee
        // only for manager
        if (emp.job_code == 200) {
            document.querySelector('.navbar-search div.input-group').hidden = false;
        }


    }
}



// Check for session credentials upon login
window.onload = () => {
    console.log("begin");
    // grab user session object
    const auth = sessionStorage.getItem("authenticated");
    console.log("auth ", auth);
    // validate session object
    const employee = Authentication.checkAuth(auth);
    console.log("employee JSON -- ", employee);

    // Initialize dashboard actions
    const dashboard = new DashboardEvents(employee);
    dashboard.getAllReimbursementRequest(employee);
}