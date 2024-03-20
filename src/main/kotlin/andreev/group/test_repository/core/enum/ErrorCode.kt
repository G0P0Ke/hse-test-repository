package andreev.group.test_repository.core.enum

enum class ErrorCode(val errorMessage: String) {
    INVALID_FIELD_VALUE("invalid.field.value"),
    TEST_NOT_FOUND("Test for id {0} not found"),
    USER_NOT_FOUND("User for id {0} not found "),
    QUESTION_NOT_FOUND("Question for id {0} not found"),
    ANSWER_NOT_FOUND("Answer for id {0} not found"),
    TEST_EXIST("Test already exists"),
    USER_EXIST("User already exists"),
}