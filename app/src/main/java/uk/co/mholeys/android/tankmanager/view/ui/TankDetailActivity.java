package uk.co.mholeys.android.tankmanager.view.ui;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import uk.co.mholeys.android.tankmanager.R;
import uk.co.mholeys.android.tankmanager.model.entity.Tank;
import uk.co.mholeys.android.tankmanager.viewmodel.TankListViewModel;

public class TankDetailActivity extends AppCompatActivity {

    private static final String TAG = TankDetailActivity.class.getSimpleName();

    public static final String EXTRA_ID = "TANK_DETAIL_ACTIVITY._EXTRA_TANK_ID";
    private long mTankId;
    private TankListViewModel mViewModel;
    private Tank mTank;

    private TextView mTankTitleTextView;
    private TextView mTankSizeTextView;
    private TextView mTankTypeTextView;
    private TextView mTankSizeUnitTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tank_detail);

        Intent intent = getIntent();
        mTankId = intent.getLongExtra(EXTRA_ID, 0);
        setupViewModel();

        mTankTitleTextView = findViewById(R.id.tank_title_text_view);
        mTankTypeTextView = findViewById(R.id.tank_type_text_view);
        mTankSizeTextView = findViewById(R.id.tank_size_text_view);
        mTankSizeUnitTextView = findViewById(R.id.tank_size_unit_text_view);

    }

    private void setupViewModel() {
        // Setup View Model
        mViewModel = ViewModelProviders.of(this).get(TankListViewModel.class);
        mViewModel.get((int) mTankId).observe(this, new Observer<Tank>() {
            @Override
            public void onChanged(@Nullable Tank tank) {
                showTank(tank);
            }
        });
    }

    private void showTank(Tank tank) {
        mTank = tank;
        mTankTitleTextView.setText(mTank.name);
        mTankTypeTextView.setText(getResources().getStringArray(R.array.tank_types_array)[mTank.type.ordinal()]);
        mTankSizeTextView.setText(mTank.size);
        mTankSizeUnitTextView.setText(mTank.units);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }
}