package tobyspring.splearn.application.provided

import tobyspring.splearn.domain.Member

/**
 * 회원 조회와 관련된 기능을 제공한다.
 */
interface MemberFinder {
    fun find(memberId: Long): Member
}