package com.dan.bakingapp.Adapter;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.dan.bakingapp.Models.Step;
import com.dan.bakingapp.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Dat T Do on 9/28/2017.
 */

public class DetailAdapter extends RecyclerView.Adapter<DetailAdapter.RecyclerViewHolder> {
    private List<Step> mStepList;
    private LayoutInflater mInflater;
    private Context mContext;

    public DetailAdapter(Context context, ArrayList<Step> stepList) {
        this.mContext = context;
        this.mStepList = stepList;
    }




    @Override
    public DetailAdapter.RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        int layoutIdForListItem = R.layout.step_cards;

        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(layoutIdForListItem, parent,  false);
        DetailAdapter.RecyclerViewHolder viewHolder = new DetailAdapter.RecyclerViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(DetailAdapter.RecyclerViewHolder holder, int position) {
        holder.textRecyclerView.setText(mStepList.get(position).getShortDescription());
        String imageUrl=mStepList.get(position).getThumbnailURL();

        if (imageUrl!="") {
            Uri builtUri = Uri.parse(imageUrl).buildUpon().build();
            Picasso.with(mContext).load(builtUri).into(holder.imageRecyclerView);
        }
    }


    @Override
    public int getItemCount() {
        return (mStepList == null) ? 0 : mStepList.size();
    }


    class RecyclerViewHolder extends RecyclerView.ViewHolder  {
        @Bind(R.id.step_short_description)
        TextView textRecyclerView;
        @Bind(R.id.step_image)
        ImageView imageRecyclerView;


        public RecyclerViewHolder(View itemView) {
            super(itemView);
            try {
                ButterKnife.bind(this, itemView);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }


}
