package company.orderApp.controller;

import company.orderApp.controller.response.PagingDto;
import company.orderApp.controller.response.UserResponse;
import company.orderApp.domain.User;
import company.orderApp.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/admin/users")
public class UserAdminController {

    private final UserRepository userRepository;

    @GetMapping("")
    public PagingDto allUsers(@PageableDefault(size = 20) Pageable pageable) {

        Page<User> users = userRepository.findAll(pageable);
        List<UserResponse> userList = users.map(UserResponse::new).getContent();


        return new PagingDto(userRepository.count(), userList, 20.0);
    }
}
