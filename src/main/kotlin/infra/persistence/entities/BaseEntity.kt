package infra.persistence.entities

import jakarta.persistence.*
import java.time.LocalDateTime
import java.time.ZoneId
import kotlinx.datetime.*

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

fun LocalDateTime?.toKotlinDateTime(): kotlinx.datetime.LocalDateTime? {
    if (this == null) {
        return null
    }
    val instant = this.atZone(ZoneId.systemDefault()).toInstant()
    val kotlinInstant = kotlinx.datetime.Instant.fromEpochMilliseconds(instant.toEpochMilli())
    return kotlinInstant.toLocalDateTime(TimeZone.currentSystemDefault())
}
