package company.orderApp.service;


import company.orderApp.domain.Announcement;
import company.orderApp.repository.AnnouncementRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AnnouncementService {

    private final AnnouncementRepository announcementRepository;

    /**
     * 공지 작성
     */
    @Transactional
    public void addAnnouncement(Announcement announcement) {
        announcementRepository.save(announcement);
    }

    /**
     * 공지 삭제
     */
    @Transactional
    public void removeAnnouncement(Announcement announcement) {
        announcementRepository.delete(announcement);
    }
}
