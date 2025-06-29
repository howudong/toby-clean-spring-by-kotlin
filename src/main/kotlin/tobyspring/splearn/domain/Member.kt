package tobyspring.splearn.domain

class Member private constructor(
    val email: String,
    val nickname: String,
    var passwordHash: String,
) {
    companion object {
        fun create(email: String, nickname: String, password: String, passwordEncoder: PasswordEncoder): Member {
            return Member(email, nickname, passwordEncoder.encode(password))
        }
    }

    var status: MemberStatus = MemberStatus.PENDING
        private set

    fun activate() {
        check(status == MemberStatus.PENDING) { "PENDING 상태가 아닙니다" }

        status = MemberStatus.ACTIVE
    }

    fun deActivated() {
        check(status == MemberStatus.ACTIVE) { "ACTIVE 상태가 아닙니다" }

        status = MemberStatus.DEACTIVATED
    }
}
