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

    Given("가입 완료된 회원이 주어지고,") {
        val newMember = Member("tjdvy963@naver.com", "howudong", "asdfsfd")
        newMember.activate()

        When("해당 회원이 탈퇴했을 때,") {
            newMember.deActivated()

            Then("해당 멤버의 상태는 DEACTIVATED가 되어야 한다.") {
                newMember.status shouldBe MemberStatus.DEACTIVATED
            }
        }
    }

    Given("회원이 주어지고,") {
        val newMember = Member("tjdvy963@naver.com", "howudong", "asdfsfd")

        When("가입 완료되지 않은 상태에서 탈퇴하려고 하면") {
            Then("IllegalStateException이 발생해야 한다") {
                shouldThrow<IllegalStateException> { newMember.deActivated() }
            }
        }
        When("이미 탈퇴한 상태에서 또 탈퇴하려고 하면,") {
            newMember.activate()
            newMember.deActivated()

            Then("IllegalStateException가 발생해야 한다") {
                shouldThrow<IllegalStateException> { newMember.deActivated() }
            }
        }
    }
})
