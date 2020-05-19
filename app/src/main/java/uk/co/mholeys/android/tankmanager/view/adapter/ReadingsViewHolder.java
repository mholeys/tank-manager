package uk.co.mholeys.android.tankmanager.view.adapter;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.selection.ItemDetailsLookup;
import androidx.recyclerview.widget.RecyclerView;

import uk.co.mholeys.android.tankmanager.R;
import uk.co.mholeys.android.tankmanager.model.entity.Readings;
import uk.co.mholeys.android.tankmanager.model.entity.Tank;

public class ReadingsViewHolder extends RecyclerView.ViewHolder {

    private final ItemDetailsLookup.ItemDetails<Long> mItemDetails;
    long id;

    public final TextView mAmmoniaValueText;
    public final TextView mPhValueText;
    public final TextView mNitriteValueText;
    public final TextView mNitrateValueText;
    public final TextView mKhValueText;
    public final TextView mGhValueText;
    public final TextView mSalinityValueText;
    public final TextView mPhosphateValueText;
    public final TextView mIronValueText;
    public final TextView mCopperValueText;
    public final TextView mMagnesiumValueText;
    public final TextView mCalciumValueText;
    public final TextView mCO2ValueText;
    public final TextView mO2ValueText;
    public final TextView mDateTakenText;

    public Readings mReading;

    public ReadingsViewHolder(@NonNull View itemView) {
        super(itemView);

        mAmmoniaValueText = itemView.findViewById(R.id.ammonia_value_text);
        mPhValueText = itemView.findViewById(R.id.ph_value_text);
        mNitriteValueText = itemView.findViewById(R.id.nitrite_value_text);
        mNitrateValueText = itemView.findViewById(R.id.nitrate_value_text);
        mKhValueText = itemView.findViewById(R.id.kh_value_text);
        mGhValueText = itemView.findViewById(R.id.gh_value_text);
        mSalinityValueText = itemView.findViewById(R.id.salinity_value_text);
        mPhosphateValueText = itemView.findViewById(R.id.phosphate_value_text);
        mIronValueText = itemView.findViewById(R.id.iron_value_text);
        mCopperValueText = itemView.findViewById(R.id.copper_value_text);
        mMagnesiumValueText = itemView.findViewById(R.id.magnesium_value_text);
        mCalciumValueText = itemView.findViewById(R.id.calcium_value_text);
        mCO2ValueText = itemView.findViewById(R.id.co2_value_text);
        mO2ValueText = itemView.findViewById(R.id.o2_value_text);
        mDateTakenText = itemView.findViewById(R.id.date_text);

        mItemDetails = new ItemDetailsLookup.ItemDetails<Long>() {
            @Override
            public int getPosition() {
                return getAdapterPosition();
            }

            @Nullable
            @Override
            public Long getSelectionKey() {
                return id;
            }
        };
    }

    public void showDefault() {
//        mThumbnailView.setImageResource(R.drawable.default_photo_48dp);
        mAmmoniaValueText.setText(R.string.zero);
        mPhValueText.setText(R.string.zero);
        mNitriteValueText.setText(R.string.zero);
        mNitrateValueText.setText(R.string.zero);
        mKhValueText.setText(R.string.zero);
        mGhValueText.setText(R.string.zero);
        mSalinityValueText.setText(R.string.zero);
        mPhosphateValueText.setText(R.string.zero);
        mIronValueText.setText(R.string.zero);
        mCopperValueText.setText(R.string.zero);
        mMagnesiumValueText.setText(R.string.zero);
        mCalciumValueText.setText(R.string.zero);
        mCO2ValueText.setText(R.string.zero);
        mO2ValueText.setText(R.string.zero);
    }

    public ItemDetailsLookup.ItemDetails<Long> getItemDetails() {
        return mItemDetails;
    }

}
