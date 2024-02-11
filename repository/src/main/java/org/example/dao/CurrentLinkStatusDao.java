package org.example.dao;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jooq.DSLContext;
import org.jooq.Record;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static org.jooq.generated.tables.AccessLog.ACCESS_LOG;
import static org.jooq.generated.tables.PinLog.PIN_LOG;
import static org.jooq.generated.tables.ShareLog.SHARE_LOG;


@Slf4j
@AllArgsConstructor
@Service
public class CurrentLinkStatusDao {

    private DSLContext ctx;

    /**
     * @param id {@link String} resource id.
     * @return {@link Optional} of type {@link Record}, as returned by a 'SELECT' query from 'share_log' with left-joins to
     * 'access_log' and 'pin_log'
     */
    public Optional<Record> getLinkStatusForId(final String id) {
        return ctx
                .select()
                .from(SHARE_LOG)
                .leftJoin(ACCESS_LOG).on(ACCESS_LOG.ID.eq(SHARE_LOG.ID))
                .leftJoin(PIN_LOG).on(PIN_LOG.ID.eq(SHARE_LOG.ID))
                .where(SHARE_LOG.ID.eq(id))
                .fetchOptional();
    }
}
