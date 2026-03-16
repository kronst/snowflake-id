package io.snowflake.spring

import io.snowflake.core.Snowflake
import io.snowflake.core.SnowflakeGenerator
import org.springframework.beans.factory.InitializingBean

/**
 * Initializes the global Snowflake access point from the Spring context.
 */
class SnowflakeInitializer(
    private val snowflakeGenerator: SnowflakeGenerator,
) : InitializingBean {
    override fun afterPropertiesSet() {
        Snowflake.init(generator = snowflakeGenerator)
    }
}
