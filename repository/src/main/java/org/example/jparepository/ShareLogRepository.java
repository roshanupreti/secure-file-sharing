package org.example.jparepository;


import org.example.entity.ShareLog;
import org.springframework.data.jpa.repository.support.JpaRepositoryImplementation;
import org.springframework.stereotype.Repository;

@Repository
public interface ShareLogRepository extends JpaRepositoryImplementation<ShareLog, String> {
    // Custom query methods if needed
}

