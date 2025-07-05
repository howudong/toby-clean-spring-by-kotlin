package tobyspring.splearn.application.member.provided

import jakarta.validation.Valid
import tobyspring.splearn.domain.member.Member
import tobyspring.splearn.domain.member.MemberInfoUpdateRequest
import tobyspring.splearn.domain.member.MemberRegisterRequest

/**
 * 회원 등록과 관련된 기능을 제공한다.
 */
interface MemberRegister {
    fun register(@Valid memberRegisterRequest: MemberRegisterRequest): Member
    fun activate(memberId: Long): Member
    fun deactivate(memberId: Long): Member
    fun updateInfo(memberId: Long, infoUpdateRequest: MemberInfoUpdateRequest): Member
}