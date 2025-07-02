package tobyspring.splearn

import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.annotation.Import
import tobyspring.SplearnConfiguration

@SpringBootTest
@Import(SplearnConfiguration::class)
class SplearnApplicationTests {

    @Test
    fun contextLoads() {
    }

}
