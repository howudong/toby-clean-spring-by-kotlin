package tobyspring.splearn.domain

import io.kotest.assertions.throwables.shouldThrow
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
    Given("회원이 생성되고,") {
        val newMember = Member("tjdvy963@naver.com", "howudong", "asdfsfd")
        When("처음 가입 완료 될때") {
            newMember.activate()
            Then("멤버 상태는 ACTIVE 상태가 되어야 한다") {
                newMember.status shouldBe MemberStatus.ACTIVE
            }
        }
        When("동일 멤버가 두번째로 가입 완료를 시도하면") {
            Then("IllegalStateException이 발생해야한다.") {
                shouldThrow<IllegalStateException> { newMember.activate() }
            }
        }
    }
})
