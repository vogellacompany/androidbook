package com.example.android.rssreader;


import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.TextView;

public class DetailFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_rssitem_detail,
                container, false);
        
        return view;
    }

    public void setText(String item) {
        WebView view = (WebView) getView().findViewById(R.id.webview);
        view.loadUrl(item);
    }
}