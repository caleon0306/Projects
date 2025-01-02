package com.example.caloriecounter;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.View;

import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class MainActivity extends AppCompatActivity {
    public static final String CLASS_NAME = "MainActivity";

    dbHelper dbHelp;

    TextView accountDisplay, macroDisplay;
    TextView calorieDisplay, proteinDisplay, carbDisplay, fatDisplay;

    Button buttonGoToAccountSettings;
    Button buttonGoToMeals;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        String className = "MainActivity";
        String funcName = "onCreate";
        String identifier = className + " " + funcName;
        Log.d(identifier, "onCreateRunning");

        super.onCreate(savedInstanceState);
        dbHelp = new dbHelper(this);

        setContentView(R.layout.home);

        //get account information
        //if an account is not created you are redirected to create_account.xml view
        checkInfo();

        Log.d(identifier, "checkInfo passed");

        //Initialize button to take you to your accounts settings page
        buttonGoToAccountSettings = findViewById(R.id.buttonGoToAccountSettings);
        buttonGoToAccountSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //get Intent for the account settings class
                Intent settingsPage = new Intent(MainActivity.this, EditAccount.class);
                startActivity(settingsPage);
            }
        });

        buttonGoToMeals = findViewById(R.id.buttonGoToMeals);
        buttonGoToMeals.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mealsPage = new Intent(MainActivity.this, Meals.class);
                startActivity(mealsPage);
            }
        });
    }

    //get account info of user
    //if there is no account direct them to the create account page
    private void checkInfo(){
        String className = "MainActivity";
        String funcName = "checkInfo";
        String identifier = className + " " + funcName;
        Log.d(identifier, "checkInfo starting");

        List<String> accInfo = dbHelp.getAccountInformation();
        List<Integer> macroInfo = dbHelp.getMacrosInformation();

        Log.d(identifier, "accInfo: " + accInfo.toString());
        Log.d(identifier, "macroInfo: " + macroInfo.toString());

        if(accInfo.isEmpty() || macroInfo.isEmpty()){
            Log.d(identifier, "no account info or macro info");
            Intent createAccount = new Intent(MainActivity.this, CreateAccount.class);
            startActivity(createAccount);
        }
        else{
            Log.d(identifier, "acc info and macro info both not empty");
            accountDisplay(accInfo);

            macroDisplay(macroInfo);
        }
    }

    // display user information to the screen
    private void accountDisplay(List<String> accInfo){
        accountDisplay = findViewById(R.id.textViewGreeting);
        if(!accInfo.isEmpty()){
            accountDisplay.setText("Welcome, " + accInfo.get(1));
        }
    }

    //gets all meals eaten today, used to track daily macros
    private List<List<String>> getEatenMealsToday() {
        String identifier = CLASS_NAME + " getEatenMealsToday";
        Log.d(identifier, "starting func");

        List<List<Integer>> eatenToday = dbHelp.getEatenMealsTodayIDs();
        List<List<String>> mealsToday = new ArrayList<>();

        for (int i = 0; i < eatenToday.size(); i++) {
            List<String> meal = new ArrayList<>();
            Log.d(identifier, "eatenid: " + String.valueOf(eatenToday.get(i).get(0)));
            Log.d(identifier, "mealinfo: " +String.valueOf(dbHelp.getMealById(eatenToday.get(i).get(1))));

            meal.add(String.valueOf(eatenToday.get(i).get(0)));
            meal.addAll(dbHelp.getMealById(eatenToday.get(i).get(1)));
            Log.d(identifier, "meal: " + meal);

            mealsToday.add(meal);
        }
        Log.d(identifier, "mealsToday: " + mealsToday);

        return mealsToday;
    }

    private List<Integer> getEatenMacrosToday(List<List<String>> mealsToday){
        String identifier = CLASS_NAME + " getEatenMacrosToday";
        Log.d(identifier, "func starting");
        Log.d(identifier, "mealsToday: " + mealsToday);

        int calories = 0, protein = 0, carb = 0, fat = 0;
        for(List<String> meal:mealsToday){
            Log.d(identifier, "meal: " + meal);

            calories += Integer.parseInt(meal.get(3));
            protein += Integer.parseInt(meal.get(4));
            carb += Integer.parseInt(meal.get(5));
            fat += Integer.parseInt(meal.get(6));
        }

        List<Integer> macrosToday = new ArrayList<>();
        macrosToday.add(calories);
        macrosToday.add(protein);
        macrosToday.add(carb);
        macrosToday.add(fat);

        return macrosToday;
    }

    private List<Integer> calcMacrosRemaining(List<Integer> dailyMacros, List<Integer> macrosToday){
        List<Integer> macrosRemaining = new ArrayList<>();
        macrosRemaining.add(dailyMacros.get(1) - macrosToday.get(0));
        macrosRemaining.add(dailyMacros.get(2) - macrosToday.get(1));
        macrosRemaining.add(dailyMacros.get(3) - macrosToday.get(2));
        macrosRemaining.add(dailyMacros.get(4) - macrosToday.get(3));

        return macrosRemaining;
    }

    //show the amount of macros to eat
    private void macroDisplay(List<Integer> dailyMacros){
        String identifier = CLASS_NAME + " macroDisplay";
        int POSITIVE = Color.GREEN;
        int NEGATIVE = Color.RED;
        int WIGGLE_ROOM = 100;

        Log.d(identifier, "func starting");

        List<List<String>> mealsToday = getEatenMealsToday();
        Log.d(identifier, "mealsToday: " + mealsToday);

        List<Integer> macrosRemaining = calcMacrosRemaining(dailyMacros, getEatenMacrosToday(mealsToday));

        Log.d(identifier, "macrosRemaining: " + macrosRemaining);

        calorieDisplay = findViewById(R.id.textViewCalorieDisplay);
        proteinDisplay = findViewById(R.id.textViewProteinDisplay);
        carbDisplay = findViewById(R.id.textViewCarbDisplay);
        fatDisplay = findViewById(R.id.textViewFatDisplay);

        if(macrosRemaining.get(0) < 0){
            calorieDisplay.setTextColor(NEGATIVE);
        }
        if(macrosRemaining.get(1) < 0){
            proteinDisplay.setTextColor(NEGATIVE);
        }
        if(macrosRemaining.get(2) < 0){
            carbDisplay.setTextColor(NEGATIVE);
        }
        if(macrosRemaining.get(3) < 0){
            fatDisplay.setTextColor(NEGATIVE);
        }

        calorieDisplay.setText("Calories: " + macrosRemaining.get(0));
        proteinDisplay.setText("Protein: " + macrosRemaining.get(1));
        carbDisplay.setText("Carb: " + macrosRemaining.get(2));
        fatDisplay.setText("Fat: " + macrosRemaining.get(3));

        displayEatenToday();
    }

    private void displayEatenToday(){
        String identifier = CLASS_NAME + " displayEatenToday";
        Log.d(identifier, "func starting");

        LinearLayout layout = findViewById(R.id.scrollEatenToday);

        //reset all buttons
        for (int i = layout.getChildCount() - 1; i >= 0; i--) {
            View child = layout.getChildAt(i);
            if (child instanceof Button) {
                layout.removeViewAt(i);
            }
        }


        Log.d(identifier, "starting meal loop");
        for(List<Integer> meal: dbHelp.getEatenMealsTodayIDs()){
            Log.d(identifier, "meal: " + meal);
            Button eatenButton = new Button(this);

            eatenButton.setText(dbHelp.getMealById(meal.get(1)).get(1));
            eatenButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // Create the alert dialog here
                    AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                    builder.setTitle("Eaten Options");
                    builder.setMessage("Select your Option");
                    builder.setPositiveButton("Uneat Meal", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            uneatMeal(meal.get(0));
                            Intent home = new Intent(MainActivity.this, MainActivity.class);
                            startActivity(home);
                        }
                    });
                    builder.setNegativeButton("Go Back", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    });
                    builder.setCancelable(false);
                    builder.show();
                }
            });
            layout.addView(eatenButton);
        }
    }

    private void uneatMeal(Integer id){
        dbHelp.deleteEatenMeal(id);
    }
}