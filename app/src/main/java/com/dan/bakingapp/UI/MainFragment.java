package com.dan.bakingapp.UI;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dan.bakingapp.Adapter.MainAdapter;
import com.dan.bakingapp.Models.Recipe;
import com.dan.bakingapp.R;
import com.dan.bakingapp.Retrofit.RecipeAPI;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.dan.bakingapp.UI.MainActivity.ALL_RECIPES;

/**
 * Created by Dat T Do on 9/28/2017.
 */

public class MainFragment extends Fragment {

    public MainFragment() {
    }

    @Bind(R.id.main_recycler)
    RecyclerView mainRecyclerView;

    private ArrayList<Recipe> recipes;
    private MainAdapter mMainAdapter;




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (container != null) {
            container.removeAllViews();
        }
        View rootView = inflater.inflate(R.layout.main_fragment, container, false);
        ButterKnife.bind(this, rootView);
        recipes = new ArrayList<Recipe>();


        if (rootView.getTag() != null && rootView.getTag().equals("phone-land")) {
            GridLayoutManager mLayoutManager = new GridLayoutManager(getContext(), 4);
            mainRecyclerView.setLayoutManager(mLayoutManager);
        } else {
            LinearLayoutManager mLayoutManager = new LinearLayoutManager(getContext());
            mainRecyclerView.setLayoutManager(mLayoutManager);
        }

        // get the Recipe using RecipeAPI interface
        Gson gson = new GsonBuilder().create();

        OkHttpClient.Builder httpClientBuilder = new OkHttpClient.Builder();


        RecipeAPI service = new Retrofit.Builder()
                .baseUrl("https://d17h27t6h515a5.cloudfront.net/topher/2017/May/59121517_baking/")
                .addConverterFactory(GsonConverterFactory.create())
                .callFactory(httpClientBuilder.build())
                .build().create(RecipeAPI.class);
        service.getRecipe().enqueue((new Callback<ArrayList<Recipe>>() {
            @Override
            public void onResponse(Call<ArrayList<Recipe>> call, Response<ArrayList<Recipe>> response) {
                Integer statusCode = response.code();
                Log.v("status code: ", statusCode.toString());



                for (int i = 0; i < response.body().size(); i++) {

                    recipes.add(response.body().get(i));
                    mMainAdapter.notifyDataSetChanged();
                }

                Bundle recipesBundle = new Bundle();
                recipesBundle.putParcelableArrayList(ALL_RECIPES, recipes);


            }

            @Override
            public void onFailure(Call<ArrayList<Recipe>> call, Throwable t) {
                Log.v("http fail: ", t.getMessage());
                Log.i("abcd", "fail");
            }
        }));

        mMainAdapter = new MainAdapter(rootView.getContext(), recipes);
        mainRecyclerView.setAdapter(mMainAdapter);




        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mainRecyclerView.addOnItemTouchListener(new RecyclerClickListener(getActivity(), new RecyclerClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Recipe currentReceipe = recipes.get(position);
                Bundle arguments = new Bundle();
                arguments.putParcelable("recipe", currentReceipe);
                Intent intent = new Intent(getActivity(), DetailRecipeActivity.class);
                intent.putExtra("recipe", currentReceipe);
                startActivity(intent);
            }
        }));
    }

}
