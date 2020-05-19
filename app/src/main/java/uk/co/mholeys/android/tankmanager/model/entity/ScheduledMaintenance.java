package uk.co.mholeys.android.tankmanager.model.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import java.time.OffsetDateTime;

import uk.co.mholeys.android.tankmanager.model.EMaintenanceType;

@Entity(tableName = "scheduled_maintenance",
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
public class ScheduledMaintenance {

    @PrimaryKey(autoGenerate = true)
    public long scheduleId;

    @ColumnInfo(name = "date_scheduled")
    public OffsetDateTime date;

    public EMaintenanceType type;

    public String notes;

    @ColumnInfo(name = "tankId")
    public long tankId;

}