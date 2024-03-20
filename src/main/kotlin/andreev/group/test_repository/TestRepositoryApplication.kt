package andreev.group.test_repository

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.data.jpa.repository.config.EnableJpaAuditing

@SpringBootApplication
@EnableJpaAuditing
class TestRepositoryApplication

fun main(args: Array<String>) {
    runApplication<TestRepositoryApplication>(*args)
}
