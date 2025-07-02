package tobyspring.splearn.application.provided

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.extensions.spring.SpringTestExtension
import io.kotest.extensions.spring.SpringTestLifecycleMode
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import jakarta.transaction.Transactional
import jakarta.validation.ConstraintViolationException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.annotation.Import
import tobyspring.SplearnConfiguration
import tobyspring.splearn.application.MemberService
import tobyspring.splearn.domain.DuplicateEmailException
import tobyspring.splearn.domain.MemberFixture.Companion
import tobyspring.splearn.domain.MemberRegisterRequest
import tobyspring.splearn.domain.MemberStatus

@SpringBootTest
@Transactional
@Import(SplearnConfiguration::class)
class MemberRegisterTest : BehaviorSpec() {
    override fun extensions() = listOf(SpringTestExtension(SpringTestLifecycleMode.Root))

    @Autowired
    private lateinit var memberService: MemberService

    init {
        Given("createMemberRegister가 주어지고") {
            val createMemberRegister = Companion.createMemberRegisterRequest()
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
        Given("중복된 createMemberRegister가 주어지고") {
            val createMemberRegister1 = Companion.createMemberRegisterRequest()
            val createMemberRegister2 = Companion.createMemberRegisterRequest()
            When("한명은 가입하고") {
                memberService.register(createMemberRegister1)
                Then("두번째가 가입했을때 이메일이 중복이므로 EmailDuplicateException이 발생해야한다") {
                    shouldThrow<DuplicateEmailException> {
                        memberService.register(createMemberRegister2)
                    }
                }
            }
        }
        Given("nickname이 5자 미만이면") {
            val createMemberRegister = MemberRegisterRequest(
                email = "tjdvy963@naver.com",
                nickname = "asdf",
                password = "secret",
            )
            When("회원을 등록할때") {
                Then("ConstraintViolationException이 발생한다.") {
                    shouldThrow<ConstraintViolationException> {
                        memberService.register(createMemberRegister)
                    }
                }
            }
        }
    }
}