package com.example.caloriecounter;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.List;
import java.util.Objects;

public class EditMeal extends AppCompatActivity {
    private static final String CLASS_NAME = "EditMeal";

    EditText inputName;
    EditText inputCal;
    EditText inputPro;
    EditText inputCarb;
    EditText inputFat;

    Button buttonUpdateMeal;
    Button buttonGoToMeals;
    Button buttonGoHome;

    dbHelper dbHelp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        String identifier = CLASS_NAME + " onCreate";
        Log.d(identifier,"func starting");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_meal);

        Intent intent = getIntent();
        String id = intent.getStringExtra("id");
        Log.d(identifier, "id: " + id);

        dbHelp = new dbHelper(this);
        if(id == null){
            noIdPassed();
        }

        inputName = findViewById(R.id.inputEditMealName);
        inputCal = findViewById(R.id.inputEditMealCalories);
        inputPro = findViewById(R.id.inputEditMealProtein);
        inputCarb = findViewById(R.id.inputEditMealCarbs);
        inputFat = findViewById(R.id.inputEditMealFats);

        buttonUpdateMeal = findViewById(R.id.buttonUpdateMeal);
        buttonUpdateMeal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String result = updateMeal(Integer.parseInt(id));
                Log.d(identifier, "result: " + result);
                if(Objects.equals(result, "")){
                    AlertDialog.Builder builder = new AlertDialog.Builder(EditMeal.this);
                    builder.setTitle("Result");
                    builder.setMessage("Meal Updated Successfully");
                    builder.setPositiveButton("Back to Meals", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent home = new Intent(EditMeal.this, Meals.class);
                            startActivity(home);
                        }
                    });
                    builder.setCancelable(false);
                    builder.show();
                }
                else{
                    AlertDialog.Builder builder = new AlertDialog.Builder(EditMeal.this);
                    builder.setTitle("Result");
                    builder.setMessage("Error Occurred: " + result);
                    builder.setPositiveButton("Back to Meals", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent meals = new Intent(EditMeal.this, Meals.class);
                            startActivity(meals);
                        }
                    });
                    builder.setNegativeButton("Update Again", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    });
                    builder.setCancelable(false);
                    builder.show();
                }
            }
        });

        buttonGoToMeals = findViewById(R.id.buttonGoBackToMeals);
        buttonGoToMeals.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent meals = new Intent(EditMeal.this, Meals.class);
                startActivity(meals);
            }
        });

        buttonGoHome = findViewById(R.id.buttonGoHome);
        buttonGoHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent home = new Intent(EditMeal.this, MainActivity.class);
                startActivity(home);
            }
        });

        displayMealInfo(id);
    }

    private void noIdPassed(){
        AlertDialog.Builder builder = new AlertDialog.Builder(EditMeal.this);
        builder.setTitle("Error");
        builder.setMessage("Unknown Error Occurred");
        builder.setPositiveButton("Go Back", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent meals = new Intent(EditMeal.this, Meals.class);
                startActivity(meals);
            }
        });
        builder.setCancelable(false);
        builder.show();
    }

    private void displayMealInfo(String id){
        List<String> meal = dbHelp.getMealById(Integer.parseInt(id));

        inputName.setText(meal.get(1));
        inputCal.setText(meal.get(2));
        inputPro.setText(meal.get(3));
        inputCarb.setText(meal.get(4));
        inputFat.setText(meal.get(5));
    }

    public String updateMeal(Integer id){
        String name = inputName.getText().toString();
        String calories = inputCal.getText().toString();
        String protein = inputPro.getText().toString();
        String carbs = inputCarb.getText().toString();
        String fats = inputFat.getText().toString();

        String result = new InputChecker().checkMealInputs(name, calories, protein, carbs, fats);
        if(Objects.equals(result, "")){
            try{
                result = dbHelp.updateMeal(id, name, Integer.parseInt(calories), Integer.parseInt(protein), Integer.parseInt(carbs), Integer.parseInt(fats));
                return result;
            }
            catch(Exception e){
                return "Error Occurred: " + e;
            }
        }
        return result;
    }
}
