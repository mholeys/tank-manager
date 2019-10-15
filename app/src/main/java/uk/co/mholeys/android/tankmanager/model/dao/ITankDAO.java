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
    long[] insertMany(Tank... tanks);

    @Insert
    long[] insertMany(List<Tank> tanks);

    @Insert
    long insert(Tank tank);

    @Update
    void update(Tank tank);

    @Delete
    void delete(Tank tank);

    @Delete
    void deleteMany(Tank... tanks);

    @Delete
    void deleteMany(List<Tank> tanks);

    @Query("SELECT * FROM Tank WHERE tId = :id;")
    LiveData<Tank> get(int id);

    @Query("SELECT * FROM Tank ORDER BY tId;")
    LiveData<List<Tank>> getAll();


}
