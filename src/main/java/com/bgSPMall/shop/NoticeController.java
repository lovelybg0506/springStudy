package com.bgSPMall.shop;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class NoticeController {

    private final NoticeRepository noticeRepository;
    private final NoticeService noticeService;

    @GetMapping("/notice")
    public String Notice(Model model) {

        noticeService.getNotice(model);

        return "notice.html";
    }
}
