package uk.co.mholeys.android.tankmanager.view.ui.tank;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
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
    int mode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tank_edit);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        mTankId = intent.getLongExtra(EXTRA_ID, 0);
        mode = intent.getIntExtra(EXTRA_MODE, 0);

        mTankTitleEditText = findViewById(R.id.tank_name_edit_text);
        mTankTypeSpinner = findViewById(R.id.tank_type_spinner);
        mTankSizeEditText = findViewById(R.id.tank_size_edit_text);
        mTankSizeUnitEditText = findViewById(R.id.tank_size_unit_edit_text);
        mSubmitButton = findViewById(R.id.add_tank_button);

        if (mode == EXTRA_MODE_CREATE) {
            setupViewModel(new Tank());
            mSubmitButton.setText(R.string.add);
        } else {
            // Load the tank they wish to edit
            setupViewModel(null);
            mSubmitButton.setText(R.string.save);
        }

        mSubmitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveChanges();
            }
        });


        mSpinnerAdapter = ArrayAdapter.createFromResource(this,
                R.array.tank_types_array, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        mSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        // Apply the adapter to the spinner
        mTankTypeSpinner.setAdapter(mSpinnerAdapter);

    }

    private void setupViewModel(Tank tank) {
        // Setup View Model
        mViewModel = ViewModelProviders.of(this).get(TankListViewModel.class);
        if (tank == null) {
            mViewModel.get((int) mTankId).observe(this, new Observer<Tank>() {
                @Override
                public void onChanged(@Nullable Tank tank) {
                showTank(tank);
                }
            });
        } else {
            showTank(tank);
        }
    }

    private void showTank(Tank tank) {
        mTank = tank;
        mTankTitleEditText.setText(mTank.name);
        if (tank.type != null) {
            mTankTypeSpinner.setSelection(mTank.type.ordinal());
        }
        mTankSizeEditText.setText(mTank.size);
        mTankSizeUnitEditText.setText(mTank.units);
    }

    @Override
    public void onBackPressed() {
        if (mode == EXTRA_MODE_EDIT) {
            boolean changes = !mTankTitleEditText.getText().toString().equals(mTank.name) ||
                    !mTankSizeEditText.getText().toString().equals(mTank.size) ||
                    !mTankSizeUnitEditText.getText().toString().equals(mTank.units) ||
                    mTank.type.ordinal() != mTankTypeSpinner.getSelectedItemPosition();
            // Only ask to confirm if there are changes
            if (changes) {
                askDiscardChanges();
                return;
            }
        } else if (mode == EXTRA_MODE_CREATE) {
            Log.d(TAG, "onBackPressed: Default selected is " + mTankTypeSpinner.getSelectedItemPosition());
            boolean changes = !mTankTitleEditText.getText().toString().trim().equals("") ||
                    !mTankSizeEditText.getText().toString().trim().equals("") ||
                    !mTankSizeUnitEditText.getText().toString().trim().equals("");
                    //||
                    //mTank.type.ordinal() != mTankTypeSpinner.getSelectedItemPosition();
            // Only ask to confirm if there are changes
            if (changes) {
                askDiscardChanges();
                return;
            }
        }
        super.onBackPressed();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }

    private void askDiscardChanges() {
        new AlertDialog.Builder(this)
                .setTitle("Changes!")
                .setMessage("What do you want to do with the changes")
                .setIcon(R.drawable.ic_warning_amber_a100_24dp)
                .setPositiveButton(R.string.save, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int buttonId) {
                        saveChanges();
                    }
                })
                .setNegativeButton(R.string.discard, new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int buttonId) {
                        TankEditActivity.super.onBackPressed();
                    }})
                .setNeutralButton(android.R.string.no, null).show();
    }

    private void saveChanges(){
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

}
