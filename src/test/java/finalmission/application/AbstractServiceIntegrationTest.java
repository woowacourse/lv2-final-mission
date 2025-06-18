package finalmission.application;

import finalmission.infrastructure.RandomNameClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.transaction.annotation.Transactional;

@ActiveProfiles("test")
@SpringBootTest
@Transactional
public abstract class AbstractServiceIntegrationTest {

    @MockitoBean
    protected RandomNameClient randomNameClient;
}
