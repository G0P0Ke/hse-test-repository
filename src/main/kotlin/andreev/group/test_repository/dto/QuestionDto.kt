package andreev.group.test_repository.dto

import com.fasterxml.jackson.annotation.JsonSetter
import com.fasterxml.jackson.annotation.Nulls
import javax.validation.Valid
import javax.validation.constraints.NotNull
import javax.validation.constraints.Size

data class CreateQuestionRequest(
    @field:[NotNull Size(min=1, max=1000)]
    val text: String,

    @field:[Valid JsonSetter(nulls = Nulls.AS_EMPTY)]
    val answers: Set<CreateAnswerRequest> = emptySet(),
)

data class QuestionResponse(
    val id: Int,
    val text: String,
    val answers: Set<AnswerResponse>? = null,
)

data class UpdateQuestionRequest(
    @field:[JsonSetter(nulls = Nulls.AS_EMPTY)]
    val text: String? = null,
    @field:[Valid JsonSetter(nulls = Nulls.AS_EMPTY)]
    val answers: Set<CreateAnswerRequest> = emptySet(),
)