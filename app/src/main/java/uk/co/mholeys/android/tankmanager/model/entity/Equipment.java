package uk.co.mholeys.android.tankmanager.model.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(tableName = "equipment",
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
public class Equipment {

    @PrimaryKey(autoGenerate = true)
    public long equipmentId;

    @ColumnInfo(name = "name")
    public String name;

    @ColumnInfo(name = "type")
    public String type;

    @ColumnInfo(name = "tankId")
    public long tankId;

}
