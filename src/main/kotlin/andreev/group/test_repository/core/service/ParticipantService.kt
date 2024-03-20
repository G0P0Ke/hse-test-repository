package andreev.group.test_repository.core.service

import andreev.group.test_repository.UserAlreadyExistsException
import andreev.group.test_repository.UserNotFoundException
import andreev.group.test_repository.core.model.Participant
import andreev.group.test_repository.core.model.ParticipantTestAgent
import andreev.group.test_repository.core.model.ParticipantTestAgentId
import andreev.group.test_repository.core.repository.ParticipantRepository
import andreev.group.test_repository.core.repository.ParticipantTestAgentRepository
import andreev.group.test_repository.dto.CreateParticipantRequest
import andreev.group.test_repository.dto.ParticipantTestRequest
import mu.KLogging
import org.springframework.stereotype.Service
import javax.transaction.Transactional

@Service
class ParticipantService(
    private val participantRepository: ParticipantRepository,
    private val participantTestAgentRepository: ParticipantTestAgentRepository,
    private val testService: TestService
) {

    companion object : KLogging()

    fun getParticipant(id: Int): Participant {
        logger.info { "Try to user test with userId: $id" }
        val result = participantRepository.findByUserId(id)
        if (result.isPresent) {
            logger.info { "Successfully get user with id: $id" }
            return result.get()
        }
        logger.info { "User with id: $id not found" }
        throw UserNotFoundException(id.toString())
    }

    @Transactional
    fun createParticipant(request: CreateParticipantRequest): Participant {
        logger.info { "Try to create user with email: ${request.email}" }
        val exist = participantRepository.findByEmail(request.email)
        if (exist.isPresent) {
            throw UserAlreadyExistsException("User with email: ${request.email} already exists")
        }
        val participant = Participant(userId = request.userId, email = request.email)
        logger.info { "Successfully created user with email: ${participant.email}" }
        return participantRepository.save(participant)
    }

    @Transactional
    fun addTestToParticipant(userId: Int, request: ParticipantTestRequest): Participant {
        val test = testService.getTest(request.testId)
        val participant = getParticipant(userId)
        val participantTestAgent = ParticipantTestAgent(
            id = ParticipantTestAgentId(participant.id!!, test.id),
            participant = participant,
            test = test,
            result = request.result
        )
        participantTestAgentRepository.save(participantTestAgent)

        return getParticipant(userId)
    }
}