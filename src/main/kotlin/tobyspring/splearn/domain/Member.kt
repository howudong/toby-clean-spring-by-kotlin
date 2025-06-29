package tobyspring.splearn.domain

class Member private constructor(
    val email: Email,
    var passwordHash: String,
) {

    companion object {
        fun create(email: String, nickname: String, password: String, passwordEncoder: PasswordEncoder): Member {
            val newMember = Member(
                Email(address = email),
                passwordEncoder.encode(password)
            )
            newMember.changeNickname(nickname)
            return newMember
        }
    }

    var nickname = "알수없는 사용자"
        private set

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

    fun verifyPassword(password: String, passwordEncoder: PasswordEncoder): Boolean =
        passwordEncoder.matches(password, passwordHash)

    fun changeNickname(nickname: String) {
        this.nickname = nickname
    }

    fun changePassword(password: String, passwordEncoder: PasswordEncoder) {
        this.passwordHash = passwordEncoder.encode(password)
    }

    fun isActive() = status == MemberStatus.ACTIVE
}
