package uk.co.mholeys.android.tankmanager.view.ui.maintenance.readings;

import android.content.Intent;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.selection.SelectionTracker;
import androidx.recyclerview.selection.StorageStrategy;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import uk.co.mholeys.android.tankmanager.model.entity.Readings;
import uk.co.mholeys.android.tankmanager.model.entity.Tank;
import uk.co.mholeys.android.tankmanager.view.adapter.ReadingsListAdapter;
import uk.co.mholeys.android.tankmanager.view.adapter.ReadingsViewHolder;
import uk.co.mholeys.android.tankmanager.view.adapter.SelectionAdapter;
import uk.co.mholeys.android.tankmanager.view.adapter.TankListAdapter;
import uk.co.mholeys.android.tankmanager.view.adapter.TankViewHolder;
import uk.co.mholeys.android.tankmanager.view.ui.maintenance.AMaintenanceTabFragment;
import uk.co.mholeys.android.tankmanager.R;
import uk.co.mholeys.android.tankmanager.view.ui.tank.TankDetailActivity;
import uk.co.mholeys.android.tankmanager.view.ui.tank.TankListActivity;
import uk.co.mholeys.android.tankmanager.viewmodel.ReadingsListViewModel;
import uk.co.mholeys.android.tankmanager.viewmodel.TankListViewModel;

public class ReadingsFragment extends AMaintenanceTabFragment {

    private static final String ARG_TANK_ID = "READINGS_FRAGMENT._ARG_TANK_ID";

    private RecyclerView mRecyclerView;
    private TextView mEmptyMessageView;
    private Button mAddReadingButton;
    private ReadingsListAdapter mAdapter;
    private ReadingsListViewModel mViewModel;
    private SelectionTracker<Long> mSelectionTracker;
    private GridLayoutManager mLayoutManager;

    private long tankId;


    public ReadingsFragment() {
        // Required empty public constructor
    }

    @Override
    public int getTabTextStringResource() {
        return R.string.readings_fragment_tab_text;
    }

    public static ReadingsFragment newInstance(long tankId) {
        ReadingsFragment fragment = new ReadingsFragment();
        Bundle args = new Bundle();
        args.putLong(ARG_TANK_ID, tankId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_readings, container, false);
        mRecyclerView = view.findViewById(R.id.readings_list_recycler_view);
        mEmptyMessageView = view.findViewById(R.id.empty_message_view);

        if (getArguments() != null) {
            tankId = getArguments().getLong(ARG_TANK_ID);
        }

        mViewModel = ViewModelProviders.of(this).get(ReadingsListViewModel.class);

        mLayoutManager = new GridLayoutManager(getContext(), 1);

        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setHasFixedSize(true);

        mAdapter = new ReadingsListAdapter(getActivity(), mViewModel, tankId);
        // When the user picks a reading open up the that reading view
        mAdapter.setOnItemClickListener(new ReadingsListAdapter.OnItemClickListener() {
            @Override
            public void onClick(ReadingsViewHolder vh, Readings reading, long id) {
                // TODO: open readings detail activity/fragment
//                Intent intent = new Intent(getContext(), TankDetailActivity.class);
//                intent.putExtra(TankDetailActivity.EXTRA_ID, id);
//                startActivity(intent);
            }
        });
        mRecyclerView.setAdapter(mAdapter);

        mAddReadingButton = view.findViewById(R.id.add_reading_button);
        mAddReadingButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // TODO: launch add/edit readings activity
            }
        });


        // Selection tracker
//        mSelectionTracker = new SelectionTracker.Builder<>("my_selection",
//                mRecyclerView,
//                new SelectionAdapter.KeyProvider(mRecyclerView, mAdapter),
//                new SelectionAdapter.DetailsLookup(mRecyclerView, mAdapter),
//                StorageStrategy.createLongStorage())
//                .withSelectionPredicate(new SelectionAdapter.Predicate())
//                .build();
//
//        mAdapter.setSelectionTracker(mSelectionTracker);

        return view;
    }
}
