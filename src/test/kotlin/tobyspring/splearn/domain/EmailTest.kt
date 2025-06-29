package tobyspring.splearn.domain

import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.equals.shouldBeEqual

class EmailTest : BehaviorSpec({
    Given("두 이메일이 주어졌을 때") {
        val email1 = Email(address = "tjdvy1@naver.com")
        val email2 = Email(address = "tjdvy1@naver.com")
        When("두 이메일이 같다면") {
            Then("같아야 한다.") {
                email1 shouldBeEqual email2
            }
        }
    }
})
