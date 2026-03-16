package io.snowflake.core

import java.util.concurrent.locks.ReentrantLock

/**
 * A thread-safe implementation of the Snowflake ID generation algorithm.
 * This generator produces 64-bit unique IDs based on the current timestamp, data center ID, node ID, and a sequence number.
 */
class SnowflakeGenerator(
    private val dataCenterId: Long,
    private val nodeId: Long,
) {
    private val lock = ReentrantLock()
    private var sequence = 0L
    private var lastTimestamp = -1L

    init {
        require(dataCenterId in 0..MAX_DATA_CENTER_ID) { "Data center ID must be between 0 and $MAX_DATA_CENTER_ID" }
        require(nodeId in 0..MAX_NODE_ID) { "Node ID must be between 0 and $MAX_NODE_ID" }
    }

    /**
     * Generates the next Snowflake ID in a thread-safe manner.
     */
    fun nextId(): Long {
        try {
            lock.lock()

            var timestamp = currentMillis()
            if (timestamp < lastTimestamp) {
                throw IllegalStateException("Clock is moving backwards. Refusing to generate ID for ${lastTimestamp - timestamp} ms")
            }

            if (timestamp == lastTimestamp) {
                sequence = (sequence + 1) and SEQUENCE_MASK
                if (sequence == 0L) {
                    timestamp = tilNextMillis(lastTimestamp)
                }
            } else {
                sequence = 0L
            }

            lastTimestamp = timestamp

            return ((timestamp - EPOCH) shl TIMESTAMP_SHIFT) or
                (dataCenterId shl DATA_CENTER_ID_SHIFT) or
                (nodeId shl NODE_ID_SHIFT) or
                sequence
        } finally {
            lock.unlock()
        }
    }

    private fun tilNextMillis(lastTimestamp: Long): Long {
        var timestamp = currentMillis()
        while (timestamp <= lastTimestamp) {
            timestamp = currentMillis()
        }
        return timestamp
    }

    private fun currentMillis() = System.currentTimeMillis()

    companion object {
        private const val EPOCH = 1288834974657L // Nov 04 2010 01:42:54 UTC
        private const val DATA_CENTER_ID_BITS = 5
        private const val MAX_DATA_CENTER_ID = (-1L shl DATA_CENTER_ID_BITS).inv()
        private const val NODE_ID_BITS = 5
        private const val MAX_NODE_ID = (-1L shl NODE_ID_BITS).inv()
        private const val SEQUENCE_BITS = 12
        private const val NODE_ID_SHIFT = SEQUENCE_BITS
        private const val DATA_CENTER_ID_SHIFT = SEQUENCE_BITS + NODE_ID_BITS
        private const val TIMESTAMP_SHIFT = SEQUENCE_BITS + NODE_ID_BITS + DATA_CENTER_ID_BITS
        private const val SEQUENCE_MASK = (-1L shl SEQUENCE_BITS).inv()
    }
}
