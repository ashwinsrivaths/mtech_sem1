public class Manushya
{
	String name;
	int age = 1;
	long height;
	
	{
		System.out.println("in inst init of Manushaya");
		long t1 = System.currentTimeMillis();	
		System.out.println("t1 = "+t1);
		height = t1 % 103;
	}
	
	public Manushya(String n)
	{
		System.out.println("in string param constr of Manushaya");
		name = n;
	}
	public Manushya(String n,int a)
	{
		System.out.println("in string,int param constr of Manushaya");
		
		name = n;
		age = a;	
	}
	public static void main(String[] ar)
	{
		Manushya m1 = new Manushya("Manu",2);
		Manushya m2 = new Manushya("Mani");
		
		System.out.println("m1.name = "+m1.name+" m1.age = "+m1.age+" m1.height = "+m1.height);
		System.out.println("m2.name = "+m2.name+" m2.age = "+m2.age+" m2.height = "+m2.height);
	}
}



