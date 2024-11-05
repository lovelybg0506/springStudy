package com.bgSPMall.shop.sales;

import com.bgSPMall.shop.Member.Member;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@ToString
public class Sales {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String itemName;
    private Integer price;
    private Integer count;

    @ManyToOne(fetch = FetchType.LAZY) // EAGER : 필요없어도 미리 가져옴, LAZY : 필요할 때만 가져옴
    @JoinColumn(name = "member_id",
                foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private Member member;

    @CreationTimestamp
    private LocalDateTime created;
}