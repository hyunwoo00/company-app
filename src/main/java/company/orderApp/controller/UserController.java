package company.orderApp.controller;


import company.orderApp.controller.request.UpdateUserRequest;
import company.orderApp.controller.response.*;
import company.orderApp.domain.User;
import company.orderApp.repository.UserRepository;
import company.orderApp.service.UserService;
import company.orderApp.service.exception.NonExistentUserException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;
    private final UserRepository userRepository;




    @GetMapping("{id}")
    public UserResponse user(@PathVariable("id") Long userId){
        User user =userRepository.findById(userId)
                .orElseThrow(() -> new NonExistentUserException("존재하지 않는 회원입니다."));

        return new UserResponse(user);
    }

    @PutMapping("{id}")
    public ResponseEntity<?> updateUser(@PathVariable Long id, @RequestBody @Valid UpdateUserRequest request) {

        userService.update(id, request.getName(), request.getPhoneNumber(), request.getAddress());

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> deleteUser(@PathVariable("id") Long id) {
        userService.withdrawMember(id);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("test")
    public ResponseEntity test() {
        return new ResponseEntity<>(HttpStatus.OK);
    }




}
