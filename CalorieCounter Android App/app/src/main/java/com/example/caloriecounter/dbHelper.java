package com.example.caloriecounter;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;

public class dbHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "CalorieCounter.db";
    private static final int DATABASE_VERSION = 1;

    public static final String TABLE_ACCOUNTS = "accounts";
    public static final String ACCOUNTS_ID = "account_id";
    public static final String ACCOUNTS_NAME = "name";
    public static final String ACCOUNTS_AGE = "age";
    public static final String ACCOUNTS_GENDER = "gender";
    public static final String ACCOUNTS_HEIGHT = "height";
    public static final String ACCOUNTS_WEIGHT = "weight";
    public static final String ACCOUNTS_ACTIVITY = "activity";
    public static final String ACCOUNTS_GOAL = "goal";

    public static final String TABLE_MEALS = "meals";
    public static final String MEALS_ID = "meal_id";
    public static final String MEALS_NAME = "meal_name";
    public static final String MEALS_CALORIE= "calories";
    public static final String MEALS_PROTEIN = "protein";
    public static final String MEALS_CARBS = "carbs";
    public static final String MEALS_FATS = "fats";

    public static final String TABLE_EATEN = "meals_eaten";
    public static final String EATEN_ID = "eaten_id";
    public static final String EATEN_MEAL_ID = "meal_id";
    public static final String EATEN_DATE = "date";

    public static final String TABLE_DAILY_MACROS = "daily_macros";
    public static final String MACROS_ID = "id";
    public static final String MACROS_CALORIES = "calories";
    public static final String MACROS_PROTEIN = "protein";
    public static final String MACROS_FATS = "fats";
    public static final String MACROS_CARBS = "carbs";

    private static final String CLASS_NAME = "dbHelper";

    public dbHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db){
        //create a query to create accounts table if it is not created
        String USER_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_ACCOUNTS + "(" +
                ACCOUNTS_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                ACCOUNTS_NAME + " TEXT," +
                ACCOUNTS_AGE + " TEXT," +
                ACCOUNTS_GENDER + " TEXT," +
                ACCOUNTS_HEIGHT + " INTEGER," +
                ACCOUNTS_WEIGHT + " INTEGER," +
                ACCOUNTS_ACTIVITY + " INTEGER," +
                ACCOUNTS_GOAL + " INTEGER)";

        //create a query to create meals table if it is not created
        String USER_MEALS = "CREATE TABLE IF NOT EXISTS " + TABLE_MEALS + "(" +
                MEALS_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                MEALS_NAME + " TEXT," +
                MEALS_CALORIE + " INTEGER," +
                MEALS_PROTEIN + " INTEGER," +
                MEALS_CARBS + " INTEGER," +
                MEALS_FATS + " INTEGER)";

        //create a query to create a meals eaten table if it is not created
        String EATEN_MEALS = "CREATE TABLE IF NOT EXISTS " + TABLE_EATEN + "(" +
                EATEN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                EATEN_MEAL_ID + " INTEGER," +
                EATEN_DATE + " TEXT)";

        //create a query to create a table to hold the recommended macros for each user
        String MACROS = "CREATE TABLE IF NOT EXISTS " + TABLE_DAILY_MACROS + "("+
                MACROS_ID + " INTEGER PRIMARY KEY," +
                MACROS_CALORIES + " INTEGER," +
                MACROS_PROTEIN + " INTEGER," +
                MACROS_CARBS + " INTEGER," +
                MACROS_FATS + " INTEGER)";


        db.execSQL(USER_TABLE);
        db.execSQL(USER_MEALS);
        db.execSQL(EATEN_MEALS);
        db.execSQL(MACROS);
    }

    public void resetTables(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ACCOUNTS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_MEALS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_EATEN);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_DAILY_MACROS);

        onCreate(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
    }

    public long createAccount(String name, Integer age, String gender, Integer height, Integer weight, Integer activity, Integer goal){
        String className = "dbHelper";
        String funcName = "createAccount";
        String identifier = className + " " + funcName;
        Log.d(identifier, funcName + " starting");

        SQLiteDatabase db = this.getWritableDatabase();
        long newRowIdAcc = -1;
        db.beginTransaction();

        try{
            Log.d(identifier, "attempting to add values for account info");
            //create values to hold where the information is going
            ContentValues accValues = new ContentValues();
            accValues.put(ACCOUNTS_NAME, name);
            accValues.put(ACCOUNTS_AGE, age);
            accValues.put(ACCOUNTS_GENDER, gender);
            accValues.put(ACCOUNTS_HEIGHT, height);
            accValues.put(ACCOUNTS_WEIGHT, weight);
            accValues.put(ACCOUNTS_ACTIVITY, activity);
            accValues.put(ACCOUNTS_GOAL, goal);
            Log.d(identifier, "all values added");

            //attempt to insert data into table
            newRowIdAcc = db.insert(TABLE_ACCOUNTS, null, accValues);
            Log.d(identifier, "account info row attempted to insert");

            if (newRowIdAcc != -1){
                Log.d(identifier, "account info row created");
                // create row for macro information in macro table
                Log.d(identifier, "attempting to use macro calculator");
                List<Integer> info = new MacroCalculator().getMacroInfo(age, gender,height, weight, activity, goal);
                Log.d(identifier, "MacroInfo: " + info);

                ContentValues macroValues = new ContentValues();
                macroValues.put(MACROS_ID, Integer.parseInt(String.valueOf(newRowIdAcc)));
                macroValues.put(MACROS_CALORIES, info.get(0));
                macroValues.put(MACROS_PROTEIN, info.get(1));
                macroValues.put(MACROS_CARBS, info.get(2));
                macroValues.put(MACROS_FATS, info.get(3));
                Log.d(identifier, "macroValues added");
                long newRowIdMacro = db.insert(TABLE_DAILY_MACROS, null, macroValues);
                if (newRowIdMacro != -1){
                    db.setTransactionSuccessful();
                    Log.d(identifier, "insert into macros table successful");
                }else{
                    Log.d(identifier, "insert into macros table failed");
                }

            }else{
                Log.d(identifier, "new account info row not created");
            }
        } catch (Exception e){
            Log.d(identifier, "EXCEPTION: " + e);
        } finally {
            Log.d(identifier, "in finally");
            db.endTransaction();
            db.close();
        }
        Log.d(identifier, "ending createAccount");
        return newRowIdAcc;
    }


    //gets all accounts
    //returns a Cursor with all account information
    public Cursor getAccounts(){
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + TABLE_ACCOUNTS, null);
    }

    //gets account information for the single account that is created
    //returns an empty List<String> if there is no created account
    public List<String> getAccountInformation(){
        String className = "dbHelper";
        String funcName = "getAccountInformation";
        String identifier = className + " " + funcName;

        Log.d(identifier, "entering getAccountInfo");

        Cursor cursor = this.getAccounts();
        List<String> accountInfo = new ArrayList<String>();
        if (cursor.getCount() > 0){
            Log.d(identifier, "cursor has result > 0");
            int idCol = cursor.getColumnIndex(dbHelper.ACCOUNTS_ID);
            int nameCol = cursor.getColumnIndex(dbHelper.ACCOUNTS_NAME);
            int ageCol = cursor.getColumnIndex(dbHelper.ACCOUNTS_AGE);
            int genderCol = cursor.getColumnIndex(dbHelper.ACCOUNTS_GENDER);
            int heightCol = cursor.getColumnIndex(dbHelper.ACCOUNTS_HEIGHT);
            int weightCol = cursor.getColumnIndex(dbHelper.ACCOUNTS_WEIGHT);
            int activityCol = cursor.getColumnIndex(dbHelper.ACCOUNTS_ACTIVITY);
            int goalCol = cursor.getColumnIndex(dbHelper.ACCOUNTS_GOAL);

            cursor.moveToFirst();

            int id = cursor.getInt(idCol);
            String name = cursor.getString(nameCol);
            int age = cursor.getInt(ageCol);
            String gender = cursor.getString(genderCol);
            int height= cursor.getInt(heightCol);
            int weight= cursor.getInt(weightCol);
            int activity= cursor.getInt(activityCol);
            int goal = cursor.getInt(goalCol);

            accountInfo.add(String.valueOf(id));
            accountInfo.add(name);
            accountInfo.add(String.valueOf(age));
            accountInfo.add(gender);
            accountInfo.add(String.valueOf(height));
            accountInfo.add(String.valueOf(weight));
            accountInfo.add(String.valueOf(activity));
            accountInfo.add(String.valueOf(goal));

        }
        else{
            Log.d(identifier, "cursor has no result");
        }
        Log.d("getAccountInto", "ending func");
        cursor.close();
        return accountInfo;
    }

    //gets all accounts
    //returns a Cursor with all account information
    public Cursor getDailyMacros(){
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + TABLE_DAILY_MACROS, null);
    }

    //gets account information for the single account that is created
    //returns an empty List<String> if there is no created account
    public List<Integer> getMacrosInformation(){
        String className = "dbHelper";
        String funcName = "getMacrosInformation";
        String identifier = className + " " + funcName;

        Log.d(identifier, "entering getMacrosInfo");

        Cursor cursor = this.getDailyMacros();
        List<Integer> macroInfo = new ArrayList<>();

        if (cursor.getCount() > 0){
            Log.d(identifier, "cursor has result > 0");
            int idCol = cursor.getColumnIndex(dbHelper.MACROS_ID);
            int calCol = cursor.getColumnIndex(dbHelper.MACROS_CALORIES);
            int proCol = cursor.getColumnIndex(dbHelper.MACROS_PROTEIN);
            int carbCol = cursor.getColumnIndex(dbHelper.MACROS_CARBS);
            int fatCol = cursor.getColumnIndex(dbHelper.MACROS_FATS);

            cursor.moveToFirst();

            int id = cursor.getInt(idCol);
            int cal = cursor.getInt(calCol);
            int pro = cursor.getInt(proCol);
            int carb = cursor.getInt(carbCol);
            int fat = cursor.getInt(fatCol);

            macroInfo.add(id);
            macroInfo.add(cal);
            macroInfo.add(pro);
            macroInfo.add(carb);
            macroInfo.add(fat);

        }
        else{
            Log.d(identifier, "cursor has no result");
        }
        Log.d(identifier, "ending func");
        cursor.close();
        return macroInfo;
    }

    public void updateAccount(Integer id, String name, Integer age, String gender, Integer height, Integer weight, Integer activity, Integer goal){
        String identifier = CLASS_NAME + " updateAccount";
        Log.d(identifier, "Starting updateAccount");
        Log.d(identifier, "parameters");
        Log.d(identifier, "id: " + id);
        Log.d(identifier, "name: " + name);
        Log.d(identifier, "age: " + age);
        Log.d(identifier, "gender: " + gender);
        Log.d(identifier, "height: " + height);
        Log.d(identifier, "weight: " + weight);
        Log.d(identifier, "activity: " + activity);
        Log.d(identifier, "goal: " + goal);

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(ACCOUNTS_NAME, name);
        values.put(ACCOUNTS_AGE, age);
        values.put(ACCOUNTS_GENDER, gender);
        values.put(ACCOUNTS_HEIGHT, height);
        values.put(ACCOUNTS_WEIGHT, weight);
        values.put(ACCOUNTS_ACTIVITY, activity);
        values.put(ACCOUNTS_GOAL, goal);

        String[] selection = {String.valueOf(id)};
        db.update(TABLE_ACCOUNTS, values, "account_id = ?", selection);
        db.close();

        updateMacros(id, age, gender, height, weight, activity, goal);
    }

    public void updateMacros(Integer id, Integer age, String gender, Integer height, Integer weight, Integer activity, Integer goal){
        List<Integer> macroInfo = new MacroCalculator().getMacroInfo(age, gender, height, weight, activity, goal);

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(MACROS_CALORIES, macroInfo.get(0));
        values.put(MACROS_PROTEIN, macroInfo.get(1));
        values.put(MACROS_CARBS, macroInfo.get(2));
        values.put(MACROS_FATS, macroInfo.get(3));

        String[] selection = {String.valueOf(id)};
        db.update(TABLE_DAILY_MACROS, values, "id = ?", selection);
        db.close();
    }

    public Cursor getMealsCursor(){
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + TABLE_MEALS, null);
    }

    //returns an array list of array list of Strings
    public List<List<String>> getMeals(){
        Cursor cursor = this.getMealsCursor();

        // create main array list of array list of string
        List<List<String>> meals = new ArrayList<>();

        if(cursor.getCount() > 0){
            for(int i = 0; i < cursor.getCount(); i++){
                cursor.moveToNext();
                int idCol = cursor.getColumnIndex(dbHelper.MEALS_ID);
                int nameCol = cursor.getColumnIndex(dbHelper.MEALS_NAME);
                int caloriesCol = cursor.getColumnIndex(dbHelper.MEALS_CALORIE);
                int proteinCol = cursor.getColumnIndex(dbHelper.MEALS_PROTEIN);
                int carbsCol = cursor.getColumnIndex(dbHelper.MEALS_CARBS);
                int fatsCol = cursor.getColumnIndex(dbHelper.MEALS_FATS);


                int id = cursor.getInt(idCol);
                String name = cursor.getString(nameCol);
                int calories = cursor.getInt(caloriesCol);
                int protein = cursor.getInt(proteinCol);
                int carbs = cursor.getInt(carbsCol);
                int fats = cursor.getInt(fatsCol);

                // create array list String and all information for current meal
                List<String> curMeal = new ArrayList<>();
                curMeal.add(String.valueOf(id));
                curMeal.add(name);
                curMeal.add(String.valueOf(calories));
                curMeal.add(String.valueOf(protein));
                curMeal.add(String.valueOf(carbs));
                curMeal.add(String.valueOf(fats));

                //add current meal to the main array list
                meals.add(curMeal);
            }

        }
        cursor.close();
        return meals;
    }

    public long createMeal(String name, Integer calories, Integer protein, Integer carbs, Integer fats){
        SQLiteDatabase db = this.getWritableDatabase();
        long newRowId = -1;
        db.beginTransaction();

        //check if a meal name exists, do not allow duplicates
        if(checkMealNameExists(name)){
            db.endTransaction();
            return -2;
        }

        try{
            //create values to hold where the information is going
            ContentValues values = new ContentValues();
            values.put(MEALS_NAME, name);
            values.put(MEALS_CALORIE, calories);
            values.put(MEALS_PROTEIN, protein);
            values.put(MEALS_CARBS, carbs);
            values.put(MEALS_FATS, fats);

            //attempt to insert data into table
            newRowId = db.insert(TABLE_MEALS, null, values);

            if (newRowId != -1){
                db.setTransactionSuccessful();
            }
        } catch (Exception e){

        } finally {
            db.endTransaction();
        }
        return newRowId;
    }

    //Function that returns True if a meal with the name already exists in the database
    public boolean checkMealNameExists(String name){
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_MEALS + " WHERE " + MEALS_NAME + " = ?";
        Cursor cursor = db.rawQuery(query, new String[]{name});

        int count = cursor.getCount();
        cursor.close();
        return count > 0;
    }

    public long insertMealEaten(int id){
        String identifier = CLASS_NAME + " insertMealEaten";

        SQLiteDatabase db = this.getWritableDatabase();

        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        String currentDate = dateFormat.format(calendar.getTime());

        Log.d(identifier, "currentDate: " + currentDate);
        long newRowId = -1;

        db.beginTransaction();
        try{
            ContentValues values = new ContentValues();
            values.put(EATEN_MEAL_ID, id);
            values.put(EATEN_DATE, currentDate);

            newRowId = db.insert(TABLE_EATEN, null, values);
            if (newRowId != -1){
                db.setTransactionSuccessful();
            }
        }
        catch (Exception e){
            Log.d(identifier, "ERROR: " + e);
        }finally{
            db.endTransaction();
            db.close();
        }
        return newRowId;
    }

    private Cursor getEatenMealsTodayCursor(){
        SQLiteDatabase db = this.getReadableDatabase();

        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        String currentDate = dateFormat.format(calendar.getTime());

        String query = "SELECT * FROM " + TABLE_EATEN + " WHERE " + EATEN_DATE + " = ?";
        return db.rawQuery(query, new String[]{currentDate});

    }

    public List<Integer> getEatenMealsTodayId(){
        String identifier = CLASS_NAME + " getEatenMealsToday";
        Log.d(identifier, "func starting");

        List<Integer> mealsId = new ArrayList<>();

        Cursor cursor = getEatenMealsTodayCursor();
        if (cursor.getCount() > 0){
            for (int i = 0; i < cursor.getCount(); i++){
                cursor.moveToNext();

                int eatenidcol = cursor.getColumnIndex(EATEN_ID);

                int eatenid = cursor.getInt(eatenidcol);
                Log.d(identifier, "eatenid: " + eatenid);

                mealsId.add(eatenid);
            }
        }
        return mealsId;
    }

    public List<List<Integer>> getEatenMealsTodayIDs(){
        String identifier = CLASS_NAME + " getEatenMealsTodayIds";
        Log.d(identifier, "func starting");

        List<List<Integer>> mealsToday = new ArrayList<>();

        Cursor cursor = getEatenMealsTodayCursor();
        for(int i = 0; i < cursor.getCount(); i++){
            List<Integer> meal = new ArrayList<>();
            cursor.moveToNext();

            int eatenCol = cursor.getColumnIndex(EATEN_ID);
            int mealIDCol = cursor.getColumnIndex(EATEN_MEAL_ID);

            int eatenId = cursor.getInt(eatenCol);
            int mealId = cursor.getInt(mealIDCol);

            Log.d(identifier, "eatenid: " + eatenId);
            Log.d(identifier, "mealid: " + mealId);
            meal.add(eatenId);
            meal.add(mealId);

            mealsToday.add(meal);
        }

        return mealsToday;
    }

    public Cursor getMealByIdCursor (int id){
        SQLiteDatabase db = this.getReadableDatabase();

        String query = "SELECT * FROM " + TABLE_MEALS + " WHERE " + MEALS_ID + " = ?";
        return db.rawQuery(query, new String[]{String.valueOf(id)});
    }

    public List<String> getMealById(int id){
        String identifier = CLASS_NAME + " getMealById";

        Cursor cursor = getMealByIdCursor(id);

        List<String> mealInfo = new ArrayList<>();

        if(cursor.getCount() > 0){
            cursor.moveToFirst();
            int nameCol = cursor.getColumnIndex(dbHelper.MEALS_NAME);
            int caloriesCol = cursor.getColumnIndex(dbHelper.MEALS_CALORIE);
            int proteinCol = cursor.getColumnIndex(dbHelper.MEALS_PROTEIN);
            int carbsCol = cursor.getColumnIndex(dbHelper.MEALS_CARBS);
            int fatsCol = cursor.getColumnIndex(dbHelper.MEALS_FATS);

            String name = cursor.getString(nameCol);
            int calories = cursor.getInt(caloriesCol);
            int protein = cursor.getInt(proteinCol);
            int carbs = cursor.getInt(carbsCol);
            int fats = cursor.getInt(fatsCol);

            //all information for current meal
            mealInfo.add(String.valueOf(id));
            mealInfo.add(name);
            mealInfo.add(String.valueOf(calories));
            mealInfo.add(String.valueOf(protein));
            mealInfo.add(String.valueOf(carbs));
            mealInfo.add(String.valueOf(fats));
        }
        Log.d(identifier, "mealInfo: " + mealInfo);
        return mealInfo;
    }

    public Cursor getMealByNameCursor(String name){
        String identifier = CLASS_NAME + " getMealByNameCursor";
        Log.d(identifier, "starting func");
        Log.d(identifier, "name: " + name);

        SQLiteDatabase db = this.getReadableDatabase();

        String query = "SELECT * FROM " + TABLE_MEALS + " WHERE " + MEALS_NAME + " LIKE ?";
        String[] selectionArgs = {"%" + name + "%"}; // Add wildcards for substring matching
        return db.rawQuery(query, selectionArgs);

    }

    public List<List<String>> getMealByName (String mealName){
        String identifier = CLASS_NAME + " getMealByName";
        Log.d(identifier, "func starting");
        Log.d(identifier, "mealName: " + mealName);

        Cursor cursor = getMealByNameCursor(mealName);

        List<List<String>> meals = new ArrayList<>();

        if(cursor.getCount() > 0){
            Log.d(identifier, "cursor.getCount() > 0");
            for(int i = 0; i < cursor.getCount(); i++){

                cursor.moveToNext();
                int idCol = cursor.getColumnIndex(dbHelper.MEALS_ID);
                int nameCol = cursor.getColumnIndex(dbHelper.MEALS_NAME);
                int caloriesCol = cursor.getColumnIndex(dbHelper.MEALS_CALORIE);
                int proteinCol = cursor.getColumnIndex(dbHelper.MEALS_PROTEIN);
                int carbsCol = cursor.getColumnIndex(dbHelper.MEALS_CARBS);
                int fatsCol = cursor.getColumnIndex(dbHelper.MEALS_FATS);


                int id = cursor.getInt(idCol);
                String name = cursor.getString(nameCol);
                int calories = cursor.getInt(caloriesCol);
                int protein = cursor.getInt(proteinCol);
                int carbs = cursor.getInt(carbsCol);
                int fats = cursor.getInt(fatsCol);

                // create array list String and all information for current meal
                List<String> curMeal = new ArrayList<>();
                curMeal.add(String.valueOf(id));
                curMeal.add(name);
                curMeal.add(String.valueOf(calories));
                curMeal.add(String.valueOf(protein));
                curMeal.add(String.valueOf(carbs));
                curMeal.add(String.valueOf(fats));

                //add current meal to the main array list
                meals.add(curMeal);
            }
        }
        return meals;
    }

    public String updateMeal(Integer id, String name, Integer calorie, Integer protein, Integer carb, Integer fat){
        String identifier = CLASS_NAME + " updateMeal";
        Log.d(identifier, "Starting updateMeal");
        Log.d(identifier, "parameters");
        Log.d(identifier, "id: " + id);
        Log.d(identifier, "name: " + name);
        Log.d(identifier, "calorie: " + calorie);
        Log.d(identifier, "protein: " + protein);
        Log.d(identifier, "carb: " + carb);
        Log.d(identifier, "fat: " + fat);

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        if(checkMealNameExists(name)){
            Log.d(identifier, "same name found");
            if(!checkKeepingSameName(id, name)){
                Log.d(identifier, "Meal name is taken");
                return "Name Taken";

            }else{
                Log.d(identifier, "meal name is being kept");
            }
        }

        try{
            values.put(MEALS_NAME, name);
            values.put(MEALS_CALORIE, calorie);
            values.put(MEALS_PROTEIN, protein);
            values.put(MEALS_CARBS, carb);
            values.put(MEALS_FATS, fat);

            String[] selection = {String.valueOf(id)};
            db.update(TABLE_MEALS, values, MEALS_ID + " = ?", selection);
            db.close();
        }
        catch (Exception e){
            return "Error Occurred: " + e;
        }

        return "";
    }

    public boolean checkKeepingSameName(Integer id, String name){
        String identifier = CLASS_NAME + " checkKeepingSameName";
        Log.d(identifier, "id: " + id);
        Log.d(identifier, "name: " + name);

        Cursor cursor = getMealByIdCursor(id);
        cursor.moveToFirst();

        int nameCol = cursor.getColumnIndex(MEALS_NAME);

        String existsName = cursor.getString(nameCol);
        Log.d(identifier, "existing name: " + existsName);

        boolean result = Objects.equals(existsName, name);
        Log.d(identifier, "result: " + result);

        return result;
    }

    public void deleteEatenMeal(Integer id){
        SQLiteDatabase dbHelp = this.getWritableDatabase();

        String whereClause = EATEN_ID + " = ? AND " + EATEN_DATE + " = ?";

        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        String currentDate = dateFormat.format(calendar.getTime());

        String[] whereArgs = {String.valueOf(id), currentDate};
        dbHelp.delete(TABLE_EATEN, whereClause, whereArgs);
        dbHelp.close();
    }
}
