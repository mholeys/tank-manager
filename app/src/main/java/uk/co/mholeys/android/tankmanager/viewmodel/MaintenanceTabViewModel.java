package uk.co.mholeys.android.tankmanager.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import uk.co.mholeys.android.tankmanager.model.DataRepository;
import uk.co.mholeys.android.tankmanager.model.entity.Tank;

public class MaintenanceTabViewModel extends AndroidViewModel {

    private final DataRepository dataRepository;
    private long mTankId;
    private LiveData<Tank> mTank;

    public MaintenanceTabViewModel(@NonNull Application application) {
        super(application);
        dataRepository = DataRepository.getInstance(application);
    }

    public void setup(long tankId) {
        this.mTankId = tankId;
        mTank = dataRepository.getTank(tankId);
    }

}
