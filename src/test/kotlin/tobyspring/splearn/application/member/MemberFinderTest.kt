package tobyspring.splearn.application.member.provided

import io.kotest.core.extensions.Extension
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.extensions.spring.SpringTestExtension
import io.kotest.extensions.spring.SpringTestLifecycleMode
import io.kotest.matchers.equals.shouldBeEqual
import jakarta.persistence.EntityManager
import jakarta.transaction.Transactional
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.annotation.Import
import org.springframework.test.context.TestConstructor
import tobyspring.SplearnConfiguration
import tobyspring.splearn.application.member.MemberModifyService
import tobyspring.splearn.application.member.MemberQueryService
import tobyspring.splearn.domain.member.MemberFixture

@SpringBootTest
@Transactional
@Import(SplearnConfiguration::class)
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
class MemberFinderTest(
    private val memberQueryService: MemberQueryService,
    private val memberModifyService: MemberModifyService,
    private val entityManager: EntityManager,
) : BehaviorSpec() {
    override fun extensions(): List<Extension> = listOf(SpringTestExtension(SpringTestLifecycleMode.Root))

    init {
        Given("저장된 회원이 주어지고") {
            val memberRegisterRequest = MemberFixture.Companion.createMemberRegisterRequest()
            val member = memberModifyService.register(memberRegisterRequest)

            entityManager.flush()
            entityManager.clear()

            When("그 회원의 id로 조회했을 떄") {
                val findMember = memberQueryService.find(memberId = member.id!!)

                Then("조회되어야 한다.(이메일이 같아야 한다.)") {
                    findMember.email shouldBeEqual member.email
                }
            }
        }
    }

}