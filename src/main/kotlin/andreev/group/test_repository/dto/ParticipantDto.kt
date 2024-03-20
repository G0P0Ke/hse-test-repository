package andreev.group.test_repository.dto

import javax.validation.constraints.NotNull
import javax.validation.constraints.Size

data class ParticipantResponse(
    val userId: Int,
    val email: String,
    val tests: Set<ParticipantTestResponse>? = emptySet()
)

data class ParticipantTestResponse(
    val testId: Int,
    val name: String,
    val result: Int? = null
)

data class CreateParticipantRequest(
    @field:[NotNull Size(min=1, max=255)]
    val email: String,
    @field:[NotNull]
    val userId: Int,
)

data class ParticipantTestRequest(
    @field:[NotNull]
    val testId: Int,
    val result: Int? = null,
)