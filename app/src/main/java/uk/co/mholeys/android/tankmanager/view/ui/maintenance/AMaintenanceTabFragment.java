package uk.co.mholeys.android.tankmanager.view.ui.maintenance;

import androidx.fragment.app.Fragment;

public abstract class AMaintenanceTabFragment extends Fragment {
    
    public abstract int getTabTextStringResource();

    public static AMaintenanceTabFragment newInstance(long tankId) {
        return null;
    }
    
}
