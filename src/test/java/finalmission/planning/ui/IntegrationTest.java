package finalmission.planning.ui;

import finalmission.planning.auth.infra.JwtTokenProvider;
import finalmission.planning.domain.User;
import finalmission.planning.domain.UserRole;
import finalmission.planning.infra.repository.UserRepository;
import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public abstract class IntegrationTest {

   @LocalServerPort
   private int port;

   @Autowired
   protected JwtTokenProvider jwtTokenProvider;

   @Autowired
   private UserRepository userRepository;

    @BeforeEach
    void setUp() {
        RestAssured.port = port;
    }

    protected String saveNormalUserAndCreateToken() {
        User saved = userRepository.save(new User("토큰용 유저", "user@email.com", "1234", UserRole.NORMAL));
        return jwtTokenProvider.createToken(saved.getId(), saved.getRole());
    }

    protected String saveAdminUserAndCreateToken() {
        User saved = userRepository.save(new User("토큰용 유저", "user@email.com", "1234", UserRole.ADMIN));
        return jwtTokenProvider.createToken(saved.getId(), saved.getRole());
    }
}
