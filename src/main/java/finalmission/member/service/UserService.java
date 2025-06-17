package finalmission.member.service;

import finalmission.member.domain.User;
import finalmission.member.dto.request.UserCreateRequest;
import finalmission.member.dto.response.UserResponse;
import finalmission.member.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public UserResponse creatUser(UserCreateRequest request) {
        User withoutId = User.createWithoutId(request.email(), request.password());
        User savedUser = userRepository.save(withoutId);
        return new UserResponse(savedUser.getId(), savedUser.getEmail());
    }
}
