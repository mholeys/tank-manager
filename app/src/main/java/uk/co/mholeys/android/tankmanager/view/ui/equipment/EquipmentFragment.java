package uk.co.mholeys.android.tankmanager.view.ui.equipment;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import uk.co.mholeys.android.tankmanager.R;
import uk.co.mholeys.android.tankmanager.view.ui.maintenance.AMaintenanceTabFragment;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link EquipmentFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EquipmentFragment extends AMaintenanceTabFragment {

    private static final String ARG_TANK_ID = "EQUIPMENT_FRAGMENT._ARG_TANK_ID";

    private long mTankId;


    public EquipmentFragment() {
        // Required empty public constructor
    }

    @Override
    public int getTabTextStringResource() {
        return R.string.equipment_fragment_tab_name;
    }

    public static EquipmentFragment newInstance(long tankId) {
        EquipmentFragment fragment = new EquipmentFragment();
        Bundle args = new Bundle();
        args.putLong(ARG_TANK_ID, tankId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mTankId = getArguments().getLong(ARG_TANK_ID);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_equipment, container, false);
    }

}
