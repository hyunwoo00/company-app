package company.orderApp.domain;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Setter
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Announcement {

    @Id
    @GeneratedValue
    @Column(name = "announcement_id")
    private Long id;

    private String title;
    private String content;
    private LocalDateTime createdTime;

    //==생성 메서드==//
    public static Announcement createAnnouncement(String title, String content) {
        Announcement announcement = new Announcement();
        announcement.setContent(content);
        announcement.setTitle(title);
        announcement.setCreatedTime(LocalDateTime.now());

        return announcement;
    }

}
