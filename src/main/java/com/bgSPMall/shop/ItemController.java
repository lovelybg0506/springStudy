package com.bgSPMall.shop;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;


@Controller
@RequiredArgsConstructor
public class ItemController {

    // item Table 가져오기
    private final ItemRepository itemRepository;  // 원하는 class에 repository 등록 (@RequiredArgsConstructor 사용해야함(lombok))

    // item list
    @GetMapping("/list")
    public String list(Model model) {

        var result = itemRepository.findAll(); //테이블 안의 모든데이터를 List 자료형으로 가져옴
        model.addAttribute("items", result); // list.html에서 items라는 이름으로 데이터셋 사용하겠다

        return "list.html";
    }

    // write document
    @GetMapping("/write")
    public String write() {

        return "write.html";
    }

    // add item in database
    @PostMapping("/addItem")
    public String addItem(@RequestParam Map<String,String> formData) {
//    public String addItem(@ModelAttribute Item item) { // 변수명item 이라는 Item객체에 form에서 넘어온 데이터를 넣어라

        String title = formData.get("title");
        String strPrice = formData.get("price");

        if (title == null || title.isEmpty()) {
            throw new IllegalArgumentException("제목을 입력해주세요.");
        }

        int price;
        try {
            price = Integer.parseInt(strPrice);
            if (price < 0) {
                throw new IllegalArgumentException("가격은 음수일 수 없습니다.");
            }
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("가격형식이 올바르지 않습니다.");
        }

        Item item = new Item();
        item.setTitle(title);
        item.setPrice(price);

        itemRepository.save(item);

        return "redirect:/list"; // redirect
    }

    @GetMapping("/detail/{id}")
    String detail(@PathVariable Long id, Model model) {   // 유저가 URL파라미터에 입력한 값

        Optional<Item> result = itemRepository.findById(id);
        // Optional : 비어있을 수도 있고 Item 일 수도 있기 때문에 사용

        if (result.isPresent()) {   // Optional은 없는걸 쓰려고하면 에러나기 때문에 Optional 객체가 값을 가지고 있으면 실행 값이 없으면 넘어감
            model.addAttribute("detailData", result.get());
            return "detail.html";
        } else {
            return "redirect:/list";
        }

    }

}
