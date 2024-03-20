package andreev.group.test_repository.core.model

import com.fasterxml.jackson.annotation.JsonIgnore
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.util.Date
import javax.persistence.CascadeType
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.EntityListeners
import javax.persistence.EnumType
import javax.persistence.Enumerated
import javax.persistence.FetchType
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.OneToMany
import javax.persistence.Table


@Entity
@Table(name = "test")
@EntityListeners(AuditingEntityListener::class)
data class Test(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    val id: Int,

    @Column(name = "test_date")
    val date: Date,

    @Column(name = "test_name", unique = true)
    val name: String,

    @Enumerated(value = EnumType.STRING)
    @Column(name = "level")
    val level: TestLevel,

    @Column(name = "tag")
    val tag: String? = null,

    @OneToMany(mappedBy = "test", fetch = FetchType.LAZY, cascade = [(CascadeType.ALL)], orphanRemoval = true)
    val questions: MutableSet<Question> = mutableSetOf(),

    @JsonIgnore
    @OneToMany(mappedBy = "test", fetch = FetchType.LAZY)
    val users: Set<ParticipantTestAgent>? = null,
) {
    override fun hashCode(): Int {
        return id.hashCode()
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false
        other as Test
        return id == other.id
    }
}

enum class TestLevel {
    EASY,
    NORMAL,
    HARD
}
