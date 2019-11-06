package uk.co.mholeys.android.tankmanager.view.ui.maintenance;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import uk.co.mholeys.android.tankmanager.AMaintenanceTabFragment;
import uk.co.mholeys.android.tankmanager.R;

public class ReadingsFragment extends AMaintenanceTabFragment {

    private static final String ARG_TANK_ID = "READINGS_FRAGMENT._ARG_TANK_ID";

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
        if (getArguments() != null) {
            tankId = getArguments().getLong(ARG_TANK_ID);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_readings, container, false);
    }
}
