package com.bgSPMall.shop;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;

    public void saveItem(String title, Integer price) {

        if (title == null || title.isEmpty()) {
            throw new IllegalArgumentException("제목을 입력해주세요.");
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

        itemRepository.save(item);
    }

}
