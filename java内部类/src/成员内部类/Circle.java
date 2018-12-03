package 成员内部类;
/**
 * 成员内部类可以直接去访问所有的成员变量并且不受任何限制
 * 但是成员内部类的创建需要一个指向外部类的引用
 * Outer.Inner inner = Outer.new Inner();
 * 在内部类初始化的时候会提供一个构造参数指向Outer
 * @author jhc
 *
 */
public class Circle {
	private double radius = 0;
	public Circle(double radius){
		this.radius = radius;
		getDrawInstance().display();
	}
	private Draw getDrawInstance(){
		return new Draw();
	}
	class Draw{
		public Draw(){
			
		}
		public void display(){
			System.out.println(radius);
		}
	}
	public static void main(String[] args){
		new Circle(1.0);
	}
}
