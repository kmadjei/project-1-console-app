class Authentication {

    // checks for authenticated user in sessionStorage
    static checkAuth(auth) {
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
            window.location.replace("index.html");
        }
        return null;
    }


    // Initialize log out process
    static logOut() {
        sessionStorage.clear();
        window.location.replace("index.html");
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
            sessionStorage.setItem("reimbursements", JSON.stringify(reimbursements));
            this.buildRequestHTML(reimbursements, employee.job_code);

        } catch (err) {
            console.error(err.message);
        }
         
    }




    // Filter through for Reimbursement results
    getRejectedReimbursements(job_code) {
        console.log("getRejectedReimbursements().....");

        let reimbursements = JSON.parse(sessionStorage.getItem("reimbursements"))
                                .filter((item) => {
                                    if (item.rb_status == "rejected") {
                                        return item;
                                    }
                                });
        $('#reimbursements').html("");
        this.buildRequestHTML(reimbursements, job_code);
    }

    getResolvedReimbursements(job_code) {
        console.log("getResolvedReimbursements().....");

        let reimbursements = JSON.parse(sessionStorage.getItem("reimbursements"))
                                .filter((item) => {
                                    if (item.rb_status == "resolved") {
                                        return item;
                                    }
                                });
            $('#reimbursements').html("");          
            this.buildRequestHTML(reimbursements,  job_code);
    }


    getPendingReimbursements(job_code) {
        console.log("getPendingReimbursements() .....");

        let reimbursements = JSON.parse(sessionStorage.getItem("reimbursements"))
                                .filter((item) => {
                                    if (item.rb_status == "pending") {
                                        return item;
                                    }
                                });
        $('#reimbursements').html("");
        this.buildRequestHTML(reimbursements, job_code);
    }

    getAllReq(job_code) {
        console.log("getAllReq() .....");

        let reimbursements = JSON.parse(sessionStorage.getItem("reimbursements"));
        console.log(reimbursements)
        $('#reimbursements').html("");
        this.buildRequestHTML(reimbursements, job_code);
    }


    // Generate Reimbursement request
    async requestReimbursement(emp_id) {
            
        // get user's reimbursement amount 
        let amount = prompt("Please enter the amount to be Reimbursed", 22.50)

        while(parseFloat(amount) === NaN){
            amount = prompt("Please enter a correct number for your amount ")
        }
        const formData = new FormData();
        formData.append("emp_id", emp_id);
        formData.append("amount", amount);

        try {
            let response = await fetch('http://127.0.0.1:7474/reimbursements', {
            method: 'POST',
            body: formData,
            });
            let result = await response.text();
            console.log(result);

            if (!response.ok){

                $('#flashMsg')
                    .html(                    
                        `<div class="alert alert-warning" role="alert">
                            ${result}
                        </div>`
                    ).fadeIn(200).fadeOut(1000);

            } else {
                $('#flashMsg')
                .html(                    
                    `<div class="alert alert-success" role="alert">
                        ${result}
                    </div>`
                ).fadeIn(200).fadeOut(4000);

            }
            
        } catch (error) {
            console.log(error.message);

        }


    }

    // construct Reimbursement list
    buildRequestHTML(reimbursements, job_code) {
        console.log("buildRequestHTM().....");

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
                    <span class="rb_id${item.rb_id}">ID: ${item.rb_id}</span>
                    <span class="status ${status}">Status: ${item.rb_status}</span> 
                    <span class="amount${item.rb_id}"> ${item.rb_amount}</span>
                </h4>
                `;


            // displays for regular employees
            if (job_code == 100) {
                html +=`
                    <div class="d-flex align-items-center justify-content-between">
                        <h4 class="small font-weight-bold">Date: ${dateFormat}</h4>
                        <button class="btn btn-primary btn-sm" id="rb_${item.rb_id}" 
                        onclick="DashboardEvents.updateRequest(event)"
                        >Update</button>
                    </div>
                </div>
                <div class="dropdown-divider"></div>
                `;
            } else if (job_code == 200) {
                //display for managers
                html +=`
                    <div class="d-flex align-items-center justify-content-between">
                        <h4 class="small font-weight-bold">Date: ${dateFormat}</h4>
                        <button class="btn btn-primary btn-sm" id="rb_${item.rb_id}" 
                        onclick="DashboardEvents.updateRequest(event)"
                        >Update</button>
                    </div>
                </div>
                <div class="dropdown-divider"></div>
                `;
            }
                

            //update html body for Reimbursements
            document.getElementById("reimbursements").innerHTML += html;

        });

    } 


    static async updateRequest(event) {
        console.log("updateRequest()...");

        // get request ID
        const reqID = event.currentTarget.id.split("_")[1];
        // get req value
        const amount = $(`span.amount${reqID}`).val();

          // get user's reimbursement amount 
          let newAmount = prompt("Please enter the amount to be Reimbursed", amount)

          while(parseFloat(amount) === NaN){
              newAmount = prompt("Please enter a correct number for your amount ")
          }
          const formData = new FormData();
          formData.append("rb_id", reqID);
          formData.append("amount", newAmount);
  
          try {
              let response = await fetch('http://127.0.0.1:7474/reimbursements/details/update', {
              method: 'PUT',
              body: formData,
              });
              let result = await response.text();
              console.log(result);
  
              if (!response.ok){
  
                  $('#flashMsg')
                      .html(                    
                          `<div class="alert alert-warning" role="alert">
                              ${result}
                          </div>`
                      ).fadeIn(200).fadeOut(4000);
  
              } else {

                // get old stored value
                let oldReq = JSON.parse(sessionStorage.getItem("reimbursements"));

                let newReq = oldReq.map((item) => {
                    if (item.rb_id == reqID) {
                        item.rb_amount = newAmount;
                        return item;
                    } else {
                        return item;
                    }
                });
                // store new values
                sessionStorage.setItem("reimbursements", JSON.stringify(newReq));

                  $('#flashMsg')
                  .html(                    
                      `<div class="alert alert-success" role="alert">
                          ${result}
                      </div>`
                  ).fadeIn().fadeOut(4000);


                  // update html page
                  $(`span.amount${reqID}`).text(`${newAmount}`);
              }
              
          } catch (error) {
              console.log(error.message);
  
          }
  


    }

    // construct employee Info Card
    buildEmployeeInfoHTML(emp) {   
        console.log(" buildEmployeeInfoHTML(emp).....");
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
        <button class="btn btn-primary float-right mt-4"
        data-toggle="modal" data-target="#employeeModal"
        ">Update</button>
        `;

        document.getElementById("employeeInfo").innerHTML = htmlInfo;

        // Nav display name
        document.getElementById("navName").innerHTML = `${emp.fname} ${emp.lname}`;


        // display search bar to search for an employee
        // only for manager
        if (emp.job_code == 200) {
            document.querySelector('.navbar-search div.input-group').hidden = false;
        }

        $('#EmpModal').html(
            `
            <div class="modal fade" id="employeeModal" tabindex="-1" role="dialog" aria-labelledby="employeeModalLabel" aria-hidden="true">
            <div class="modal-dialog" role="document">
              <div class="modal-content">
                <div class="modal-header">
                  <h5 class="modal-title" id="requestModalLabel">Update Employee Info</h5>
                  <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                  </button>
                </div>
                <div class="modal-body">
                  <form id="employeeInfoForm">
                    <div class="form-group">
                      <label for="employeeID" class="col-form-label">Employee ID</label>
                      <input type="number" class="form-control" id="employeeID" name="empID" value="${emp.emp_id}" disabled>
                    </div>
                    <div class="form-group">
                      <label for="jobcode" class="col-form-label">Job Code</label>
                      <input type="number" class="form-control" id="jobecode" name="jobcode" value="${emp.job_code}" disabled>
                    </div>
                    <div class="form-group">
                      <label for="fname" class="col-form-label">First Name</label>
                      <input type="text" class="form-control" id="fname" name="fname"value="${emp.fname}">
                    </div>
                    <div class="form-group">
                      <label for="lname" class="col-form-label">Last Name</label>
                      <input type="text" class="form-control" id="lname" name="lname" value="${emp.lname}">
                    </div>
                    <div class="form-group">
                      <label for="email" class="col-form-label">Email</label>
                      <input type="email" class="form-control" id="email" name="email" value="${emp.email}">
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
                        <button type="submit" class="btn btn-primary">Submit</button>
                    </div>
                  </form>
   
                </div>
               
              </div>
            </div>
          </div>
            
            
            `
        );

    }


    async updateEmployeeInfo(event) {
        console.log("updateEmployeeInfo....")
      
        //stop form submission to server
        event.preventDefault();
        
        const formData = new FormData();
        formData.append("empID", $("#employeeID").val());
        formData.append("fname", $("#fname").val());
        formData.append("lname", $("#lname").val());
        formData.append("email", $("#email").val());


        try {
            let response = await fetch('http://127.0.0.1:7474/employees/update', {
            method: 'PUT',
            body: formData,
            });

            if (!response.ok) {
                let result = await response.text();
                $('#employeeModal').hide();
                $('#flashMsg')
                .html(                    
                    `<div class="alert alert-warning" role="alert">
                        ${result}
                    </div>`
                ).fadeIn(200).fadeOut(1000);
                
                throw new Error(result);
            } else {
                let emp = await response.json();
                
                // update session object
                sessionStorage.setItem("authenticated", JSON.stringify(emp));

                // provide user feedback
                $('#employeeModal').hide();
                this.buildEmployeeInfoHTML(emp);
                $('#flashMsg')
                .html(                    
                    `<div class="alert alert-success" role="alert">
                        Employee Info Updated Successfully.
                    </div>`
                ).fadeIn(200).fadeOut(4000);
            }
        } catch (error) {
            console.error(error.message);
        }


    }



}


// Check for session credentials when page is loaded
window.onload = () => {


    
    console.log("begin");
    // grab user session object
    const auth = sessionStorage.getItem("authenticated");
    console.log("auth ", auth);
    // validate session object
    const employee = Authentication.checkAuth(auth);


    // display search bar to search for an employee
    // only for manager
    if (employee.job_code == 200) {
        document.querySelector('.navbar-search div.input-group').hidden = false;
    }

    console.log("employee JSON -- ", employee);

    // Initialize dashboard actions
    const dashboard = new DashboardEvents(employee);

    $('#getAllReimbursements').click(() => dashboard.getAllReimbursementRequest(employee));

    $('#requestBtn').click(() => dashboard.requestReimbursement(employee.emp_id));

    // initialize submit event for editing employee info
    $('#employeeInfoForm').submit(dashboard.updateEmployeeInfo);

    $('#reqAll').click(() => dashboard.getAllReq(employee.job_code));
    $('#reqRejected').click(() => dashboard.getRejectedReimbursements(employee.job_code));
    $('#reqResolved').click(() => dashboard.getResolvedReimbursements(employee.job_code));
    $('#reqPending').click(() => dashboard.getPendingReimbursements(employee.job_code));

    // ends user's session
    $('#logOut').click(() => Authentication.logOut());

}

