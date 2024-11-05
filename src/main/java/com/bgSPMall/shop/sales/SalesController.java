package com.bgSPMall.shop.sales;

import com.bgSPMall.shop.Member.CustomUser;
import com.bgSPMall.shop.Member.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class SalesController {

    private final SalesRepository salesRepository;

    @PostMapping("/order")
    String postOrder(@RequestParam String title,
                     @RequestParam Integer price,
                     @RequestParam Integer count,
                     Authentication auth) {
        Sales sales = new Sales();

        sales.setCount(count);
        sales.setPrice(price);
        sales.setItemName(title);

        CustomUser user = (CustomUser) auth.getPrincipal();

        var member = new Member();
        member.setId(user.id);
        sales.setMember(member);

        salesRepository.save(sales);

        return "redirect:/list";
    }

    @GetMapping("/order/all")
    String getOrderAll() {

        List<Sales> result = salesRepository.customFindAll();

        return "index.html";
    }

}