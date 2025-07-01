package tobyspring.splearn.application.provided

import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.extensions.spring.SpringTestExtension
import io.kotest.extensions.spring.SpringTestLifecycleMode
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.annotation.Import
import tobyspring.SplearnConfiguration
import tobyspring.splearn.application.MemberService
import tobyspring.splearn.domain.MemberFixture
import tobyspring.splearn.domain.MemberStatus

@SpringBootTest
@Import(SplearnConfiguration::class)
class MemberRegisterTest : BehaviorSpec() {
    override fun extensions() = listOf(SpringTestExtension(SpringTestLifecycleMode.Root))

    @Autowired
    private lateinit var memberService: MemberService

    init {
        Given("createMemberRegister가 주어지고") {
            val createMemberRegister = MemberFixture.Companion.createMemberRegisterRequest()
            When("회원이 register 되었을 때") {
                val member = memberService.register(createMemberRegister)
                Then("회원 id는 Null이 아니고") {
                    member.id shouldNotBe null
                }
                Then("회원 상태는 PENDING 이어야 한다.") {
                    member.status() shouldBe MemberStatus.PENDING
                }
            }
        }
    }
}