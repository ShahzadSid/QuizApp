package com.shahzadthedeveloper.quizapp.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.shahzadthedeveloper.quizapp.R;
import com.shahzadthedeveloper.quizapp.activities.HomeActivity;
import com.shahzadthedeveloper.quizapp.model.User;
import com.shahzadthedeveloper.quizapp.realm.RealmController;
import com.shahzadthedeveloper.quizapp.utilities.Utilities;
import com.shahzadthedeveloper.quizapp.activities.BaseActivity;
import com.shahzadthedeveloper.quizapp.activities.MainActivity;

import io.realm.Realm;

import static com.shahzadthedeveloper.quizapp.utilities.Constants.EMAIL_ADDRESS;

/**
 * Created by muhammadshahzad on 2/26/18.
 */

public class LoginFragment extends Fragment implements View.OnClickListener {

    public static final String TAG = "LoginFragment";

    private TextInputEditText etEmail, etPassword;
    private Button btnLogin;
    private String mEmail, mPassword;
    private TextView tvSignup;

    private Realm realm;

    public LoginFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_login, container, false);

        init(rootView);

        return rootView;
    }

    private void init(View rootView) {
        etEmail = rootView.findViewById(R.id.et_email);
        etPassword = rootView.findViewById(R.id.et_password);
        btnLogin = rootView.findViewById(R.id.btn_login);
        tvSignup = rootView.findViewById(R.id.tv_signup);
        btnLogin.setOnClickListener(this);
        tvSignup.setOnClickListener(this);
        this.realm = RealmController.with(this).getRealm();
    }

    @Override
    public void onClick(View view) {
        if(view == btnLogin){
            actionLogin();
        }else if(view == tvSignup){
            ((MainActivity)getActivity()).showSignupFragment();
        }
    }

    private void actionLogin() {
        mEmail = etEmail.getText().toString().toLowerCase();
        mPassword = etPassword.getText().toString();
        if(TextUtils.isEmpty(mEmail)||TextUtils.isEmpty(mPassword)){
            Utilities.getInstance(getContext()).showSnackBar(getActivity(),this.getString(R.string.completeFormError));

            return;
        }
        if(!Utilities.getInstance(getContext()).isEmailValid(mEmail)){
            Utilities.getInstance(getContext()).showSnackBar(getActivity(),this.getString(R.string.emailInvalidError));
            return;
        }
        if(!Utilities.getInstance(getContext()).isPasswordValid(mPassword)){
            Utilities.getInstance(getContext()).showSnackBar(getActivity(),this.getString(R.string.wrongPasswordError));
            return;
        }
        ((BaseActivity) getContext()).showProgressBar();
        User user = RealmController.getInstance().checkUserValidation(mEmail,mPassword);
        if (user != null) {
            openHomeActivity();
        }else {
            ((BaseActivity)getContext()).hideProgressBar();
            Utilities.getInstance(getContext()).showSnackBar(getActivity(),this.getString(R.string.invalidCredentials));
            etPassword.setText("");
        }



    }

    private void openHomeActivity() {
        Intent intent = new Intent(getActivity(), HomeActivity.class);
        intent.putExtra(EMAIL_ADDRESS,mEmail);
        startActivity(intent);
        getActivity().finish();
    }


}
