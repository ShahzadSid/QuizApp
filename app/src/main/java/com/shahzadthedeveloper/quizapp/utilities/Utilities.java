package com.shahzadthedeveloper.quizapp.utilities;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.design.widget.Snackbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;

import com.shahzadthedeveloper.quizapp.R;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by muhammadshahzad on 2/26/18.
 */

public class Utilities {

    private static Utilities instance = null;
    private static Context mContext;


    private Utilities() {

    }

    public static Utilities getInstance(Context context) {
        mContext = context;
        if (instance == null) {
            instance = new Utilities();
        }
        return instance;
    }

    public boolean isEmailValid(String email) {
        boolean isValid = false;

        String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
        CharSequence inputStr = email;

        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(inputStr);
        if (matcher.matches()) {
            isValid = true;
        }

        return isValid;
    }

    public boolean isPasswordValid(String password) {
        boolean isValid = false;

        String expression = "^(?=.*[a-zA-Z])(?=.*\\d)(?=.*[!@#$%^&*()_+])[A-Za-z\\d][A-Za-z\\d!@#$%^&*()_+]{7,19}$";
        CharSequence inputStr = password;

        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(inputStr);
        if (matcher.matches()) {
            isValid = true;
        }

        return isValid;
    }

    public boolean isPhoneValid(String phone) {
        boolean isValid = false;

        String expression = "^\\+?[0-9. ()-]{10,25}$";;
        CharSequence inputStr = phone;

        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(inputStr);
        if (matcher.matches()) {
            isValid = true;
        }

        return isValid;
    }


    public Dialog loadingDialog(Context context){
        Dialog dialog = new Dialog(context, android.R.style.Theme_Black);
        View view = LayoutInflater.from(context).inflate(R.layout.progress_dialog, null);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setContentView(view);
        return dialog;
    }

    public void showSnackBar(Activity parent, String message){
        Snackbar.make(parent.findViewById(android.R.id.content),
                message, Snackbar.LENGTH_LONG).show();
    }

}
