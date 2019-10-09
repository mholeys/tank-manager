package uk.co.mholeys.android.tankmanager.model;

import android.content.Context;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import uk.co.mholeys.android.tankmanager.model.dao.IEquipmentDAO;
import uk.co.mholeys.android.tankmanager.model.dao.IMaintenanceDAO;
import uk.co.mholeys.android.tankmanager.model.dao.IReadingDAO;
import uk.co.mholeys.android.tankmanager.model.dao.ITankDAO;
import uk.co.mholeys.android.tankmanager.model.entity.Tank;

public class DataRepository {

    private static final String TAG = DataRepository.class.getSimpleName();

    private static DataRepository instance;

    // Dao's
    private final IEquipmentDAO equipmentDao;
    private final IMaintenanceDAO maintenanceDao;
    private final IReadingDAO readingDao;
    private final ITankDAO tankDao;


    private Executor mExecutor;

    private DataRepository(Context context) {
        // Setup database
        this.equipmentDao = TankDatabase.getDatabase(context).equipmentDao();
        this.maintenanceDao = TankDatabase.getDatabase(context).maintenanceDao();
        this.readingDao = TankDatabase.getDatabase(context).readingDao();
        this.tankDao = TankDatabase.getDatabase(context).tankDao();

        // Create executor to handle all database requests/syncs
        this.mExecutor = Executors.newSingleThreadExecutor();
    }

    public static DataRepository getInstance(Context context) {
        if (instance == null) {
            instance = new DataRepository(context);
        }
        return instance;
    }

}
