package infra.persistence.entities

import jakarta.persistence.*
import java.time.LocalDateTime

@MappedSuperclass
abstract class BaseEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) var id: Long? = null

    @Column(updatable = false) var createdAt: LocalDateTime = LocalDateTime.now()

    var updatedAt: LocalDateTime? = null

    @PreUpdate
    fun onUpdate() {
        updatedAt = LocalDateTime.now()
    }
}
