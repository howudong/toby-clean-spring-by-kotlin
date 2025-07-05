package tobyspring.splearn.domain.member

class MemberFixture {
    companion object {
        fun createPasswordEncoder() = object : PasswordEncoder {
            override fun encode(password: String): String = password.uppercase()

            override fun matches(password: String, passwordHash: String): Boolean =
                password.uppercase() == passwordHash
        }

        fun createMemberRegisterRequest(email: String = "tjdvy963@naver.com"): MemberRegisterRequest =
            MemberRegisterRequest(
                email = email,
                nickname = "howudong",
                password = "secret",
            )
    }
}