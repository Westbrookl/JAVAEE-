package com.jhc.运行时反射获得类的结构信息;
/**
 * 类的结构可以分为五种
 * 一个是Field 即类中的成员变量
 * 一个是Constructor 是类的构造函数
 * 一个是Method 是类中的方法
 * 一个是得到所有实现的接口
 * 一个是类前面的注释。
 * getDeclaredField()是为了得到类本身自由方法
 */
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class Test {
	public static void main(String[] args)throws ClassNotFoundException,NoSuchMethodException,
	NoSuchFieldException{
		Class<User> user = User.class;
		Constructor userCons = user.getConstructor();
		System.out.println("Constructor: "+ userCons);
		Field name1 = user.getDeclaredField("name");
		System.out.println("Field name "+ name1);
		Method m1 = user.getMethod("getName", null);
		System.out.println("getName(): "+ m1);
		Class<?>[] I1 = user.getInterfaces();
		System.out.println("Interfaces: "+I1);
		MyAnnation my1 = user.getAnnotation(MyAnnation.class);
		System.out.println("MyAnnation: " + my1);
	}
	@MyAnnation
	public static class User implements IUser{
		private String name;
		public User(){
			
		}
		public String getName(){
			return name;
		}
		public void setName(String name){
			this.name = name;
		}
		
	}
	public static interface IUser{
		
	}
	@Retention(RetentionPolicy.RUNTIME)
	@Target(ElementType.TYPE)
	public static @interface MyAnnation{
		
	}
}
