package tobyspring.splearn.domain.member

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe

class ProfileTest : StringSpec({
    "올바른 프로필 주소 테스트" {
        Profile("success")
        Profile("0111111")
        Profile("1")
    }
    "올바르지 않은 주소 테스트" {
        shouldThrow<IllegalArgumentException> { Profile("toLongtoLongtoLongtoLongtoLongtoLongtoLong") }
    }
    "URL 테스트" {
        Profile("howudong")
            .let { it.url() shouldBe "@howudong" }
    }
})
