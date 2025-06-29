package tobyspring.splearn.domain

class Member(
    val email: String,
    val nickname: String,
    var passwordHash: String,
) {
    var status: MemberStatus = MemberStatus.PENDING
        private set

    fun activate() {
        check(status == MemberStatus.PENDING) { "PENDING 상태가 아닙니다" }
        status = MemberStatus.ACTIVE
    }
}
