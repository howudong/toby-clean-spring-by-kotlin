package tobyspring.splearn.application.provided

import jakarta.transaction.Transactional
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.annotation.Import
import org.springframework.test.context.TestConstructor
import tobyspring.SplearnConfiguration

@SpringBootTest
@Transactional
@Import(SplearnConfiguration::class)
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
class MemberFinderTest {
}