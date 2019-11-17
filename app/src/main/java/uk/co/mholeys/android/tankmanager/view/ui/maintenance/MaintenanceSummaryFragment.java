package uk.co.mholeys.android.tankmanager.view.ui.maintenance;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import uk.co.mholeys.android.tankmanager.R;
import uk.co.mholeys.android.tankmanager.model.entity.Maintenance;
import uk.co.mholeys.android.tankmanager.model.entity.ScheduledMaintenance;
import uk.co.mholeys.android.tankmanager.view.ui.MaintenanceActivity;
import uk.co.mholeys.android.tankmanager.viewmodel.MaintenanceSummaryViewModel;

public class MaintenanceSummaryFragment extends Fragment {

    private static final String ARG_TANK_ID = "MAINTENANCE_SUMMARY_FRAGMENT._ARG_TANK_ID";

    private MaintenanceSummaryViewModel mViewModel;

    private TextView lastMaintenanceTypeTextView;
    private TextView lastMaintenanceDateTextView;
    private TextView nextWaterChangeDateTextView;
    private TextView nextTaskTypeTextView;
    private TextView nextTaskDateTextView;

    private TextView lastTextView;
    private TextView nextWaterChangeTextView;
    private TextView nextMaintenanceTextView;
    private long tankId;

    public static MaintenanceTabFragment newInstance(long tankId) {
        MaintenanceTabFragment fragment = new MaintenanceTabFragment();
        Bundle args = new Bundle();
        args.putLong(ARG_TANK_ID, tankId);
        fragment.setArguments(args);
        return fragment;
    }

    public MaintenanceSummaryFragment() { }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.maintenance_summary_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(MaintenanceSummaryViewModel.class);

        lastMaintenanceTypeTextView = getView().findViewById(R.id.last_maintenance_type_text_view);
        lastMaintenanceDateTextView = getView().findViewById(R.id.last_maintenance_date_text_view);
        nextWaterChangeDateTextView = getView().findViewById(R.id.next_water_change_date_text_view);
        nextTaskTypeTextView = getView().findViewById(R.id.next_task_type_text_view);
        nextTaskDateTextView = getView().findViewById(R.id.next_task_date_text_view);

        // Labels
        lastTextView = getView().findViewById(R.id.last_text_view);
        nextWaterChangeTextView = getView().findViewById(R.id.next_water_change_text_view);
        nextMaintenanceTextView = getView().findViewById(R.id.next_maintenance_text_view);

        if (tankId == -1) {
            //failed
            lastTextView.setText(R.string.failed_to_load);
            nextWaterChangeTextView.setVisibility(View.GONE);
            nextMaintenanceTextView.setVisibility(View.GONE);
        } else {
            mViewModel.setup(tankId);
        }

        mViewModel.getLastMaintenance().observe(this, new Observer<Maintenance>() {
            @Override
            public void onChanged(Maintenance maintenance) {
                if (maintenance == null) {
                    lastTextView.setText(R.string.no_maintenance_done);
                    lastMaintenanceTypeTextView.setVisibility(View.GONE);
                    lastMaintenanceDateTextView.setVisibility(View.GONE);
                } else {
                    lastTextView.setText(R.string.last_maintenance);
                    lastMaintenanceTypeTextView.setVisibility(View.VISIBLE);
                    lastMaintenanceDateTextView.setVisibility(View.VISIBLE);
                    lastMaintenanceTypeTextView.setText(maintenance.type.toString());
                    lastMaintenanceDateTextView.setText(maintenance.date.toString());
                }
            }
        });

        mViewModel.getNextWaterChange().observe(this, new Observer<ScheduledMaintenance>() {
            @Override
            public void onChanged(ScheduledMaintenance maintenance) {
                if (maintenance == null) {
                    nextWaterChangeTextView.setText(R.string.no_scheduled_water_changes);
                    nextWaterChangeDateTextView.setVisibility(View.GONE);
                } else {
                    nextWaterChangeTextView.setText(R.string.next_water_change);
                    nextWaterChangeDateTextView.setVisibility(View.VISIBLE);
                    nextWaterChangeDateTextView.setText(maintenance.date.toString());
                }
            }
        });

        mViewModel.getNextMaintenance().observe(this, new Observer<ScheduledMaintenance>() {
            @Override
            public void onChanged(ScheduledMaintenance maintenance) {
                if (maintenance == null) {
                    nextMaintenanceTextView.setText(R.string.no_scheduled_maintenance);
                    nextTaskTypeTextView.setVisibility(View.GONE);
                    nextTaskDateTextView.setVisibility(View.GONE);
                } else {
                    nextMaintenanceTextView.setText(R.string.next_task);
                    nextTaskTypeTextView.setVisibility(View.VISIBLE);
                    nextTaskDateTextView.setVisibility(View.VISIBLE);
                    nextTaskTypeTextView.setText(maintenance.type.toString());
                    nextTaskDateTextView.setText(maintenance.date.toString());
                }
            }
        });



        getView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO: open maintenance details activity
                Intent intent = new Intent(getActivity(), MaintenanceActivity.class);
                intent.putExtra(MaintenanceActivity.EXTRA_TANK_ID, tankId);
                startActivity(intent);

            }
        });

    }


}
