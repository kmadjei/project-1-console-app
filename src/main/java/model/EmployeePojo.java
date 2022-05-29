package model;

public class EmployeePojo {
	
	private int emp_id;
	private String password;
	private int job_code;
	private String fname;
	private String lname;
	private String email;
	
	
	public EmployeePojo() {
		super();
	}


	public EmployeePojo(int emp_id, String password, int job_code, String fname, String lname, String email) {
		super();
		this.emp_id = emp_id;
		this.password = password;
		this.job_code = job_code;
		this.fname = fname;
		this.lname = lname;
		this.email = email;
	}


	public int getUser_id() {
		return emp_id;
	}


	public void setUser_id(int user_id) {
		this.emp_id = user_id;
	}


	public String getPassword() {
		return password;
	}


	public void setPassword(String password) {
		this.password = password;
	}


	public int getJob_code() {
		return job_code;
	}


	public void setJob_code(int job_code) {
		this.job_code = job_code;
	}


	public String getFname() {
		return fname;
	}


	public void setFname(String fname) {
		this.fname = fname;
	}


	public String getLname() {
		return lname;
	}


	public void setLname(String lname) {
		this.lname = lname;
	}


	public String getEmail() {
		return email;
	}


	public void setEmail(String email) {
		this.email = email;
	}


	@Override
	public String toString() {
		return "EmployeePojo [emp_id=" + emp_id + ", password=" + password + ", job_code=" + job_code + ", fname="
				+ fname + ", lname=" + lname + ", email=" + email + "]";
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result + ((fname == null) ? 0 : fname.hashCode());
		result = prime * result + job_code;
		result = prime * result + ((lname == null) ? 0 : lname.hashCode());
		result = prime * result + ((password == null) ? 0 : password.hashCode());
		result = prime * result + emp_id;
		return result;
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		EmployeePojo other = (EmployeePojo) obj;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		if (fname == null) {
			if (other.fname != null)
				return false;
		} else if (!fname.equals(other.fname))
			return false;
		if (job_code != other.job_code)
			return false;
		if (lname == null) {
			if (other.lname != null)
				return false;
		} else if (!lname.equals(other.lname))
			return false;
		if (password == null) {
			if (other.password != null)
				return false;
		} else if (!password.equals(other.password))
			return false;
		if (emp_id != other.emp_id)
			return false;
		return true;
	}
	
	
	
	
	
	

}
