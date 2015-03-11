package rj.hive.database.models;

public class Customer 
{
	public String fname;
	public String lname;
	public String email;
	public String address1;
	public String address2;
	public String city;
	public String mobile;
	public String notes;

	
	
	public Customer(String fname, String lname, String email, String address1,
			String address2, String city, String mobile, String notes) {
		super();
		this.fname = fname;
		this.lname = lname;
		this.email = email;
		this.address1 = address1;
		this.address2 = address2;
		this.city = city;
		this.mobile = mobile;
		this.notes = notes;
	}

}
