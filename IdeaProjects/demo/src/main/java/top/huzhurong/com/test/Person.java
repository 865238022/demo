package com.test;

import com.annotation.Autowired;
import com.annotation.Component;
import com.annotation.Qualifier;

@Component
public class Person {
	
	private String name ;
	
	@Autowired
	@Qualifier("wudi")
	private Car car;
	
	public String getName() {
		return name;
	}
	
	public Car getCar() {
		return car;
	}

	public void setCar(Car car) {
		this.car = car;
	}

	public void setName(String name) {
		this.name = name;
	}

	//Person [name=wudi, car=null]   这个跟的预期是不符的 Person [name= , car []]
	@Override
	public String toString() {
		return "Person [name=" + name + ", car=" + car + "]";
	}

}
