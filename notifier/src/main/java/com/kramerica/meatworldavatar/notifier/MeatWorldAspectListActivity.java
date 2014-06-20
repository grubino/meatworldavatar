package com.kramerica.meatworldavatar.notifier;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.app.FragmentActivity;
import android.util.Log;


/**
 * An activity representing a list of MeatWorldAspects. This activity
 * has different presentations for handset and tablet-size devices. On
 * handsets, the activity presents a list of items, which when touched,
 * lead to a {@link MeatWorldAspectDetailActivity} representing
 * item details. On tablets, the activity presents the list of items and
 * item details side-by-side using two vertical panes.
 * <p>
 * The activity makes heavy use of fragments. The list of items is a
 * {@link MeatWorldAspectListFragment} and the item details
 * (if present) is a {@link MeatWorldAspectDetailFragment}.
 * <p>
 * This activity also implements the required
 * {@link MeatWorldAspectListFragment.Callbacks} interface
 * to listen for item selections.
 */
public class MeatWorldAspectListActivity extends FragmentActivity
        implements MeatWorldAspectListFragment.Callbacks {

    /**
     * Whether or not the activity is in two-pane mode, i.e. running on a tablet
     * device.
     */
    private boolean mTwoPane;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meatworldaspect_list);
        bindService(new Intent(this, MeatWorldDataSink.class), new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName componentName, IBinder iBinder) {}

            @Override
            public void onServiceDisconnected(ComponentName componentName) {}
        }, BIND_AUTO_CREATE);

        if (findViewById(R.id.meatworldaspect_detail_container) != null) {
            // The detail container view will be present only in the
            // large-screen layouts (res/values-large and
            // res/values-sw600dp). If this view is present, then the
            // activity should be in two-pane mode.
            mTwoPane = true;

            // In two-pane mode, list items should be given the
            // 'activated' state when touched.
            ((MeatWorldAspectListFragment) getSupportFragmentManager()
                    .findFragmentById(R.id.meatworldaspect_list))
                    .setActivateOnItemClick(true);
        }

        // TODO: If exposing deep links into your app, handle intents here.
    }

    /**
     * Callback method from {@link MeatWorldAspectListFragment.Callbacks}
     * indicating that the item with the given ID was selected.
     */
    @Override
    public void onItemSelected(String id) {
        if (mTwoPane) {
            // In two-pane mode, show the detail view in this activity by
            // adding or replacing the detail fragment using a
            // fragment transaction.
            Bundle arguments = new Bundle();
            arguments.putString(MeatWorldAspectDetailFragment.ARG_ITEM_ID, id);
            MeatWorldAspectDetailFragment fragment = new MeatWorldAspectDetailFragment();
            fragment.setArguments(arguments);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.meatworldaspect_detail_container, fragment)
                    .commit();

        } else {
            // In single-pane mode, simply start the detail activity
            // for the selected item ID.
            Intent detailIntent = new Intent(this, MeatWorldAspectDetailActivity.class);
            detailIntent.putExtra(MeatWorldAspectDetailFragment.ARG_ITEM_ID, id);
            startActivity(detailIntent);
        }
    }
}
