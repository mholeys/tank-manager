package uk.co.mholeys.android.tankmanager.view.adapter;

import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.ActionMode;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.selection.Selection;
import androidx.recyclerview.selection.SelectionTracker;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import uk.co.mholeys.android.tankmanager.R;
import uk.co.mholeys.android.tankmanager.model.entity.Readings;
import uk.co.mholeys.android.tankmanager.model.entity.Tank;
import uk.co.mholeys.android.tankmanager.viewmodel.ReadingsListViewModel;
import uk.co.mholeys.android.tankmanager.viewmodel.TankListViewModel;

public class ReadingsListAdapter extends RecyclerView.Adapter<ReadingsViewHolder> {

    private static final String TAG = ReadingsListAdapter.class.getSimpleName();

    private final ReadingsListViewModel mReadingsListViewModel;
    private final long mTankId;
    private FragmentActivity mActivity;
    private List<Readings> mReadings = new ArrayList<>();
    private ActionMode mActionMode;
    private List<OnItemClickListener> clickListeners = new ArrayList<>();
    private SelectionTracker<Long> mSelectionTracker;

    public ReadingsListAdapter(FragmentActivity activity, ReadingsListViewModel readingsListViewModel, long tankId) {
        super();
        this.mActivity = activity;
        this.mReadingsListViewModel = readingsListViewModel;
        mTankId = tankId;
        setHasStableIds(true);
    }

    @NonNull
    @Override
    public ReadingsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflate the layout, initialize the View Holder
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_readings, parent, false);
        return new ReadingsViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final ReadingsViewHolder holder, final int position) {
        // Use the provided View Holder on the onCreateViewHolder method to populate the
        // current row on the RecyclerView
        // TODO: update to readings
        final Readings reading = mReadings.get(position);
        holder.id = reading.readingId;

        holder.mReading = reading;

        holder.mAmmoniaValueText.setText(String.valueOf(reading.ammonia));
        holder.mPhValueText.setText(String.valueOf(reading.PH));
        holder.mNitriteValueText.setText(String.valueOf(reading.nitrite));
        holder.mNitrateValueText.setText(String.valueOf(reading.nitrate));
        holder.mKhValueText.setText(String.valueOf(reading.KH));
        holder.mGhValueText.setText(String.valueOf(reading.GH));
        holder.mSalinityValueText.setText(String.valueOf(reading.salinity));
        holder.mPhosphateValueText.setText(String.valueOf(reading.phosphate));
        holder.mIronValueText.setText(String.valueOf(reading.iron));
        holder.mCopperValueText.setText(String.valueOf(reading.copper));
        holder.mMagnesiumValueText.setText(String.valueOf(reading.magnesium));
        holder.mCalciumValueText.setText(String.valueOf(reading.calcium));
        holder.mCO2ValueText.setText(String.valueOf(reading.CO2));
        holder.mO2ValueText.setText(String.valueOf(reading.O2));


        // Draw selected border if selected
        if (mSelectionTracker.isSelected(reading.readingId)) {
            holder.itemView.setForeground(mActivity.getDrawable(R.drawable.tank_selected_bg));
        } else {
            holder.itemView.setForeground(null);
        }

        // Keep selection border up-to-date
        mSelectionTracker.addObserver(new SelectionTracker.SelectionObserver<Long>() {
            @Override
            public void onItemStateChanged(@NonNull Long key, boolean selected) {
                super.onItemStateChanged(key, selected);
                if (key == holder.id) {
                    if (selected) {
                        holder.itemView.setForeground(mActivity.getDrawable(R.drawable.tank_selected_bg));
                    } else {
                        holder.itemView.setForeground(null);
                    }
                }
            }
        });

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mActionMode == null) {
                    notifyListeners(holder, reading, reading.readingId);
                }
            }
        });


        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(final View v) {
                if (mActionMode != null) {
                    return false;
                }

                mActionMode = ((AppCompatActivity) mActivity).startSupportActionMode(new ActionMode.Callback() {
                    @Override
                    public boolean onCreateActionMode(ActionMode mode, Menu menu) {
                        MenuInflater inflater = mode.getMenuInflater();
                        inflater.inflate(R.menu.menu_readings_list_context_action, menu);
                        return true;
                    }

                    @Override
                    public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
                        return false;
                    }

                    @Override
                    public boolean onActionItemClicked(final ActionMode mode, final MenuItem item) {
                        final Selection<Long> selection = mSelectionTracker.getSelection();
                        switch (item.getItemId()) {
                            case R.id.action_bar_delete:
                                new AlertDialog.Builder(mActivity)
                                        .setTitle(R.string.remove_readings)
                                        .setMessage(mActivity.getResources().getQuantityString(R.plurals.confirm_delete_readings, selection.size()))
                                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                Toast.makeText(mActivity.getApplicationContext(),
                                                        mActivity.getResources().getQuantityString(R.plurals.readings_deleted, selection.size(), selection.size()),
                                                        Toast.LENGTH_LONG).show();
                                                Readings[] readingsToDelete = new Readings[selection.size()];
                                                int i = 0;
                                                for (Long id : selection) {
                                                    int pos = getPositionOfReadings(id);
                                                    readingsToDelete[i] = mReadings.get(pos);
                                                    i++;
                                                }
                                                mReadingsListViewModel.deleteReadings(readingsToDelete);
                                                mode.finish();
                                            }
                                        })
                                        .setNegativeButton(android.R.string.cancel, null)
                                        .show();
                                return true;
//                             TODO: duplicate/rename/edit?
                            default:
                                return false;
                        }
                    }

                    @Override
                    public void onDestroyActionMode(ActionMode mode) {
                        mActionMode = null;
                        mSelectionTracker.clearSelection();
                    }
                });
                return true;
            }
        });
    }

    @Override
    public void onViewRecycled(@NonNull ReadingsViewHolder holder) {
        super.onViewRecycled(holder);
        holder.showDefault();
    }

    public void updateReadings(List<Readings> readings) {
        DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(new ReadingsDiffCallback(mReadings, readings));
        mReadings.clear();
        mReadings.addAll(readings);
        diffResult.dispatchUpdatesTo(this);
    }

    public int getPositionOfReadings(long id) {
        for (int i = 0; i < mReadings.size(); i++) {
            Readings reading = mReadings.get(i);
            if (reading.readingId == id) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public int getItemCount() {
        return mReadings.size();
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        clickListeners.add(listener);
    }

    public void removeOnItemClickListener(OnItemClickListener listener) {
        clickListeners.remove(listener);
    }

    private void notifyListeners(ReadingsViewHolder vh, Readings reading, long id) {
        for (OnItemClickListener listener : clickListeners) {
            listener.onClick(vh, reading, id);
        }
    }

    public void setSelectionTracker(SelectionTracker<Long> selectionTracker) {
        this.mSelectionTracker = selectionTracker;
        this.mSelectionTracker.addObserver(new SelectionTracker.SelectionObserver() {
            @Override
            public void onSelectionChanged() {
                super.onSelectionChanged();
                if (mActionMode != null) {
                    if (mSelectionTracker.getSelection().size() == 0) {
                        mActionMode.finish();
                    }
                }
            }
        });
    }

    public interface OnItemClickListener {
        void onClick(ReadingsViewHolder vh, Readings reading, long id);
    }

    @Override
    public long getItemId(int position) {
        return mReadings.get(position).readingId;
    }

}
