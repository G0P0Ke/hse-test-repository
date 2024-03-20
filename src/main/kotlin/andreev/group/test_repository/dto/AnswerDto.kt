package andreev.group.test_repository.dto

import javax.validation.constraints.NotNull
import javax.validation.constraints.Size

data class CreateAnswerRequest(
    @field:[NotNull Size(min=1, max=1000)]
    val text: String,

    @field:[NotNull]
    val isCorrect: Boolean,
)

data class AnswerResponse(
    val id: Int,
    val text: String,
    val isCorrect: Boolean
)