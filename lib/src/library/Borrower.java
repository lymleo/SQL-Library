package library;

public class Borrower {
	private String borrower_ID;
	private String SSN;
	private String Name;
	
	private String Email;
	private String Address;
	
	private String Phone;
	
	
	public String getborrower_ID()
	{
		return borrower_ID;
	}
	public void setID(String borrower_ID)
	{
		this.borrower_ID = borrower_ID;
	}
	
	public String getSSN()
	{
		return SSN;
	}
	public void setSSN(String SSN)
	{
		this.SSN = SSN;
	}
	
	public String getName()
	{
		return Name;
	}
	public void setName(String Name)
	{
		this.Name = Name;
	}

	
	public String getEmail()
	{
		return Email;
	}
	public void setEmail(String Email)
	{
		this.Email = Email;
	}
	
	public String getAddress()
	{
		return Address;
	}
	public void setAddress(String Address)
	{
		this.Address = Address;
	}
	

	
	public String getPhone()
	{
		return Phone;
	}
	public void setPhone(String Phone)
	{
		this.Phone = Phone;
	}
	

}
