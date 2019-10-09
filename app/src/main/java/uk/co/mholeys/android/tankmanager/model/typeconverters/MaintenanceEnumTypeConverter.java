package uk.co.mholeys.android.tankmanager.model.typeconverters;

import androidx.room.TypeConverter;

import java.time.OffsetDateTime;

import uk.co.mholeys.android.tankmanager.model.EMaintenanceType;

public class MaintenanceEnumTypeConverter {

    @TypeConverter
    public EMaintenanceType toEMaintenanceType(String value) {
        return EMaintenanceType.fromString(value);
    }

    @TypeConverter
    public String fromEMaintenanceType(EMaintenanceType type) {
        return type.toString();
    }

}
