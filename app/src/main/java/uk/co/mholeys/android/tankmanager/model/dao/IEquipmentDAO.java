package uk.co.mholeys.android.tankmanager.model.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import uk.co.mholeys.android.tankmanager.model.entity.Equipment;

@Dao
public interface IEquipmentDAO {

    @Insert
    long[] insertMany(Equipment... equipment);

    @Insert
    long[] insertMany(List<Equipment> equipment);

    @Insert
    long insert(Equipment equipment);

    @Update
    void update(Equipment equipment);

    @Delete
    void delete(Equipment equipment);

    @Delete
    void deleteMany(Equipment... equipment);

    @Delete
    void deleteMany(List<Equipment> equipment);

    @Query("SELECT * FROM equipment WHERE equipmentId = :id;")
    LiveData<Equipment> get(long id);

    @Query("SELECT * FROM equipment ORDER BY equipmentId;")
    LiveData<List<Equipment>> getAll();

    @Query("SELECT * FROM equipment WHERE tankId=:tankId")
    LiveData<List<Equipment>> getEquipmentOfTank(final long tankId);

}
