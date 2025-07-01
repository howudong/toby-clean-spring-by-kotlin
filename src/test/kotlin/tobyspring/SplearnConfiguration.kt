package tobyspring

import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.Bean
import tobyspring.splearn.application.required.EmailSender
import tobyspring.splearn.domain.Email
import tobyspring.splearn.domain.MemberFixture
import tobyspring.splearn.domain.PasswordEncoder

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