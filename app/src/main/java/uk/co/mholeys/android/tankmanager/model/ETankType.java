package uk.co.mholeys.android.tankmanager.model;

import android.content.res.Resources;
import android.util.Log;

import uk.co.mholeys.android.tankmanager.R;

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

    public String toString(Resources resources) {
        int o = this.ordinal();
        String s = null;
        try {
            s = resources.getStringArray(R.array.tank_types_array)[o];
        } catch (ArrayIndexOutOfBoundsException e) {
            s = "Other";
        }
        return s;
    }

}
