package uk.co.mholeys.android.tankmanager.model.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import java.time.OffsetDateTime;

import uk.co.mholeys.android.tankmanager.model.EMaintenanceType;

@Entity(tableName = "maintenance",
        foreignKeys =
        @ForeignKey(
                entity = Tank.class,
                parentColumns = "tId",
                childColumns = "tankId",
                onDelete = ForeignKey.CASCADE
        ),
        indices = {
               @Index(value = "tankId")
        })
public class Maintenance {

    @PrimaryKey(autoGenerate = true)
    public long maintenanceId;

    @ColumnInfo(name = "date_done")
    public OffsetDateTime date;

    public EMaintenanceType type;

    public String notes;

    @ColumnInfo(name = "tankId")
    public long tankId;

}
