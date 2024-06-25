package com.kafka.producer.repo;

import com.kafka.producer.entity.RecordLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface RecordLogRepository extends JpaRepository<RecordLog, Long> {
}
