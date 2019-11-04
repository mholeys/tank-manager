package uk.co.mholeys.android.tankmanager.model.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import uk.co.mholeys.android.tankmanager.model.EMaintenanceType;
import uk.co.mholeys.android.tankmanager.model.entity.Maintenance;
import uk.co.mholeys.android.tankmanager.model.entity.ScheduledMaintenance;

@Dao
public interface IScheduledMaintenanceDAO {

    @Insert
    long[] insertMany(ScheduledMaintenance... scheduledMaintenance);

    @Insert
    long[] insertMany(List<ScheduledMaintenance> scheduledMaintenance);

    @Insert
    long insert(ScheduledMaintenance scheduledMaintenance);

    @Update
    void update(ScheduledMaintenance scheduledMaintenance);

    @Delete
    void delete(ScheduledMaintenance scheduledMaintenance);

    @Delete
    void deleteMany(ScheduledMaintenance... scheduledMaintenance);

    @Delete
    void deleteMany(List<ScheduledMaintenance> scheduledMaintenance);

    @Query("SELECT * FROM scheduled_maintenance WHERE scheduleId = :id;")
    LiveData<ScheduledMaintenance> get(long id);

    @Query("SELECT * FROM scheduled_maintenance ORDER BY date_scheduled;")
    LiveData<List<ScheduledMaintenance>> getAll();

    @Query("SELECT * FROM scheduled_maintenance WHERE tankId=:tankId")
    LiveData<List<ScheduledMaintenance>> getScheduledMaintenanceOnTank(final long tankId);

    @Query("SELECT * FROM scheduled_maintenance WHERE tankId=:tankId AND (NOT type=1) ORDER BY date_scheduled ASC LIMIT 1")
    LiveData<ScheduledMaintenance> getNextMaintenanceToDoOnTank(long tankId);

    // TODO: determine order so that first is next date
    @Query("SELECT * FROM scheduled_maintenance WHERE tankId=:tankId AND type=:type ORDER BY date_scheduled ASC LIMIT 1")
    LiveData<ScheduledMaintenance> getNextTypeOnTank(long tankId, EMaintenanceType type);
}
