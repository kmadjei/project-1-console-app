package model;

public class ReimbursementPojo {
	
	private int rb_id;
	private String rb_status;
	private double rb_amount;
	private String rb_timestamp;
	private int emp_id;
	
	
	public ReimbursementPojo() {
		super();		
	}


	public ReimbursementPojo(int rb_id, String rb_status, double rb_amount, String rb_timestamp, int emp_id) {
		super();
		this.rb_id = rb_id;
		this.rb_status = rb_status;
		this.rb_amount = rb_amount;
		this.rb_timestamp = rb_timestamp;
		this.emp_id = emp_id;
	}


	public int getRb_num() {
		return rb_id;
	}


	public void setRb_num(int rb_id) {
		this.rb_id = rb_id;
	}


	public String getRb_status() {
		return rb_status;
	}


	public void setRb_status(String rb_status) {
		this.rb_status = rb_status;
	}


	public double getRb_amount() {
		return rb_amount;
	}


	public void setRb_amount(double rb_amount) {
		this.rb_amount = rb_amount;
	}


	public String getRb_timestamp() {
		return rb_timestamp;
	}


	public void setRb_timestamp(String rb_timestamp) {
		this.rb_timestamp = rb_timestamp;
	}


	public int getEmp_id() {
		return emp_id;
	}


	public void setEmp_id(int emp_id) {
		this.emp_id = emp_id;
	}


	@Override
	public String toString() {
		return "ReimbursementPojo #" + rb_id + " [ rb_status = " + rb_status + ", rb_amount = " + rb_amount
				+ ", rb_timestamp = " + rb_timestamp + ", emp_id = " + emp_id + " ]";
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits(rb_amount);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + rb_id;
		result = prime * result + ((rb_status == null) ? 0 : rb_status.hashCode());
		result = prime * result + ((rb_timestamp == null) ? 0 : rb_timestamp.hashCode());
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
		ReimbursementPojo other = (ReimbursementPojo) obj;
		if (Double.doubleToLongBits(rb_amount) != Double.doubleToLongBits(other.rb_amount))
			return false;
		if (rb_id != other.rb_id)
			return false;
		if (rb_status == null) {
			if (other.rb_status != null)
				return false;
		} else if (!rb_status.equals(other.rb_status))
			return false;
		if (rb_timestamp == null) {
			if (other.rb_timestamp != null)
				return false;
		} else if (!rb_timestamp.equals(other.rb_timestamp))
			return false;
		if (emp_id != other.emp_id)
			return false;
		return true;
	}
	
	

}
