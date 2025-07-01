package tobyspring.splearn.application.required

import org.springframework.stereotype.Component
import tobyspring.splearn.domain.Email

/**
 * 이메일을 전송하는 역할을 담당한다.
 */
@Component
interface EmailSender {
    fun send(email: Email, subject: String, body: String)
}