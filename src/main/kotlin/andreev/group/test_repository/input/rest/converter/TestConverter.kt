package andreev.group.test_repository.input.rest.converter

import andreev.group.test_repository.core.model.Answer
import andreev.group.test_repository.core.model.Question
import andreev.group.test_repository.core.model.Test
import andreev.group.test_repository.dto.AnswerResponse
import andreev.group.test_repository.dto.QuestionResponse
import andreev.group.test_repository.dto.TestResponse
import org.springframework.stereotype.Component

@Component
class TestConverter {

    fun toTestResponse(test: Test) = with(test) {
        TestResponse(
            id = id,
            name = name,
            questions = questions.map { toQuestionResponse(question = it) }.toSet(),
            level = level,
            tag = tag,
        )
    }

    fun toQuestionResponse(question: Question) = with(question) {
        QuestionResponse(
            id = id,
            text = text,
            answers = answers.map { toAnswerResponse(answer = it) }.toSet()
        )
    }

    private fun toAnswerResponse(answer: Answer) = with(answer) {
        AnswerResponse(
            id = id,
            text = text,
            isCorrect = correct
        )
    }
}