package io.snowflake.spring

import org.springframework.boot.context.properties.ConfigurationProperties

/**
 * Configuration properties for Snowflake generator initialization.
 */
@ConfigurationProperties(prefix = "snowflake")
data class SnowflakeProperties(
    val generator: SnowflakeGeneratorProperties = SnowflakeGeneratorProperties(),
)

data class SnowflakeGeneratorProperties(
    val dataCenterId: Long = 1,
    val nodeId: Long = 1,
)
