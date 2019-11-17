package uk.co.mholeys.android.tankmanager.view.ui.tank;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
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

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_tank_edit, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.app_bar_edit:
                Intent intent = new Intent(this, TankEditActivity.class);
                intent.putExtra(TankEditActivity.EXTRA_ID, mTank.getId());
                intent.putExtra(TankEditActivity.EXTRA_MODE, TankEditActivity.EXTRA_MODE_EDIT);
                startActivity(intent);
                return true;
            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);
        }
    }

}
