package uk.co.mholeys.android.tankmanager.view.ui.ui.tanklist;

import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import uk.co.mholeys.android.tankmanager.R;

public class TankDetailFragment extends Fragment {

    private TankDetailViewModel mViewModel;

    public static TankDetailFragment newInstance() {
        return new TankDetailFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.tank_detail_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(TankDetailViewModel.class);
        // TODO: Use the ViewModel
    }

}
