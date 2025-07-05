package tobyspring.splearn.domain.member

import jakarta.persistence.Column
import jakarta.persistence.Embeddable
import java.util.regex.Pattern

@Embeddable
data class Profile(
    @Column(name = "profile", length = 15) val address: String
) {
    companion object {
        private const val PROFILE_REGEX = "[a-z0-9]+"
    }

    init {
        if (Pattern.compile(PROFILE_REGEX).matcher(address).matches().not()) {
            throw IllegalArgumentException("프로필 주소 형식이 올바르지 않습니다. $address")
        }
        if (address.length > 15) {
            throw IllegalArgumentException("프로필 주소 형식이 올바르지 않습니다. $address")
        }
    }

    fun url() = "@$address"
}