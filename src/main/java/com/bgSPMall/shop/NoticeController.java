package com.bgSPMall.shop;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class NoticeController {

    private final NoticeRepository noticeRepository;

    @GetMapping("/notice")
    public String Notice(Model model) {

        var result = noticeRepository.findAll();

        model.addAttribute("notices", result);

        return "notice.html";
    }
}
