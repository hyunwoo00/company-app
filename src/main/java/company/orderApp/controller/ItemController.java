package company.orderApp.controller;


import company.orderApp.controller.request.ItemRequest;
import company.orderApp.controller.response.ItemDetailDto;
import company.orderApp.controller.response.ItemDto;
import company.orderApp.controller.response.ResultResponse;
import company.orderApp.domain.item.Item;
import company.orderApp.repository.ItemRepository;
import company.orderApp.service.ItemService;
import company.orderApp.service.exception.NonExistentItemException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/items")
public class ItemController {

    private final ItemService itemService;
    private final ItemRepository itemRepository;


    @GetMapping("")
    public ResultResponse items() {
        List<Item> items = itemRepository.findAllByVisibleTrue();
        List<ItemDto> result = items.stream().map(ItemDto::new).toList();

        return new ResultResponse(result.size(), result);
    }

    @GetMapping("{id}")
    public ItemDetailDto itemDetails(@PathVariable("id") Long itemId) {
        Item item = itemRepository.findById(itemId)
                .orElseThrow(() -> new NonExistentItemException("존재하지 않는 상품입니다."));

        return new ItemDetailDto(item);
    }



}
