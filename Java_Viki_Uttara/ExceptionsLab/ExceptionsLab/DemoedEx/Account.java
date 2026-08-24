public class Account
{
	BigDecimal balance;
	
	public void credit(BigDecimal amt)
	{
		//peform input validations
		//perform business validations
		//apply business logic
		balance.add(amt);
	}
}
