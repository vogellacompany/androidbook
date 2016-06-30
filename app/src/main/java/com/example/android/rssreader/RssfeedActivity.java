package com.example.android.rssreader;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Toast;
import android.widget.Toolbar;

import com.example.android.rssfeedlibrary.RssItem;

public class RssfeedActivity extends Activity implements MyListFragment.OnItemSelectedListener, ActionMode.Callback {

    private RssItem selectedRssItem;
    private Toolbar tb;
    Handler handler;
    View refreshButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tb = (Toolbar) findViewById(R.id.toolbar);
        setActionBar(tb);

        if (findViewById(R.id.fragment_container) == null) {
            // all good, we use the fragments defined in the layout
            return;
        }
        // if savedInstanceState is null we need to load at least one fragment
        if (savedInstanceState != null) {
            // cleanup any existing fragments in case we are in detailed mode
            getFragmentManager().executePendingTransactions();
            Fragment fragmentById = getFragmentManager().findFragmentById(R.id.fragment_container);
            if (fragmentById!=null) {
                getFragmentManager().beginTransaction()
                        .remove(fragmentById).commit();
            }
        }
        MyListFragment listFragment = new MyListFragment();
        FrameLayout viewById = (FrameLayout) findViewById(R.id.fragment_container);
        getFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, listFragment).commit();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        Toolbar tb = (Toolbar) findViewById(R.id.toolbar);
        tb.inflateMenu(R.menu.mainmenu);
        tb.setOnMenuItemClickListener(
                new Toolbar.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        return onOptionsItemSelected(item);
                    }
                });
        return true;
    }



    // #<3>
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
//                refreshButton.animate().rotation(100f).setDuration(2000);
//                RotateAnimation rotate = new RotateAnimation(180, 360, Animation.RELATIVE_TO_SELF,
//                        0.5f,  Animation.RELATIVE_TO_SELF, 0.5f);
//                rotate.setDuration(1000);
//                rotate.setRepeatCount(Animation.INFINITE);
//                refreshButton.startAnimation(rotate);
//                refreshButton.clearAnimation();

//                refreshButton.setEnabled(false);
            case R.id.action_settings:
                Intent intent = new Intent(this, MyPreferences.class);
                startActivity(intent);
                Toast.makeText(this, "Action Settings selected", Toast.LENGTH_SHORT)
                        .show();
                return true;
            default:
                break;
        }

        return  super.onOptionsItemSelected(item);
    }


    @Override
    public void onRssItemSelected(String link) {
        if (getResources().getBoolean(R.bool.twoPaneMode)) {
            DetailFragment fragment = (DetailFragment) getFragmentManager()
                    .findFragmentById(R.id.detailFragment);
            fragment.setText(link);
        } else {
            // replace the fragment
            // Create fragment and give it an argument for the selected article
            DetailFragment newFragment = new DetailFragment();
            Bundle args = new Bundle();
            args.putString(DetailFragment.EXTRA_URL, link);
            newFragment.setArguments(args);
            FragmentTransaction transaction = getFragmentManager().beginTransaction();

            // Replace whatever is in the fragment_container view with this fragment,
            // and add the transaction to the back stack so the user can navigate back

            transaction.replace(R.id.fragment_container, newFragment);
            transaction.addToBackStack(null);

            // Commit the transaction
            transaction.commit();

        }
    }



}
