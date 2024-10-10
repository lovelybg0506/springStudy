package com.bgSPMall.shop;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.time.ZonedDateTime;
import java.util.Date;

@Controller
public class BasicController {
    @GetMapping("/") // 메인페이지로 접속하면 아래 코드 실행
    //@ResponseBody <- return에 문자 그대로를 보내라는 뜻이라서 주석처리해야 index.html file을 보여줌
    public String hello() {
        return "index.html";
    }

    @GetMapping("/date")
    @ResponseBody
    public String date() {

        return ZonedDateTime.now().toString();
    }

    @GetMapping("/default")
    public String defaultIndex() {
        return "default_index.html";
    }
}
