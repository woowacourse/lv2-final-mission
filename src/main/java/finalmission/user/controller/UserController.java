package finalmission.user.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import finalmission.user.dto.UserRequest;
import finalmission.user.dto.UserResponse;
import finalmission.user.service.UserService;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @PostMapping("/login")
    public ResponseEntity<UserResponse.Login> login(@RequestBody UserRequest.Login request) {
        return ResponseEntity.ok().body(userService.login(request));
    }

    @PostMapping("/join")
    public ResponseEntity<Void> join(@RequestBody UserRequest.Join request) {
        userService.join(request);
        return ResponseEntity.ok().build();
    }
}
