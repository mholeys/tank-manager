package uk.co.mholeys.android.tankmanager.viewmodel;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

import uk.co.mholeys.android.tankmanager.model.DataRepository;
import uk.co.mholeys.android.tankmanager.model.entity.Tank;

public class TankListViewModel extends AndroidViewModel {

    private static final String TAG = TankListViewModel.class.getSimpleName();
    private final DataRepository dataRepository;

    public TankListViewModel(@NonNull Application application) {
        super(application);
        dataRepository = DataRepository.getInstance(application);
    }

    public LiveData<List<Tank>> getTanksToDisplay() {
        return dataRepository.getTanks();
    }

    public LiveData<Tank> get(long id) {
        return dataRepository.getTank(id);
    }

    public MutableLiveData<Long> insertTank(Tank tank) {
        return dataRepository.insertTank(tank);
    }

    public void deleteTanks(Tank[] tanksToDelete) {
        Log.d(TAG, "deleteTanks: Not implemented");
    }

    public LiveData<List<Tank>> getTanksToDisplay(String query) {
        Log.e(TAG, "getTanksToDisplay: Search not implemented");
        return dataRepository.getTanks();
    }
}
