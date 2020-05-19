package uk.co.mholeys.android.tankmanager.viewmodel;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

import uk.co.mholeys.android.tankmanager.model.DataRepository;
import uk.co.mholeys.android.tankmanager.model.entity.Readings;
import uk.co.mholeys.android.tankmanager.model.entity.Tank;

public class ReadingsListViewModel extends AndroidViewModel {

    private static final String TAG = ReadingsListViewModel.class.getSimpleName();
    private final DataRepository dataRepository;
    private long mTankId;
    private LiveData<Tank> mTank;

    public ReadingsListViewModel(@NonNull Application application) {
        super(application);
        dataRepository = DataRepository.getInstance(application);
    }

    public LiveData<List<Readings>> getReadingsToDisplay() {
        return dataRepository.getTankReadings(mTankId);
    }

    public LiveData<Readings> get(long readingId) {
        return dataRepository.getTankReading(mTank, readingId);
    }

    public MutableLiveData<Long> insertReading(Readings readings) {
        return dataRepository.insertReadings(readings);
    }

    public void deleteReadings(Readings[] readingsToDelete) {
        dataRepository.deleteReadings(readingsToDelete);
    }

    public LiveData<List<Readings>> getReadingsToDisplay(String query) {
        Log.e(TAG, "getReadingsToDisplay: Search not implemented");
        return getReadingsToDisplay();
    }

    public void updateReadings(Readings reading) {
        dataRepository.updateReadingOfTank(mTankId, reading);
    }

    public void setup(long tankId) {
        this.mTankId = tankId;
        mTank = dataRepository.getTank(tankId);
    }

}
