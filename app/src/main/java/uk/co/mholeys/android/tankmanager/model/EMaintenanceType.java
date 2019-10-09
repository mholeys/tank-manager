package uk.co.mholeys.android.tankmanager.model;

public enum EMaintenanceType {

    OTHER,
    WATER_CHANGE,
    FILTER_CLEAN,
    FILTER_REPLACE_MEDIA,
    FILTER_REPLACE_BIO,
    FILTER_REPLACE_CARBON,
    MOTOR_REPLACE_SHAFT,
    MOTOR_REPLACE_IMPELLER,
    MOTOR_REPLACE_SEAL,
    MOTOR_CLEAN_SHAFT,
    MOTOR_CLEAN_IMPELLER;


    public static EMaintenanceType fromString(String s) {
        for (EMaintenanceType e : values()) {
            if (e.toString().equalsIgnoreCase(s)) {
                return e;
            }
        }
        return EMaintenanceType.OTHER;
    }

}
