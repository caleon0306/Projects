package com.example.caloriecounter;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class CreateAccount extends AppCompatActivity {
    Button buttonCreateAccount;
    dbHelper dbHelp;

    private final String CLASS_NAME = "CreateAccount";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        String identifier = CLASS_NAME + " onCreate";
        Log.d(identifier, "onCreate running");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_account);

        dbHelp = new dbHelper(this);

        buttonCreateAccount = findViewById(R.id.buttonCreateAccount);
        buttonCreateAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(identifier, "Create Account Button Clicked");
                List<String> info = getAccountInput();
                Log.d(identifier, "Account Info: " + info);
                // if info is size 1 an error occurred, display error to the screen
                if (info.size() == 1){
                    alertAccountFailed(info.get(0));
                }
                else{
                    try{
                        Log.d(identifier, "Attempting to create account");
                        dbHelp.createAccount(info.get(0), Integer.parseInt(info.get(1)),
                                info.get(2), Integer.parseInt(info.get(3)), Integer.parseInt(info.get(4)),
                                Integer.parseInt(info.get(5)), Integer.parseInt(info.get(6)));
                        Log.d(identifier, "createAccount finished");
                        Intent homePage = new Intent(CreateAccount.this, MainActivity.class);
                        Log.d(identifier, "going to homePage");
                        startActivity(homePage);
                    } catch (Exception e){
                        alertAccountFailed(e.toString());
                    }
                }
            }
        });
    }

    private List<String> getAccountInput (){
        Log.d("getAccountInput", "entering getAccountInput");
        List<String> info = new ArrayList<String>();

        EditText inputName = findViewById(R.id.inputName);
        EditText inputAge = findViewById(R.id.inputAge);
        RadioGroup genderGroup = findViewById(R.id.radioGroupGender);
        EditText inputHeight = findViewById(R.id.inputHeight);
        EditText inputWeight = findViewById(R.id.inputWeight);
        RadioGroup activityGroup = findViewById(R.id.radioGroupActivity);
        RadioGroup goalGroup = findViewById(R.id.radioGroupGoal);

        int checkedGender = genderGroup.getCheckedRadioButtonId();
        int checkedActivity = activityGroup.getCheckedRadioButtonId();
        int checkedGoal = goalGroup.getCheckedRadioButtonId();

        Log.d("getAccountInput", "got all selected input");

        // check that the radio buttons are selected
        if (checkedGender == -1){
            info.add("Gender not selected.");
            return info;
        }
        if (checkedActivity == -1){
            info.add("Activity level not selected.");
            return info;
        }
        if (checkedGoal == -1){
            info.add("Weight gain/loss speed not selected");
            return info;
        }

        Log.d("getAccountInput", "all radio buttons passed");

        try{
            RadioButton genderButton = findViewById(checkedGender);
            RadioButton activityButton = findViewById(checkedActivity);
            RadioButton goalButton = findViewById(checkedGoal);

            String name = inputName.getText().toString();
            String age = inputAge.getText().toString();
            String gender = genderButton.getText().toString();
            String height = inputHeight.getText().toString();
            String weight = inputWeight.getText().toString();
            String activity = activityButton.getText().toString();
            String goal = goalButton.getText().toString();


            // check all input to see if it is in the correct format
            String result = new InputChecker().checkAccountInputs(name, age, gender, height, weight, activity, goal);

            // return an error message in a size 1 list string
            if(!result.isEmpty()){
                Log.d("getAccountInput", "Input checker result is not empty");
                info.add(result);
                return info;
            }

            activity = new InputChecker().convertActivity(activity);
            goal = new InputChecker().convertGoal(goal);

            info.add(name);
            info.add(age);
            info.add(gender);
            info.add(height);
            info.add(weight);
            info.add(activity);
            info.add(goal);
        }catch (Exception e){
            Log.d("getAccountInfo", "exception " + e + " occurred");
            //return empty array list signifying something went wrong
            info.add("Error occurred: " + e);
            return info;
        }
        Log.d("getAccountInput", "exiting get account input");
        return info;
    }

    private void alertAccountFailed(String message){
        AlertDialog.Builder builder = new AlertDialog.Builder(CreateAccount.this);
        builder.setTitle("Account Creation Failed");
        builder.setMessage(message);
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        builder.setCancelable(false);
        builder.show();
    }

}
