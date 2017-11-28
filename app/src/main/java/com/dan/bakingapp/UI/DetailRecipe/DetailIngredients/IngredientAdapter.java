package com.dan.bakingapp.UI.DetailRecipe.DetailIngredients;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dan.bakingapp.Models.Ingredient;
import com.dan.bakingapp.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Dat T Do on 10/6/2017.
 */

public class IngredientAdapter extends RecyclerView.Adapter<com.dan.bakingapp.UI.DetailRecipe.DetailIngredients.IngredientAdapter.RecyclerViewHolder> {
    private List<Ingredient> mIngredientList;
    private LayoutInflater mInflater;
    private Context mContext;

    public IngredientAdapter(Context context, ArrayList<Ingredient> ingredientList) {
        this.mContext = context;
        this.mIngredientList = ingredientList;
    }


    @Override
    public com.dan.bakingapp.UI.DetailRecipe.DetailIngredients.IngredientAdapter.RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        int layoutIdForListItem = R.layout.cards_ingredient;

        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(layoutIdForListItem, parent, false);
        com.dan.bakingapp.UI.DetailRecipe.DetailIngredients.IngredientAdapter.RecyclerViewHolder viewHolder = new com.dan.bakingapp.UI.DetailRecipe.DetailIngredients.IngredientAdapter.RecyclerViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(com.dan.bakingapp.UI.DetailRecipe.DetailIngredients.IngredientAdapter.RecyclerViewHolder holder, int position) {
        holder.nameTextView.setText(mIngredientList.get(position).getIngredient());
        holder.quantityTextView.setText(mIngredientList.get(position).getQuantity().toString() + " " + mIngredientList.get(position).getMeasure());
    }


    @Override
    public int getItemCount() {
        return (mIngredientList == null) ? 0 : mIngredientList.size();
    }


    class RecyclerViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.ingredient_name)
        TextView nameTextView;
        @Bind(R.id.ingredient_quantity)
        TextView quantityTextView;

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
