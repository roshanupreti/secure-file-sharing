package org.example.config;

import static org.junit.jupiter.api.Assertions.assertTrue;

import javax.sql.DataSource;

import org.jooq.impl.DefaultDSLContext;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {JooqConfig.class})
@ExtendWith(SpringExtension.class)
class JooqConfigTest {

    @MockBean
    private DataSource dataSource;

    @Autowired
    private JooqConfig jooqConfig;

    /**
     * Method under test: {@link JooqConfig#getDSLContext()}
     */
    @Test
    void get_dsl_context() {
        // Arrange, Act and Assert
        assertTrue(jooqConfig.getDSLContext() instanceof DefaultDSLContext);
    }
}
