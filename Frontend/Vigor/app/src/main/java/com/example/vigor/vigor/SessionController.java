package com.example.vigor.vigor;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

/**
 * Controller made to properly and efficiently handle user tasks, including getting and setting user
 * variables as well as handling login and logout attempts.
 *
 * @author Kirkland Keith
 */
public class SessionController {
    private static String TAG = SessionController.class.getSimpleName();

    // File in which user info will be saved
    private static final String PREFERENCE_FILE = "VigorUserInfo";

    // SP File Keys
    private static final String LOGGEDIN_KEY = "isLoggedIn";
    private static final String USER_ID = "userID";
    private static final String USER_EMAIL = "eMail";
    private static final String USER_FIRST = "firstName";
    private static final String USER_LAST = "lastName";
    private static final String USER_ROLE = "userRole";


    SharedPreferences sharedPref;
    Editor spEditor;
    Context context;

    /**
     * Initializes the session controller to be used acrossed an individual activity
     * @param context activity context associated with the controller object
     */
    public SessionController(Context context) {
        this.context = context;
        sharedPref = this.context.getSharedPreferences(PREFERENCE_FILE, Context.MODE_PRIVATE);
        spEditor = sharedPref.edit();
    }

    /**
     * Method that stores the user's account information locally for later use.
     *
     * @param isLoggedIn Boolean to determine whether a user is logged in or not. (Default false)
     * @param uID User ID of the app's current user
     * @param email User email of the app's current user
     * @param firstName User's first name
     * @param lastName User's last name
     * @param userRole Role of the app's current user
     */
    public void attemptLogin(boolean isLoggedIn, int uID, String email, String firstName,
                             String lastName, String userRole) {
        spEditor.putBoolean(LOGGEDIN_KEY, isLoggedIn);

        if (isLoggedIn) {
            spEditor.putInt(USER_ID, uID);
            spEditor.putString(USER_EMAIL, email);
            spEditor.putString(USER_FIRST, firstName);
            spEditor.putString(USER_LAST, lastName);
            spEditor.putString(USER_ROLE, userRole);
        }

        spEditor.commit();
    }

    /**
     * Removes all stored information of the previously logged in user.
     */
    public void attemptLogout() {
        spEditor.clear();
        spEditor.putBoolean(LOGGEDIN_KEY, false);
        spEditor.commit();
    }

    /**
     * Checks to determine if a user is currently logged in or not.
     *
     * @return boolean of the user's current login status
     */
    public boolean checkLogin() {
        return sharedPref.getBoolean(LOGGEDIN_KEY, false);
    }

    /**
     * User Id Getter
     * @return returns logged in user's ID
     */
    public int returnUserID() {
        return sharedPref.getInt(USER_ID, 0);
    }

    /**
     * Email getter
     * @return returns logged in user's email
     */
    public String returnEmail() {
        return sharedPref.getString(USER_EMAIL, "");
    }

    /**
     * first name getter
     * @return returns logged in user's first name
     */
    public String returnFirstName() {
        return sharedPref.getString(USER_FIRST, "");
    }

    /**
     * last name getter
     * @return returns logged in user's last name
     */
    public String returnLastName() {
        return sharedPref.getString(USER_LAST, "");
    }

    /**
     * full name getter
     * @return returns logged in user's full name
     */
    public String returnFullName() {
        String fullName = sharedPref.getString(USER_FIRST, "") + " " +
                sharedPref.getString(USER_LAST, "");
        return fullName;
    }

    /**
     * role getter
     * @return returns logged in user's role
     */
    public String returnUserRole() {
        return sharedPref.getString(USER_ROLE, "");
    }

}
