package tobyspring.splearn.application.required

import tobyspring.splearn.domain.Email

/**
 * 이메일을 전송하는 역할을 담당한다.
 */
interface EmailSender {
    fun send(email: Email, subject: String, body: String)
}