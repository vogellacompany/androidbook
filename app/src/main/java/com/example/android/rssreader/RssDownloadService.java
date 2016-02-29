package com.example.android.rssreader;

import android.app.IntentService;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
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
        String s = intent.getExtras().getString(RssApplication.URL);
        List<RssItem> list = RssFeedProvider.parse(s);
        RssApplication.list.clear();
        RssApplication.list.addAll(list);
        Intent i = new Intent("neuedaten");
        sendBroadcast(i);


    }

}
