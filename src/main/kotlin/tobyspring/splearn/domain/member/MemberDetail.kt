package tobyspring.splearn.domain.member

import jakarta.persistence.Entity
import tobyspring.splearn.domain.AbstractEntity
import java.time.LocalDateTime

@Entity
class MemberDetail private constructor(
    profile: String? = null,
    introduction: String? = null,
    val registeredAt: LocalDateTime,
    activatedAt: LocalDateTime? = null,
    deactivatedAt: LocalDateTime? = null,
) : AbstractEntity() {
    var profile: String? = profile
        protected set

    var introduction: String? = introduction
        protected set

    var activatedAt: LocalDateTime? = activatedAt
        protected set

    var deactivatedAt: LocalDateTime? = deactivatedAt
        protected set

    companion object {
        fun create() = MemberDetail(
            registeredAt = LocalDateTime.now()
        )
    }

    internal fun activate() {
        check(activatedAt == null) { "이미 acitvated가 등록된 상태입니다." }

        activatedAt = LocalDateTime.now()
    }

    internal fun deactivate() {
        check(deactivatedAt == null) { "이미 deactivate가 등록된 상태입니다." }

        deactivatedAt = LocalDateTime.now()
    }
}