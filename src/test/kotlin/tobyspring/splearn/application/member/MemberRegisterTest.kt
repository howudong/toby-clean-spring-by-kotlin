package tobyspring.splearn.application.member.provided

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.extensions.spring.SpringTestExtension
import io.kotest.extensions.spring.SpringTestLifecycleMode
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import jakarta.persistence.EntityManager
import jakarta.transaction.Transactional
import jakarta.validation.ConstraintViolationException
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.annotation.Import
import org.springframework.test.context.TestConstructor
import tobyspring.SplearnConfiguration
import tobyspring.splearn.application.member.MemberModifyService
import tobyspring.splearn.domain.member.DuplicateEmailException
import tobyspring.splearn.domain.member.MemberFixture.Companion
import tobyspring.splearn.domain.member.MemberRegisterRequest
import tobyspring.splearn.domain.member.MemberStatus

@SpringBootTest
@Transactional
@Import(SplearnConfiguration::class)
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
class MemberRegisterTest(
    private val memberModifyService: MemberModifyService,
    private val entityManager: EntityManager,

    ) : BehaviorSpec() {
    override fun extensions() = listOf(SpringTestExtension(SpringTestLifecycleMode.Root))

    init {
        Given("createMemberRegister가 주어지고") {
            val createMemberRegister = Companion.createMemberRegisterRequest()
            When("회원이 register 되었을 때") {
                val member = memberModifyService.register(createMemberRegister)
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
                memberModifyService.register(createMemberRegister1)
                Then("두번째가 가입했을때 이메일이 중복이므로 EmailDuplicateException이 발생해야한다") {
                    shouldThrow<DuplicateEmailException> {
                        memberModifyService.register(createMemberRegister2)
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
                        memberModifyService.register(createMemberRegister)
                    }
                }
            }
        }
        Given("등록된 회원이 주어지고,") {
            val memberRegisterRequest = Companion.createMemberRegisterRequest()
            val member = memberModifyService.register(memberRegisterRequest)

            entityManager.flush()
            entityManager.clear()

            When("그 회원이 가입 완료를 시켰을 때") {
                val updateMember = memberModifyService.activate(member.id!!)

                Then("그 회원의 상태는 ACTIVE 여야 한다") {
                    updateMember.status() shouldBe MemberStatus.ACTIVE
                }
            }
        }
    }
}