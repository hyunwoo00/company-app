package company.orderApp.controller;

import company.orderApp.controller.request.LoginRequest;
import company.orderApp.controller.request.SignUpRequest;
import company.orderApp.controller.request.UpdateUserRequest;
import company.orderApp.controller.response.*;
import company.orderApp.domain.Address;
import company.orderApp.domain.User;
import company.orderApp.jwt.JwtToken;
import company.orderApp.jwt.JwtTokenProvider;
import company.orderApp.repository.UserRepository;
import company.orderApp.service.UserService;
import company.orderApp.service.exception.ExpiredRefreshTokenException;
import company.orderApp.service.exception.NonExistentUserException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

import static company.orderApp.controller.request.SignUpRequest.*;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;
    private final UserRepository userRepository;
    private final JwtTokenProvider jwtTokenProvider;

    @PostMapping("")
    public ResponseEntity<?> signUp(@RequestBody @Valid SignUpRequest signUpRequest) {

        userService.join(toEntity(signUpRequest));

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/login")
    public LoginResponse login(@RequestBody @Valid LoginRequest loginRequest) {

        log.info("username = " + loginRequest.getUsername() + ", password = " + loginRequest.getPassword());
        JwtToken jwtToken = userService.signIn(loginRequest.getUsername(), loginRequest.getPassword());

        return new LoginResponse(jwtToken.getAccessToken(),jwtToken.getRefreshToken(),userService.findByUserName(loginRequest.getUsername()));
    }

    @GetMapping("/logout/{id}")
    public ResponseEntity logout(@PathVariable("id") Long userId, HttpServletRequest request) {
        /*String accessToken = resolveToken(request);
        User user =userRepository.findById(userId)
                .orElseThrow(() -> new NonExistentUserException("존재하지 않는 회원입니다."));

        userService.signOut(accessToken, user.getUsername());*/

        return new ResponseEntity(HttpStatus.OK);
    }


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

    @GetMapping("/refresh")
    public ResponseEntity<?> refresh(HttpServletRequest request) {

        String accessToken = resolveToken(request);
        String refreshToken = resolveRefreshToken(request);
        Long userId = resolveUserId(request);

        User user = userService.findUserById(userId)
                 .orElseThrow(() -> new NonExistentUserException("존재하지 않는 회원입니다."));


        //accessToken 만료
        if(!jwtTokenProvider.validateToken(accessToken)){
            try {
                String newAccessToken = userService.reIssueAccessToken(user.getUsername(), refreshToken);
                RefreshResponse refreshResponse = new RefreshResponse();
                refreshResponse.setAccessToken(newAccessToken);

                return new ResponseEntity<>(refreshResponse, HttpStatus.OK); //200
            }
            //refreshToken 만료
            catch(ExpiredRefreshTokenException e){
                return new ResponseEntity<>(HttpStatus.UNAUTHORIZED); //401
            }
        }
        //accessToken 만료X
        else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST); //400
        }
    }

    private String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer")) {
            return bearerToken.substring(7);
        }
        return null;
    }

    private String resolveRefreshToken(HttpServletRequest request) {
        return request.getHeader("refresh");
    }

    private Long resolveUserId(HttpServletRequest request) {
        return Long.parseLong(request.getHeader("userId"));
    }


}
