package tobyspring.splearn.application

import org.springframework.stereotype.Service
import tobyspring.splearn.application.provided.MemberRegister
import tobyspring.splearn.application.required.EmailSender
import tobyspring.splearn.application.required.MemberRepository
import tobyspring.splearn.domain.Member
import tobyspring.splearn.domain.MemberRegisterRequest
import tobyspring.splearn.domain.PasswordEncoder

@Service
class MemberService(
    private val memberRepository: MemberRepository,
    private val emailSender: EmailSender,
    private val passwordEncoder: PasswordEncoder,
) : MemberRegister {
    override fun register(memberRegisterRequest: MemberRegisterRequest): Member {
        // check

        val member = Member.register(memberRegisterRequest, passwordEncoder)

        memberRepository.save(member)

        emailSender.send(member.email, "가입을 완료해주세요!", "아래 링크를 눌러 가입을 완료해주세요")

        return member
    }
}