package tobyspring.splearn.adapter.security

import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe

class SecurePasswordEncoderTest : BehaviorSpec() {
    val securePasswordEncoder = SecurePasswordEncoder()

    init {
        Given("암호화 한 비밀번호가 주어졌을 때") {
            val passwordHash = securePasswordEncoder.encode("secret")
            When("암호화한 비밀번호를 원래 비밀번호와 비교하면") {
                val result = securePasswordEncoder.matches("secret", passwordHash)

                Then("결과는 true가 나와야 한다") {
                    result shouldBe true
                }
            }
            When("암호환 비밀번호를 다른 비밀번호와 비교하면") {
                val result2 = securePasswordEncoder.matches("wrong", passwordHash)
                Then("결과는 False가 나와야 한다") {
                    result2 shouldBe false
                }
            }
        }
    }
}
