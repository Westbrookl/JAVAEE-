package 测试;
/**
 * 关于内部类的初始 化
 * 对于成员内部类的初始化
 * 首先初始化一个外部类
 * 然后再去初始化内部类
 * @author jhc
 *
 */
public class Test1 {
	public static void main(String[] args){
		Bean2  b2 = new Bean2();
		b2.j++;
		/**
		 * 
		 */
		Test1.Bean1 b1 = new Test1().new Bean1();
		b1.i++;
		
		Bean.Bean3 b3 = new Bean().new Bean3();
		b3.k++;
		
	}
	class Bean1{
		public int i=0;
	}
	static class Bean2{
		public int j = 0;
	}
}
