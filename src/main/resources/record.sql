CREATE TABLE kafka_record_status (
    record_id VARCHAR(255) PRIMARY KEY,  -- Unique identifier for the record
    kafka_topic VARCHAR(255) NOT NULL,   -- Name of the Kafka topic
    partition INT NOT NULL,              -- Kafka partition number
    offset BIGINT NOT NULL,              -- Offset of the record in the Kafka topic
    status VARCHAR(50) NOT NULL,         -- Status of the record (e.g., 'PUSHED', 'FAILED', 'RETRIED')
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,  -- Timestamp when the record was created
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,  -- Timestamp when the record was last updated
    error_message TEXT,                  -- Error message if the push failed
    metadata JSON,                       -- Additional metadata related to the record
    CONSTRAINT chk_status CHECK (status IN ('PUSHED', 'FAILED', 'RETRIED'))
);

-- Optional index to speed up queries based on status
CREATE INDEX idx_status ON kafka_record_status(status);
