package com.example.caloriecounter;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

//This class will check user input to ensure it is in the correct format
//Each function will return a string
//If the input passes all tests the return string will be empty
//A String with the type of error will be returned if there is an error
public class InputChecker {

    //check input to see if it is a number
    private String checkIsNum(String num){
        try{
            Integer.parseInt(num);
        } catch (Exception e){
            return "must be an int";
        }
        return "";
    }

    // Function checks all inputs required to create an account
    public String checkAccountInputs(String name, String age, String gender, String height, String weight, String activity, String goal){
        String funcName = "checkAccountInputs";
        Log.d(funcName, "starting func");

        String checkName = checkAccountName(name);
        if (!checkName.isEmpty()){
            return checkName;
        }
        Log.d(funcName, "name pass");

        String checkAge = checkAge(age);
        if (!checkAge.isEmpty()){
            return checkAge;
        }
        Log.d(funcName, "age pass");

        String checkGender = checkGender(gender);
        if (!checkGender.isEmpty()){
            return checkGender;
        }
        Log.d(funcName, "gender pass");

        String checkHeight = checkHeight(height);
        if (!checkHeight.isEmpty()){
            return checkHeight;
        }
        Log.d(funcName, "height pass");

        String checkWeight = checkWeight(weight);
        if (!checkWeight.isEmpty()){
            return checkWeight;
        }
        Log.d(funcName, "weight pass");

        String checkActivity = checkActivity(activity);
        if (!checkActivity.isEmpty()){
            return checkActivity;
        }
        Log.d(funcName, "activity pass");

        String checkGoal = checkGoal(goal);
        if (!checkGoal.isEmpty()){
            return checkGoal;
        }
        Log.d(funcName, "goal pass");

        return "";
    }

    public String checkAccountName(String name){
        if (name.isEmpty()){
            return "Name cannot be empty";
        }
        return "";
    }

    public String checkAge (String age){
        if (age.isEmpty()){
            return "Age cannot be empty";
        }
        String result = checkIsNum(age);
        if (!result.isEmpty()){
            return "Age must be an integer";
        }
        result = checkPositiveNum(age);
        if(!result.isEmpty()){
            return "Age must be greater than 0";
        }
        return "";
    }

    public String checkHeight (String height){
        if (height.isEmpty()){
            return "Height cannot be empty";
        }
        String result = checkIsNum(height);
        if (!result.isEmpty()){
            return "Height must be an integer";
        }
        result = checkPositiveNum(height);
        if(!result.isEmpty()){
            return "Height must be greater than 0";
        }
        return "";
    }

    public String checkWeight (String weight){
        if (weight.isEmpty()){
            return "Weight cannot be empty";
        }
        String result = checkIsNum(weight);
        if (!result.isEmpty()){
            return "Weight must be an integer";
        }
        result = checkPositiveNum(weight);
        if(!result.isEmpty()){
            return "Weight must be greater than 0";
        }
        return "";
    }

    public String checkGender (String gender){
        if (gender.isEmpty()){
            return "Gender cannot be empty";
        }
        if (!gender.equals("Male") && !gender.equals("Female")){
            return "Gender must be Male or Female";
        }

        return "";
    }

    public String checkActivity (String activity){
        String funcName = "checkActivity";
        Log.d(funcName, "starting func");
        Log.d(funcName, "activity value: " + activity);
        if (activity.isEmpty()){
            return "Activity cannot be empty";
        }
        Log.d(funcName, "activity is not empty");

        List<String> activityOptions = new ArrayList<>();
        activityOptions.add("Sedentary");
        activityOptions.add("Light Activity");
        activityOptions.add("Moderate Activity");
        activityOptions.add("Very Active");
        activityOptions.add("Extreme Activity");

        if (!activityOptions.contains(activity)){
            return "Activity must be a selected option.";
        }
        return "";
    }

    public String convertActivity(String activity){
        switch (activity) {
            case "Sedentary":
                return "1";
            case "Light Activity":
                return "2";
            case "Moderate Activity":
                return "3";
            case "Very Active":
                return "4";
            case "Extreme Activity":
                return "5";
        }
        return "Unexpected error";
    }

    public String checkGoal (String goal){
        String funcName = "checkGoal";
        Log.d(funcName, "Starting function");
        Log.d(funcName, "goal value: " + goal);
        if (goal.isEmpty()){
            return "Goal cannot be empty";
        }

        List<String> goalOptions = new ArrayList<>();
        goalOptions.add("Lose Fast");
        goalOptions.add("Lose Slow");
        goalOptions.add("Maintain");
        goalOptions.add("Gain Slow");
        goalOptions.add("Gain Fast");

        if(!goalOptions.contains(goal)){
            return "Goal must be a selected option.";
        }
        return "";
    }

    public String convertGoal(String goal) {
        switch (goal) {
            case "Lose Fast":
                return "1";
            case "Lose Slow":
                return "2";
            case "Maintain":
                return "3";
            case "Gain Slow":
                return "4";
            case "Gain Fast":
                return "5";
        }
        return "Unexpected error";
    }

    // check all of the inputs for a meal to ensure they pass all requirements
    // TODO ADD A CHECK TO MAKE SURE A MEAL WITH THE SAME NAME IS NOT CREATED
    public String checkMealInputs(String name, String calories, String protein, String carbs, String fats){
        String checkName = checkMealName(name);
        if(!checkName.isEmpty()){
            return checkName;
        }

        String checkCalories = checkCalories(calories);
        if (!checkCalories.isEmpty()){
            return checkCalories;
        }

        String checkProtein = checkProtein(protein);
        if (!checkProtein.isEmpty()){
            return checkProtein;
        }

        String checkCarbs = checkCarbs(carbs);
        if (!checkCarbs.isEmpty()){
            return checkCarbs;
        }

        String checkFats = checkFats(fats);
        if (!checkFats.isEmpty()){
            return checkFats;
        }

        return "";
    }

    public String checkMealName(String name){
        if (name.isEmpty()){
            return "Meal name cannot be empty";
        }
        return "";
    }

    public String checkCalories (String calories){
        if (calories.isEmpty()){
            return "Calories cannot be empty";
        }
        String result = checkIsNum(calories);
        if (!result.isEmpty()){
            return "Calories must be an integer";
        }
        result = checkPositiveNum(calories);
        if(!result.isEmpty()){
            return "Calories must be greater than 0";
        }
        return "";
    }

    public String checkProtein (String protein){
        if (protein.isEmpty()){
            return "Protein cannot be empty";
        }
        String result = checkIsNum(protein);
        if (!result.isEmpty()){
            return "Protein must be an integer";
        }
        result = checkPositiveNum(protein);
        if(!result.isEmpty()){
            return "Protein must be greater than 0";
        }
        return "";
    }

    public String checkCarbs (String carbs){
        if (carbs.isEmpty()){
            return "Carbs cannot be empty";
        }
        String result = checkIsNum(carbs);
        if (!result.isEmpty()){
            return "Carbs must be an integer";
        }
        result = checkPositiveNum(carbs);
        if(!result.isEmpty()){
            return "Carbs must be greater than 0";
        }
        return "";
    }

    public String checkFats (String fats){
        if (fats.isEmpty()){
            return "Fats cannot be empty";
        }
        String result = checkIsNum(fats);
        if (!result.isEmpty()){
            return "Fats must be an integer";
        }
        result = checkPositiveNum(fats);
        if(!result.isEmpty()){
            return "Fats must be greater than 0";
        }
        return "";
    }

    //check to see if an integer is > 0
    private String checkPositiveNum(String input){
        if (Integer.parseInt(input) <= 0){
            return "Num is not greater than 0";
        }
        return "";
    }
}
