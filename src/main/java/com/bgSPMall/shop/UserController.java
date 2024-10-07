package com.bgSPMall.shop;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class UserController {

    private final NoticeRepository noticeRepository;

    @GetMapping("/user")
    public String User(Model model) {

        var result = noticeRepository.findAll();

        model.addAttribute("users", result);

        var user = new User();
        user.setName("userName");
        user.setAge(-3);
        System.out.println(user.getName() + " " + user.getAge());

        user.changeAge(120);
        System.out.println(user.getName() + " " + user.getAge());

        user.addAge();
        System.out.println(user.getName() + " " + user.getAge());


        return "1";
    }
}
