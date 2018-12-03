package 匿名内部类;
/**
 * 注意匿名内部为为什么非要传入的参数是final类型？
 * 因为当传入到匿名（局部）内部类的参数的时候，首先是将该值压栈然后复制一个和它一模一样的值，这样能够保证
 * 当test结束的时候，而thread的方法并没有结束保证了局部变量的一致性。
 * 在JDK8中并不需要加上final这个关键字
 * 但是依然是不能更改局部变量或者传入参数的值，否则的话就会数据不一致导致异常。
 * @author jhc
 *
 */
public class Test {
	public static void main(String[] args){
		final int i = 3;
		test( i);
	}
	public static void test(final int a ){
		final int b = 5;
		new Thread(){
			public void run(){
				System.out.println(a);
				System.out.println(b);
			}
		}.start();
	}
}
