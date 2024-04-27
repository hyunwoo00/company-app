package company.orderApp.repository;

import company.orderApp.domain.Announcement;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AnnouncementRepository extends JpaRepository<Announcement, Long> {

    @Override
    Page<Announcement> findAll(Pageable pageable);

}
