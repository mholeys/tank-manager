package uk.co.mholeys.android.tankmanager.model;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import androidx.sqlite.db.SupportSQLiteDatabase;

import uk.co.mholeys.android.tankmanager.model.dao.IEquipmentDAO;
import uk.co.mholeys.android.tankmanager.model.dao.IMaintenanceDAO;
import uk.co.mholeys.android.tankmanager.model.dao.IReadingDAO;
import uk.co.mholeys.android.tankmanager.model.dao.IScheduledMaintenanceDAO;
import uk.co.mholeys.android.tankmanager.model.dao.ITankDAO;
import uk.co.mholeys.android.tankmanager.model.entity.Equipment;
import uk.co.mholeys.android.tankmanager.model.entity.Maintenance;
import uk.co.mholeys.android.tankmanager.model.entity.Readings;
import uk.co.mholeys.android.tankmanager.model.entity.ScheduledMaintenance;
import uk.co.mholeys.android.tankmanager.model.entity.Tank;
import uk.co.mholeys.android.tankmanager.model.typeconverters.DateTimeTypeConverter;
import uk.co.mholeys.android.tankmanager.model.typeconverters.MaintenanceEnumTypeConverter;
import uk.co.mholeys.android.tankmanager.model.typeconverters.TankTypeEnumTypeConverter;

@Database(entities = {
            Tank.class,
            Equipment.class,
            Maintenance.class,
            ScheduledMaintenance.class,
            Readings.class
        },
        version = 1
)
@TypeConverters({
        MaintenanceEnumTypeConverter.class,
        DateTimeTypeConverter.class,
        TankTypeEnumTypeConverter.class
})
public abstract class TankDatabase extends RoomDatabase {

    public abstract IEquipmentDAO equipmentDao();
    public abstract IMaintenanceDAO maintenanceDao();
    public abstract IScheduledMaintenanceDAO scheduledMaintenanceDao();
    public abstract IReadingDAO readingDao();
    public abstract ITankDAO tankDao();

    // marking the instance as volatile, ensures atomic access to the variable
    private static volatile TankDatabase INSTANCE;

    public static TankDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (TankDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            TankDatabase.class, "tanks_database")
                            //.addMigration(...)
                            // Wipes and rebuilds instead of migrating if no Migration object.
                            .fallbackToDestructiveMigration()
                            .addCallback(sRoomDatabaseCallback)
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    private static RoomDatabase.Callback sRoomDatabaseCallback = new RoomDatabase.Callback() {
        @Override
        public void onOpen(@NonNull SupportSQLiteDatabase db) {
            super.onOpen(db);
            // do any init operation about any initialisation here
        }
    };

}
