package 测试;

public class Outer {
	private int i = 0;
	class Inner{
		private int i = 1;
		public Inner(){
			int i = 5;
			System.out.println("局部变量 "+i);
			System.out.println("内部类变量 "+this.i);
			System.out.println("外部类变量 "+Outer.this.i);
		}
	}
}
