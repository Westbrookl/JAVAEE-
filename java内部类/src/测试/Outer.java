package ����;

public class Outer {
	private int i = 0;
	class Inner{
		private int i = 1;
		public Inner(){
			int i = 5;
			System.out.println("�ֲ����� "+i);
			System.out.println("�ڲ������ "+this.i);
			System.out.println("�ⲿ����� "+Outer.this.i);
		}
	}
}
