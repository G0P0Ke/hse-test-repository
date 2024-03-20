package andreev.group.test_repository.core.model

import org.springframework.data.jpa.domain.support.AuditingEntityListener
import javax.persistence.CascadeType
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.EntityListeners
import javax.persistence.FetchType
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne
import javax.persistence.OneToMany
import javax.persistence.Table


@Entity
@Table(name = "question")
@EntityListeners(AuditingEntityListener::class)
data class Question(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    val id: Int,

    @Column(name = "text")
    var text: String,

    @ManyToOne
    @JoinColumn(name = "test_id", nullable = false)
    val test: Test,

    @OneToMany(mappedBy = "question", fetch = FetchType.LAZY, cascade = [(CascadeType.ALL)], orphanRemoval = true)
    var answers: MutableSet<Answer> = mutableSetOf(),
) {
    override fun toString(): String {
        return "Question(id=$id text=$text, test_name=${test.name})"
    }

    override fun hashCode(): Int {
        return id.hashCode()
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false
        other as Question
        return id == other.id
    }
}
