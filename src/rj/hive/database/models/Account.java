package rj.hive.database.models;

public class Account {
	public String address_id;
	public String address;
	public String customer_id;
	public String email;
	public String firstname;
	public String lastname;
	public String mobile;
	public String password;
	public String telephone;
	
	public Account(String address_id,
			String address,
			String customer_id,
			String email,
			String firstname,
			String lastname,
			String mobile,
			String password,
			String telephone) 
	{
		super();
		this.address_id = address_id;
		this.address = address;
		this.customer_id = customer_id;
		this.email = email;
		this.firstname = firstname;
		this.lastname = lastname;
		this.mobile = mobile;
		this.password = password;
		this.telephone = telephone;
	}
}