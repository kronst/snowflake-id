package io.snowflake.spring

import io.snowflake.core.SnowflakeGenerator
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.getBean
import org.springframework.boot.autoconfigure.AutoConfigurations
import org.springframework.boot.test.context.runner.ApplicationContextRunner
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

class SnowflakeAutoConfigurationTest {
    private val contextRunner =
        ApplicationContextRunner()
            .withConfiguration(AutoConfigurations.of(SnowflakeAutoConfiguration::class.java))

    @Test
    fun `creates default snowflake beans`() {
        contextRunner.run { context ->
            assertThat(context).hasSingleBean(SnowflakeGenerator::class.java)
            assertThat(context).hasSingleBean(SnowflakeInitializer::class.java)

            val properties: SnowflakeProperties = context.getBean<SnowflakeProperties>()
            assertThat(properties.generator.dataCenterId).isEqualTo(1L)
            assertThat(properties.generator.nodeId).isEqualTo(1L)
        }
    }

    @Test
    fun `binds generator properties from configuration`() {
        contextRunner
            .withPropertyValues(
                "snowflake.generator.data-center-id=3",
                "snowflake.generator.node-id=7",
            ).run { context ->
                val properties: SnowflakeProperties = context.getBean<SnowflakeProperties>()

                assertThat(properties.generator.dataCenterId).isEqualTo(3L)
                assertThat(properties.generator.nodeId).isEqualTo(7L)
            }
    }

    @Test
    fun `backs off when custom generator bean is present`() {
        contextRunner
            .withUserConfiguration(CustomGeneratorConfiguration::class.java)
            .run { context ->
                assertThat(context).hasSingleBean(SnowflakeGenerator::class.java)
                assertThat(context.getBean<SnowflakeGenerator>()).isSameAs(CustomGeneratorConfiguration.generator)
                assertThat(context).hasSingleBean(SnowflakeInitializer::class.java)
            }
    }

    @Configuration(proxyBeanMethods = false)
    private class CustomGeneratorConfiguration {
        @Bean
        fun customSnowflakeGenerator(): SnowflakeGenerator = generator

        companion object {
            val generator = SnowflakeGenerator(dataCenterId = 5, nodeId = 9)
        }
    }
}
