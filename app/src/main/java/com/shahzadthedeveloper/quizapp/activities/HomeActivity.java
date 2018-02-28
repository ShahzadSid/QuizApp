package com.shahzadthedeveloper.quizapp.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.shahzadthedeveloper.quizapp.R;
import com.shahzadthedeveloper.quizapp.model.User;
import com.shahzadthedeveloper.quizapp.realm.RealmController;
import com.shahzadthedeveloper.quizapp.utilities.Utilities;

import io.realm.Realm;

import static com.shahzadthedeveloper.quizapp.utilities.Constants.EMAIL_ADDRESS;

public class HomeActivity extends BaseActivity implements View.OnClickListener {

    public static final String TAG = "HomeActivity";
    private TextView firstNameTV,lastNameTV,phoneNumberTV;
    private ImageButton phoneEditButton;
    private Button userTypeBtn, logoutBtn;
    private String userType,mEmail = "";

    private static int LOGOUT_TIMER = 5000;

    private Realm realm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        init();
    }

    private void init() {
        firstNameTV=(TextView)findViewById(R.id.firstNameTV);
        lastNameTV=(TextView)findViewById(R.id.lastNameTV);
        phoneNumberTV=(TextView)findViewById(R.id.phoneNumberTV);
        phoneEditButton=(ImageButton) findViewById(R.id.phoneChangeButton);
        userTypeBtn=(Button)findViewById(R.id.btnUserType);
        logoutBtn=(Button)findViewById(R.id.btnLogout);
        phoneEditButton.setOnClickListener(this);
        userTypeBtn.setOnClickListener(this);
        logoutBtn.setOnClickListener(this);

        this.realm = RealmController.with(this).getRealm();

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            mEmail = extras.getString(EMAIL_ADDRESS);
            User user = RealmController.getInstance().getUser(mEmail);
            if (user!= null) {
                firstNameTV.setText(user.getFirstName());
                lastNameTV.setText(user.getLastName());
                phoneNumberTV.setText(user.getPhone());
                userType = user.getUserType();
            }
        }
    }

    @Override
    public void onClick(View view) {
        if(view == phoneEditButton){
            phoneEditTapped();
        }else if(view == userTypeBtn){
            Utilities.getInstance(this).showSnackBar(this,"Your account type is: "+userType);
            Toast.makeText(this, "Your account type is: "+userType, Toast.LENGTH_SHORT).show();
        }else if (view == logoutBtn) {
            logoutTapped();
        }
    }

    private void phoneEditTapped() {
        User user = RealmController.getInstance().getUser(mEmail);
        if (user!=null) {
            fireAlertDialogBox("Quiz App","Your phone number is: ",user);
        }


    }

    public void fireAlertDialogBox(String title, String message, final User user) {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.phone_dialog, null);
        dialogBuilder.setView(dialogView);

        final EditText edt = (EditText) dialogView.findViewById(R.id.et_phone);
        dialogBuilder.setTitle(title);
        dialogBuilder.setMessage(message);
        edt.setText(user.getPhone());
        edt.setSelection(edt.getText().length());
        dialogBuilder.setPositiveButton("Done", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                RealmController.getInstance().updateUser(user.getEmail(),edt.getText().toString());
                phoneNumberTV.setText(edt.getText().toString());
            }
        });
        dialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                dialog.cancel();
            }
        });
        AlertDialog b = dialogBuilder.create();
        b.show();
    }


    private void logoutTapped() {

        showProgressBar();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                //Start the splash activity here after five seconds
                Intent intent = new Intent(HomeActivity.this,SplashActivity.class);
                startActivity(intent);
                finish();
            }
        },LOGOUT_TIMER);
    }
}
