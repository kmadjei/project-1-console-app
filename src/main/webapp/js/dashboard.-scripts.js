class Authentication {
    
    constructor() {
        this.auth = sessionStorage.getItem("authenticated");
    }

    // checks for authenticated user in sessionStorage
    checkAuth() {
        if (this.auth){
            // after validating user 
            // get user info from Session
            return JSON.parse(JSON.stringify(this.auth));
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

    }



    async getAllReimbursementRequest () {
        
        try {
            let response = await fetch(`http://127.0.0.1:7474/reimbursements/emp/${this.employee.emp_id}`);

            if(!response.ok) {
                throw new Error("Sorry! Currently experiencing network issues");
            }

            // handle data on client side
            let reimbursements = await response.json();


        } catch (err) {
            console.error(err.message);
        }
         
    }

    // construct Reimbursement list
    buildRequestHTML(reimbursements ) {
        
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

        });
    }

    // construct employee Info Card
    buildEmployeeInfoHTML(emp) {
        let htmlInfo = `
        <div class="d-flex align-items-center justify-content-between">
            <b>Employee ID </b>
            <span>---</span>
            <span>${emp.emp_id}</span>
        </div>
        <div class="d-flex align-items-center justify-content-between">
            <b>Job Code</b>
            <span>---</span>
            <span>${emp.job_code}</span>
        </div>
        <div class="d-flex align-items-center justify-content-between">
            <b>First Name</b>
            <span>---</span>
            <span>${emp.fname}</span>
        </div>
        <div class="d-flex align-items-center justify-content-between">
            <b>Last Name</b>
            <span>---</span>
            <span>${emp.lname}</span>
        </div>
        <div class="d-flex align-items-center justify-content-between">
            <b>Email</b>
            <span>---</span>
            <span>${emp.email}</span>
        </div>
        <button class="btn btn-primary btn-sm float-right">Update</button>
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

const auth = new Authentication();

// Check for session credentials upon login
window.onbeforeunload = () => {

    auth.checkAuth();
}