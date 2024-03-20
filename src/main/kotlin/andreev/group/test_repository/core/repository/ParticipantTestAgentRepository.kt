package andreev.group.test_repository.core.repository

import andreev.group.test_repository.core.model.ParticipantTestAgent
import andreev.group.test_repository.core.model.ParticipantTestAgentId
import org.springframework.data.jpa.repository.JpaRepository

interface ParticipantTestAgentRepository: JpaRepository<ParticipantTestAgent, ParticipantTestAgentId> {
}