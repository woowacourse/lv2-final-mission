package finalmission.application;

import finalmission.infrastructure.RandomNameClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.transaction.annotation.Transactional;

@ActiveProfiles("test")
@SpringBootTest
@Import(TestClockConfig.class)
@Transactional
public abstract class AbstractServiceIntegrationTest {

    @MockitoBean
    protected RandomNameClient randomNameClient;
}
