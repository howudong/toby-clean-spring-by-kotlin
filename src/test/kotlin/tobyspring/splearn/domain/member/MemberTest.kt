package tobyspring.splearn.domain.member

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe


class MemberTest : BehaviorSpec() {
    private lateinit var member: Member
    private lateinit var passwordEncoder: PasswordEncoder

    init {
        beforeContainer { testCase ->
            if (testCase.descriptor.depth() == 1) {
                passwordEncoder = MemberFixture.createPasswordEncoder()
                member = Member.register(
                    MemberFixture.createMemberRegisterRequest(),
                    passwordEncoder = passwordEncoder
                )
            }
        }

        Given("멤버가")
        {
            When("처음 생성되면") {
                Then("PENDING으로 생성되어야 한다.") {
                    member.status() shouldBe MemberStatus.PENDING

                }
                Then("memberDetail의 registerdAt이 존재해야한다.") {
                    member.memberDetail.registeredAt shouldNotBe null
                }
            }
        }

        Given("회원이 생성되고,")
        {
            When("처음 가입 완료 될때") {
                member.activate()
                Then("멤버 상태는 ACTIVE 상태가 되어야 한다") {

                    member.status() shouldBe MemberStatus.ACTIVE
                }
                Then("MemberDetail의 ActivatedAt은 null이 아니어야 한다") {
                    member.memberDetail.activatedAt shouldNotBe null
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
                    member.status() shouldBe MemberStatus.DEACTIVATED
                }
                Then("해당 멤버의 MemberDetail의 deactivatedAt은 null이 아니어야 한다.") {
                    member.memberDetail.deactivatedAt shouldNotBe null
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
        Given("맞는 비밀번호가 주어졌을 때") {
            val password = "secret"

            When("회원의 비밀번호와 비교한다면") {
                val result = member.verifyPassword(password, passwordEncoder)
                Then("true를 반환한다.") {
                    result shouldBe true
                }
            }

        }
        Given("틀린 비밀번호가 주어졌을 때") {
            val password = "secret12"

            When("회원의 비밀번호와 비교한다면") {
                val result = member.verifyPassword(password, passwordEncoder)
                Then("false를 반환한다.") {
                    result shouldBe false
                }
            }
        }

        Given("howudong 회원이 주어지고") {
            When("아이디를 toby로 변경한다면") {
                member.changeNickname("toby")
                Then("nickname이 toby로 변경되어야 한다") {
                    member.nickname() shouldBe "toby"
                }
            }
        }
        Given("새로운 비밀번호가 주어지고,") {
            val password = "secret12"

            When("회원의 비밀번호를 변경하면") {
                member.changePassword(password, passwordEncoder)

                Then("변경한 패스워드와 비밀번호가 일치해야한다") {
                    member.verifyPassword(password, passwordEncoder) shouldBe true
                }
            }
        }
        Given("회원이 주어졌을 때") {
            When("초기 상태는 Pending 이므로") {
                Then("isActive가 false여야 한다.") {
                    member.isActive() shouldBe false
                }
            }

            When("가입 완료를 했다면") {
                member.activate()
                Then("isActive는 true여야 한다.") {
                    member.isActive() shouldBe true
                }
            }

            When("탈퇴했다면") {
                member.activate()
                member.deActivated()
                Then("isActive는 false여야 한다.") {
                    member.isActive() shouldBe false
                }
            }
        }
        Given("잘못된 이메일이 주어졌을 때") {
            val email = "dsfsfsfsdfs"

            When("이를 이용해서 새로운 회원을 만들면") {
                Then("테스트는 예외를 발생시켜야 한다.") {
                    shouldThrow<IllegalArgumentException> {
                        Member.register(
                            MemberFixture.createMemberRegisterRequest(email),
                            passwordEncoder
                        )
                    }
                }
            }
        }
    }
}

