package org.example.config;

import lombok.AllArgsConstructor;
import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.jooq.conf.RenderNameCase;
import org.jooq.conf.Settings;
import org.jooq.impl.DSL;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;

@AllArgsConstructor
@Component
public class JooqConfig {

    private DataSource dataSource;

    @Bean
    public DSLContext getDSLContext() {
        return DSL.using(dataSource, SQLDialect.MARIADB, new Settings().withRenderNameCase(RenderNameCase.LOWER));
    }
}



