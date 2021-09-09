package com.nexstudio.msscbeerservice.web.mappers;

import java.sql.Timestamp;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;

import org.springframework.stereotype.Component;

@Component
public class DateMapper {
    public OffsetDateTime aOffsetDateTime(Timestamp ts) {
        return (ts != null)
            ? OffsetDateTime.of(
                ts.toLocalDateTime().getYear(), 
                ts.toLocalDateTime().getMonthValue(), 
                ts.toLocalDateTime().getDayOfMonth(), 
                ts.toLocalDateTime().getHour(), 
                ts.toLocalDateTime().getMinute(), 
                ts.toLocalDateTime().getSecond(), 
                ts.toLocalDateTime().getNano(), 
                ZoneOffset.UTC)
            : null;
    }

    public Timestamp aTimestamp(OffsetDateTime offsetDateTime) {
        return (offsetDateTime != null) 
            ? Timestamp.valueOf(offsetDateTime.atZoneSameInstant(ZoneOffset.UTC).toLocalDateTime()) 
            : null;
    }
}
