package com.bgSPMall.shop.item;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;


@Controller
@RequiredArgsConstructor
public class ItemController {

    // item Table 가져오기
    private final ItemRepository itemRepository;  // 원하는 class에 repository 등록 (@RequiredArgsConstructor 사용해야함(lombok))
    private final ItemService itemService;  // new ItemService가 들어있음

    // item list
    @GetMapping("/list")
    public String list(Model model) {

        itemService.getList(model);

        return "list.html";
    }

    // write document
    @GetMapping("/write")
    public String write() {

        return "write.html";
    }

    // add item in database
    @PostMapping("/addItem")
    public String addItem(@RequestParam Map<String, String> formData) {
//    public String addItem(@ModelAttribute Item item) { // 변수명item 이라는 Item객체에 form에서 넘어온 데이터를 넣어라

        String title = formData.get("n_title");
        Integer price = Integer.valueOf(formData.get("n_price"));
        String description = formData.get("n_description");

        itemService.saveItem(title, price, description);

        return "redirect:/list"; // redirect
    }

    @GetMapping("/detail/{id}")
    String detail(@PathVariable Long id, Model model) throws Exception {   // 유저가 URL파라미터에 입력한 값

        // Optional : 비어있을 수도 있고 Item 일 수도 있기 때문에 사용
        Optional<Item> result = itemRepository.findById(id);

        if (result.isPresent()) {   // Optional은 없는걸 쓰려고하면 에러나기 때문에 Optional 객체가 값을 가지고 있으면 실행 값이 없으면 넘어감
            model.addAttribute("detailData", result.get());
            return "detail.html";
        } else {
            return "redirect:/list";
        }
    }

    // edit page
    @GetMapping("/edit/{id}")
    String edit(@PathVariable Long id, Model model) throws Exception {

        Optional<Item> result = itemRepository.findById(id);

        if (result.isPresent()) {   // Optional은 없는걸 쓰려고하면 에러나기 때문에 Optional 객체가 값을 가지고 있으면 실행 값이 없으면 넘어감
            model.addAttribute("detailData", result.get());
            return "edit.html";
        } else {
            return "redirect:/list";
        }
    }

    // edit item
    @PostMapping("/editItem")
    public String editItem(@RequestParam Map<String, String> formData) {

        Long id = Long.valueOf(formData.get("n_id"));
        String title = formData.get("n_title");
        Integer price = Integer.valueOf(formData.get("n_price"));
        String description = formData.get("n_description");

        itemService.editItem(id, title, price, description);

        return "redirect:/list";
    }

    // /deleteItem으로 delete요청을 하면 실행되는 코드
    @DeleteMapping("/deleteItem/{id}")
    @ResponseBody
    ResponseEntity<String> deleteItem(@PathVariable Long id) {

        try {
            itemRepository.deleteById(id);
            return ResponseEntity.ok("삭제완료");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("삭제실패 : " + e.getMessage());
        }

    }

    // test
    @GetMapping("/test2")
    String deleteItem() {
        var result = new BCryptPasswordEncoder().encode("문자~");

        System.out.println(result);

        return "redirect:/list";

    }

}