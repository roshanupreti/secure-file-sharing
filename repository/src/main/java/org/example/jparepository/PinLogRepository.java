package org.example.jparepository;

import org.example.entity.PinLog;
import org.springframework.data.jpa.repository.support.JpaRepositoryImplementation;
import org.springframework.stereotype.Repository;

@Repository
public interface PinLogRepository extends JpaRepositoryImplementation<PinLog, String> {
}
