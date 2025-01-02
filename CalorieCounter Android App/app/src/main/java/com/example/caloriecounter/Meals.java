package com.example.caloriecounter;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

public class Meals extends AppCompatActivity {
    String CLASS_NAME = "Meals";

    dbHelper dbHelp;

    Button buttonGoToHome;
    Button buttonCreateMeal;
    Button buttonSearchMeal;

    EditText inputSearchMeal;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.meals_page);

        dbHelp = new dbHelper(this);

        // display current meals on the page
        displayMeals(dbHelp.getMeals());

        // button to take you pack to home page
        buttonGoToHome = findViewById(R.id.buttonGoToHome);
        buttonGoToHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent homePage = new Intent(Meals.this, MainActivity.class);
                startActivity(homePage);
            }
        });

        buttonCreateMeal = findViewById(R.id.buttonGoToCreateMeal);
        buttonCreateMeal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent createMealPage = new Intent(Meals.this, CreateMeal.class);
                startActivity(createMealPage);
            }
        });

        buttonSearchMeal = findViewById(R.id.buttonSearchMeal);
        buttonSearchMeal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                searchMeals();
            }
        });

    }

    public void displayMeals(List<List<String>> meals){
        LinearLayout layout = findViewById(R.id.LinearLayoutScrollMeals);

        //reset all buttons
        for (int i = layout.getChildCount() - 1; i >= 0; i--) {
            View child = layout.getChildAt(i);
            if (child instanceof Button) {
                layout.removeViewAt(i);
            }
        }

        for(List<String> meal: meals){
            Button mealButton = new Button(this);
            mealButton.setText(meal.get(1));
            mealButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // Create the alert dialog here
                    AlertDialog.Builder builder = new AlertDialog.Builder(Meals.this);
                    builder.setTitle("Meal Options");
                    builder.setMessage("Select your Option, or click outside to go back");
                            builder.setPositiveButton("Track Meal", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    eatMeal(meal);
                                }
                            });
                    builder.setNegativeButton("Edit Meal", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent editMeal = new Intent(Meals.this, EditMeal.class);
                            editMeal.putExtra("id", meal.get(0));
                            startActivity(editMeal);
                        }
                    });
                    builder.show();

                }
            });
            layout.addView(mealButton);
        }
    }

    //eat a meal and put into the database for the meal eaten today
    public void eatMeal(List<String> meal){
        String identifier = CLASS_NAME + " eatMeal";
        Log.d(identifier, "meal: " + meal.toString());

        AlertDialog.Builder builder = new AlertDialog.Builder(Meals.this);
        builder.setTitle("Result");
        if(dbHelp.insertMealEaten(Integer.parseInt(meal.get(0))) != -1){
            builder.setMessage("Meal Tracked!");
            builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                }
            });
        }else{
            builder.setMessage("Error Occured.");
            builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                }
            });
        }
        builder.setCancelable(false);
        builder.show();
    }

    public void searchMeals(){
        String identifier = CLASS_NAME + " searchMeals";
        Log.d(identifier, "func starting");

        inputSearchMeal = findViewById(R.id.inputSearchMeal);
        String meal = inputSearchMeal.getText().toString();
        Log.d(identifier, "meal: " + meal);
        //if there is no search show all meals
        if (meal.isEmpty()){
            Log.d(identifier, "meal is empty");
            displayMeals(dbHelp.getMeals());
        }else{
            Log.d(identifier, "meal is not empty");
            displayMeals(dbHelp.getMealByName(meal));
        }
    }
}
