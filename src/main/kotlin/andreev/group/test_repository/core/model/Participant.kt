package andreev.group.test_repository.core.model

import com.fasterxml.jackson.annotation.JsonIgnore
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import javax.persistence.CascadeType
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.EntityListeners
import javax.persistence.FetchType
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.OneToMany
import javax.persistence.Table

@Entity
@Table(name = "participant")
@EntityListeners(AuditingEntityListener::class)
data class Participant(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    val id: Int? = null,

    @Column(name = "user_id", unique = true)
    val userId: Int,

    @Column(name = "email", unique = true)
    val email: String,

    @JsonIgnore
    @OneToMany(mappedBy = "participant", fetch = FetchType.LAZY, cascade = [(CascadeType.ALL)], orphanRemoval = true)
    var answers: MutableSet<Answer> = mutableSetOf(),

    @JsonIgnore
    @OneToMany(mappedBy = "participant", fetch = FetchType.LAZY)
    val tests: Set<ParticipantTestAgent>? = null,
) {
    override fun toString(): String {
        return "Participant(userId: $userId, email: $email)"
    }

    override fun hashCode(): Int {
        return id.hashCode()
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false
        other as Participant
        return email == other.email && userId == other.userId
    }
}
