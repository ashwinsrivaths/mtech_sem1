public class SuperDuck
{
	// mark state with private access so
	// that class users cannot access and set whatever state
	// they want
	
	private int size;  
	private String name;
	
	//expose 1 setter and 1 getter method per instance variable
	public void setSize(int n)
	{
		// validate the passed input first and then only 
		// set the state
		if(n > 0 && n <=20)
			size = n;
		else
			System.out.println("Size of Duck must be > 0 and <= 20");
	}
	
	public int getSize()
	{
		return size;	
	}
	public void setName(String str)
	{
		if(str!=null) // what are we checking here?
			name = str;
		else
			System.out.println("should not pass null as name value");
	}
	public String getName() // why do we need getter when user only passes data?
	{
		return name;	
	}
	public void dance()
	{
		if(size!=0) // can you remember why you are checking this?
		{
			if (size < 10)
				System.out.println("Duck with name "+name+" doing cha cha...");
			else
				System.out.println("Duck with name "+name+" doing bharatnatya");
			
		}
		else
			System.out.println("First set the size on Duck correctly and then ask it to dance ri!");
	}

}

class TestDuck
{
	public static void main(String[] args)
	{
		SuperDuck d1 = new SuperDuck(); // create SuperDuck ref, point to SuperDuck obj
		//d1.size = -5;
		//d1.name = "Janu";
		d1.setSize(-5);
		d1.setName("Janu");
		d1.dance(); // check if this works
		
	}
	
}
