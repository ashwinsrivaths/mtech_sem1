public class Area
{
	public int area(int x)
	{
		double ar=3.14*x*x;
		return ar;
	}
	public int area(int a, int b, int c)
	{
		double s=(a+b+c)/3;
		double ai=(s*(s-a)*(s-b)*(s-c))*(1/2);
		return ai;
	}
}
public class MainProg
{
	public static void main(String[] args)
	{
		Area o=new Area();
		double q=O.area(5);
		double w=o.area(1,2,3);
		System.out.println("Circle="+q);
		System.out.println("Triangle="+w);
	}
}