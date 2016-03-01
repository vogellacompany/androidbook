package com.example.android.rssreader;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;

import com.example.android.rssfeedlibrary.RssItem;

public class DetailsActivity extends Activity {

    private  RssItem selectedRssItem;
    private boolean dualPaneMode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activithy_details);
        dualPaneMode = getResources().getBoolean(R.bool.dualPaneMode);
        if (dualPaneMode){
            finish();
        }
        String key1 = getIntent().getStringExtra("key1");
        Fragment fragmentById = getFragmentManager().findFragmentById(R.id.detailFragment);

    }


}
