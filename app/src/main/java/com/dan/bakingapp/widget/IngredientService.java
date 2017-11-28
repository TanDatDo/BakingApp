package com.dan.bakingapp.widget;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;

import java.util.ArrayList;

/**
 * Created by Dat T Do on 10/7/2017.
 */

public class IngredientService extends IntentService {

    public static final String ACTION_LIST_OF_INGREDIENTS = "action_list_of_ingredients";

    public IngredientService() {
        super("UpdateService");
    }

    public static void startIngredientService(Context context, ArrayList<String> ingredientList) {
        Intent intent = new Intent(context, IngredientService.class);
        intent.putExtra(ACTION_LIST_OF_INGREDIENTS, ingredientList);
        context.startService(intent);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            ArrayList<String> ingredientList = intent.getStringArrayListExtra(ACTION_LIST_OF_INGREDIENTS);
            handleActionUpdateIngredients(ingredientList);

        }
    }


    private void handleActionUpdateIngredients(ArrayList<String> ingredientList) {
        Intent intent = new Intent("android.appwidget.action.APPWIDGET_UPDATE2");
        intent.setAction("android.appwidget.action.APPWIDGET_UPDATE2");
        intent.putExtra(ACTION_LIST_OF_INGREDIENTS, ingredientList);
        sendBroadcast(intent);
    }

}
