package uk.co.mholeys.android.tankmanager.model;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import uk.co.mholeys.android.tankmanager.model.dao.IEquipmentDAO;
import uk.co.mholeys.android.tankmanager.model.dao.IMaintenanceDAO;
import uk.co.mholeys.android.tankmanager.model.dao.IReadingDAO;
import uk.co.mholeys.android.tankmanager.model.dao.ITankDAO;
import uk.co.mholeys.android.tankmanager.model.entity.Equipment;
import uk.co.mholeys.android.tankmanager.model.entity.Maintenance;
import uk.co.mholeys.android.tankmanager.model.entity.Readings;
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

    public LiveData<List<Tank>> getTanks() {
        return tankDao.getAll();
    }

    public LiveData<Tank> getTank(long tankId) {
        return tankDao.get(tankId);
    }

    public LiveData<List<Equipment>> getTankEquipment(int tankId) {
        return equipmentDao.getEquipmentOfTank(tankId);
    }

    public LiveData<List<Maintenance>> getTankMaintenance(int tankId) {
        return maintenanceDao.getMaintenanceDoneOnTank(tankId);
    }

    public LiveData<List<Readings>> getTankReadings(int tankId) {
        return readingDao.getReadingsOfTank(tankId);
    }

    public MutableLiveData<Long> insertTank(final Tank tank) {
        final MutableLiveData<Long> tankIdLiveData = new MutableLiveData<>();
        mExecutor.execute(new Runnable() {
            @Override
            public void run() {
                long tankId = tankDao.insert(tank);
                tank.tId = tankId;
                tankIdLiveData.postValue(tankId);
            }
        });
        return tankIdLiveData;
    }

    public void updateTank(final Tank tank) {
        mExecutor.execute(new Runnable() {
                @Override
                public void run() {
                    tankDao.update(tank);
                }
        });
    }
}
