package uk.co.mholeys.android.tankmanager.view.ui.maintenance;

import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.tabs.TabLayout;

import uk.co.mholeys.android.tankmanager.R;
import uk.co.mholeys.android.tankmanager.view.adapter.MaintenancePagerAdapter;
import uk.co.mholeys.android.tankmanager.viewmodel.MaintenanceTabViewModel;

public class MaintenanceTabFragment extends Fragment {

    private static final String ARG_TANK_ID = "MAINTENANCE_TAB_FRAGMENT._ARG_TANK_ID";

    private MaintenanceTabViewModel mViewModel;
    private long mTankId;
    private ViewPager viewPager;
    private MaintenancePagerAdapter maintenancePagerAdapter;

    public static MaintenanceTabFragment newInstance(long tankId) {
        MaintenanceTabFragment fragment = new MaintenanceTabFragment();
        Bundle args = new Bundle();
        args.putLong(ARG_TANK_ID, tankId);
        fragment.setArguments(args);
        return fragment;
    }

    public MaintenanceTabFragment() { }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.maintenance_tab_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        if (getArguments() != null) {
            mTankId = getArguments().getLong(ARG_TANK_ID);
        }

        maintenancePagerAdapter = new MaintenancePagerAdapter(getChildFragmentManager(), mTankId, getResources());
        viewPager = view.findViewById(R.id.pager);
        viewPager.setAdapter(maintenancePagerAdapter);

        TabLayout tabLayout = getView().findViewById(R.id.tab_layout);
        tabLayout.setupWithViewPager(viewPager);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(MaintenanceTabViewModel.class);
    }
}

