package andreev.group.test_repository.core.repository

import andreev.group.test_repository.core.model.Test
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.Optional

@Repository
interface TestRepository: JpaRepository<Test, Int> {

    fun findByName(name: String): Optional<Test>
}