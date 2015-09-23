package com.example.android.rssreader;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.rssfeedlibrary.RssItem;

import java.util.List;

public class MyRecyclerAdapter  extends RecyclerView.Adapter<MyRecyclerAdapter.ViewHolder>{
    private List<RssItem> rssItems;
    private MyListFragment myListFragment;

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.rowlayout, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final RssItem rssItem = rssItems.get(position);
        holder.mainLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myListFragment.updateDetail(rssItem.getTitle());
            }
        });
        holder.mainLayout.setOnLongClickListener(new View.OnLongClickListener(){
            @Override
            public boolean onLongClick(View v) {
                myListFragment.goToActionMode(rssItem);
                return true;
            }
        });

        holder.txtHeader.setText(rssItem.getTitle());
        holder.txtFooter.setText(rssItem.getLink());


    }

    public MyRecyclerAdapter(List<RssItem> rssItems, MyListFragment myListFragment) {
        this.rssItems = rssItems;
        this.myListFragment = myListFragment;
    }

    @Override
    public int getItemCount() {
        return rssItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public CardView mainLayout;
        public TextView txtHeader;
        public TextView txtFooter;

        public ViewHolder(View v) {
            super(v);
            mainLayout = (CardView) v;
            txtHeader = (TextView) v.findViewById(R.id.rsstitle);
            txtFooter = (TextView) v.findViewById(R.id.rssurl);
        }
    }
}
