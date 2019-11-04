package uk.co.mholeys.android.tankmanager.view.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import uk.co.mholeys.android.tankmanager.R;
import uk.co.mholeys.android.tankmanager.model.entity.Tank;
import uk.co.mholeys.android.tankmanager.viewmodel.MaintenanceViewModel;
import uk.co.mholeys.android.tankmanager.viewmodel.TankListViewModel;

public class MaintenanceActivity extends AppCompatActivity {

    private static final String TAG = MaintenanceActivity.class.getSimpleName();

    public static final String EXTRA_TANK_ID = "MAINTENANCE_ACTIVITY._EXTRA_TANK_ID";

    private long mTankId;
    private MaintenanceViewModel mViewModel;
    private Tank mTank;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maintenance);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        // TODO: add following
        // TODO: - way to show history of maintenance (probable tabs for different types in list)
        // TODO: - way to record a done maintenance
        // and then lead into adding next times scheduled
        // TODO: - way to show scheduled maintenance
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }

}
