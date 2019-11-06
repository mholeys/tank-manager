package uk.co.mholeys.android.tankmanager.view.adapter;

import android.content.res.Resources;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.ArrayList;

import uk.co.mholeys.android.tankmanager.AMaintenanceTabFragment;
import uk.co.mholeys.android.tankmanager.view.ui.maintenance.MaintenanceHistoryFragment;
import uk.co.mholeys.android.tankmanager.view.ui.maintenance.ReadingsFragment;

public class MaintenancePagerAdapter extends FragmentPagerAdapter {

    private ArrayList<AMaintenanceTabFragment> pages = new ArrayList<AMaintenanceTabFragment>();

    private long tankId;
    private Resources resources;

    public MaintenancePagerAdapter(FragmentManager childFragmentManager, long tankId, Resources resources) {
        super(childFragmentManager);
        this.tankId = tankId;
        this.resources = resources;
        pages.add(ReadingsFragment.newInstance(tankId));
        pages.add(MaintenanceHistoryFragment.newInstance(tankId));
        pages.add(ReadingsFragment.newInstance(tankId));
    }

    @Override
    public Fragment getItem(int position) {
        return pages.get(position);
    }

    @Override
    public int getCount() {
        return pages.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return resources.getString(pages.get(position).getTabTextStringResource());
    }


}
