package uk.co.mholeys.android.tankmanager.view.ui.readings;

import android.content.Intent;
import android.os.Bundle;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.selection.SelectionTracker;
import androidx.recyclerview.selection.StorageStrategy;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import uk.co.mholeys.android.tankmanager.model.entity.Readings;
import uk.co.mholeys.android.tankmanager.model.entity.Tank;
import uk.co.mholeys.android.tankmanager.view.adapter.ReadingsListAdapter;
import uk.co.mholeys.android.tankmanager.view.adapter.ReadingsSelectionAdapter;
import uk.co.mholeys.android.tankmanager.view.adapter.ReadingsViewHolder;
import uk.co.mholeys.android.tankmanager.view.ui.maintenance.AMaintenanceTabFragment;
import uk.co.mholeys.android.tankmanager.R;
import uk.co.mholeys.android.tankmanager.viewmodel.ReadingsListViewModel;

public class ReadingsFragment extends AMaintenanceTabFragment {

    private static final String TAG = ReadingsFragment.class.getSimpleName();

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
        mViewModel.setup(tankId);

        mLayoutManager = new GridLayoutManager(getContext(), 1);

        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setHasFixedSize(true);

        mAdapter = new ReadingsListAdapter(getActivity(), mViewModel, tankId);
        // When the user picks a reading open up the that reading view
        mAdapter.setOnItemClickListener(new ReadingsListAdapter.OnItemClickListener() {
            @Override
            public void onClick(ReadingsViewHolder vh, Readings reading, long id) {
                // TODO: consider adding popup to select view/edit
                Intent intent = new Intent(getContext(), ReadingsEditActivity.class);
                intent.putExtra(ReadingsEditActivity.EXTRA_ID, id);
                intent.putExtra(ReadingsEditActivity.EXTRA_TANK_ID, tankId);
                intent.putExtra(ReadingsEditActivity.EXTRA_MODE, ReadingsEditActivity.EXTRA_MODE_EDIT);
                startActivity(intent);
            }
        });
        mRecyclerView.setAdapter(mAdapter);

        mAddReadingButton = view.findViewById(R.id.add_reading_button);
        mAddReadingButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), ReadingsEditActivity.class);
                intent.putExtra(ReadingsEditActivity.EXTRA_TANK_ID, tankId);
                intent.putExtra(ReadingsEditActivity.EXTRA_MODE, ReadingsEditActivity.EXTRA_MODE_CREATE);
                startActivity(intent);
            }
        });


        mSelectionTracker = new SelectionTracker.Builder<>("reading_selection",
                mRecyclerView,
                new ReadingsSelectionAdapter.KeyProvider(mRecyclerView, mAdapter),
                new ReadingsSelectionAdapter.DetailsLookup(mRecyclerView, mAdapter),
                StorageStrategy.createLongStorage())
                .withSelectionPredicate(new ReadingsSelectionAdapter.Predicate())
                .build();

        mAdapter.setSelectionTracker(mSelectionTracker);

        Observer<List<Readings>> readingsObserver = new Observer<List<Readings>>() {
            @Override
            public void onChanged(@Nullable List<Readings> readings) {
                mAdapter.updateReadings(readings);

                Log.d(TAG, "Got list of readings, size " + readings.size());
                for (Readings r : readings) {
                    Log.d(TAG, r.tankId + " " + r.readingId + " " + r.ammonia);
                }

                if (readings.size() < 1) {
                    mRecyclerView.setVisibility(View.GONE);
                    mEmptyMessageView.setVisibility(View.VISIBLE);
                } else {
                    mRecyclerView.setVisibility(View.VISIBLE);
                    mEmptyMessageView.setVisibility(View.GONE);
                }
            }
        };
        mViewModel.getReadingsToDisplay().observe(this, readingsObserver);

        return view;
    }
}
