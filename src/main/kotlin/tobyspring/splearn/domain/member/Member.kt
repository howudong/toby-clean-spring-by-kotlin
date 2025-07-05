package tobyspring.splearn.domain.member

import jakarta.persistence.CascadeType
import jakarta.persistence.Column
import jakarta.persistence.Embedded
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.FetchType
import jakarta.persistence.OneToOne
import lombok.ToString
import org.hibernate.annotations.NaturalId
import org.hibernate.annotations.NaturalIdCache
import tobyspring.splearn.domain.AbstractEntity
import tobyspring.splearn.domain.shared.Email

@Entity
@NaturalIdCache
@ToString(callSuper = true)
class Member private constructor(
    @Embedded
    @NaturalId
    val email: Email,
    @Enumerated(EnumType.STRING)
    @Column(length = 100)
    private var status: MemberStatus = MemberStatus.PENDING,
    @Column(length = 200)
    private var passwordHash: String,
    @Column(length = 100)
    private var nickname: String,

    @OneToOne(fetch = FetchType.LAZY, cascade = [CascadeType.ALL])
    val memberDetail: MemberDetail,
) : AbstractEntity() {

    companion object {
        internal fun register(memberRegisterRequest: MemberRegisterRequest, passwordEncoder: PasswordEncoder) =
            Member(
                email = Email(memberRegisterRequest.email),
                passwordHash = passwordEncoder.encode(memberRegisterRequest.password),
                nickname = memberRegisterRequest.nickname,
                memberDetail = MemberDetail.create()
            )
    }

    fun activate() {
        check(status == MemberStatus.PENDING) { "PENDING 상태가 아닙니다" }

        status = MemberStatus.ACTIVE

        memberDetail.activate()
    }

    fun deActivated() {
        check(status == MemberStatus.ACTIVE) { "ACTIVE 상태가 아닙니다" }

        status = MemberStatus.DEACTIVATED

        memberDetail.deactivate()
    }

    fun updateInfo(infoUpdateRequest: MemberInfoUpdateRequest) {
        check(status == MemberStatus.ACTIVE) { "가입 완료된 상태에서만 변경할 수 있습니다." }

        this.nickname = infoUpdateRequest.nickname

        memberDetail.updateInfo(infoUpdateRequest)
    }

    fun verifyPassword(password: String, passwordEncoder: PasswordEncoder): Boolean =
        passwordEncoder.matches(password, passwordHash)
    

    fun changePassword(password: String, passwordEncoder: PasswordEncoder) {
        this.passwordHash = passwordEncoder.encode(password)
    }

    fun isActive() = status == MemberStatus.ACTIVE

    fun nickname() = nickname
    fun status() = status
}
