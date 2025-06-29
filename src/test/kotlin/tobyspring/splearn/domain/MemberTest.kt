package tobyspring.splearn.domain

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe


class MemberTest : BehaviorSpec() {
    private lateinit var member: Member

    init {
        beforeContainer { testCase ->
            if (testCase.descriptor.depth() == 1) {
                member = Member.create(
                    "tjdvy953@naver.com",
                    "howudong",
                    "secret",
                    object : PasswordEncoder {
                        override fun encode(password: String): String = password.uppercase()

                        override fun matches(password: String, passwordHash: String): Boolean =
                            password.uppercase() == passwordHash
                    })
            }
        }

        Given("멤버가")
        {
            When("처음 생성되면") {
                Then("PENDING으로 생성되어야 한다.") {
                    member.status shouldBe MemberStatus.PENDING

                }
            }
        }

        Given("회원이 생성되고,")
        {
            When("처음 가입 완료 될때") {
                member.activate()
                Then("멤버 상태는 ACTIVE 상태가 되어야 한다") {

                    member.status shouldBe MemberStatus.ACTIVE
                }
            }
        }
        Given("회원이 ") {
            When("동일 멤버가 두번째로 가입 완료를 시도하면") {
                member.activate()
                Then("IllegalStateException이 발생해야한다.") {
                    shouldThrow<IllegalStateException> { member.activate() }
                }
            }
        }

        Given("가입 완료된 회원이 주어지고,")
        {
            When("해당 회원이 탈퇴했을 때,") {
                member.activate() // 원래는 GIVEN이 맞는데 코드 동작을 위해 어쩔 수 없이 WHEN 절로 옮김
                member.deActivated()

                Then("해당 멤버의 상태는 DEACTIVATED가 되어야 한다.") {
                    member.status shouldBe MemberStatus.DEACTIVATED
                }
            }
        }

        Given("회원이 주어지고,")
        {
            When("가입 완료되지 않은 상태에서 탈퇴하려고 하면") {
                Then("IllegalStateException이 발생해야 한다") {
                    shouldThrow<IllegalStateException> { member.deActivated() }
                }
            }
            When("이미 탈퇴한 상태에서 또 탈퇴하려고 하면,") {
                member.activate()
                member.deActivated()

                Then("IllegalStateException가 발생해야 한다") {
                    shouldThrow<IllegalStateException> { member.deActivated() }
                }
            }
        }
    }
}

