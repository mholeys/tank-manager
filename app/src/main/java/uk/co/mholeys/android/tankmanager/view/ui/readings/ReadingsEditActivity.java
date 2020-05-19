package uk.co.mholeys.android.tankmanager.view.ui.readings;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;

import uk.co.mholeys.android.tankmanager.R;
import uk.co.mholeys.android.tankmanager.model.entity.Readings;
import uk.co.mholeys.android.tankmanager.viewmodel.ReadingsListViewModel;

public class ReadingsEditActivity extends AppCompatActivity {

    private static final String TAG = ReadingsEditActivity.class.getSimpleName();

    public static final String EXTRA_ID = "READINGS_EDIT_ACTIVITY._EXTRA_READING_ID";
    public static final String EXTRA_TANK_ID = "READINGS_EDIT_ACTIVITY._EXTRA_TANK_ID";
    public static final String EXTRA_MODE = "READINGS_EDIT_ACTIVITY._EXTRA_MODE";
    public static final int EXTRA_MODE_EDIT = 1;
    public static final int EXTRA_MODE_CREATE = 0;

    private long mTankId;
    private long mReadingsId;
    private ReadingsListViewModel mViewModel;
    private Readings mReadings;

    private EditText mDateTakenEditText;

    private EditText mAmmoniaEditText;
    private EditText mPhEditText;
    private EditText mNitriteEditText;
    private EditText mNitrateEditText;
    private EditText mKhEditText;
    private EditText mGhEditText;
    private EditText mSalinityEditText;
    private EditText mPhosphateEditText;
    private EditText mIronEditText;
    private EditText mCopperEditText;
    private EditText mMagnesiumEditText;
    private EditText mCalciumEditText;
    private EditText mCo2EditText;
    private EditText mO2EditText;

    private Button mSubmitButton;

    int mode;

    OffsetDateTime mDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_readings_edit);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        mTankId = intent.getLongExtra(EXTRA_TANK_ID, -1);
        mReadingsId = intent.getLongExtra(EXTRA_ID, -1);
        mode = intent.getIntExtra(EXTRA_MODE, -1);

        mAmmoniaEditText = findViewById(R.id.ammonia_edit_text);
        mPhEditText = findViewById(R.id.ph_edit_text);
        mNitriteEditText = findViewById(R.id.nitrite_edit_text);
        mNitrateEditText = findViewById(R.id.nitrate_edit_text);
        mKhEditText = findViewById(R.id.kh_edit_text);
        mGhEditText = findViewById(R.id.gh_edit_text);
        mSalinityEditText = findViewById(R.id.salinity_edit_text);
        mPhosphateEditText = findViewById(R.id.phosphate_edit_text);
        mIronEditText = findViewById(R.id.iron_edit_text);
        mCopperEditText = findViewById(R.id.copper_edit_text);
        mMagnesiumEditText = findViewById(R.id.magnesium_edit_text);
        mCalciumEditText = findViewById(R.id.calcium_edit_text);
        mCo2EditText = findViewById(R.id.co2_edit_text);
        mO2EditText = findViewById(R.id.o2_edit_text);
        mSubmitButton = findViewById(R.id.add_reading_button);
        mDateTakenEditText = findViewById(R.id.date_taken_date_text);

        if (mode == EXTRA_MODE_CREATE) {
            setupViewModel(new Readings());
            mSubmitButton.setText(R.string.add);
        } else {
            // Load the reading they wish to edit
            setupViewModel(null);
            mSubmitButton.setText(R.string.save);
        }

        mSubmitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveChanges();
            }
        });
        mDateTakenEditText.setInputType(InputType.TYPE_NULL);
        mDateTakenEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    showDatePicker();
                }
            }
        });
        mDateTakenEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDatePicker();
            }
        });
    }

    private void showDatePicker() {
        DialogFragment newFragment = new DateTakenPickerFragment(new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                OffsetDateTime dateTime = OffsetDateTime.of(year, month, dayOfMonth, 0, 0, 0, 0, ZoneOffset.UTC);
                mDate = dateTime;
                mDateTakenEditText.setText(dateTime.format(DateTimeFormatter.ISO_LOCAL_DATE));
            }
        });
        newFragment.show(getSupportFragmentManager(), "datePicker");
    }

    private void setupViewModel(Readings reading) {
        // Setup View Model
        mViewModel = ViewModelProviders.of(this).get(ReadingsListViewModel.class);
        if (reading == null) {
            mViewModel.get((int) mReadingsId).observe(this, new Observer<Readings>() {
                @Override
                public void onChanged(@Nullable Readings readings) {
                    showReading(readings);
                }
            });
        } else {
            showReading(reading);
        }
    }

    private void showReading(Readings reading) {
        mReadings = reading;
        mDate = OffsetDateTime.now();

        // Dont populate text boxes with values if we are creating a new readings
        if (mode == EXTRA_MODE_EDIT) {
            mAmmoniaEditText.setText(String.valueOf(reading.ammonia));
            mPhEditText.setText(String.valueOf(reading.PH));
            mNitriteEditText.setText(String.valueOf(reading.nitrite));
            mNitrateEditText.setText(String.valueOf(reading.nitrate));
            mKhEditText.setText(String.valueOf(reading.KH));
            mGhEditText.setText(String.valueOf(reading.GH));
            mSalinityEditText.setText(String.valueOf(reading.salinity));
            mPhosphateEditText.setText(String.valueOf(reading.phosphate));
            mIronEditText.setText(String.valueOf(reading.iron));
            mCopperEditText.setText(String.valueOf(reading.copper));
            mMagnesiumEditText.setText(String.valueOf(reading.magnesium));
            mCalciumEditText.setText(String.valueOf(reading.calcium));
            mCo2EditText.setText(String.valueOf(reading.CO2));
            mO2EditText.setText(String.valueOf(reading.O2));

            mDate = reading.date;
            mDateTakenEditText.setText(reading.date.format(DateTimeFormatter.ISO_LOCAL_DATE));
        } else {
            mDateTakenEditText.setText(mDate.format(DateTimeFormatter.ISO_LOCAL_DATE));
        }
    }

    @Override
    public void onBackPressed() {
        if (mode == EXTRA_MODE_EDIT) {
            boolean changes =
                    !mAmmoniaEditText.getText().toString().equals(mReadings.ammonia) ||
                    !mPhEditText.getText().toString().equals(mReadings.PH) ||
                    !mNitriteEditText.getText().toString().equals(mReadings.nitrite) ||
                    !mNitrateEditText.getText().toString().equals(mReadings.nitrate) ||
                    !mKhEditText.getText().toString().equals(mReadings.KH) ||
                    !mGhEditText.getText().toString().equals(mReadings.GH) ||
                    !mSalinityEditText.getText().toString().equals(mReadings.salinity) ||
                    !mPhosphateEditText.getText().toString().equals(mReadings.phosphate) ||
                    !mIronEditText.getText().toString().equals(mReadings.iron) ||
                    !mCopperEditText.getText().toString().equals(mReadings.copper) ||
                    !mMagnesiumEditText.getText().toString().equals(mReadings.magnesium) ||
                    !mCalciumEditText.getText().toString().equals(mReadings.calcium) ||
                    !mCo2EditText.getText().toString().equals(mReadings.CO2) ||
                    !mO2EditText.getText().toString().equals(mReadings.O2) ||
                    !mDateTakenEditText.getText().toString().equals(mReadings.date.format(DateTimeFormatter.ISO_LOCAL_DATE)) ||
                    !mDate.isEqual(mReadings.date);

            // Only ask to confirm if there are changes
            if (changes) {
                askDiscardChanges();
                return;
            }
        } else if (mode == EXTRA_MODE_CREATE) {
            boolean changes =
                !mAmmoniaEditText.getText().toString().trim().equals("") ||
                !mPhEditText.getText().toString().trim().equals("") ||
                !mNitriteEditText.getText().toString().trim().equals("") ||
                !mNitrateEditText.getText().toString().trim().equals("") ||
                !mKhEditText.getText().toString().trim().equals("") ||
                !mGhEditText.getText().toString().trim().equals("") ||
                !mSalinityEditText.getText().toString().trim().equals("") ||
                !mPhosphateEditText.getText().toString().trim().equals("") ||
                !mIronEditText.getText().toString().trim().equals("") ||
                !mCopperEditText.getText().toString().trim().equals("") ||
                !mMagnesiumEditText.getText().toString().trim().equals("") ||
                !mCalciumEditText.getText().toString().trim().equals("") ||
                !mCo2EditText.getText().toString().trim().equals("") ||
                !mO2EditText.getText().toString().trim().equals("") ||
                !mDateTakenEditText.getText().toString().equals(mReadings.date.format(DateTimeFormatter.ISO_LOCAL_DATE)) ||
                !mDate.isEqual(mReadings.date);

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
                        ReadingsEditActivity.super.onBackPressed();
                    }})
                .setNeutralButton(android.R.string.no, null).show();
    }

    private void saveChanges(){
        // Add/Save readings
        if (mode == EXTRA_MODE_EDIT) {
            // Update readings
            mReadings.ammonia = toFloat(mAmmoniaEditText.getText().toString());
            mReadings.PH = toFloat(mPhEditText.getText().toString());
            mReadings.nitrite = toFloat(mNitriteEditText.getText().toString());
            mReadings.nitrate = toFloat(mNitrateEditText.getText().toString());
            mReadings.KH = toFloat(mKhEditText.getText().toString());
            mReadings.GH = toFloat(mGhEditText.getText().toString());
            mReadings.salinity = toFloat(mSalinityEditText.getText().toString());
            mReadings.phosphate = toFloat(mPhosphateEditText.getText().toString());
            mReadings.iron = toFloat(mIronEditText.getText().toString());
            mReadings.copper = toFloat(mCopperEditText.getText().toString());
            mReadings.magnesium = toFloat(mMagnesiumEditText.getText().toString());
            mReadings.calcium = toFloat(mCalciumEditText.getText().toString());
            mReadings.CO2 = toFloat(mCo2EditText.getText().toString());
            mReadings.O2 = toFloat(mO2EditText.getText().toString());
            mReadings.date = mDate;

            mReadings.tankId = mTankId;
            mViewModel.updateReadings(mReadings);
        } else if (mode == EXTRA_MODE_CREATE) {
            // Create new readings and save
            mReadings.ammonia = toFloat(mAmmoniaEditText.getText().toString());
            mReadings.PH = toFloat(mPhEditText.getText().toString());
            mReadings.nitrite = toFloat(mNitriteEditText.getText().toString());
            mReadings.nitrate = toFloat(mNitrateEditText.getText().toString());
            mReadings.KH = toFloat(mKhEditText.getText().toString());
            mReadings.GH = toFloat(mGhEditText.getText().toString());
            mReadings.salinity = toFloat(mSalinityEditText.getText().toString());
            mReadings.phosphate = toFloat(mPhosphateEditText.getText().toString());
            mReadings.iron = toFloat(mIronEditText.getText().toString());
            mReadings.copper = toFloat(mCopperEditText.getText().toString());
            mReadings.magnesium = toFloat(mMagnesiumEditText.getText().toString());
            mReadings.calcium = toFloat(mCalciumEditText.getText().toString());
            mReadings.CO2 = toFloat(mCo2EditText.getText().toString());
            mReadings.O2 = toFloat(mO2EditText.getText().toString());
            mReadings.date = mDate;

            mReadings.tankId = mTankId;
            mViewModel.insertReading(mReadings);
        }
        finish();
    }

    private float toFloat(String value) {
        try {
            return Float.valueOf(value);
        } catch (NumberFormatException e) {
            return -1;
        }
    }

}
