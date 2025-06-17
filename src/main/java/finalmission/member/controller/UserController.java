package finalmission.member.controller;

import finalmission.member.dto.request.UserCreateRequest;
import finalmission.member.dto.response.UserResponse;
import finalmission.member.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/users")
    public ResponseEntity<UserResponse> registerUser(@RequestBody  UserCreateRequest request){
        UserResponse userResponse = userService.creatUser(request);
        return ResponseEntity.ok(userResponse);
    }
}
