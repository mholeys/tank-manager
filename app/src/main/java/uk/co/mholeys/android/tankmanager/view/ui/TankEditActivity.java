package uk.co.mholeys.android.tankmanager.view.ui;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import uk.co.mholeys.android.tankmanager.R;
import uk.co.mholeys.android.tankmanager.model.ETankType;
import uk.co.mholeys.android.tankmanager.model.entity.Tank;
import uk.co.mholeys.android.tankmanager.viewmodel.TankListViewModel;

public class TankEditActivity extends AppCompatActivity {

    private static final String TAG = TankEditActivity.class.getSimpleName();

    public static final String EXTRA_ID = "TANK_EDIT_ACTIVITY._EXTRA_TANK_ID";
    public static final String EXTRA_MODE = "TANK_EDIT_ACTIVITY._EXTRA_MODE";
    public static final int EXTRA_MODE_EDIT = 1;
    public static final int EXTRA_MODE_CREATE = 0;

    private long mTankId;
    private TankListViewModel mViewModel;
    private Tank mTank;

    private EditText mTankTitleEditText;
    private EditText mTankSizeEditText;
    private EditText mTankSizeUnitEditText;
    private Spinner mTankTypeSpinner;
    private ArrayAdapter<CharSequence> mSpinnerAdapter;
    private Button mSubmitButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tank_edit);

        Intent intent = getIntent();
        mTankId = intent.getLongExtra(EXTRA_ID, 0);
        final int mode = intent.getIntExtra(EXTRA_MODE, 0);

        mTankTitleEditText = findViewById(R.id.tank_name_edit_text);
        mTankTypeSpinner = findViewById(R.id.tank_type_spinner);
        mTankSizeEditText = findViewById(R.id.tank_size_edit_text);
        mTankSizeUnitEditText = findViewById(R.id.tank_size_unit_edit_text);
        mSubmitButton = findViewById(R.id.add_tank_button);

        if (mode == EXTRA_MODE_CREATE) {
            showTank(new Tank());
            mSubmitButton.setText(R.string.add);
        } else {
            setupViewModel();
            mSubmitButton.setText(R.string.save);
        }

        mSubmitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Add/Save tank
                if (mode == EXTRA_MODE_EDIT) {
                    // Update tank
                    // Create new tank and save
                    mTank.name = mTankTitleEditText.getText().toString();
                    mTank.size = mTankSizeEditText.getText().toString();
                    mTank.units = mTankSizeUnitEditText.getText().toString();

                    ETankType tankType = ETankType.OTHER;
                    if (mTankTypeSpinner.getSelectedItemId() < ETankType.values().length) {
                        try {
                            tankType = ETankType.values()[(int) mTankTypeSpinner.getSelectedItemId()];
                        } catch (ArrayIndexOutOfBoundsException e) {
                            // Type doesnt exist so stick with other
                            Log.w(TAG, "mSubmitButton.onClick: Failed to determine selected tank type, using ETankType.OTHER");
                        }
                    }
                    mTank.type = tankType;
                    mTank.units = mTankSizeUnitEditText.getText().toString();
                    // TODO!
                    mViewModel.updateTank(mTank);
                } else if (mode == EXTRA_MODE_CREATE) {
                    // Create new tank and save
                    mTank.name = mTankTitleEditText.getText().toString();
                    mTank.size = mTankSizeEditText.getText().toString();
                    mTank.units = mTankSizeUnitEditText.getText().toString();

                    ETankType tankType = ETankType.OTHER;
                    if (mTankTypeSpinner.getSelectedItemId() < ETankType.values().length) {
                        try {
                            tankType = ETankType.values()[(int) mTankTypeSpinner.getSelectedItemId()];
                        } catch (ArrayIndexOutOfBoundsException e) {
                            // Type doesnt exist so stick with other
                            Log.w(TAG, "mSubmitButton.onClick: Failed to determine selected tank type, using ETankType.OTHER");
                        }
                    }
                    mTank.type = tankType;
                    mTank.units = mTankSizeUnitEditText.getText().toString();
                    mViewModel.insertTank(mTank);
                }
                finish();
            }
        });


        mSpinnerAdapter = ArrayAdapter.createFromResource(this,
                R.array.tank_types_array, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        mSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        // Apply the adapter to the spinner
        mTankTypeSpinner.setAdapter(mSpinnerAdapter);

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
        // Check if we are creating or editing
        if (tank != null) {
            mTank = tank;
            mTankTitleEditText.setText(mTank.name);
            if (tank.type != null) {
                mTankTypeSpinner.setSelection(mTank.type.ordinal());
            }
            mTankSizeEditText.setText(mTank.size);
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }
}
