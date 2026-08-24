package com.uttara.ex;

//custom ex, user defined ex
public class NotEnoughMoneyInCardException extends Exception
{
	public NotEnoughMoneyInCardException()
	{
		
	}
	public NotEnoughMoneyInCardException(String msg)
	{
		super(msg);	
	}
}



