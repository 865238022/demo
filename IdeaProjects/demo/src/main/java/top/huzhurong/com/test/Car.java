package com.test;

import com.annotation.Component;

@Component("wudi")
public class Car {
	private String bound;
	private int speed;
	public String getBound() {
		return bound;
	}
	public void setBound(String bound) {
		this.bound = bound;
	}
	public int getSpeed() {
		return speed;
	}
	public void setSpeed(int speed) {
		this.speed = speed;
	}
	@Override
	public String toString() {
		return "Car [bound=" + bound + ", speed=" + speed + "]";
	}
}
