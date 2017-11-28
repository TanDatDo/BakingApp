package com.dan.bakingapp.UI.DetailRecipe.DetailIngredients;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dan.bakingapp.Models.Ingredient;
import com.dan.bakingapp.Models.Recipe;
import com.dan.bakingapp.R;
import com.dan.bakingapp.UI.MainActivity;
import com.dan.bakingapp.widget.IngredientService;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;


public class IngredientFragment extends Fragment {

    @Bind(R.id.detail_ingredient_recycler)
    RecyclerView ingredientRecyclerView;
    private Recipe mRecipe;
    private List<Ingredient> mIngredientList;
    private IngredientAdapter mIngredientAdapter;

    public IngredientFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_ingredient, container, false);
        ButterKnife.bind(this, rootView);

        if (savedInstanceState == null) {
            mRecipe = getActivity().getIntent().getParcelableExtra(MainActivity.IntentKeyWord.SELECTED_RECIPE);
        } else {
            mRecipe = savedInstanceState.getParcelable(MainActivity.IntentKeyWord.SELECTED_RECIPE);
        }

        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(getString(R.string.ingredient_of) + " " + mRecipe.getName());
        mIngredientList = mRecipe.getIngredients();
        final ArrayList<String> widgetIngredientList = new ArrayList<String>();

        for (int i = 0; i < mIngredientList.size(); i++) {
            Ingredient currentIngredient = mIngredientList.get(i);
            widgetIngredientList.add(currentIngredient.getIngredient() + "\n" + "Quantity: " + currentIngredient.getQuantity().toString() + "\n" +
                    "Measure: " + currentIngredient.getMeasure() + "\n");
        }

        IngredientService.startIngredientService(getContext(), widgetIngredientList);

        mIngredientAdapter = new IngredientAdapter(getActivity(), (ArrayList<Ingredient>) mIngredientList);

        if (rootView.getTag() != null && rootView.getTag().equals("phone-land")) {
            GridLayoutManager mLayoutManager = new GridLayoutManager(getContext(), 4);
            ingredientRecyclerView.setLayoutManager(mLayoutManager);
        } else {
            LinearLayoutManager mLayoutManager = new LinearLayoutManager(getContext());
            ingredientRecyclerView.setLayoutManager(mLayoutManager);
        }

        ingredientRecyclerView.setAdapter(mIngredientAdapter);
        return rootView;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(MainActivity.IntentKeyWord.SELECTED_RECIPE, mRecipe);
    }
}
