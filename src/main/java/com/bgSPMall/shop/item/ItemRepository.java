package com.bgSPMall.shop.item;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ItemRepository extends JpaRepository<Item, Long> {

    Page<Item> findPageBy(Pageable page);
    List<Item> findAllByTitleContains(String title);

//    @Query(value =  "select * from item where title like ?1", nativeQuery = true)
//    Item rawQuery1(String searchText);


    @Query(value = "SELECT * FROM item WHERE to_tsvector('english', title) @@ to_tsquery('english', ?1)", nativeQuery = true)
    List<Item> fullTextSearch(String searchText);


    @Query(value = "SELECT * FROM item WHERE to_tsvector('simple', title) @@ to_tsquery('simple', :searchText)",
            countQuery = "SELECT count(*) FROM item WHERE to_tsvector('simple', title) @@ to_tsquery('simple', :searchText)",
            nativeQuery = true)
    Page<Item> fullTextSearchWithPagination(@Param("searchText") String searchText, Pageable pageable);

}