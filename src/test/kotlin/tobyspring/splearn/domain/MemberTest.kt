package tobyspring.splearn.domain

import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe


class MemberTest : BehaviorSpec({
    Given("멤버가") {
        When("처음 생성되면") {
            val newMember = Member("tjdvy963@naver.com", "howudong", "asdfsfd")
            Then("PENDING으로 생성되어야 한다.") {
                newMember.status shouldBe MemberStatus.PENDING

            }
        }
    }
})
