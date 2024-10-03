package com.bgSPMall.shop;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ShopApplication {

	public static void main(String[] args) {
		SpringApplication.run(ShopApplication.class, args);

//		Friend kim = new Friend("kim",12);
//		Friend kang = new Friend("kang",13);
//
//		System.out.println(kim.name + kim.age);
//		System.out.println(kang.name + kang.age);
	}

}

class Friend {

	String name;
	int age;

	Friend(String name, int age){
		this.name = name;
		this.age = age;
	}
}