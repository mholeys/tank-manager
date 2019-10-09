package uk.co.mholeys.android.tankmanager.model.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import uk.co.mholeys.android.tankmanager.model.entity.Tank;

@Dao
public interface ITankDAO {

    @Insert
    LiveData<Long[]> insertMany(Tank... tanks);

    @Insert
    LiveData<Long[]> insertMany(List<Tank> tanks);

    @Insert
    LiveData<Long> insert(Tank tank);

    @Update
    void update(Tank tank);

    @Delete
    void delete(Tank tank);

    @Delete
    void deleteMany(Tank... tanks);

    @Delete
    void deleteMany(List<Tank> tanks);

    @Query("SELECT * FROM Tank WHERE tankId = :id;")
    LiveData<Tank> get(long id);

    @Query("SELECT * FROM Tank ORDER BY tankId;")
    LiveData<List<Tank>> getAll();


}
