public class TestStatic
{
	static int i = 20;
	
	public void test()
	{
	//	TestStatic ts = new TestStatic();
		System.out.println("TestStatic.i = "+TestStatic.i);	
	}
	public static void main(String[] args)
	{
		new TestStatic().test();
	}

}




