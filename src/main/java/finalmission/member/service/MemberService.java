package finalmission.member.service;

import finalmission.user.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class MemberService {

    private final UserRepository userRepository;

    public MemberService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
}
