package io.snowflake.core

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.joinAll
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import java.util.concurrent.ConcurrentHashMap
import kotlin.test.assertEquals

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class SnowflakeTest {
    @BeforeAll
    fun setup() {
        val generator = SnowflakeGenerator(dataCenterId = 1, nodeId = 1)
        Snowflake.init(generator)
    }

    @Test
    fun `check only unique ids generate`() =
        runBlocking {
            val ids = ConcurrentHashMap.newKeySet<Long>()
            val jobs = arrayListOf<Job>()

            repeat(NUMBER_OF_COROUTINES) {
                val job =
                    launch(Dispatchers.Default) {
                        repeat(NUMBER_OF_IDS_PER_COROUTINE) {
                            ids.add(Snowflake.next())
                        }
                    }
                jobs.add(job)
            }

            jobs.joinAll()
            assertEquals(NUMBER_OF_COROUTINES * NUMBER_OF_IDS_PER_COROUTINE, ids.size)
        }

    companion object {
        private const val NUMBER_OF_COROUTINES = 1000
        private const val NUMBER_OF_IDS_PER_COROUTINE = 1000
    }
}
