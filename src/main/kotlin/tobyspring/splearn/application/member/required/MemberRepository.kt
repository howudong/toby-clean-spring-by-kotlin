package tobyspring.splearn.application.member.required

import org.springframework.data.repository.Repository
import tobyspring.splearn.domain.member.Member
import tobyspring.splearn.domain.shared.Email
import java.util.Optional

interface MemberRepository : Repository<Member, Long> {
    fun save(member: Member): Member
    fun findByEmail(email: Email): List<Member>
    fun findById(id: Long): Optional<Member>
}