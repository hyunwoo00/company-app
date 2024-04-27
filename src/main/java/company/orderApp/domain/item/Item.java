package company.orderApp.domain.item;

import jakarta.persistence.*;
import lombok.*;


@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Item {

    @Id
    @GeneratedValue
    @Column(name = "item_id")
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private int price;

    @Column(nullable = false)
    private String imgPath;

    @Column(nullable = false)
    private String itemNumber;

    @Column(nullable = false)
    private String manufacturer;

    private boolean isBest;

    @Column(nullable = false)
    private String description;

    // 몇개부터 할인
    private int minimumQuantityForDiscount;

    /**
     * 등록된 상품이 주문된 경우 orderItem에 item의 key가 존재해 삭제하지 못하기 때문에 item의 데이터베이스에서의 삭제 대신
     * 숨기기를 설정해 itemList에서 제외한다.
     */
    private boolean visible;

    //==생성 메서드==//
    public static Item createItem(String name, int price, String imgPath, String itemNumber, String manufacturer, String description, int saleAmount, boolean isBest) {
        return Item.builder()
                .name(name)
                .price(price)
                .imgPath(imgPath)
                .itemNumber(itemNumber)
                .manufacturer(manufacturer)
                .minimumQuantityForDiscount(saleAmount)
                .isBest(isBest)
                .visible(true)
                .description(description)
                .build();
    }

    public void hide(){
        this.visible = false;
    }

}
