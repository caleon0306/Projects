// Generated by view binder compiler. Do not edit!
package com.example.caloriecounter.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.example.caloriecounter.R;
import com.google.android.material.textfield.TextInputEditText;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class CreateAccountBinding implements ViewBinding {
  @NonNull
  private final LinearLayout rootView;

  @NonNull
  public final Button buttonCreateAccount;

  @NonNull
  public final TextInputEditText inputAge;

  @NonNull
  public final TextInputEditText inputHeight;

  @NonNull
  public final TextInputEditText inputName;

  @NonNull
  public final TextInputEditText inputWeight;

  @NonNull
  public final RadioButton radioButtonActivity1;

  @NonNull
  public final RadioButton radioButtonActivity2;

  @NonNull
  public final RadioButton radioButtonActivity3;

  @NonNull
  public final RadioButton radioButtonActivity4;

  @NonNull
  public final RadioButton radioButtonActivity5;

  @NonNull
  public final RadioButton radioButtonFemale;

  @NonNull
  public final RadioButton radioButtonGainFast;

  @NonNull
  public final RadioButton radioButtonGoalGainSlow;

  @NonNull
  public final RadioButton radioButtonGoalLoseFast;

  @NonNull
  public final RadioButton radioButtonGoalLoseSlow;

  @NonNull
  public final RadioButton radioButtonGoalMaintain;

  @NonNull
  public final RadioButton radioButtonMale;

  @NonNull
  public final RadioGroup radioGroupActivity;

  @NonNull
  public final RadioGroup radioGroupGender;

  @NonNull
  public final RadioGroup radioGroupGoal;

  @NonNull
  public final LinearLayout textView;

  @NonNull
  public final TextView textView2;

  @NonNull
  public final TextView textView26;

  @NonNull
  public final TextView textView3;

  @NonNull
  public final TextView textView4;

  @NonNull
  public final TextView textView5;

  @NonNull
  public final TextView textView6;

  @NonNull
  public final TextView textView8;

  private CreateAccountBinding(@NonNull LinearLayout rootView, @NonNull Button buttonCreateAccount,
      @NonNull TextInputEditText inputAge, @NonNull TextInputEditText inputHeight,
      @NonNull TextInputEditText inputName, @NonNull TextInputEditText inputWeight,
      @NonNull RadioButton radioButtonActivity1, @NonNull RadioButton radioButtonActivity2,
      @NonNull RadioButton radioButtonActivity3, @NonNull RadioButton radioButtonActivity4,
      @NonNull RadioButton radioButtonActivity5, @NonNull RadioButton radioButtonFemale,
      @NonNull RadioButton radioButtonGainFast, @NonNull RadioButton radioButtonGoalGainSlow,
      @NonNull RadioButton radioButtonGoalLoseFast, @NonNull RadioButton radioButtonGoalLoseSlow,
      @NonNull RadioButton radioButtonGoalMaintain, @NonNull RadioButton radioButtonMale,
      @NonNull RadioGroup radioGroupActivity, @NonNull RadioGroup radioGroupGender,
      @NonNull RadioGroup radioGroupGoal, @NonNull LinearLayout textView,
      @NonNull TextView textView2, @NonNull TextView textView26, @NonNull TextView textView3,
      @NonNull TextView textView4, @NonNull TextView textView5, @NonNull TextView textView6,
      @NonNull TextView textView8) {
    this.rootView = rootView;
    this.buttonCreateAccount = buttonCreateAccount;
    this.inputAge = inputAge;
    this.inputHeight = inputHeight;
    this.inputName = inputName;
    this.inputWeight = inputWeight;
    this.radioButtonActivity1 = radioButtonActivity1;
    this.radioButtonActivity2 = radioButtonActivity2;
    this.radioButtonActivity3 = radioButtonActivity3;
    this.radioButtonActivity4 = radioButtonActivity4;
    this.radioButtonActivity5 = radioButtonActivity5;
    this.radioButtonFemale = radioButtonFemale;
    this.radioButtonGainFast = radioButtonGainFast;
    this.radioButtonGoalGainSlow = radioButtonGoalGainSlow;
    this.radioButtonGoalLoseFast = radioButtonGoalLoseFast;
    this.radioButtonGoalLoseSlow = radioButtonGoalLoseSlow;
    this.radioButtonGoalMaintain = radioButtonGoalMaintain;
    this.radioButtonMale = radioButtonMale;
    this.radioGroupActivity = radioGroupActivity;
    this.radioGroupGender = radioGroupGender;
    this.radioGroupGoal = radioGroupGoal;
    this.textView = textView;
    this.textView2 = textView2;
    this.textView26 = textView26;
    this.textView3 = textView3;
    this.textView4 = textView4;
    this.textView5 = textView5;
    this.textView6 = textView6;
    this.textView8 = textView8;
  }

  @Override
  @NonNull
  public LinearLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static CreateAccountBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static CreateAccountBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.create_account, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static CreateAccountBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.buttonCreateAccount;
      Button buttonCreateAccount = ViewBindings.findChildViewById(rootView, id);
      if (buttonCreateAccount == null) {
        break missingId;
      }

      id = R.id.inputAge;
      TextInputEditText inputAge = ViewBindings.findChildViewById(rootView, id);
      if (inputAge == null) {
        break missingId;
      }

      id = R.id.inputHeight;
      TextInputEditText inputHeight = ViewBindings.findChildViewById(rootView, id);
      if (inputHeight == null) {
        break missingId;
      }

      id = R.id.inputName;
      TextInputEditText inputName = ViewBindings.findChildViewById(rootView, id);
      if (inputName == null) {
        break missingId;
      }

      id = R.id.inputWeight;
      TextInputEditText inputWeight = ViewBindings.findChildViewById(rootView, id);
      if (inputWeight == null) {
        break missingId;
      }

      id = R.id.radioButtonActivity1;
      RadioButton radioButtonActivity1 = ViewBindings.findChildViewById(rootView, id);
      if (radioButtonActivity1 == null) {
        break missingId;
      }

      id = R.id.radioButtonActivity2;
      RadioButton radioButtonActivity2 = ViewBindings.findChildViewById(rootView, id);
      if (radioButtonActivity2 == null) {
        break missingId;
      }

      id = R.id.radioButtonActivity3;
      RadioButton radioButtonActivity3 = ViewBindings.findChildViewById(rootView, id);
      if (radioButtonActivity3 == null) {
        break missingId;
      }

      id = R.id.radioButtonActivity4;
      RadioButton radioButtonActivity4 = ViewBindings.findChildViewById(rootView, id);
      if (radioButtonActivity4 == null) {
        break missingId;
      }

      id = R.id.radioButtonActivity5;
      RadioButton radioButtonActivity5 = ViewBindings.findChildViewById(rootView, id);
      if (radioButtonActivity5 == null) {
        break missingId;
      }

      id = R.id.radioButtonFemale;
      RadioButton radioButtonFemale = ViewBindings.findChildViewById(rootView, id);
      if (radioButtonFemale == null) {
        break missingId;
      }

      id = R.id.radioButtonGainFast;
      RadioButton radioButtonGainFast = ViewBindings.findChildViewById(rootView, id);
      if (radioButtonGainFast == null) {
        break missingId;
      }

      id = R.id.radioButtonGoalGainSlow;
      RadioButton radioButtonGoalGainSlow = ViewBindings.findChildViewById(rootView, id);
      if (radioButtonGoalGainSlow == null) {
        break missingId;
      }

      id = R.id.radioButtonGoalLoseFast;
      RadioButton radioButtonGoalLoseFast = ViewBindings.findChildViewById(rootView, id);
      if (radioButtonGoalLoseFast == null) {
        break missingId;
      }

      id = R.id.radioButtonGoalLoseSlow;
      RadioButton radioButtonGoalLoseSlow = ViewBindings.findChildViewById(rootView, id);
      if (radioButtonGoalLoseSlow == null) {
        break missingId;
      }

      id = R.id.radioButtonGoalMaintain;
      RadioButton radioButtonGoalMaintain = ViewBindings.findChildViewById(rootView, id);
      if (radioButtonGoalMaintain == null) {
        break missingId;
      }

      id = R.id.radioButtonMale;
      RadioButton radioButtonMale = ViewBindings.findChildViewById(rootView, id);
      if (radioButtonMale == null) {
        break missingId;
      }

      id = R.id.radioGroupActivity;
      RadioGroup radioGroupActivity = ViewBindings.findChildViewById(rootView, id);
      if (radioGroupActivity == null) {
        break missingId;
      }

      id = R.id.radioGroupGender;
      RadioGroup radioGroupGender = ViewBindings.findChildViewById(rootView, id);
      if (radioGroupGender == null) {
        break missingId;
      }

      id = R.id.radioGroupGoal;
      RadioGroup radioGroupGoal = ViewBindings.findChildViewById(rootView, id);
      if (radioGroupGoal == null) {
        break missingId;
      }

      LinearLayout textView = (LinearLayout) rootView;

      id = R.id.textView2;
      TextView textView2 = ViewBindings.findChildViewById(rootView, id);
      if (textView2 == null) {
        break missingId;
      }

      id = R.id.textView26;
      TextView textView26 = ViewBindings.findChildViewById(rootView, id);
      if (textView26 == null) {
        break missingId;
      }

      id = R.id.textView3;
      TextView textView3 = ViewBindings.findChildViewById(rootView, id);
      if (textView3 == null) {
        break missingId;
      }

      id = R.id.textView4;
      TextView textView4 = ViewBindings.findChildViewById(rootView, id);
      if (textView4 == null) {
        break missingId;
      }

      id = R.id.textView5;
      TextView textView5 = ViewBindings.findChildViewById(rootView, id);
      if (textView5 == null) {
        break missingId;
      }

      id = R.id.textView6;
      TextView textView6 = ViewBindings.findChildViewById(rootView, id);
      if (textView6 == null) {
        break missingId;
      }

      id = R.id.textView8;
      TextView textView8 = ViewBindings.findChildViewById(rootView, id);
      if (textView8 == null) {
        break missingId;
      }

      return new CreateAccountBinding((LinearLayout) rootView, buttonCreateAccount, inputAge,
          inputHeight, inputName, inputWeight, radioButtonActivity1, radioButtonActivity2,
          radioButtonActivity3, radioButtonActivity4, radioButtonActivity5, radioButtonFemale,
          radioButtonGainFast, radioButtonGoalGainSlow, radioButtonGoalLoseFast,
          radioButtonGoalLoseSlow, radioButtonGoalMaintain, radioButtonMale, radioGroupActivity,
          radioGroupGender, radioGroupGoal, textView, textView2, textView26, textView3, textView4,
          textView5, textView6, textView8);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
