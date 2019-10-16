package uk.co.mholeys.android.tankmanager.view.adapter;

import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.selection.ItemDetailsLookup;
import androidx.recyclerview.selection.ItemKeyProvider;
import androidx.recyclerview.selection.SelectionTracker;
import androidx.recyclerview.widget.RecyclerView;

public class SelectionAdapter {
    public static class Predicate extends SelectionTracker.SelectionPredicate<Long> {

        @Override
        public boolean canSetStateForKey(@NonNull Long id, boolean newState) {
            return true;
        }

        @Override
        public boolean canSetStateAtPosition(int i, boolean b) {
            return true;
        }

        @Override
        public boolean canSelectMultiple() {
            return true;
        }
    }

    public static class KeyProvider extends ItemKeyProvider<Long> {

        private final TankListAdapter mAdapter;
        RecyclerView mRecyclerView;

        public KeyProvider(RecyclerView recyclerView, TankListAdapter adapter) {
            super(SCOPE_MAPPED);
            this.mRecyclerView = recyclerView;
            this.mAdapter = adapter;
        }

        @Nullable
        @Override
        public Long getKey(int position) {
            return mAdapter.getItemId(position);
        }

        @Override
        public int getPosition(@NonNull Long id) {
            return mAdapter.getPositionOfTank(id);
        }
    }

    public static class DetailsLookup extends ItemDetailsLookup<Long> {

        private final TankListAdapter mAdapter;
        RecyclerView mRecyclerView;

        public DetailsLookup(RecyclerView recyclerView, TankListAdapter adapter) {
            this.mRecyclerView = recyclerView;
            this.mAdapter = adapter;
        }

        @Nullable
        @Override
        public ItemDetailsLookup.ItemDetails<Long> getItemDetails(@NonNull MotionEvent e) {
            View view = mRecyclerView.findChildViewUnder(e.getX(), e.getY());
            if (view != null) {
                RecyclerView.ViewHolder viewHolder = mRecyclerView.getChildViewHolder(view);
                if (viewHolder instanceof TankViewHolder) {
                    return ((TankViewHolder) viewHolder).getItemDetails();
                }
            }
            return null;
        }
    }
}
