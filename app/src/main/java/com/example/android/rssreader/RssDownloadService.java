package com.example.android.rssreader;

import android.app.IntentService;
import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.widget.Toast;

import com.example.android.rssfeedlibrary.RssFeedProvider;
import com.example.android.rssfeedlibrary.RssItem;

import java.util.List;

/**
 * Created by vogella on 24.09.15.
 */
public class RssDownloadService extends IntentService {

    public RssDownloadService() {
        super("Mein krasser RSS Feedreader...");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Bundle extras = intent.getExtras();
        String url = extras.getString(RssApplication.URL);
        List<RssItem> parse = RssFeedProvider.parse(url);
        RssApplication app = (RssApplication) getApplication();
        app.list.clear();
        app.list.addAll(parse);
        Intent i = new Intent(RssApplication.RSS_UPDATE);
        sendBroadcast(i);
    }
}
