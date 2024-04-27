package company.orderApp.controller.response;


import company.orderApp.domain.Announcement;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class AnnouncementDto {
    private long id;
    private String title;
    private LocalDateTime date;


    public AnnouncementDto(Announcement announcement) {
        this.id = announcement.getId();
        this.title = announcement.getTitle();
        this.date = announcement.getCreatedTime();
    }
}
