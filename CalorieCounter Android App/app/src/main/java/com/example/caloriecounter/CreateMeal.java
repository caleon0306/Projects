package com.example.caloriecounter;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class CreateMeal extends AppCompatActivity {

    dbHelper dbHelp;

    EditText inputMealName;
    EditText inputMealCalories;
    EditText inputMealProtein;
    EditText inputMealCarbs;
    EditText inputMealFats;

    Button buttonCreateMeal;
    Button buttonGoToMeals;

    TextView tvResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_meal);

        // set all input fields
        inputMealName = findViewById(R.id.inputMealName);
        inputMealCalories = findViewById(R.id.inputMealCalories);
        inputMealProtein = findViewById(R.id.inputMealProtein);
        inputMealCarbs = findViewById(R.id.inputMealCarbs);
        inputMealFats = findViewById(R.id.inputMealFats);

        tvResult = findViewById(R.id.textViewResultCreateMeal);

        dbHelp = new dbHelper(this);

        buttonCreateMeal = findViewById(R.id.buttonCreateMeal);
        buttonCreateMeal.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               String name = inputMealName.getText().toString();
               String calories = inputMealCalories.getText().toString();
               String protein = inputMealProtein.getText().toString();
               String carbs = inputMealCarbs.getText().toString();
               String fats = inputMealFats.getText().toString();

               String result = new InputChecker().checkMealInputs(name, calories, protein, carbs, fats);
               if(result.isEmpty()){
                   try {
                       long creation = dbHelp.createMeal(name, Integer.parseInt(calories),
                               Integer.parseInt(protein), Integer.parseInt(carbs),
                               Integer.parseInt(fats));

                       if(creation > -1){
                           alertMealCreated();
                       }else if (creation == -2){
                           alertMealNameTaken();
                       }else{
                           alertUnknownError();
                       }

                   }catch (Exception e){
                       alertExceptionError(e);
                   }
               }
               else{
                    alertInputError(result);
               }
           }
       });

        //button to go to meals page
        buttonGoToMeals = findViewById(R.id.buttonGoToMeals);
        buttonGoToMeals.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mealsPage = new Intent(CreateMeal.this, Meals.class);
                startActivity(mealsPage);
            }
        });
    }

    public void alertMealCreated(){
        AlertDialog.Builder builder = new AlertDialog.Builder(CreateMeal.this);
        builder.setTitle("Result");
        builder.setMessage("Meal Created!");
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent meals = new Intent(CreateMeal.this, Meals.class);
                startActivity(meals);
            }
        });
        builder.setCancelable(false);
        builder.show();
    }

    public void alertMealNameTaken(){
        AlertDialog.Builder builder = new AlertDialog.Builder(CreateMeal.this);
        builder.setTitle("Result");
        builder.setMessage("Meal name taken");
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        builder.setCancelable(false);
        builder.show();
    }

    public void alertUnknownError(){
        AlertDialog.Builder builder = new AlertDialog.Builder(CreateMeal.this);
        builder.setTitle("Result");
        builder.setMessage("Unknown Error Occurred");
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent meals = new Intent(CreateMeal.this, Meals.class);
                startActivity(meals);
            }
        });
        builder.setCancelable(false);
        builder.show();
    }

    public void alertExceptionError(Exception e){
        AlertDialog.Builder builder = new AlertDialog.Builder(CreateMeal.this);
        builder.setTitle("Result");
        builder.setMessage("Error Occurred: " + e);
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent meals = new Intent(CreateMeal.this, Meals.class);
                startActivity(meals);
            }
        });
        builder.setCancelable(false);
        builder.show();
    }

    public void alertInputError(String result){
        AlertDialog.Builder builder = new AlertDialog.Builder(CreateMeal.this);
        builder.setTitle("Result");

        builder.setMessage("Error: " + result);
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        builder.setCancelable(false);
        builder.show();
    }
}
