package uk.co.mholeys.android.tankmanager.model.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.RawQuery;
import androidx.room.Update;

import java.util.List;

import uk.co.mholeys.android.tankmanager.model.EMaintenanceType;
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

    @Query("SELECT * FROM maintenance ORDER BY date_done;")
    LiveData<List<Maintenance>> getAll();

    @Query("SELECT * FROM maintenance WHERE tankId=:tankId")
    LiveData<List<Maintenance>> getMaintenanceDoneOnTank(final long tankId);

    @Query("SELECT * FROM maintenance WHERE tankId=:tankId ORDER BY date_done ASC LIMIT 1")
    LiveData<Maintenance> getLastMaintenanceDoneOnTank(long tankId);

    @Query("SELECT * FROM maintenance WHERE tankId=:tankId AND type=:type ORDER BY date_done ASC LIMIT 1")
    LiveData<Maintenance> getNextTypeOnTank(long tankId, EMaintenanceType type);
}
