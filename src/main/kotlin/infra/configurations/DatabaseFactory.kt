package infra.configurations

import com.ralphmorales.logger
import io.ktor.server.config.*
import jakarta.persistence.*

// DatabaseFactory is a singleton object that manages the configuration to the database using JPA
// (Java Persistence API).
// It creates an EntityManagerFactory that is used to create EntityManager instances for database
// operations.
// The EntityManagerFactory is created using the configuration properties defined in the
// application.yaml file.
object DatabaseFactory {
        private val config = ApplicationConfig("application.yaml")

        private val entityManagerFactory: EntityManagerFactory by lazy {
                Class.forName("org.postgresql.Driver")

                var dbUrl = System.getenv("DATABASE_URL") ?: System.getProperty("DATABASE_URL")
                dbUrl = dbUrl ?: config.property("ktor.database.url").getString()

                logger.info("Database URL: $dbUrl")

                Persistence.createEntityManagerFactory(
                        "ctc-persistence-unit",
                        mapOf(
                                "jakarta.persistence.jdbc.url" to dbUrl,
                                "jakarta.persistence.jdbc.driver" to
                                        config.property("ktor.database.driver").getString(),
                                "jakarta.persistence.jdbc.user" to
                                        config.property("ktor.database.user").getString(),
                                "jakarta.persistence.jdbc.password" to
                                        config.property("ktor.database.password").getString(),
                                "hibernate.hbm2ddl.auto" to
                                        config.property("ktor.database.hibernate.hbm2ddl")
                                                .getString(),
                                "hibernate.show_sql" to
                                        config.property("ktor.database.hibernate.show_sql")
                                                .getString()
                        )
                )
        }

        fun getEntityManager(): EntityManager = entityManagerFactory.createEntityManager()
}
