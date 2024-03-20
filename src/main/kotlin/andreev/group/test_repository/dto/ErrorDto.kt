package andreev.group.test_repository.dto

data class ErrorResponse(val errors: Collection<ErrorDescription>)

data class ErrorDescription(val message: String)