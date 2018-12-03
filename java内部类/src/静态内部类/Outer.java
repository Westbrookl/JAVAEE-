package ¾²Ì¬ÄÚ²¿Àà;

public class Outer{
	int a = 10;
	static int b=5;
	public Outer(){
		
	}
	static class Inner{
		public Inner(){
			//System.out.println(a);
			System.out.println(b);
		}
	}
}
