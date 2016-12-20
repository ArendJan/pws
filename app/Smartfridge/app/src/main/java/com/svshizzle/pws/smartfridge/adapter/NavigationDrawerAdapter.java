package com.svshizzle.pws.smartfridge.adapter;

/**
 * Created by Arend-Jan on 3-10-2016.
 * ____  ____  _____ _      ____           _  ____  _
 * /  _ \/  __\/  __// \  /|/  _ \         / |/  _ \/ \  /|
 * | / \||  \/||  \  | |\ ||| | \|_____    | || / \|| |\ ||
 * | |-|||    /|  /_ | | \||| |_/|\____\/\_| || |-||| | \||
 * \_/ \|\_/\_\\____\\_/  \|\____/      \____/\_/ \|\_/  \|
 */
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.svshizzle.pws.smartfridge.R;
import com.svshizzle.pws.smartfridge.model.NavDrawerItem;

import java.util.Collections;
import java.util.List;


public class NavigationDrawerAdapter extends RecyclerView.Adapter<NavigationDrawerAdapter.MyViewHolder> {
    private List<NavDrawerItem> data = Collections.emptyList();
    private LayoutInflater inflater;


    public NavigationDrawerAdapter(Context context, List<NavDrawerItem> data) {

        inflater = LayoutInflater.from(context);
        this.data = data;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.nav_drawer_row, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        NavDrawerItem current = data.get(position);
        holder.title.setText(current.getTitle());
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView title;

        MyViewHolder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.log_row_title);
        }
    }
}