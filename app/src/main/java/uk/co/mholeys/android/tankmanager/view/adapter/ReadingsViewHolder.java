package uk.co.mholeys.android.tankmanager.view.adapter;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.selection.ItemDetailsLookup;
import androidx.recyclerview.widget.RecyclerView;

import uk.co.mholeys.android.tankmanager.R;
import uk.co.mholeys.android.tankmanager.model.entity.Tank;

public class ReadingsViewHolder extends RecyclerView.ViewHolder {

    private final ItemDetailsLookup.ItemDetails<Long> mItemDetails;
    long id;
    public final ImageView mThumbnailView;
    public final TextView mTitleTextView;
    public final TextView mInfoTextView;
    public Tank mTank;

    public ReadingsViewHolder(@NonNull View itemView) {
        super(itemView);
        mThumbnailView = itemView.findViewById(R.id.tank_item_thumb);
        mTitleTextView = itemView.findViewById(R.id.tank_item_title);
        mInfoTextView = itemView.findViewById(R.id.tank_item_info);

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

//    public void setThumbnail(Bitmap thumbnail) {
//        mThumbnailView.setImageBitmap(thumbnail);
//    }

    public void showDefault() {
//        mThumbnailView.setImageResource(R.drawable.default_photo_48dp);
        mTitleTextView.setText(R.string.default_tank_title);
        mInfoTextView.setText(R.string.default_tank_info);
    }

    public ItemDetailsLookup.ItemDetails<Long> getItemDetails() {
        return mItemDetails;
    }

}
