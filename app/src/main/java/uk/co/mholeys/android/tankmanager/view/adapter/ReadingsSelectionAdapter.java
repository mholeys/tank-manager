package uk.co.mholeys.android.tankmanager.view.adapter;

import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.selection.ItemDetailsLookup;
import androidx.recyclerview.selection.ItemKeyProvider;
import androidx.recyclerview.selection.SelectionTracker;
import androidx.recyclerview.widget.RecyclerView;

public class ReadingsSelectionAdapter {
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

        private final ReadingsListAdapter mAdapter;
        RecyclerView mRecyclerView;

        public KeyProvider(RecyclerView recyclerView, ReadingsListAdapter adapter) {
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
            return mAdapter.getPositionOfReadings(id);
        }
    }

    public static class DetailsLookup extends ItemDetailsLookup<Long> {

        private final ReadingsListAdapter mAdapter;
        RecyclerView mRecyclerView;

        public DetailsLookup(RecyclerView recyclerView, ReadingsListAdapter adapter) {
            this.mRecyclerView = recyclerView;
            this.mAdapter = adapter;
        }

        @Nullable
        @Override
        public ItemDetails<Long> getItemDetails(@NonNull MotionEvent e) {
            View view = mRecyclerView.findChildViewUnder(e.getX(), e.getY());
            if (view != null) {
                RecyclerView.ViewHolder viewHolder = mRecyclerView.getChildViewHolder(view);
                if (viewHolder instanceof ReadingsViewHolder) {
                    return ((ReadingsViewHolder) viewHolder).getItemDetails();
                }
            }
            return null;
        }
    }
}
