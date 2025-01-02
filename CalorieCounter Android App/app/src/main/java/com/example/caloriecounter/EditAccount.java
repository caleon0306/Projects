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
import java.util.Objects;

public class EditAccount extends AppCompatActivity {
    String CLASS_NAME = "EditAccount";

    EditText inputName;
    EditText inputAge;
    EditText inputHeight;
    EditText inputWeight;

    RadioGroup rgGender, rgActivity, rgGoal;

    TextView tvResult;

    dbHelper dbHelp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        String identifier = CLASS_NAME + " onCreate";

        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_account);

        dbHelp = new dbHelper(this);

        inputName = findViewById(R.id.inputName);
        inputAge = findViewById(R.id.inputAge);
        inputHeight = findViewById(R.id.inputHeight);
        inputWeight = findViewById(R.id.inputWeight);

        rgGender = findViewById(R.id.radioGroupEditGender);
        rgActivity = findViewById(R.id.radioGroupEditActivity);
        rgGoal = findViewById(R.id.radioGroupEditGoal);


        tvResult = findViewById(R.id.textViewResult);

        //display current information
        List<String> accountInfo = dbHelp.getAccountInformation();
        displayAccountInfo(accountInfo);

        Button buttonUpdateAccount = findViewById(R.id.buttonUpdateAccount);
        buttonUpdateAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                List<String> infoUpdate = getEditInput();
                if (infoUpdate.size() == 1){
                    Log.d(identifier, "infoUpdate.size() == 1");
                    alertError(infoUpdate.get(0));
                }else{
                    try{
                        Log.d(identifier, "in try block");
                        dbHelp.updateAccount(Integer.parseInt(accountInfo.get(0)), infoUpdate.get(0), Integer.parseInt(infoUpdate.get(1)),
                                infoUpdate.get(2), Integer.parseInt(infoUpdate.get(3)),Integer.parseInt(infoUpdate.get(4)),
                                Integer.parseInt(infoUpdate.get(5)), Integer.parseInt(infoUpdate.get(6)));
                        AlertDialog.Builder builder = new AlertDialog.Builder(EditAccount.this);
                        builder.setTitle("Result");
                        builder.setMessage("Account Updated!");
                        builder.setPositiveButton("Home", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent home = new Intent(EditAccount.this, MainActivity.class);
                                startActivity(home);
                            }
                        });
                        builder.show();
                    }
                    catch(Exception e){
                        alertError(e.toString());
                    }
                }
            }
        });

        Button buttonGoToHome = findViewById(R.id.buttonGoToHome);
        buttonGoToHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent homePage = new Intent(EditAccount.this, MainActivity.class);
                startActivity(homePage);
            }
        });
    }

    //TODO CREATE FUNCTION IN DBHELPER TO UPDATE MACRO INFORMATION
    //get input from all of the fields
    private List<String> getEditInput(){
        List<String> info = new ArrayList<>();

        int checkedGender = rgGender.getCheckedRadioButtonId();
        int checkedActivity = rgActivity.getCheckedRadioButtonId();
        int checkedGoal = rgGoal.getCheckedRadioButtonId();

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

        try{
            RadioButton genderButton = findViewById(checkedGender);
            RadioButton activityButton = findViewById(checkedActivity);
            RadioButton goalButton = findViewById(checkedGoal);

            String name = inputName.getText().toString();
            String age = inputAge.getText().toString();
            String height = inputHeight.getText().toString();
            String weight = inputWeight.getText().toString();
            String gender = genderButton.getText().toString();
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

        } catch (Exception e){

        }
        return info;
    }

    private void displayAccountInfo(List<String> info){
        String identifier = CLASS_NAME + "displayAccountInfo";
        if (info.isEmpty()){
            info.add("0");
            info.add("Default Name");
            info.add("Default Age");
            info.add("Default Height");
            info.add("Default Weight");
            info.add("Default Activity");
            info.add("Default Goal");
        }

        Log.d(identifier, info.toString());

        inputName.setText(info.get(1));
        inputAge.setText(info.get(2));
        if(Objects.equals(info.get(3), "Male")){
            RadioButton radioMale = findViewById(R.id.radioButtonEditMale);
            radioMale.setChecked(true);
        }
        else{
            RadioButton radioFemale = findViewById(R.id.radioButtonEditFemale);
            radioFemale.setChecked(true);
        }

        inputHeight.setText(info.get(4));
        inputWeight.setText(info.get(5));

        int activityLevel = Integer.parseInt(info.get(6));
        Log.d(identifier, "activityLevel: " + activityLevel);
        switch (activityLevel){
            case 1:
                RadioButton radioActivity1 = findViewById(R.id.radioButtonEditActivity1);
                radioActivity1.setChecked(true);
                break;
            case 2:
                RadioButton radioActivity2 = findViewById(R.id.radioButtonEditActivity2);
                radioActivity2.setChecked(true);
                break;
            case 3:
                RadioButton radioActivity3 = findViewById(R.id.radioButtonEditActivity3);
                radioActivity3.setChecked(true);
                break;
            case 4:
                RadioButton radioActivity4 = findViewById(R.id.radioButtonEditActivity4);
                radioActivity4.setChecked(true);
                break;
            case 5:
                RadioButton radioActivity5 = findViewById(R.id.radioButtonEditActivity5);
                radioActivity5.setChecked(true);
                break;
        }

        int goal = Integer.parseInt(info.get(7));
        Log.d(identifier, "goal: " + goal);
        switch (goal){
            case 1:
                RadioButton radioGoal1 = findViewById(R.id.radioButtonEditGoal1);
                radioGoal1.setChecked(true);
                break;
            case 2:
                RadioButton radioGoal2 = findViewById(R.id.radioButtonEditGoal2);
                radioGoal2.setChecked(true);
                break;
            case 3:
                RadioButton radioGoal3 = findViewById(R.id.radioButtonEditGoal3);
                radioGoal3.setChecked(true);
                break;
            case 4:
                RadioButton radioGoal4 = findViewById(R.id.radioButtonEditGoal4);
                radioGoal4.setChecked(true);
                break;
            case 5:
                RadioButton radioGoal5 = findViewById(R.id.radioButtonEditGoal5);
                radioGoal5.setChecked(true);
                break;
        }
    }

    private void alertError(String message){
        AlertDialog.Builder builder = new AlertDialog.Builder(EditAccount.this);
        builder.setTitle("Error Updating Account");
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
