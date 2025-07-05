package tobyspring.splearn.domain.member

import jakarta.persistence.Entity
import tobyspring.splearn.domain.AbstractEntity
import java.time.LocalDateTime

@Entity
class MemberDetail private constructor(
    var profile: String? = null,
    var introduction: String? = null,
    val registeredAt: LocalDateTime,
    val activatedAt: LocalDateTime? = null,
    val deactivatedAt: LocalDateTime? = null,
) : AbstractEntity() {
    companion object {
        fun create() = MemberDetail(
            registeredAt = LocalDateTime.now()
        )
    }
}