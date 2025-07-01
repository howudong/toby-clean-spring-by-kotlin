package tobyspring.splearn.application

import io.kotest.core.spec.style.BehaviorSpec
import io.mockk.mockk
import io.mockk.verify
import tobyspring.splearn.application.required.EmailSender
import tobyspring.splearn.application.required.MemberRepository
import tobyspring.splearn.domain.Member
import tobyspring.splearn.domain.MemberFixture

class MemberServiceTest : BehaviorSpec() {
    init {
        val emailSenderMock = mockk<EmailSender>(relaxed = true)
        val memberService = MemberService(
            memberRepository = MemberRepositoryStub(),
            emailSender = emailSenderMock,
            passwordEncoder = MemberFixture.createPasswordEncoder()
        )

        Given("MemberRegisterRequest가 주어지고") {
            val memberRegisterRequest = MemberFixture.createMemberRegisterRequest()

            When("register Service를 호출하였을 때") {
                memberService.register(memberRegisterRequest)

                Then("emailSender이 한번은 호출되어야 한다") {
                    verify(exactly = 1) { emailSenderMock.send(any(), any(), any()) }
                }
            }
        }

    }

    inner class MemberRepositoryStub : MemberRepository {
        override fun save(member: Member): Member {
            return member
        }
    }
}
