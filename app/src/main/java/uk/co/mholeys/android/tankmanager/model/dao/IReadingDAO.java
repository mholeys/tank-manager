package uk.co.mholeys.android.tankmanager.model.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import uk.co.mholeys.android.tankmanager.model.entity.Equipment;
import uk.co.mholeys.android.tankmanager.model.entity.Readings;

@Dao
public interface IReadingDAO {

    @Insert
    long[] insertMany(Readings... readings);

    @Insert
    long[] insertMany(List<Readings> readings);

    @Insert
    long insert(Readings readings);

    @Update
    void update(Readings readings);

    @Delete
    void delete(Readings readings);

    @Delete
    void deleteMany(Readings... readings);

    @Delete
    void deleteMany(List<Readings> readings);

    @Query("SELECT * FROM readings WHERE readingId = :id;")
    LiveData<Readings> get(long id);

    @Query("SELECT * FROM readings ORDER BY readingId;")
    LiveData<List<Readings>> getAll();

    @Query("SELECT * FROM readings WHERE tankId=:tankId")
    LiveData<List<Readings>> getReadingsOfTank(final int tankId);

}
