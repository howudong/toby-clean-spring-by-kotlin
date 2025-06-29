package tobyspring.splearn.domain

class Member(
    val email : String,
    val nickname : String,
    val passwordHash : String,
    val status: MemberStatus = MemberStatus.PENDING
)
