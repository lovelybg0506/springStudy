package com.bgSPMall.shop.item;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ItemRepository extends JpaRepository<Item, Long> {

    Page<Item> findPageBy(Pageable page);
    List<Item> findAllByTitleContains(String title);

//    @Query(value =  "select * from item where title like ?1", nativeQuery = true)
//    Item rawQuery1(String searchText);


    @Query(value = "SELECT * FROM shop.item WHERE to_tsvector('korean', title) @@ to_tsquery('korean', ?1)", nativeQuery = true)
    List<Item> fullTextSearch(String searchText);

    // 메서드 호출
    List<Item> results = itemRepository.fullTextSearch("셔츠");
}