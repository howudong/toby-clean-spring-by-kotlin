package tobyspring.splearn.application.member

import jakarta.transaction.Transactional
import org.springframework.stereotype.Service
import org.springframework.validation.annotation.Validated
import tobyspring.splearn.application.member.provided.MemberFinder
import tobyspring.splearn.application.member.provided.MemberRegister
import tobyspring.splearn.application.member.required.EmailSender
import tobyspring.splearn.application.member.required.MemberRepository
import tobyspring.splearn.domain.member.DuplicateEmailException
import tobyspring.splearn.domain.member.Member
import tobyspring.splearn.domain.member.MemberInfoUpdateRequest
import tobyspring.splearn.domain.member.MemberRegisterRequest
import tobyspring.splearn.domain.member.PasswordEncoder
import tobyspring.splearn.domain.shared.Email

@Service
@Transactional
@Validated
class MemberModifyService(
    private val memberFinder: MemberFinder,
    private val memberRepository: MemberRepository,
    private val emailSender: EmailSender,
    private val passwordEncoder: PasswordEncoder,
) : MemberRegister {
    override fun register(memberRegisterRequest: MemberRegisterRequest): Member {
        checkDuplicateEmail(memberRegisterRequest)

        val member = Member.register(memberRegisterRequest, passwordEncoder)

        memberRepository.save(member)

        emailSender.send(member.email, "가입을 완료해주세요!", "아래 링크를 눌러 가입을 완료해주세요")

        return member
    }

    override fun activate(memberId: Long): Member {
        val member = memberFinder.find(memberId)

        member.activate()

        return memberRepository.save(member)
    }

    override fun deactivate(memberId: Long): Member {
        val member = memberFinder.find(memberId)

        member.deActivated()

        return memberRepository.save(member)
    }

    override fun updateInfo(memberId: Long, infoUpdateRequest: MemberInfoUpdateRequest): Member {
        val member = memberFinder.find(memberId)

        member.updateInfo(infoUpdateRequest)

        return memberRepository.save(member)
    }

    private fun checkDuplicateEmail(memberRegisterRequest: MemberRegisterRequest) {
        val isExist = memberRepository
            .findByEmail(Email(memberRegisterRequest.email))
            .isNotEmpty()

        if (isExist) {
            throw DuplicateEmailException("이미 존재하는 이메일입니다.")
        }
    }
}