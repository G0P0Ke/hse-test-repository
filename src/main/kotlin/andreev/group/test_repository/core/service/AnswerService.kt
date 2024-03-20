package andreev.group.test_repository.core.service

import andreev.group.test_repository.AnswerNotFoundException
import andreev.group.test_repository.QuestionNotFoundException
import andreev.group.test_repository.core.model.Answer
import andreev.group.test_repository.core.model.Question
import andreev.group.test_repository.core.repository.AnswerRepository
import andreev.group.test_repository.dto.CreateAnswerRequest
import mu.KLogging
import org.springframework.stereotype.Service
import javax.transaction.Transactional

@Service
class AnswerService(
    private val answerRepository: AnswerRepository
) {

    companion object : KLogging()

    fun getAnswer(id: Int): Answer {
        logger.info { "Try to find answer with id: $id" }
        val result = answerRepository.findById(id)
        if (result.isEmpty) {
            logger.info { "Answer with id: $id not found" }
            throw AnswerNotFoundException(id.toString())
        }
        logger.info { "Successfully get answer with id: $id" }
        return result.get()
    }

    @Transactional
    fun deleteAnswer(id: Int) {
        logger.info { "Try to delete answer with id: $id" }
        val result = answerRepository.findById(id)
        if (result.isEmpty) {
            logger.info { "Answer with id: $id not found" }
            throw AnswerNotFoundException(id.toString())
        }
        answerRepository.deleteById(id)
        logger.info { "Successfully delete answer with id: $id" }
    }

    @Transactional
    fun createAnswers(answerRequest: Collection<CreateAnswerRequest>, question: Question): MutableList<Answer> {
        val answers = answerRequest.map { answer ->
            val answer = Answer(id = 0, text = answer.text, correct = answer.isCorrect, question = question)
            logger.info { "Try to add answer: $answer to question: ${question.text}" }
            answer
        }

        return answerRepository.saveAll(answers)
    }

}