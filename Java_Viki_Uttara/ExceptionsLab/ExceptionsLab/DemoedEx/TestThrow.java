public class TestThrow
{
	public static void main(String[] args)
	{	
		System.out.println("main()->starting...");
		try
		{
			System.out.println("going to raise ex...");		
			Exception e = new Exception();
			if(1==1)
				throw e;
			System.out.println("after throwing...");
			
		}
		catch(RuntimeException t)
		{ 	
			t.printStackTrace();
		}
		System.out.println("main()->ending...");
		
	
	}

}
