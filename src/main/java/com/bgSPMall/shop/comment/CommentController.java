package com.bgSPMall.shop.comment;

import com.bgSPMall.shop.Member.CustomUser;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@PreAuthorize("isAuthenticated()") // 로그인했는지 검사
@RequiredArgsConstructor // db입출력함수포함
public class CommentController {

    private final CommentRepository commentRepository;

    @PostMapping("/comment")
    String postComment(@RequestParam String content,
                       @RequestParam Long parent,
                       Authentication auth)  {

        CustomUser user = (CustomUser) auth.getPrincipal();

        var data = new Comment();
        data.setContent(content);
        data.setUsername(user.getUsername());
        data.setParentId(parent);
        commentRepository.save(data);

        return "redirect:/list";
    }

}
