package company.orderApp.controller;


import company.orderApp.controller.request.NoticeRequest;
import company.orderApp.domain.Announcement;
import company.orderApp.repository.AnnouncementRepository;
import company.orderApp.service.AnnouncementService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/admin/announcement")
public class AnnouncementAdminController {

    private final AnnouncementService announcementService;
    private final AnnouncementRepository announcementRepository;

    @PostMapping("")
    public ResponseEntity create(@RequestBody NoticeRequest request) {
        Announcement announcement = Announcement.createAnnouncement(request.getTitle(), request.getContent());

        announcementService.addAnnouncement(announcement);

        return new ResponseEntity(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable("id") long id) {
        Announcement announcement = announcementRepository.findById(id).orElseThrow();

        announcementService.removeAnnouncement(announcement);

        return new ResponseEntity(HttpStatus.OK);
    }
}
