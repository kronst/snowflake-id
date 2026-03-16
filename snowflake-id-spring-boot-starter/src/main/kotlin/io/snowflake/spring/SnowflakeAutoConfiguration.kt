package io.snowflake.spring

import io.snowflake.core.SnowflakeGenerator
import org.springframework.boot.autoconfigure.AutoConfiguration
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Bean

/**
 * Autoconfigures Snowflake beans for Spring Boot applications.
 */
@AutoConfiguration
@EnableConfigurationProperties(SnowflakeProperties::class)
class SnowflakeAutoConfiguration {
    @Bean
    @ConditionalOnMissingBean
    fun snowflakeGenerator(properties: SnowflakeProperties): SnowflakeGenerator =
        SnowflakeGenerator(
            dataCenterId = properties.generator.dataCenterId,
            nodeId = properties.generator.nodeId,
        )

    @Bean
    @ConditionalOnMissingBean
    fun snowflakeInitializer(snowflakeGenerator: SnowflakeGenerator): SnowflakeInitializer =
        SnowflakeInitializer(snowflakeGenerator = snowflakeGenerator)
}
