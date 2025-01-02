package com.example.caloriecounter;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class MacroCalculator {

    private static final String CLASS_NAME = "MacroCalculator";

    private static final double PERCENT_PROTEIN = .3;
    private static final double PERCENT_CARBS = .4;
    private static final double PERCENT_FATS = .3;


    public List<Integer> getMacroInfo (int age, String gender, int height, int weight, int activity, int goal){
        String identifier = CLASS_NAME + " getMacroInfo";
        Log.d(identifier, "age: " + age);
        Log.d(identifier, "gender: " + gender);
        Log.d(identifier, "height: " + height);
        Log.d(identifier, "weight: " + weight);
        Log.d(identifier, "activity: " + activity);
        Log.d(identifier, "goal: " + goal);

        List<Integer> info = new ArrayList<>();
        int bmr = 0;
        if (gender.equals("Male")){
            bmr = calculateBMRMen(age, height, weight);
            Log.d(identifier, "Is Male");
        }
        else{
            bmr = calculateBMRFemale(age, height, weight);
            Log.d(identifier, "is not male" );
        }
        Log.d(identifier, "bmr: " + bmr);

        int calories = dailyCalories(bmr, activity, goal);
        Log.d(identifier, "calories: " + calories);
        info.add(calories);
        info.add(proteinAmount(calories));
        info.add(carbsAmount(calories));
        info.add(fatsAmount(calories));

        return info;
    }


    private int proteinAmount (int calories){
        return (int) (calories * PERCENT_PROTEIN / 4);
    }

    private int carbsAmount (int calories){
        return (int) (calories * PERCENT_CARBS/ 4);
    }

    private int fatsAmount (int calories){
        return (int) (calories * PERCENT_FATS / 9);
    }

    //Mifflin-St. Jeor Equation
    private int calculateBMRMen(int age, int height, int weight){
        //convert weight and height to kg and cm
        return (int) (10 * poundToKg(weight) + 6.25 * inchToCm(height) - 5 * age + 5);
    }

    //Mifflin-St. Jeor Equation
    private int calculateBMRFemale (int age, int height, int weight){
        return (int) (10 * poundToKg(weight) + 6.25 * inchToCm(height) - 5 * age - 161);
    }

    private double poundToKg(int weight){
        return weight * 0.454;
    }

    private double inchToCm(int height){
        return height * 2.54;
    }

    private int dailyCalories(int bmr, int activity, int goal){
        String identifier = CLASS_NAME + " dailyCalories";
        Log.d(identifier, "bmr: " + bmr);
        Log.d(identifier, "activity: " + activity);
        Log.d(identifier, "goal: " + goal);

        double multiplier = 0;
        if (activity == 1){
            multiplier = 1.2;
        }
        if (activity == 2){
            multiplier = 1.375;
        }
        if (activity == 3){
            multiplier = 1.55;
        }
        if (activity == 4){
            multiplier = 1.725;
        }
        if (activity == 5){
            multiplier = 1.9;
        }
        Log.d(identifier, "multiplier: " + multiplier);

        int calories = (int) (bmr * multiplier);

        switch (goal){
            case 1:
                calories = calories - 1000;
                break;
            case 2:
                calories = calories - 500;
                break;
            case 3:
                break;
            case 4:
                calories = calories + 500;
                break;
            case 5:
                calories = calories + 1000;
                break;
        }
        return calories;
    }

}