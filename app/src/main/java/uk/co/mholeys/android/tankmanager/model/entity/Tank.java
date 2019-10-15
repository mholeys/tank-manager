package uk.co.mholeys.android.tankmanager.model.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import uk.co.mholeys.android.tankmanager.model.ETankType;

@Entity
public class Tank {

    @PrimaryKey
    private int tId;

    @ColumnInfo(name = "name")
    public String name;

    @ColumnInfo(name = "size")
    public String size;

    @ColumnInfo(name = "units")
    public String units;

    /***
     * E.G Saltwater/Fresh/Tropical
     */
    @ColumnInfo(name = "type")
    public ETankType type;

    public int getId() {
        return tId;
    }
}
