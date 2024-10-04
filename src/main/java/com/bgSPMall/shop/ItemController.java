package com.bgSPMall.shop;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
@RequiredArgsConstructor
public class ItemController {

    // item Table 가져오기
    private final ItemRepository itemRepository;  // 원하는 class에 repository 등록 (@RequiredArgsConstructor 사용해야함(lombok))

    @GetMapping("/list")
    public String list(Model model) {

        var result = itemRepository.findAll(); //테이블 안의 모든데이터를 List 자료형으로 가져옴
//        System.out.println("id : "+ result.get(0).id + " \n title : " + result.get(0).title + " \n price : " + result.get(0).price + "\n");
//        System.out.println("id : "+ result.get(1).id + " \n title : " + result.get(1).title + " \n price : " + result.get(1).price + "\n");
//        System.out.println("id : "+ result.get(2).id + " \n title : " + result.get(2).title + " \n price : " + result.get(2).price + "\n");

        model.addAttribute("title", result.get(0).title);
        model.addAttribute("price", result.get(0).price);

        return "list.html";
    }
}
