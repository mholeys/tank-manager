package uk.co.mholeys.android.tankmanager.view.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import uk.co.mholeys.android.tankmanager.R;
import uk.co.mholeys.android.tankmanager.view.ui.ui.tanklist.TankListFragment;

public class TankListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tank_list_activity);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, TankListFragment.newInstance())
                    .commitNow();
        }
    }
}
