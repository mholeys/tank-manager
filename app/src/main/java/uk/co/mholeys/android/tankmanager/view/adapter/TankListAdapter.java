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
import uk.co.mholeys.android.tankmanager.model.entity.Tank;
import uk.co.mholeys.android.tankmanager.viewmodel.TankListViewModel;

public class TankListAdapter extends RecyclerView.Adapter<TankViewHolder> {

    private static final String TAG = TankListAdapter.class.getSimpleName();

    private final TankListViewModel mTankListViewModel;
    private FragmentActivity mActivity;
    private List<Tank> mTanks = new ArrayList<>();
    private ActionMode mActionMode;
    private List<OnItemClickListener> clickListeners = new ArrayList<>();
    private SelectionTracker<Long> mSelectionTracker;

    public TankListAdapter(FragmentActivity activity, TankListViewModel imageListViewModel) {
        super();
        this.mActivity = activity;
        this.mTankListViewModel = imageListViewModel;
        setHasStableIds(true);
    }

    @NonNull
    @Override
    public TankViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflate the layout, initialize the View Holder
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_tank, parent, false);
        return new TankViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final TankViewHolder holder, final int position) {
        // Use the provided View Holder on the onCreateViewHolder method to populate the
        // current row on the RecyclerView

        final Tank tank = mTanks.get(position);
        holder.id = tank.getId();
        holder.mTank = tank;
        holder.mTitleTextView.setText(tank.name);
        holder.mInfoTextView.setText(tank.size + " " + tank.units + " ("  + tank.type.toString() + ")");


        // Draw selected border if selected
        if (mSelectionTracker.isSelected(tank.getId())) {
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
                    notifyListeners(holder, tank, tank.getId());
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
                        inflater.inflate(R.menu.menu_tank_list_context_action, menu);
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
                                        .setTitle(R.string.remove_tank)
                                        .setMessage(mActivity.getResources().getQuantityString(R.plurals.confirm_delete_tank, selection.size()))
                                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                Toast.makeText(mActivity.getApplicationContext(),
                                                        mActivity.getResources().getQuantityString(R.plurals.tanks_deleted, selection.size(), selection.size()),
                                                        Toast.LENGTH_LONG).show();
                                                Tank[] tanksToDelete = new Tank[selection.size()];
                                                int i = 0;
                                                for (Long id : selection) {
                                                    int pos = getPositionOfTank(id);
                                                    tanksToDelete[i] = mTanks.get(pos);
                                                    i++;
                                                }
                                                mTankListViewModel.deleteTanks(tanksToDelete);
                                                mode.finish();
                                            }
                                        })
                                        .setNegativeButton(android.R.string.cancel, null)
                                        .show();
                                return true;
                            // TODO: duplicate
                            // TODO: rename?
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
    public void onViewRecycled(@NonNull TankViewHolder holder) {
        super.onViewRecycled(holder);
        holder.showDefault();
    }

    public void updateTanks(List<Tank> tanks) {
        DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(new TankDiffCallback(mTanks, tanks));
        mTanks.clear();
        mTanks.addAll(tanks);
        diffResult.dispatchUpdatesTo(this);
    }

    public int getPositionOfTank(long id) {
        for (int i = 0; i < mTanks.size(); i++) {
            Tank image = mTanks.get(i);
            if (image.getId() == id) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public int getItemCount() {
        return mTanks.size();
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        clickListeners.add(listener);
    }

    public void removeOnItemClickListener(OnItemClickListener listener) {
        clickListeners.remove(listener);
    }

    private void notifyListeners(TankViewHolder vh, Tank tank, long id) {
        for (OnItemClickListener listener : clickListeners) {
            listener.onClick(vh, tank, id);
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
        void onClick(TankViewHolder vh, Tank tank, long id);
    }

    @Override
    public long getItemId(int position) {
        return mTanks.get((int)position).getId();
    }

}
