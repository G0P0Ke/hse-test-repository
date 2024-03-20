package andreev.group.test_repository.input.rest.converter

import andreev.group.test_repository.core.model.Participant
import andreev.group.test_repository.core.model.ParticipantTestAgent
import andreev.group.test_repository.dto.ParticipantResponse
import andreev.group.test_repository.dto.ParticipantTestResponse
import org.springframework.stereotype.Component

@Component
class ParticipantConverter {

    fun toParticipantResponse(participant: Participant) = with(participant) {
        ParticipantResponse(
            userId = userId,
            email = email,
            tests = participant.tests?.map { toParticipantTestResponse(it) }?.toSet()
        )
    }

    fun toParticipantTestResponse(agent: ParticipantTestAgent) = with(agent) {
        ParticipantTestResponse(
            testId = test.id,
            name = test.name,
            result = result
        )
    }
}