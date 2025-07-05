package tobyspring

import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.Bean
import tobyspring.splearn.application.member.required.EmailSender
import tobyspring.splearn.domain.member.MemberFixture
import tobyspring.splearn.domain.member.PasswordEncoder
import tobyspring.splearn.domain.shared.Email

@TestConfiguration
class SplearnConfiguration {

    @Bean
    fun emailSender(): EmailSender = object : EmailSender {
        override fun send(email: Email, subject: String, body: String) {
            println("Sending email $email to $subject")
        }
    }

    @Bean
    fun passwordEncoder(): PasswordEncoder = MemberFixture.createPasswordEncoder()
}