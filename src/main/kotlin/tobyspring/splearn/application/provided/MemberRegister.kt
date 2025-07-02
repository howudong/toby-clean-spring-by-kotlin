package tobyspring.splearn.application.provided

import jakarta.validation.Valid
import tobyspring.splearn.domain.Member
import tobyspring.splearn.domain.MemberRegisterRequest

/**
 * 회원 등록과 관련된 기능을 제공한다.
 */
interface MemberRegister {
    fun register(@Valid memberRegisterRequest: MemberRegisterRequest): Member
}