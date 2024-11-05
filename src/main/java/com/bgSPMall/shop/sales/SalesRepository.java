package com.bgSPMall.shop.sales;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SalesRepository extends JpaRepository<Sales,Integer> {

    @Query(value = "SELECT s FROM Sales s JOIN FETCH s.member") // s.member가 가리키는 테이블 join
    List<Sales> customFindAll();
}
