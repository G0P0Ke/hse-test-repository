package andreev.group.test_repository.input.rest.controller

import andreev.group.test_repository.core.service.AnswerService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/v1")
class AnswerController(
    private val answerService: AnswerService
) {

    @DeleteMapping("/answer/{id}")
    fun deleteAnswer(@PathVariable id: Int): ResponseEntity<String> {
        answerService.deleteAnswer(id)
        return ResponseEntity.ok("Answer with id: $id was successfully deleted")
    }
}