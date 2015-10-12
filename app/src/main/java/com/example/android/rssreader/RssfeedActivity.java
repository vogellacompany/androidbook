package com.example.android.rssreader;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;
import android.widget.Toolbar;

import com.example.android.rssfeedlibrary.RssItem;

public class RssfeedActivity extends Activity implements MyListFragment.OnItemSelectedListener, ActionMode.Callback {

    private RssItem selectedRssItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar tb = (Toolbar) findViewById(R.id.toolbar);
        setActionBar(tb);
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

    //NEW
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_refresh:
                Intent i = new Intent(this, RssDownloadService.class);
                i.putExtra(RssApplication.URL, "http://www.vogella.com/article.rss");
                startService(i);
                break;
            case R.id.action_settings:
                Intent intent = new Intent(this, MyPreferences.class);
                startActivity(intent);
                Toast.makeText(this, "Action Settings selected", Toast.LENGTH_SHORT)
                        .show();
                break;

            default:
                break;
        }

        return true;
    }


    @Override
    public void onRssItemSelected(String link) {
        if (getResources().getBoolean(R.bool.twoPaneMode)) {
            DetailFragment fragment = (DetailFragment) getFragmentManager()
                    .findFragmentById(R.id.detailFragment);
            fragment.setText(link);
        } else {
            Intent intent = new Intent(getApplicationContext(),
                    DetailActivity.class);
            intent.putExtra(DetailActivity.EXTRA_URL, link);
            startActivity(intent);
        }
    }

    @Override
    public void goToActionMode(RssItem item) {
        this.selectedRssItem = item;
        startActionMode(this);
    }

    @Override
    public boolean onCreateActionMode(ActionMode mode, Menu menu) {
        MenuInflater inflater = mode.getMenuInflater();
        inflater.inflate(R.menu.actionmode, menu);
        return true;
    }

    @Override
    public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
        return false;
    }

    @Override
    public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.putExtra(Intent.EXTRA_TEXT, "I found this interesting link" + selectedRssItem.getLink());
        intent.setType("text/plain");
        startActivity(intent);
        mode.finish(); // Action picked, so close the CAB
        selectedRssItem = null;
        return true;
    }

    @Override
    public void onDestroyActionMode(ActionMode mode) {

    }
}
