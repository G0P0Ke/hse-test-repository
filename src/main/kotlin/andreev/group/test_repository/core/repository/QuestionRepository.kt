package andreev.group.test_repository.core.repository

import andreev.group.test_repository.core.model.Question
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface QuestionRepository: JpaRepository<Question, Int> {
}