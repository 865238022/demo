package com.test;

import com.classPath.ClassPathContextApplication;

public class Main {
	public static void main(String[] args) {
		ClassPathContextApplication ctx = 
					new ClassPathContextApplication("applicationContext.xml");
		
		Car car = (Car) ctx.getBean("wudi");
		car.setBound("BMW");car.setSpeed(20);
		Person user = (Person) ctx.getBean("person");
		user.setName("ksks");
		System.out.println(user);
	}
}
