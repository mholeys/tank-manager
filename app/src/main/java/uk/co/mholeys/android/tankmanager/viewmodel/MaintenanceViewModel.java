package uk.co.mholeys.android.tankmanager.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import uk.co.mholeys.android.tankmanager.model.DataRepository;
import uk.co.mholeys.android.tankmanager.model.entity.Maintenance;
import uk.co.mholeys.android.tankmanager.model.entity.ScheduledMaintenance;
import uk.co.mholeys.android.tankmanager.model.entity.Tank;

public class MaintenanceViewModel extends AndroidViewModel {

    private final DataRepository dataRepository;
    private long mTankId;
    private LiveData<Tank> mTank;

    public void setup(long tankId) {
        this.mTankId = tankId;
        mTank = dataRepository.getTank(tankId);
    }

    public MaintenanceViewModel(@NonNull Application application) {
        super(application);
        dataRepository = DataRepository.getInstance(application);
    }

    public LiveData<List<Maintenance>> getMaintenanceDone() {
        return dataRepository.getMaintenanceDone(mTankId);
    }

    public LiveData<Maintenance> getLastMaintenance() {
        return dataRepository.getLastMaintenance(mTankId);
    }

    public LiveData<ScheduledMaintenance> getNextMaintenance() {
        return dataRepository.getNextMaintenance(mTankId);
    }

    public LiveData<ScheduledMaintenance> getNextWaterChange() {
        return dataRepository.getNextWaterChange(mTankId);
    }
}
