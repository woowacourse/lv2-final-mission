package finalmission.user.service;

import java.util.Objects;

import org.springframework.stereotype.Service;

import finalmission.auth.JwtProvider;
import finalmission.user.domain.User;
import finalmission.user.dto.UserRequest;
import finalmission.user.dto.UserResponse;
import finalmission.user.repository.UserRepository;
import finalmission.util.NameGenerator;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {

    private final JwtProvider jwtProvider;
    private final UserRepository userRepository;
    private final NameGenerator nameGenerator;

    public UserResponse.Login login(UserRequest.Login request) {
        User user = userRepository.getByEmail(request.email());

        if (!Objects.equals(user.getPassword(), request.password())) {
            throw new IllegalArgumentException("잘못된 이메일 또는 비밀번호입니다.");
        }

        return new UserResponse.Login(jwtProvider.createToken(user));
    }

    public void join(UserRequest.Join request) {
        if (userRepository.findByEmail(request.email()).isPresent()) {
            throw new IllegalArgumentException("이미 존재하는 이메일입니다.");
        }
        String name = request.name();
        if (name == null) {
            name = nameGenerator.randomName();
        }
        userRepository.save(new User(name, request.email(), request.password()));
    }
}
