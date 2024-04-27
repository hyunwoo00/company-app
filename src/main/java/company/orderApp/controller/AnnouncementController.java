package company.orderApp.controller;


import company.orderApp.controller.request.NoticeRequest;
import company.orderApp.controller.response.AnnouncementDetailDto;
import company.orderApp.controller.response.AnnouncementDto;
import company.orderApp.controller.response.PagingDto;
import company.orderApp.controller.response.ResultResponse;
import company.orderApp.domain.Announcement;
import company.orderApp.repository.AnnouncementRepository;
import company.orderApp.service.AnnouncementService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/announcement")
public class AnnouncementController {

    private final AnnouncementRepository announcementRepository;
    private final AnnouncementService announcementService;


    @GetMapping("")
    public PagingDto notices(@PageableDefault(size = 20, sort = "createdTime", direction = Sort.Direction.DESC)Pageable pageable) {
        Page<Announcement> notices = announcementRepository.findAll(pageable);
        long total = announcementRepository.count();

        List<AnnouncementDto> noticeList = notices.map(AnnouncementDto::new).getContent();

        return new PagingDto(total, noticeList, 20.0);
    }

    @GetMapping("{id}")
    public AnnouncementDetailDto notices(@PathVariable("id") long id) {
        Announcement announcement = announcementRepository.findById(id).orElseThrow();

        return new AnnouncementDetailDto(announcement.getTitle(), announcement.getContent());
    }


}
