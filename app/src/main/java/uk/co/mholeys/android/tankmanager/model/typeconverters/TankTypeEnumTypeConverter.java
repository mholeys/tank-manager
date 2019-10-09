package uk.co.mholeys.android.tankmanager.model.typeconverters;

import androidx.room.TypeConverter;

import uk.co.mholeys.android.tankmanager.model.EMaintenanceType;
import uk.co.mholeys.android.tankmanager.model.ETankType;

public class TankTypeEnumTypeConverter {

    @TypeConverter
    public ETankType toETankType(String value) {
        return ETankType.fromString(value);
    }

    @TypeConverter
    public String fromETankType(ETankType type) {
        return type.toString();
    }

}
