package uk.co.mholeys.android.tankmanager.view.ui.maintenance.history;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import uk.co.mholeys.android.tankmanager.view.ui.maintenance.AMaintenanceTabFragment;
import uk.co.mholeys.android.tankmanager.R;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link MaintenanceHistoryFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link MaintenanceHistoryFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MaintenanceHistoryFragment extends AMaintenanceTabFragment {

    private static final String ARG_TANK_ID = "MAINTENANCE_HISTORY_fRAGMENT._ARG_TANK_ID";

    private long tankId;

    public MaintenanceHistoryFragment() { }

    public static MaintenanceHistoryFragment newInstance(long tankId) {
        MaintenanceHistoryFragment fragment = new MaintenanceHistoryFragment();
        Bundle args = new Bundle();
        args.putLong(ARG_TANK_ID, tankId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            tankId = getArguments().getLong(ARG_TANK_ID);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_maintenance_history, container, false);
    }

    @Override
    public int getTabTextStringResource() {
        return R.string.maintenace_history_tab_text;
    }

}
