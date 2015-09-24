package com.example.android.rssreader;

import android.app.Application;

import com.example.android.rssfeedlibrary.RssItem;

import java.util.ArrayList;
import java.util.List;

public class RssApplication extends Application {
    public final static String URL ="url";
    public final static String RSS_UPDATE ="neuedaten";
    public static List<RssItem> list = new ArrayList<>();
}
