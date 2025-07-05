package tobyspring.splearn.application.member.required

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.extensions.spring.SpringTestExtension
import io.kotest.extensions.spring.SpringTestLifecycleMode
import io.kotest.matchers.shouldNotBe
import jakarta.persistence.EntityManager
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.dao.DataIntegrityViolationException
import tobyspring.splearn.domain.member.Member
import tobyspring.splearn.domain.member.MemberFixture

@DataJpaTest
class MemberRepositoryTest : BehaviorSpec() {
    override fun extensions() = listOf(
        SpringTestExtension(
            SpringTestLifecycleMode.Root
        )
    )

    @Autowired
    lateinit var memberRepository: MemberRepository

    @Autowired
    lateinit var entityManager: EntityManager

    init {
        Given("회원이 생성되고") {
            val member =
                Member.register(
                    MemberFixture.createMemberRegisterRequest(),
                    MemberFixture.createPasswordEncoder()
                )
            When("회원이 저장되면") {
                memberRepository.save(member)
                Then("id가 null이면 안된다") {
                    entityManager.flush()
                    entityManager.clear()
                    member.id shouldNotBe null
                }
            }
        }
        Given("동일한 이메일을 가진 회원이 두명 주어지고,") {
            val member1 = Member.register(
                MemberFixture.createMemberRegisterRequest(),
                MemberFixture.createPasswordEncoder()
            )
            val member2 = Member.register(
                MemberFixture.createMemberRegisterRequest(),
                MemberFixture.createPasswordEncoder()
            )
            When("한명이 저장된 상태에서") {
                memberRepository.save(member1)
                Then("두번째 같은 이메일을 가진 사람이 저장되려고 하면 DataIntegrityViolationException이 발생한다") {
                    shouldThrow<DataIntegrityViolationException> {
                        memberRepository.save(member2)
                    }
                }
            }
        }
    }
}