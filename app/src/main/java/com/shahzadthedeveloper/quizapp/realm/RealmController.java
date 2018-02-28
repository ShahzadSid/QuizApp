package com.shahzadthedeveloper.quizapp.realm;

import android.app.Activity;
import android.app.Application;
import android.support.v4.app.Fragment;
import android.util.Log;

import com.shahzadthedeveloper.quizapp.model.User;

import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by muhammadshahzad on 2/28/18.
 */

public class RealmController {

    private static RealmController instance;
    private final Realm realm;

    public RealmController(Application application) {
        realm = Realm.getDefaultInstance();
    }

    public static RealmController with(Fragment fragment) {

        if (instance == null) {
            instance = new RealmController(fragment.getActivity().getApplication());
        }
        return instance;
    }

    public static RealmController with(Activity activity) {

        if (instance == null) {
            instance = new RealmController(activity.getApplication());
        }
        return instance;
    }

    public static RealmController with(Application application) {

        if (instance == null) {
            instance = new RealmController(application);
        }
        return instance;
    }

    public static RealmController getInstance() {

        return instance;
    }

    public Realm getRealm() {

        return realm;
    }

    //Refresh the realm istance
    public void refresh() {

        realm.refresh();
    }

    //clear all objects from User.class
    public void clearAll() {

        realm.beginTransaction();
        realm.clear(User.class);
        realm.commitTransaction();
    }

    //query a single item with the given email
    public User getUser(String email) {

        return realm.where(User.class).equalTo("email", email).findFirst();
    }

    public User checkUserValidation(String email, String password) {
        RealmResults<User> realmObjects = realm.where(User.class).findAll();
        for (User myRealmObject : realmObjects) {
            if (email.equals(myRealmObject.getEmail()) && password.equals(myRealmObject.getPassword())) {
                Log.e("user_email", myRealmObject.getEmail());
                return myRealmObject;
            }
        }
        return null;
    }


    //check if User.class is empty
    public boolean hasUser(String email) {

        try {
            User user = realm.where(User.class).equalTo("email",email).findFirst();
            if (user != null) {
                return true;
            }
        }catch (NullPointerException ne) {
            Log.v("Realm",ne.getLocalizedMessage());
        }
        return false;
    }

    public  void addUser(User user) {
        realm.beginTransaction();
        realm.copyToRealm(user);
        realm.commitTransaction();
    }

    public void updateUser(String email,String newPhone) {
        User user = realm.where(User.class)
                .equalTo("email", email)
                .findFirst();
        realm.beginTransaction();
        if (user != null) {

            user.setPhone(newPhone);
        } else {

        }
        realm.commitTransaction();
    }

}
