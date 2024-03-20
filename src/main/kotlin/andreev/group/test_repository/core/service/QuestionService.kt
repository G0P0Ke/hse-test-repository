package andreev.group.test_repository.core.service

import andreev.group.test_repository.QuestionNotFoundException
import andreev.group.test_repository.core.model.Question
import andreev.group.test_repository.core.model.Test
import andreev.group.test_repository.core.repository.QuestionRepository
import andreev.group.test_repository.dto.CreateQuestionRequest
import andreev.group.test_repository.dto.UpdateQuestionRequest
import mu.KLogging
import org.springframework.stereotype.Service
import javax.transaction.Transactional

@Service
class QuestionService(
    private val questionRepository: QuestionRepository,
    private val answerService: AnswerService,
) {

    companion object : KLogging()

    fun getQuestion(id: Int): Question {
        logger.info { "Try to find question with id: $id" }
        val result = questionRepository.findById(id)
        if (result.isEmpty) {
            logger.info { "Question with id: $id not found" }
            throw QuestionNotFoundException(id.toString())
        }
        logger.info { "Successfully get question with id: $id" }
        return result.get()
    }

    @Transactional
    fun deleteQuestion(id: Int) {
        logger.info { "Try to delete question with id: $id" }
        val result = questionRepository.findById(id)
        if (result.isEmpty) {
            logger.info { "Question with id: $id not found" }
            throw QuestionNotFoundException(id.toString())
        }
        questionRepository.deleteById(id)
        logger.info { "Successfully delete question with id: $id" }
    }

    @Transactional
    fun createQuestions(questionRequest: Collection<CreateQuestionRequest>, test: Test): MutableList<Question> {
        val questions = questionRequest.map { question ->
            val newQuestion = Question(id = 0, text = question.text, test = test)
            logger.info { "Try to create question with text: ${question.text}" }
            val savedQuestion = questionRepository.save(newQuestion)
            if (question.answers.isNotEmpty()) {
                savedQuestion.answers.addAll(
                    answerService.createAnswers(
                        answerRequest = question.answers,
                        question = savedQuestion
                    )
                )
            }
            savedQuestion
        }

        return questions.toMutableList()
    }

    @Transactional
    fun updateQuestion(id: Int, request: UpdateQuestionRequest): Question {
        val question = getQuestion(id)
        logger.info { "Try to update question with text: ${question.text}" }
        val savedQuestion = with(request) {
            question.text = if (text.isNullOrBlank()) question.text else this.text
            question.answers.addAll(
                answerService.createAnswers(
                    answerRequest = answers,
                    question = question
                )
            )
            questionRepository.save(question)
        }
        logger.info { "Successfully update question! Updated question: $savedQuestion" }
        return savedQuestion
    }
}