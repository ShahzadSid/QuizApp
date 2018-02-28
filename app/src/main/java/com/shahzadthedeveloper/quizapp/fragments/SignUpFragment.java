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
import android.widget.Spinner;
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

public class SignUpFragment extends Fragment implements View.OnClickListener {

    public static final String TAG = "SignUpFragment";

    private TextInputEditText etFirstName, etLastName, etEmail, etPassword ,etPhone;
    private Spinner spinnerUsers;
    private Button btnSignup;
    private String mfirstName, mLastName, mEmail, mPassword , mPhone,mUserType;
    private TextView tvLogin;

    private Realm realm;


    public SignUpFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_signup, container, false);

        init(rootView);

        return rootView;
    }

    private void init(View rootView) {
        etFirstName = rootView.findViewById(R.id.et_first_name);
        etLastName = rootView.findViewById(R.id.et_last_name);
        etEmail = rootView.findViewById(R.id.et_email);
        etPassword = rootView.findViewById(R.id.et_password);
        etPhone = rootView.findViewById(R.id.et_phone);
        spinnerUsers = rootView.findViewById(R.id.spinner_users);
        btnSignup = rootView.findViewById(R.id.btn_sign_up);
        tvLogin = rootView.findViewById(R.id.tv_login);
        btnSignup.setOnClickListener(this);
        tvLogin.setOnClickListener(this);

        this.realm = RealmController.with(this).getRealm();
    }

    @Override
    public void onClick(View view) {
        if(view == btnSignup){
            actionSignup();
        }else if(view == tvLogin){
            ((MainActivity)getActivity()).showLoginFragment();
        }
    }


    private void actionSignup() {
        mfirstName =etFirstName.getText().toString();
        mLastName = etLastName.getText().toString();
        mEmail = etEmail.getText().toString().toLowerCase();
        mPassword = etPassword.getText().toString();
        mPhone = etPhone.getText().toString();
        mUserType = spinnerUsers.getSelectedItem().toString();
        Log.v(TAG,mUserType);

        if(TextUtils.isEmpty(mEmail)||TextUtils.isEmpty(mPassword)||TextUtils.isEmpty(mPhone)){
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
        if(!Utilities.getInstance(getContext()).isPhoneValid(mPhone)){
            Utilities.getInstance(getContext()).showSnackBar(getActivity(),this.getString(R.string.phoneInvalidError));
            return;
        }
        if (spinnerUsers.getSelectedItemPosition() == 0){
            Utilities.getInstance(getContext()).showSnackBar(getActivity(),this.getString(R.string.userInvalidError));
            return;
        }


        if (RealmController.getInstance().hasUser(mEmail)) {
            Utilities.getInstance(getContext()).showSnackBar(getActivity(),this.getString(R.string.emailAlreadyError));
            return;
        }else {
            ((BaseActivity) getContext()).showProgressBar();

            User user = new User();
            user.setFirstName(mfirstName);
            user.setLastName(mLastName);
            user.setEmail(mEmail);
            user.setPassword(mPassword);
            user.setPhone(mPhone);
            user.setUserType(mUserType);
            addNewUser(user);
        }

    }
    private void addNewUser(User user) {
        RealmController.getInstance().addUser(user);
        openHomeActivity();
    }

    private void openHomeActivity() {
        Intent intent = new Intent(getActivity(), HomeActivity.class);
        intent.putExtra(EMAIL_ADDRESS,mEmail);
        startActivity(intent);
        startActivity(intent);
        getActivity().finish();
    }
}
