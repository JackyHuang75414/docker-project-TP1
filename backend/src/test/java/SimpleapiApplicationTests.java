import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")  // 使用測試配置
class SimpleapiApplicationTests {

    @Test
    void contextLoads() {
        // 測試應用上下文加載
    }
}