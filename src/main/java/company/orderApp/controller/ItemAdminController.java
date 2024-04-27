package company.orderApp.controller;

import company.orderApp.controller.request.ItemRequest;
import company.orderApp.domain.item.Item;
import company.orderApp.service.ItemService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/admin/items")
public class ItemAdminController {

    private final ItemService itemService;

    /**
     * 상품 등록
     */
    @PostMapping("")
    public ResponseEntity registerItem(@RequestBody ItemRequest request) {

        Item item = Item.createItem(request.getName(), request.getPrice(), request.getImgPath(), request.getItemNumber(), request.getManufacturer(), request.getDescription(), request.getMinimumQuantityForDiscount(), request.getIsBest());

        itemService.registerItem(item);


        return new ResponseEntity(HttpStatus.OK);
    }


    /**
     * 상품 삭제
     */
    @DeleteMapping("{id}")
    public ResponseEntity<?> deleteUser(@PathVariable("id") Long id) {
        itemService.removeItem(id);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
