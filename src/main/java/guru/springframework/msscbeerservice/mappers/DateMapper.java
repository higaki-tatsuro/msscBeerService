package guru.springframework.msscbeerservice.mappers;

import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;

@Component
public class DateMapper {

    public OffsetDateTime asOffsetDateTime(Timestamp ts){
        if(ts == null){
            return null;
        }

        LocalDateTime ldt = ts.toLocalDateTime();

        return OffsetDateTime.of(ldt.getYear(), ldt.getMonthValue(), ldt.getDayOfMonth(), ldt.getHour(),ldt.getMinute(),
                ldt.getSecond(), ldt.getNano(), ZoneOffset.of("+09:00"));
    }

    public Timestamp asTimestamp(OffsetDateTime offsetDateTime){
        if(offsetDateTime == null){
            return null;
        }

        return Timestamp.valueOf(offsetDateTime.atZoneSameInstant(ZoneOffset.of("+09:00")).toLocalDateTime());
    }

}
