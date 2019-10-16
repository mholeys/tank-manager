package uk.co.mholeys.android.tankmanager.view.ui.ui.tanklist;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.selection.SelectionTracker;
import androidx.recyclerview.selection.StorageStrategy;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import uk.co.mholeys.android.tankmanager.R;
import uk.co.mholeys.android.tankmanager.model.entity.Tank;
import uk.co.mholeys.android.tankmanager.view.adapter.SelectionAdapter;
import uk.co.mholeys.android.tankmanager.view.adapter.TankListAdapter;
import uk.co.mholeys.android.tankmanager.view.adapter.TankViewHolder;
import uk.co.mholeys.android.tankmanager.viewmodel.TankListViewModel;

public class TankListFragment extends Fragment {

    private RecyclerView mRecyclerView;
    private TankListAdapter mAdapter;
    private TankListViewModel mViewModel;
    private SelectionTracker<Long> mSelectionTracker;

    public static TankListFragment newInstance() {
        return new TankListFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.tank_list_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(TankListViewModel.class);
        mAdapter = new TankListAdapter(getActivity(), mViewModel);
        // When the user picks a tank open up the tank specific page
        mAdapter.setOnItemClickListener(new TankListAdapter.OnItemClickListener() {
            @Override
            public void onClick(TankViewHolder vh, Tank tank, long id) {
                Fragment fragment = TankDetailFragment.newInstance();

                FragmentManager fm = TankListFragment.this.getFragmentManager();
                FragmentTransaction transaction = fm.beginTransaction();
                transaction.replace(R.id.container, fragment);
                transaction.commit();
            }
        });
        mRecyclerView.setAdapter(mAdapter);

        // Selection tracker
        mSelectionTracker = new SelectionTracker.Builder<>("my_selection",
                mRecyclerView,
                new SelectionAdapter.KeyProvider(mRecyclerView, mAdapter),
                new SelectionAdapter.DetailsLookup(mRecyclerView, mAdapter),
                StorageStrategy.createLongStorage())
                .withSelectionPredicate(new SelectionAdapter.Predicate())
                .build();

        mAdapter.setSelectionTracker(mSelectionTracker);
    }

}
