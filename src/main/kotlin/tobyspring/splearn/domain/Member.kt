package tobyspring.splearn.domain

import jakarta.persistence.Embedded
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import org.hibernate.annotations.NaturalId
import org.hibernate.annotations.NaturalIdCache

@Entity
@NaturalIdCache
class Member private constructor(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,
    @Embedded
    @NaturalId
    val email: Email,
    @Enumerated(EnumType.STRING)
    private var status: MemberStatus = MemberStatus.PENDING,
    private var passwordHash: String,
    private var nickname: String
) {

    companion object {
        fun register(memberRegisterRequest: MemberRegisterRequest, passwordEncoder: PasswordEncoder) =
            Member(
                email = Email(memberRegisterRequest.email),
                passwordHash = passwordEncoder.encode(memberRegisterRequest.password),
                nickname = memberRegisterRequest.nickname
            )
    }

    fun activate() {
        check(status == MemberStatus.PENDING) { "PENDING 상태가 아닙니다" }

        status = MemberStatus.ACTIVE
    }

    fun deActivated() {
        check(status == MemberStatus.ACTIVE) { "ACTIVE 상태가 아닙니다" }

        status = MemberStatus.DEACTIVATED
    }

    fun verifyPassword(password: String, passwordEncoder: PasswordEncoder): Boolean =
        passwordEncoder.matches(password, passwordHash)

    fun changeNickname(nickname: String) {
        this.nickname = nickname
    }

    fun changePassword(password: String, passwordEncoder: PasswordEncoder) {
        this.passwordHash = passwordEncoder.encode(password)
    }

    fun isActive() = status == MemberStatus.ACTIVE

    fun nickname() = nickname
    fun status() = status
}
