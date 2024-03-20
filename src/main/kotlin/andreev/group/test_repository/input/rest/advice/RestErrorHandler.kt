package andreev.group.test_repository.input.rest.advice

import andreev.group.test_repository.AnswerNotFoundException
import andreev.group.test_repository.QuestionNotFoundException
import andreev.group.test_repository.TestAlreadyExistsException
import andreev.group.test_repository.TestNotFoundException
import andreev.group.test_repository.UserAlreadyExistsException
import andreev.group.test_repository.UserNotFoundException
import andreev.group.test_repository.core.enum.ErrorCode
import andreev.group.test_repository.dto.ErrorDescription
import andreev.group.test_repository.dto.ErrorResponse
import mu.KLogging
import mu.KotlinLogging
import org.hibernate.validator.internal.engine.path.PathImpl
import org.springframework.context.MessageSource
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.context.request.WebRequest
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler
import java.text.MessageFormat
import java.util.Locale
import javax.validation.ConstraintViolationException

@ControllerAdvice
@ResponseBody
class RestErrorHandler(val exceptionMessageSource: MessageSource) : ResponseEntityExceptionHandler() {
    private companion object : KLogging() {
        val log = KotlinLogging.logger {} //to avoid name clash with `logger` from ResponseEntityExceptionHandler
        val DEFAULT_HEADERS = HttpHeaders().apply {
            contentType = MediaType.APPLICATION_JSON
        }
    }

    override fun handleMethodArgumentNotValid(
        ex: MethodArgumentNotValidException,
        headers: HttpHeaders,
        status: HttpStatus,
        request: WebRequest
    ): ResponseEntity<Any> {
        val errors = ex.bindingResult
            .fieldErrors.map {
                ErrorDescription("Field ${it.field} ${it.defaultMessage}")
            }
        return response(errors, HttpStatus.BAD_REQUEST)
    }


    @ExceptionHandler(value = [ConstraintViolationException::class])
    fun handle(e: ConstraintViolationException): ResponseEntity<Any> {
        val errors = e.constraintViolations?.map {
            val propertyPath: PathImpl? = it.propertyPath as? PathImpl
            buildErrorDto(ErrorCode.INVALID_FIELD_VALUE, propertyPath?.leafNode?.asString(), it.message)
        }.orEmpty()
        return response(HttpStatus.BAD_REQUEST, *errors.toTypedArray())
    }

    @ExceptionHandler(Throwable::class)
    fun handle(e: Throwable): ResponseEntity<Any> {
        log.error(e.message, e)
        return response(
            listOf(ErrorDescription(e.message ?: "No exception message was provided")),
            HttpStatus.INTERNAL_SERVER_ERROR
        )
    }

    @ExceptionHandler(value = [TestNotFoundException::class])
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    fun handle(e: TestNotFoundException): ErrorResponse {
        log.info(e) { e.message }
        val errorCode = ErrorCode.TEST_NOT_FOUND
        val errorMessage = MessageFormat.format(errorCode.errorMessage, e.testId)
        return ErrorResponse(listOf(ErrorDescription(errorMessage ?: "No exception message was provided")))
    }

    @ExceptionHandler(value = [UserNotFoundException::class])
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    fun handle(e: UserNotFoundException): ErrorResponse {
        log.info(e) { e.message }
        val errorCode = ErrorCode.USER_NOT_FOUND
        val errorMessage = MessageFormat.format(errorCode.errorMessage, e.userId)
        return ErrorResponse(listOf(ErrorDescription(errorMessage ?: "No exception message was provided")))
    }

    @ExceptionHandler(value = [QuestionNotFoundException::class])
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    fun handle(e: QuestionNotFoundException): ErrorResponse {
        log.info(e) { e.message }
        val errorCode = ErrorCode.QUESTION_NOT_FOUND
        val errorMessage = MessageFormat.format(errorCode.errorMessage, e.questionId)
        return ErrorResponse(listOf(ErrorDescription(errorMessage ?: "No exception message was provided")))
    }

    @ExceptionHandler(value = [AnswerNotFoundException::class])
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    fun handle(e: AnswerNotFoundException): ErrorResponse {
        log.info(e) { e.message }
        val errorCode = ErrorCode.ANSWER_NOT_FOUND
        val errorMessage = MessageFormat.format(errorCode.errorMessage, e.answerId)
        return ErrorResponse(listOf(ErrorDescription(errorMessage ?: "No exception message was provided")))
    }

    @ExceptionHandler(value = [TestAlreadyExistsException::class])
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    fun handle(e: TestAlreadyExistsException): ErrorResponse {
        log.error(e.message, e)
        val errorCode = ErrorCode.TEST_EXIST
        val errorMessage = MessageFormat.format(errorCode.errorMessage)
        return ErrorResponse(listOf(ErrorDescription(errorMessage ?: "No exception message was provided")))
    }

    @ExceptionHandler(value = [UserAlreadyExistsException::class])
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    fun handle(e: UserAlreadyExistsException): ErrorResponse {
        log.error(e.message, e)
        val errorCode = ErrorCode.USER_EXIST
        val errorMessage = MessageFormat.format(errorCode.errorMessage)
        return ErrorResponse(listOf(ErrorDescription(errorMessage ?: "No exception message was provided")))
    }

    private fun buildErrorDto(error: ErrorCode, vararg args: String?): ErrorDescription {
        return ErrorDescription(
            exceptionMessageSource.getMessage(error.errorMessage, args, Locale.getDefault())
        )
    }

    private fun response(status: HttpStatus, vararg errors: ErrorDescription): ResponseEntity<Any> {
        val errorsList = errors.toList()
        log.info { errorsList }
        return ResponseEntity(ErrorResponse(errorsList), DEFAULT_HEADERS, status)
    }

    private fun response(
        errors: List<ErrorDescription>,
        status: HttpStatus,
    ): ResponseEntity<Any> {
        return ResponseEntity(ErrorResponse(errors), DEFAULT_HEADERS, status)
    }

}