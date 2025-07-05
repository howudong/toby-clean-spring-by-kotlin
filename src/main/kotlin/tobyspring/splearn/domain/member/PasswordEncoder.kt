package tobyspring.splearn.domain.member

import org.springframework.stereotype.Component

@Component
interface PasswordEncoder {
    fun encode(password: String): String
    fun matches(password: String, passwordHash: String): Boolean
}