package tobyspring.splearn.application.required

import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.extensions.spring.SpringTestExtension
import io.kotest.extensions.spring.SpringTestLifecycleMode
import io.kotest.matchers.shouldNotBe
import jakarta.persistence.EntityManager
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import tobyspring.splearn.domain.Member
import tobyspring.splearn.domain.MemberFixture

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
    }
}