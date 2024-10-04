package com.bgSPMall.shop;

import jakarta.persistence.*;

// 상품 Table
@Entity
public class Item {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    @Column(nullable = false)
    public String title;

    @Column(nullable = false)
    public Integer price;  // column용 변수에서는 int 대신 Integer 사용

}

