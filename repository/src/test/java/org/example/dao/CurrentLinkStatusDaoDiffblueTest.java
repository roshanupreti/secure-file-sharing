package org.example.dao;

import java.util.Optional;

import org.jooq.Record;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {CurrentLinkStatusDao.class})
@ExtendWith(SpringExtension.class)
class CurrentLinkStatusDaoDiffblueTest {
    @Autowired
    private CurrentLinkStatusDao currentLinkStatusDao;

    /**
     * Method under test: {@link CurrentLinkStatusDao#getLinkStatusForId(String)}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testGetLinkStatusForId() {
        // TODO: Complete this test.
        //   Diffblue AI was unable to find a test

        // Arrange
        // TODO: Populate arranged inputs
        String id = "";

        // Act
        Optional<Record> actualLinkStatusForId = this.currentLinkStatusDao.getLinkStatusForId(id);

        // Assert
        // TODO: Add assertions on result
    }
}
