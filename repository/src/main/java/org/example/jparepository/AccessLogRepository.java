package org.example.jparepository;


import org.example.entity.AccessLog;
import org.springframework.data.jpa.repository.support.JpaRepositoryImplementation;
import org.springframework.stereotype.Repository;

@Repository
public interface AccessLogRepository extends JpaRepositoryImplementation<AccessLog, String> {
    // Custom query methods if needed
}
