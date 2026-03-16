# snowflake-id

Lightweight Kotlin library for generating Snowflake IDs.

## Modules

- `snowflake-id-core` - core generator without framework dependencies
- `snowflake-id-spring-boot-starter` - Spring Boot auto-configuration

## Core Usage

Create a generator directly:

```kotlin
import io.snowflake.core.SnowflakeGenerator

val generator = SnowflakeGenerator(
    dataCenterId = 1,
    nodeId = 1,
)

val id = generator.nextId()
```

Use the global access point:

```kotlin
import io.snowflake.core.Snowflake
import io.snowflake.core.SnowflakeGenerator

Snowflake.init(
    SnowflakeGenerator(
        dataCenterId = 1,
        nodeId = 1,
    ),
)

val id = Snowflake.next()
```

## Spring Boot Usage

Add the starter dependency and configure the generator:

```yaml
snowflake:
  generator:
    data-center-id: 1
    node-id: 1
```

If no `SnowflakeGenerator` bean is defined, the starter creates one automatically and initializes `Snowflake`.

```kotlin
import io.snowflake.core.Snowflake
import io.snowflake.core.SnowflakeGenerator
import org.springframework.stereotype.Service

@Service
class OrderService(
    private val snowflakeGenerator: SnowflakeGenerator,
) {
    fun nextOrderId(): Long = snowflakeGenerator.nextId()

    fun nextGlobalOrderId(): Long = Snowflake.next()
}
```

You can override the auto-configured generator with your own bean:

```kotlin
import io.snowflake.core.SnowflakeGenerator
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class SnowflakeConfig {
    @Bean
    fun snowflakeGenerator(): SnowflakeGenerator =
        SnowflakeGenerator(
            dataCenterId = 2,
            nodeId = 3,
        )
}
```

## Build

```bash
./gradlew build
```
