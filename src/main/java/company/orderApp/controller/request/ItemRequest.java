package company.orderApp.controller.request;


import jakarta.persistence.Column;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ItemRequest {
    private String name;

    private int price;

    private String imgPath;

    private String itemNumber;

    private String manufacturer;

    private Boolean isBest;

    private String description;

    private int minimumQuantityForDiscount;
}
