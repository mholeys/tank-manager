package uk.co.mholeys.android.tankmanager.model.typeconverters;

import androidx.room.TypeConverter;

import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;

public class DateTimeTypeConverter {

    private DateTimeFormatter formatter = DateTimeFormatter.ISO_OFFSET_DATE_TIME;

    @TypeConverter
    public OffsetDateTime toOffsetDateTime(String value) {
        return OffsetDateTime.from(formatter.parse(value));
    }

    @TypeConverter
    public String fromOffsetDateTime(OffsetDateTime dateTime) {
        return formatter.format(dateTime);
    }

}
