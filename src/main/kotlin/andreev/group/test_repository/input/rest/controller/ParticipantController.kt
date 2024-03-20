package andreev.group.test_repository.input.rest.controller

import andreev.group.test_repository.core.service.ParticipantService
import andreev.group.test_repository.dto.CreateParticipantRequest
import andreev.group.test_repository.dto.ParticipantResponse
import andreev.group.test_repository.dto.ParticipantTestRequest
import andreev.group.test_repository.input.rest.converter.ParticipantConverter
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
class ParticipantController(
    private val participantService: ParticipantService,
    private val participantConverter: ParticipantConverter
) {

    @GetMapping("/user/{userId}")
    @Operation(summary = "Получение пользователя")
    fun getParticipant(@PathVariable @Valid userId: Int): ResponseEntity<ParticipantResponse> {
        val result = participantService.getParticipant(userId)
        return ResponseEntity.ok(participantConverter.toParticipantResponse(result))
    }

    @PostMapping("/user")
    @Operation(summary = "Добавление пользователя в репозиторий тестов")
    fun createParticipant(@RequestBody @Valid request: CreateParticipantRequest) : ResponseEntity<ParticipantResponse> {
        val result = participantService.createParticipant(request)
        return ResponseEntity.ok(participantConverter.toParticipantResponse(result))
    }

    @PostMapping("/user/{userId}/test")
    @Operation(summary = "Добавление прохождения теста пользователю")
    fun addTestToParticipant(
        @PathVariable @Valid userId: Int,
        @RequestBody @Valid request: ParticipantTestRequest
    ): ResponseEntity<ParticipantResponse> {
        val result = participantService.addTestToParticipant(userId, request)
        return ResponseEntity.ok(participantConverter.toParticipantResponse(result))
    }
}