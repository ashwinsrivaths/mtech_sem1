public class A
{
	static int q = 22;
	static
	{
		System.out.println("in static init of A q = "+q);	
		q = 33;
	}
	
	int p = 10; //inst var with inst field init
	{ // instance init 1
		System.out.println("in inst init 1 p = "+p);
		p = 20;
	}
	{ // instance init 2
		System.out.println("in inst init 2 p = "+p);
		p = 30;
	}
	public A()
	{
		System.out.println("in no-arg constr of A p = "+p);
		p = 40;
	}

}