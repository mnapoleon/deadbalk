package org.napoleon.deadbalk.config

import io.r2dbc.postgresql.PostgresqlConnectionConfiguration
import io.r2dbc.postgresql.PostgresqlConnectionFactory
import io.r2dbc.spi.ConnectionFactory
import io.r2dbc.spi.ConnectionFactoryOptions
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean
import org.springframework.boot.autoconfigure.r2dbc.R2dbcProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.io.ClassPathResource
import org.springframework.data.r2dbc.config.AbstractR2dbcConfiguration
import org.springframework.r2dbc.connection.init.ConnectionFactoryInitializer
import org.springframework.r2dbc.connection.init.ResourceDatabasePopulator
import java.time.Duration

@Configuration
class DBDataConfig(
    private val r2dbcProperties: R2dbcProperties
): AbstractR2dbcConfiguration() {
    /*@Bean
    fun initializeTable(@Qualifier("connectionFactory") connectionFactory: ConnectionFactory): ConnectionFactoryInitializer {
        val initializer = ConnectionFactoryInitializer()
        initializer.setConnectionFactory(connectionFactory)
        initializer.setDatabasePopulator(ResourceDatabasePopulator(ClassPathResource("migrations/V1__init.sql")))
        return initializer
    }*/

    @Bean
    @ConditionalOnMissingBean
    override fun connectionFactory(): ConnectionFactory {
        return PostgresqlConnectionFactory(
            PostgresqlConnectionConfiguration.builder()
                .addPropertiesFromUrl(r2dbcProperties.url)
                .username(r2dbcProperties.username)
                .password(r2dbcProperties.password)
                .schema(r2dbcProperties.properties["schema"])
                .build()
        )
    }

    private fun PostgresqlConnectionConfiguration.Builder.addPropertiesFromUrl(url: String) = apply {
        val factoryOptions = ConnectionFactoryOptions.parse(url)
        host(factoryOptions.getRequiredValue(ConnectionFactoryOptions.HOST) as String)
        database(factoryOptions.getValue(ConnectionFactoryOptions.DATABASE) as String)
        //connectTimeout(factoryOptions.getValue(ConnectionFactoryOptions.CONNECT_TIMEOUT) as Duration)
        if (factoryOptions.getValue(ConnectionFactoryOptions.SSL) == true) enableSsl()
        port(factoryOptions.getRequiredValue(ConnectionFactoryOptions.PORT) as Int)
    }
}