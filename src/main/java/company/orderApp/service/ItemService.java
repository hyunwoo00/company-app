package company.orderApp.service;


import company.orderApp.domain.item.Item;
import company.orderApp.repository.ItemRepository;
import company.orderApp.service.exception.ExcessBestItemException;
import company.orderApp.service.exception.NonExistentItemException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ItemService {

    private final ItemRepository itemRepository;


    /**
     * 상품 등록
     */
    @Transactional
    public Long registerItem(Item item) {
        itemRepository.save(item);
        return item.getId();
    }

    /**
     * 상품 등록 삭제
     */
    @Transactional
    public void removeItem(Long itemId) {

        itemRepository.findById(itemId).ifPresentOrElse(
                Item::hide,
                () -> {
                    throw new NonExistentItemException("존재하지 않는 상품입니다.");
                }
        );
    }


    /**
     * 베스트 상품 갯수 조회
     */
    public long findBestItemNumber() {
        List<Item> allItem = itemRepository.findAll();

        return allItem.stream()
                .filter(Item::isBest)
                .count();

    }


    public Item findItemById(Long itemId) {
        return itemRepository.findById(itemId)
                .orElseThrow(() -> new NonExistentItemException("존재하지 않는 상품입니다."));
    }


}
