package uk.co.mholeys.android.tankmanager.model.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import java.time.OffsetDateTime;

@Entity(tableName = "readings",
        foreignKeys =
        @ForeignKey(
                entity = Tank.class,
                parentColumns = "tId",
                childColumns = "tankId",
                onDelete = ForeignKey.CASCADE
        ))
public class Readings {

    @PrimaryKey
    public int readingId;

    @ColumnInfo(name = "date_taken")
    public OffsetDateTime date;

    public float PH;
    public float GH;
    public float KH;
    public float O2;
    public float CO2;
    public float amonia;
    public float nitrite;
    public float nitrate;
    public float magnesium;
    public float phosphate;
    public float calcium;
    public float copper;
    public float salinity;
    public float iron;

    @ColumnInfo(name = "tankId")
    public int tankId;

}
