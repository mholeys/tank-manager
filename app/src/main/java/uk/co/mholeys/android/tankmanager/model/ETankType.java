package uk.co.mholeys.android.tankmanager.model;

public enum ETankType {

    MARINE,
    COLD_WATER,
    POND,
    TROPICAL,
    BRACKISH,
    OTHER;

    public static ETankType fromString(String s) {
        for (ETankType e : values()) {
            if (e.toString().equalsIgnoreCase(s)) {
                return e;
            }
        }
        return ETankType.OTHER;
    }

}
