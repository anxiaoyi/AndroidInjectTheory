package com.zk.reflect;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

public class ReflectDemo {

/**
 * public private or protected, other's return unknown
 * @param modifiers
 * @return
 */
private static String getModifiers(int modifiers) {
	switch (modifiers) {
	case Modifier.PRIVATE:
		return "private";

	case Modifier.PUBLIC:
		return "public";

	case Modifier.PROTECTED:
		return "protected";
	}
	return "unknown";
}
	
	public static void main(String args[]) throws Exception{
		Foo f = new Foo();
		
		System.out.println("==========DeclaredFields====================");
		Field[] fields = f.getClass().getDeclaredFields();
		for(Field field:fields){
			System.out.println(getModifiers(field.getModifiers()) + " " + field.getType() + " " + field.getName());
		}
		
		System.out.println("==========DeclaredMethods====================");
		Method[] methods = f.getClass().getDeclaredMethods();
		for(Method method:methods){
			System.out.println(getModifiers(method.getModifiers()) + " " + "[" + method.getReturnType() + "] " + method.getName() + "()");
		}
		
		System.out.println("==========publicFields====================");
		fields = f.getClass().getFields();
		for(Field field:fields){
			System.out.println(getModifiers(field.getModifiers()) + " " + field.getType() + " " + field.getName());
		}
		
		System.out.println("==========publicMethods====================");
		methods = f.getClass().getMethods();
		for(Method method:methods){
			System.out.println(getModifiers(method.getModifiers()) + " " + "[" + method.getReturnType() + "] " + method.getName() + "()");
		}
		
		System.out.println("==========SetDeclaredFields====================");
		fields = f.getClass().getDeclaredFields();
		int value = 230;
		for(Field field:fields){
			//java.lang.IllegalAccessException
			field.setAccessible(true);
			field.set(f, value++);
			System.out.println(field.getName() + ": " + field.get(f));
		}
		
		System.out.println("==========SetDeclaredMethods====================");
		methods = f.getClass().getDeclaredMethods();
		for(Method method:methods){
			method.setAccessible(true);
			method.invoke(f);
		}
		
		System.out.println("==========AnnotationFields====================");
		fields = f.getClass().getDeclaredFields();
		for(Field field:fields){
			field.setAccessible(true);
			FooAnnotation annotation = field.getAnnotation(FooAnnotation.class);
			if(annotation != null){
				System.out.println(field.getName() + ": " + annotation.author());
			}
		}
	}
}

@Retention(RetentionPolicy.RUNTIME)
@interface FooAnnotation{
	String author() default "Tom";
}

class Foo{
	private int valueA;
	
	@FooAnnotation(author = "Lili")
	protected int valueB;
	
	@FooAnnotation()
	public int valueC;
	
	public Foo(){
		valueA = 50;
		valueB = 100;
		valueC = 150;
	}
	
	private void printA(){
		System.out.println("valueA: " + valueA);
	}
	
	protected void printB(){
		System.out.println("valueB: " + valueB);
	}
	
	public void printC(){
		System.out.println("valueC: " + valueC);
	}
}
