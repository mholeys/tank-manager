package uk.co.mholeys.android.tankmanager.view.ui.tank;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.selection.SelectionTracker;
import androidx.recyclerview.selection.StorageStrategy;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import java.util.List;

import uk.co.mholeys.android.tankmanager.R;
import uk.co.mholeys.android.tankmanager.model.entity.Tank;
import uk.co.mholeys.android.tankmanager.view.adapter.TankSelectionAdapter;
import uk.co.mholeys.android.tankmanager.view.adapter.TankListAdapter;
import uk.co.mholeys.android.tankmanager.view.adapter.TankViewHolder;
import uk.co.mholeys.android.tankmanager.viewmodel.TankListViewModel;

public class TankListActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private TextView mEmptyMessageView;
    private TankListAdapter mAdapter;
    private TankListViewModel mViewModel;
    private SelectionTracker<Long> mSelectionTracker;
    private GridLayoutManager mLayoutManager;
    private boolean isInSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_tanks);

        mViewModel = ViewModelProviders.of(this).get(TankListViewModel.class);
        mRecyclerView = findViewById(R.id.tank_list_recycler_view);
        mEmptyMessageView = findViewById(R.id.empty_message_view);

        mLayoutManager = new GridLayoutManager(this, 1);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setHasFixedSize(true);

        mAdapter = new TankListAdapter(this, mViewModel);
        // When the user picks a tank open up the tank specific page
        mAdapter.setOnItemClickListener(new TankListAdapter.OnItemClickListener() {
            @Override
            public void onClick(TankViewHolder vh, Tank tank, long id) {
                Intent intent = new Intent(TankListActivity.this, TankDetailActivity.class);
                intent.putExtra(TankDetailActivity.EXTRA_ID, id);
                startActivity(intent);
            }
        });
        mRecyclerView.setAdapter(mAdapter);

        // Selection tracker
        mSelectionTracker = new SelectionTracker.Builder<>("tank_selection",
                mRecyclerView,
                new TankSelectionAdapter.KeyProvider(mRecyclerView, mAdapter),
                new TankSelectionAdapter.DetailsLookup(mRecyclerView, mAdapter),
                StorageStrategy.createLongStorage())
                .withSelectionPredicate(new TankSelectionAdapter.Predicate())
                .build();

        mAdapter.setSelectionTracker(mSelectionTracker);

        handleIntent(getIntent());


    }

    @Override
    protected void onNewIntent(Intent intent) {
        handleIntent(intent);
    }

    private void handleIntent(Intent intent) {
        String query = "";
        if (intent.getAction().equals(Intent.ACTION_SEARCH)) {
            query = "%" + intent.getStringExtra(SearchManager.QUERY) + "%";
            isInSearch = true;
        } else {
            isInSearch = false;
        }
        setSearchQuery(query);
    }

    public void setSearchQuery(String query) {
        Observer<List<Tank>> tanksObserver = new Observer<List<Tank>>() {
            @Override
            public void onChanged(@Nullable List<Tank> tanks) {
                mAdapter.updateTanks(tanks);

                if (isInSearch) {
                    mEmptyMessageView.setText(R.string.no_tanks_found);
                } else {
                    mEmptyMessageView.setText(R.string.no_tanks_yet);
                }

                if (tanks.size() < 1) {
                    mRecyclerView.setVisibility(View.GONE);
                    mEmptyMessageView.setVisibility(View.VISIBLE);
                } else {
                    mRecyclerView.setVisibility(View.VISIBLE);
                    mEmptyMessageView.setVisibility(View.GONE);
                }
            }
        };
        if (isInSearch) {
            mViewModel.getTanksToDisplay(query).observe(this, tanksObserver);
        } else {
            mViewModel.getTanksToDisplay().observe(this, tanksObserver);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_tank_list, menu);
        // Retrieve the SearchView and plug it into SearchManager
        SearchManager searchManager = (SearchManager) getSystemService(SEARCH_SERVICE);
        MenuItem searchMenuItem = menu.findItem(R.id.app_bar_search);
        SearchView searchView = (SearchView) searchMenuItem.getActionView();
        // Listener to go back to the showing all list
        searchMenuItem.setOnActionExpandListener(new MenuItem.OnActionExpandListener() {
            @Override
            public boolean onMenuItemActionExpand(MenuItem item) {
                return true;
            }

            @Override
            public boolean onMenuItemActionCollapse(MenuItem item) {
                isInSearch = false;
                setSearchQuery("");
                return true;
            }
        });
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.app_bar_search:
                // This is handled by the search manager
                return true;
            case R.id.app_bar_add:
                startActivity(new Intent(this, TankEditActivity.class));
                return true;
            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);
        }
    }

}
