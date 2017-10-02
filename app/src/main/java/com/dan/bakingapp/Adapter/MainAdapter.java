package com.dan.bakingapp.Adapter;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.dan.bakingapp.Models.Recipe;
import com.dan.bakingapp.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Adapter all available recipes in Main Activity
 */

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.RecyclerViewHolder> {
    private List<Recipe> mRecipeList;
    private LayoutInflater mInflater;
    private Context mContext;

    public MainAdapter(Context context, ArrayList<Recipe> recipeList) {
        this.mContext = context;
        this.mRecipeList = recipeList;
    }


    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        int layoutIdForListItem = R.layout.recipe_cards;

        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(layoutIdForListItem, parent, false);
        RecyclerViewHolder viewHolder = new RecyclerViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(MainAdapter.RecyclerViewHolder holder, int position) {
        holder.textRecyclerView.setText(mRecipeList.get(position).getName());
        String imageUrl = mRecipeList.get(position).getImage();

        if (imageUrl != "") {
            Uri builtUri = Uri.parse(imageUrl).buildUpon().build();
            Picasso.with(mContext).load(builtUri).into(holder.imageRecyclerView);
        }
    }


    @Override
    public int getItemCount() {
        return (mRecipeList == null) ? 0 : mRecipeList.size();
    }


    class RecyclerViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.recipe_title)
        TextView textRecyclerView;
        @Bind(R.id.recipe_image)
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
