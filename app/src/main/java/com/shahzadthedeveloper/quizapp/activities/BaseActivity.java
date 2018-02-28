package com.shahzadthedeveloper.quizapp.activities;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;

import com.shahzadthedeveloper.quizapp.R;
import com.shahzadthedeveloper.quizapp.utilities.Utilities;

/**
 * Created by muhammadshahzad on 2/26/18.
 */

public class BaseActivity extends AppCompatActivity {

    private Dialog progressBar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        progressBar = Utilities.getInstance(this).loadingDialog(this);
    }

    public void showProgressBar(){
        if(progressBar!=null)
            progressBar.show();
    }

    public void hideProgressBar(){
        if(progressBar!=null)
            progressBar.hide();
    }

    public void showDialogBox(String title, String msg, boolean isCancelable, String neutralText,
                              String positiveText, String negativeText, DialogInterface.OnClickListener neutralListener,
                              DialogInterface.OnClickListener positiveListener, DialogInterface.OnClickListener negativeListener){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(title);
        builder.setMessage(msg);
        if(neutralListener!=null){
            builder.setNeutralButton(neutralText, neutralListener);
        }
        if(positiveListener!=null){
            builder.setPositiveButton(positiveText, positiveListener);
        }
        if(negativeListener!=null){
            builder.setNegativeButton(negativeText, negativeListener);
        }
        builder.setCancelable(isCancelable);
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public void displayView(Fragment fragment) {
        if (fragment != null) {
            clearBackStack();
            replaceFragment(fragment);
        }
    }

    public void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragmentHolder, fragment, fragment.getClass().getName());
        //fragmentTransaction.addToBackStack(fragment.getClass().getName());
        fragmentTransaction.commit();
        //((BaseActivity)getContext()).setToolbarTitle(getResources().getString(R.string.title_day_in_out));
        //Utilities.getInstance(getContext()).saveStringPreferences(kPreferenceCurrentFragmentName, fragment.getClass().getName());
    }
    public void addFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.fragmentHolder, fragment, fragment.getClass().getName());
        fragmentTransaction.addToBackStack(fragment.getClass().getName());
        fragmentTransaction.commit();
        //((BaseActivity)getContext()).setToolbarTitle(getResources().getString(R.string.title_day_in_out));
        //Utilities.getInstance(getContext()).saveStringPreferences(kPreferenceCurrentFragmentName, fragment.getClass().getName());
    }

    private void clearBackStack() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        if (fragmentManager.getBackStackEntryCount() > 0) {
            fragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        }

    }

}
