package andreev.group.test_repository

import javax.persistence.EntityNotFoundException

class TestNotFoundException(
    val testId: String,
    override val cause: Throwable? = null,
) : EntityNotFoundException() {
    override val message: String
        get() = "Test with id $testId not found"
}

class UserNotFoundException(
    val userId: String,
    override val cause: Throwable? = null,
) : EntityNotFoundException() {
    override val message: String
        get() = "User with id $userId not found "
}

class QuestionNotFoundException(
    val questionId: String,
    override val cause: Throwable? = null,
) : EntityNotFoundException() {
    override val message: String
        get() = "Question with id $questionId not found"
}

class AnswerNotFoundException(
    val answerId: String,
    override val cause: Throwable? = null,
) : EntityNotFoundException() {
    override val message: String
        get() = "Answer with id $answerId not found"
}


class TestAlreadyExistsException(
    override val message: String?
) : RuntimeException()

class UserAlreadyExistsException(
    override val message: String?
) : RuntimeException()