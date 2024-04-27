package company.orderApp.controller.response;


import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AnnouncementDetailDto {
    private String title;
    private String content;
}
