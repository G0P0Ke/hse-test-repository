package andreev.group.test_repository.core.repository

import andreev.group.test_repository.core.model.Participant
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.Optional

@Repository
interface ParticipantRepository: JpaRepository<Participant, Int> {

    fun findByEmail(email: String) : Optional<Participant>

    fun findByUserId(id: Int): Optional<Participant>
}