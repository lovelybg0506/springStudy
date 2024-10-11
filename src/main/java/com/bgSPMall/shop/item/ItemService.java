package com.bgSPMall.shop.item;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

@Service
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;

    public void saveItem(String title, Integer price, String description) {

        if (title == null || title.isEmpty()) {
            throw new IllegalArgumentException("제목을 입력해주세요.");
        } else if (title.length() > 255) {
            throw new IllegalArgumentException("제목 글자 수 제한 255.");
        }

        if (description == null || description.isEmpty()) {
            throw new IllegalArgumentException("상세설명을 입력해주세요.");
        } else if (description.length() > 255) {
            throw new IllegalArgumentException("상세설명 글자 수 제한 3999.");
        }

        try {
            if (price < 0) {
                throw new IllegalArgumentException("가격은 음수일 수 없습니다.");
            }
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("가격형식이 올바르지 않습니다.");
        }

        Item item = new Item();
        item.setTitle(title);
        item.setPrice(price);
        item.setDescription(description);

        itemRepository.save(item);
    }

    public void getList(Model model) {

        var result = itemRepository.findAll(); // 테이블 안의 모든데이터를 List 자료형으로 가져옴
        model.addAttribute("items", result); // list.html에서 items라는 이름으로 데이터셋 사용하겠다

    }

    // 상품수정
    public void editItem(Long id, String title, Integer price, String description) {

        try {
            if (id == null) {
                throw new IllegalArgumentException("id 값 없음");
            }
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("id값 오류.");
        }

        if (title == null || title.isEmpty()) {
            throw new IllegalArgumentException("제목을 입력해주세요.");
        } else if (title.length() > 255) {
            throw new IllegalArgumentException("제목 글자 수 제한 255.");
        }

        if (description == null || description.isEmpty()) {
            throw new IllegalArgumentException("상세설명을 입력해주세요.");
        } else if (description.length() > 255) {
            throw new IllegalArgumentException("상세설명 글자 수 제한 3999.");
        }

        try {
            if (price < 0) {
                throw new IllegalArgumentException("가격은 음수일 수 없습니다.");
            }
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("가격형식이 올바르지 않습니다.");
        }

        Item item = new Item();
        item.setId(id);
        item.setTitle(title);
        item.setPrice(price);
        item.setDescription(description);

        itemRepository.save(item);
    }

}
