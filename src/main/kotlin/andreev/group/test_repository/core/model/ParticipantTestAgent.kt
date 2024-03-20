package andreev.group.test_repository.core.model

import java.io.Serializable
import javax.persistence.Column
import javax.persistence.Embeddable
import javax.persistence.EmbeddedId
import javax.persistence.Entity
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne
import javax.persistence.MapsId
import javax.persistence.Table

@Entity
@Table(name = "participant_test_agent")
data class ParticipantTestAgent(
    @EmbeddedId
    val id: ParticipantTestAgentId,

    @ManyToOne
    @MapsId("idParticipant")
    @JoinColumn(name = "participant_id", insertable = false, updatable = false)
    val participant: Participant,

    @ManyToOne
    @MapsId("idTest")
    @JoinColumn(name = "test_id", insertable = false, updatable = false)
    val test: Test,

    @Column(name = "result", nullable = true)
    val result: Int? = null,
)

@Embeddable
data class ParticipantTestAgentId(
    @Column(name = "participant_id")
    val idParticipant: Int,
    @Column(name = "test_id")
    val idTest: Int
) : Serializable