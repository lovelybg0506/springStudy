package com.bgSPMall.shop;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

// 사용자 Table
@Entity
@Getter
@Setter
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long id;
    private String name;
    private Integer age;


    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public void addAge() {
        this.age = age+1;
    }

    public void changeAge(Integer cAge) {

//        if (cAge > 100) {
//            System.out.println("나이100 이상 설정 불가능 : [" + cAge + "]");
//            return;
//        } else if (cAge < 0) {
//            System.out.println("나이 0 이하 설정 불가능 : [" + cAge + "]");
//            return;
//        }

        if (cAge > 0 && cAge < 100){
            this.age = cAge;
        }

    }
}
