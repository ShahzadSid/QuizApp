package com.shahzadthedeveloper.quizapp.activities;

import android.support.design.widget.TabLayout;
import android.os.Bundle;

import com.shahzadthedeveloper.quizapp.fragments.LoginFragment;
import com.shahzadthedeveloper.quizapp.R;
import com.shahzadthedeveloper.quizapp.fragments.SignUpFragment;

public class MainActivity extends BaseActivity {

    //Main Activity instance
    public static MainActivity sInstance;
    private TabLayout mTabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Connect the reference variable with XML element
        mTabLayout = (TabLayout) findViewById(R.id.tabs);
        sInstance = this;

        //To load login fragment by default, below is the code for that
        if (savedInstanceState == null) {
            displayView(new LoginFragment());
        }

        //Tab layout tab selected listener
        mTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                //If position is equal to 1 load SignUp Fragment otherwise always loads Login Fragment
                if (tab.getPosition() == 1) {
                    displayView(new SignUpFragment());
                }else {
                    displayView(new LoginFragment());
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

    }
    public void showSignupFragment() {
        TabLayout.Tab tab = mTabLayout.getTabAt(1);
        tab.select();
    }

    public void showLoginFragment() {
        TabLayout.Tab tab = mTabLayout.getTabAt(0);
        tab.select();
    }
}
