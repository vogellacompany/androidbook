package com.example.android.rssreader;

import android.app.Fragment;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toolbar;

import com.example.android.rssfeedlibrary.RssItem;

import java.util.List;

public class MyListFragment extends Fragment {
    private OnItemSelectedListener listener;
    RssItemAdapter adapter;
    BroadcastReceiver updateReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            updateListContent();
        }
    };

    IntentFilter f = new IntentFilter(RssApplication.RSS_UPDATE);

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_rsslist_overview, container, false);
        RecyclerView mRecyclerView = (RecyclerView) view.findViewById(R.id.my_recycler_view);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(mLayoutManager);
        adapter = new RssItemAdapter(RssApplication.list, this);
        mRecyclerView.setAdapter(adapter);
        return view;
    }



    @Override
    public void onResume() {
        super.onResume();
        getContext().registerReceiver(updateReceiver, f);
    }

    @Override
    public void onPause() {
        super.onPause();
        getContext().unregisterReceiver(updateReceiver);
    }

    public void updateListContent() {
        setListContent(RssApplication.list);
    }

    public void setListContent(List<RssItem> result) {
        adapter.notifyDataSetChanged();
    }

    public void goToActionMode(RssItem item) {
        listener.goToActionMode(item);
    }

    public interface OnItemSelectedListener {
        public void onRssItemSelected(String link);

        public void goToActionMode(RssItem item);
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnItemSelectedListener) {
            listener = (OnItemSelectedListener) context;
        } else {
            throw new ClassCastException(context.toString()
                    + " must implement MyListFragment.OnItemSelectedListener");
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        Toolbar tb = (Toolbar) getActivity().findViewById(R.id.toolbar);
        tb.inflateMenu(R.menu.listfragment_menu);
        MenuItem action_refresh = tb.getMenu().findItem(R.id.action_refresh);
        action_refresh.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                return onOptionsItemSelected(menuItem);
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_refresh:
                updateListContent();
                return true;
            default:
                break;
        }
        return false;
    }

    //    // triggers update of the details fragment
    public void updateDetail(String uri) {
        listener.onRssItemSelected(uri);
    }

}
