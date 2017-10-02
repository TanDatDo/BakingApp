package com.dan.bakingapp.Retrofit;

import com.dan.bakingapp.Models.Recipe;

import java.util.ArrayList;
import java.util.List;


import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by Dat T Do on 9/28/2017.
 */

public interface RecipeAPI {
    @GET("baking.json")
    Call<ArrayList<Recipe>> getRecipe();
}
