package com.bgSPMall.shop.item;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

// 상품 Table
@Entity
@ToString // toString 함수 사용가능
@Getter @Setter
@Table(indexes = @Index(columnList = "title", name = "itemIdx"))
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
    @Column(length = 3999)
    private String description;

    // 등록자
    private String RGTFLDUSR;

    // 이미지경로(S3)
    private String imgPath;

//    public String toString(){
//        return this.title + this.price;
//    }
}