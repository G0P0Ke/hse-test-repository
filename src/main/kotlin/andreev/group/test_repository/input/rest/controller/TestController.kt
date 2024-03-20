package andreev.group.test_repository.input.rest.controller

import andreev.group.test_repository.core.service.TestService
import andreev.group.test_repository.dto.CreateTestRequest
import andreev.group.test_repository.dto.TestResponse
import andreev.group.test_repository.input.rest.converter.TestConverter
import io.swagger.v3.oas.annotations.Operation
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import javax.validation.Valid

@RestController
@RequestMapping("/v1")
class TestController(
    private val testService: TestService,
    private val testConverter: TestConverter,
) {

    @GetMapping("/test/{testId}")
    @Operation(summary = "Получение теста по идентификатору")
    fun getTest(@PathVariable @Valid testId: Int): ResponseEntity<TestResponse> {
        val result = testService.getTest(testId)
        return ResponseEntity.ok(testConverter.toTestResponse(result))
    }

    @PostMapping("/test")
    @Operation(summary = "Создание теста")
    fun createTest(@RequestBody @Valid createTestRequest: CreateTestRequest): ResponseEntity<TestResponse> {
        val result = testService.createTest(createTestRequest)
        return ResponseEntity.ok(testConverter.toTestResponse(result))
    }

}