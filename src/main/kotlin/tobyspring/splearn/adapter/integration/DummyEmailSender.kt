package tobyspring.splearn.adapter.integration

import org.springframework.stereotype.Component
import tobyspring.splearn.application.member.required.EmailSender
import tobyspring.splearn.domain.shared.Email

@Component
class DummyEmailSender : EmailSender {
    override fun send(email: Email, subject: String, body: String) {
        println("Dummy email sender to $email subject: $subject, body: $body")
    }
}