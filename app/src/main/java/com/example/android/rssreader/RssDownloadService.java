package com.example.android.rssreader;

import android.app.IntentService;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.IBinder;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.widget.Toast;

import com.example.android.rssfeedlibrary.RssFeedProvider;
import com.example.android.rssfeedlibrary.RssItem;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;

public class RssDownloadService extends IntentService {

    public RssDownloadService() {
        super("RSS Feedreader...");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        SharedPreferences settings =
                PreferenceManager.getDefaultSharedPreferences(this);
        String url = settings.getString("url",RssApplication.URL);

        List<RssItem> list = RssFeedProvider.parse(url);
        RssApplication.list.clear();
        RssApplication.list.addAll(list);
        Intent i = new Intent(RssApplication.RSS_UPDATE);
        sendBroadcast(i);
        Gson gson = new Gson();
        Type type = new TypeToken<List<RssItem>>() {}.getType();
        String json = gson.toJson(list, type);
        try (FileOutputStream openFileOutput =
                     openFileOutput(RssApplication.RSS_FILE,
                             Context.MODE_PRIVATE);) {
                openFileOutput.write(json.getBytes());
        } catch (Exception ex){
            // do nothing
        }
    }

}
