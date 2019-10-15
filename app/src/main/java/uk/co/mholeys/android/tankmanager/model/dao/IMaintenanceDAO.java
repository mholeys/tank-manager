package uk.co.mholeys.android.tankmanager.model.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import uk.co.mholeys.android.tankmanager.model.entity.Maintenance;

@Dao
public interface IMaintenanceDAO {

    @Insert
    long[] insertMany(Maintenance... maintenance);

    @Insert
    long[] insertMany(List<Maintenance> maintenance);

    @Insert
    long insert(Maintenance maintenance);

    @Update
    void update(Maintenance maintenance);

    @Delete
    void delete(Maintenance maintenance);

    @Delete
    void deleteMany(Maintenance... maintenance);

    @Delete
    void deleteMany(List<Maintenance> maintenance);

    @Query("SELECT * FROM maintenance WHERE maintenanceId = :id;")
    LiveData<Maintenance> get(long id);

    @Query("SELECT * FROM maintenance ORDER BY maintenanceId;")
    LiveData<List<Maintenance>> getAll();

    @Query("SELECT * FROM maintenance WHERE tankId=:tankId")
    LiveData<List<Maintenance>> getMaintenanceDoneOnTank(final int tankId);

}
