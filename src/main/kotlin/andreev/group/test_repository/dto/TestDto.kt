package andreev.group.test_repository.dto

import andreev.group.test_repository.core.model.TestLevel
import andreev.group.test_repository.core.service.EnumValue
import com.fasterxml.jackson.annotation.JsonSetter
import com.fasterxml.jackson.annotation.Nulls
import javax.validation.Valid
import javax.validation.constraints.NotNull
import javax.validation.constraints.Size


data class CreateTestRequest(
    @field:[NotNull Size(min=1, max=255)]
    val name: String,

    @field:[Valid JsonSetter(nulls = Nulls.AS_EMPTY)]
    val questions: Set<CreateQuestionRequest> = emptySet(),

    @field:EnumValue(TestLevel::class)
    val level: String,

    @field:[Size(max=40)]
    val tag: String? = null
)

data class TestResponse(
    val id: Int,
    val name: String,
    val questions: Set<QuestionResponse>,
    val level: TestLevel,
    val tag: String?
)