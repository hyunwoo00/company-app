package company.orderApp.controller.response;

import company.orderApp.domain.item.Item;
import lombok.Data;

@Data
public class ItemDetailDto {

    private Long id;

    private String name;

    private int price;

    private String imgPath;

    private String itemNumber;

    private String manufacturer;

    private String description;

    public ItemDetailDto(Item item) {
        this.id = item.getId();
        this.name = item.getName();
        this.price = item.getPrice();
        this.imgPath = item.getImgPath();
        this.itemNumber = item.getItemNumber();
        this.manufacturer = item.getManufacturer();
        this.description = item.getDescription();
    }
}
