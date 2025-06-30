package tobyspring.splearn.domain

import jakarta.persistence.Embeddable
import java.util.regex.Pattern

@Embeddable
data class Email(val address: String) {
    companion object {
        private const val EMAIL_REGEX = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$"
    }

    init {
        if (Pattern.compile(EMAIL_REGEX).matcher(address).matches().not()) {
            throw IllegalArgumentException("이메일 형식이 올바르지 않습니다. $address")
        }
    }
}