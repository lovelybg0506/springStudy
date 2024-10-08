package com.bgSPMall.shop;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

// 상품 Table
@Entity
@ToString // toString 함수 사용가능
@Getter @Setter
public class Item {

    // 상품 id
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 상품명
    @Column(nullable = false)
    private String title;

    // 가격
    @Column(nullable = false)
    private Integer price;  // column용 변수에서는 int 대신 Integer 사용

    // 상품설명
    private String description;

//    public String toString(){
//        return this.title + this.price;
//    }
}