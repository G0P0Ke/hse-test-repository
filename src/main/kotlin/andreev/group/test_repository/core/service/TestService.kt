package andreev.group.test_repository.core.service

import andreev.group.test_repository.TestAlreadyExistsException
import andreev.group.test_repository.TestNotFoundException
import andreev.group.test_repository.core.model.Test
import andreev.group.test_repository.core.model.TestLevel
import andreev.group.test_repository.core.repository.TestRepository
import andreev.group.test_repository.dto.CreateTestRequest
import mu.KLogging
import org.springframework.stereotype.Service
import java.util.Date
import javax.transaction.Transactional

@Service
class TestService(
    private val testRepository: TestRepository,
    private val questionService: QuestionService,
) {

    companion object : KLogging()

    fun getTest(testId: Int): Test {
        logger.info { "Try to find test with testId: $testId" }
        val result = testRepository.findById(testId)
        if (result.isEmpty) {
            logger.info { "Test with testId: $testId not found" }
            throw TestNotFoundException(testId.toString())
        }
        logger.info { "Successfully get test with testId: $testId" }
        return result.get()
    }

    @Transactional
    fun createTest(createTestRequest: CreateTestRequest): Test {
        logger.info { "Try to create test with name: ${createTestRequest.name}" }
        val exist = testRepository.findByName(createTestRequest.name)
        if (exist.isPresent) {
            throw TestAlreadyExistsException("Test with name: ${createTestRequest.name} already exists")
        }

        val test = Test(
            id = 0, date = Date(), name = createTestRequest.name,
            level = TestLevel.valueOf(createTestRequest.level),
            tag = createTestRequest.tag
        )
        val savedTest = testRepository.save(test)

        if (createTestRequest.questions.isNotEmpty()) {
            savedTest.questions.addAll(
                questionService.createQuestions(
                    questionRequest = createTestRequest.questions,
                    test = savedTest
                ).toMutableSet()
            )
        }
        logger.info { "Successfully created test with name: ${createTestRequest.name}" }

        return savedTest
    }
}