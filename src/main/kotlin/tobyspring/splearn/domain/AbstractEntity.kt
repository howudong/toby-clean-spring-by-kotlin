package tobyspring.splearn.domain

import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.MappedSuperclass

@MappedSuperclass
abstract class AbstractEntity protected constructor(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null
)