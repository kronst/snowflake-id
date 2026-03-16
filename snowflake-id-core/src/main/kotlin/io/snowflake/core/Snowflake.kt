package io.snowflake.core

import java.util.concurrent.atomic.AtomicReference

/**
 * Provides global access to a shared Snowflake ID generator instance.
 */
object Snowflake {
    private val generator = AtomicReference<SnowflakeGenerator?>()

    /**
     * Initializes the shared generator once.
     * Subsequent calls to this method will have no effect, ensuring that the generator is only set once.
     */
    @JvmStatic
    fun init(generator: SnowflakeGenerator) {
        this.generator.compareAndSet(null, generator)
    }

    /**
     * Returns the next generated Snowflake ID.
     *
     * @throws IllegalStateException if the generator has not been initialized.
     */
    @JvmStatic
    fun next(): Long = generator.get()?.nextId() ?: throw IllegalStateException("Snowflake has not been initialized.")
}
