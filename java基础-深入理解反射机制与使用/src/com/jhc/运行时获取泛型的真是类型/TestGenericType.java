package com.jhc.运行时获取泛型的真是类型;
/**
 * 在运行的时候获得真正的类型
 * 由于类型擦除导致我们的对内容进行反编译也看不到我们的内容，
 * 但是类型擦除并不是把我们的泛型全部擦除 通过例子可以知道
 * 对于声明类型的泛型并没有擦除依然保存在类的字节码中
 * 只不过我们调用什么都需要使用上Generic
 * 比如继承的父类 getGenericSuperClass()
 * 方法中的参数 getGenericParamterTypes();
 * 
 */
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Map;

abstract class GenericType<T>{
	
}
public class TestGenericType extends GenericType<String> {
	private Map<String,Integer> map;
	Object
	public Map<String,Integer> getMap(){
		return map;
	}
	public void setMap(Map<String,Integer> map1){
		this.map = map1;
	}
	public static void main(String[] args) throws NoSuchFieldException, SecurityException,NoSuchMethodException{
		Class<TestGenericType> tClass =  TestGenericType.class;
		Type type = tClass.getGenericSuperclass();//得到继承类的类型
		if(type instanceof ParameterizedType){//判断父类是否是参数泛型的内容
			Type[] types =((ParameterizedType)type).getActualTypeArguments();//得到实际的类型
			for(Type t:types){
				System.out.println("types:+" +t);
			}
		}
		
		Field f1 = tClass.getDeclaredField("map");
		Type typeField = f1.getGenericType();
		if(typeField instanceof ParameterizedType){
			Type[] typesField = ((ParameterizedType)typeField).getActualTypeArguments();
			for(Type t1:typesField){
				System.out.println(t1);
			}
		}

		
	}
}
