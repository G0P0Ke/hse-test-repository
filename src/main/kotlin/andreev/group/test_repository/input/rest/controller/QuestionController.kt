package andreev.group.test_repository.input.rest.controller

import andreev.group.test_repository.core.service.QuestionService
import andreev.group.test_repository.dto.QuestionResponse
import andreev.group.test_repository.dto.UpdateQuestionRequest
import andreev.group.test_repository.input.rest.converter.TestConverter
import io.swagger.v3.oas.annotations.Operation
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import javax.validation.Valid

@RestController
@RequestMapping("/v1")
class QuestionController(
    private val questionService: QuestionService,
    private val testConverter: TestConverter
) {

    @PatchMapping("/question/{id}")
    @Operation(summary = "Обновить вопрос. Добавить ответы или/и изменить текст")
    fun updateQuestion(
        @PathVariable id: Int,
        @RequestBody @Valid updateQuestionRequest: UpdateQuestionRequest
    ): ResponseEntity<QuestionResponse> {
        val result = questionService.updateQuestion(id, updateQuestionRequest)
        return ResponseEntity.ok(testConverter.toQuestionResponse(result))
    }

    @DeleteMapping("/question/{id}")
    fun deleteQuestion(@PathVariable id: Int): ResponseEntity<String> {
        questionService.deleteQuestion(id)
        return ResponseEntity.ok("Question with id: $id was successfully deleted")
    }
}