package 静态内部类;
/**
 * 静态内部类与成员内部类的区别在于
 * 在创建静态内部类的时候不会有一个指向外部类的引用
 * 是独立的，这也就解释了为什么静态内部类只能引用类的静态变量
 * 因为独立于外部的类使用别的限定的内部类的成员变量就不是独立的了
 * 
 * @author jhc
 *
 */
public class Test {
	public static void main(String[] args){
		Outer.Inner inner = new Outer.Inner();
		
	}

	
}
