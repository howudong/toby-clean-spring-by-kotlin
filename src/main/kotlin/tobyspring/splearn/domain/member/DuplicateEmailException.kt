package tobyspring.splearn.domain.member

class DuplicateEmailException(override val message: String) : RuntimeException()