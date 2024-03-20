package andreev.group.test_repository.core.model

import com.fasterxml.jackson.annotation.JsonIgnore
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.EntityListeners
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne
import javax.persistence.Table

@Entity
@Table(name = "answer")
@EntityListeners(AuditingEntityListener::class)
data class Answer(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    val id: Int,

    @Column(name = "text")
    val text: String,

    @Column(name = "is_correct")
    val correct: Boolean,

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "question_id", nullable = false)
    val question: Question,

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "participant_id", nullable = true)
    val participant: Participant? = null,
) {
    override fun toString(): String {
        return "Answer(text=$text, is_correct=$correct)"
    }

    override fun hashCode(): Int {
        return id.hashCode()
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false
        other as Answer
        return id == other.id
    }
}
