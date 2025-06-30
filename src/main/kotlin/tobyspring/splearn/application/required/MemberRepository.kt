package tobyspring.splearn.application.required

import org.springframework.data.repository.Repository
import tobyspring.splearn.domain.Member

interface MemberRepository : Repository<Member, Long> {
    fun save(member: Member): Member
}