package tobyspring.splearn.application

import org.springframework.stereotype.Service
import tobyspring.splearn.application.provided.MemberFinder
import tobyspring.splearn.application.required.MemberRepository
import tobyspring.splearn.domain.Member

@Service
class MemberQueryService(
    private val memberRepository: MemberRepository,
) : MemberFinder {
    override fun find(memberId: Long): Member = memberRepository
        .findById(memberId)
        .orElseThrow { throw IllegalArgumentException("존재하지 않는 회원 id 입니다. memberId : $memberId") }
}