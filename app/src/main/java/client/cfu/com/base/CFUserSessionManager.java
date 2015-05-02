package client.cfu.com.base;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;


import client.cfu.com.constants.CFConstants;
import client.cfu.com.entities.CFUser;

/**
 * 
 */
public class CFUserSessionManager {

    private static CFUserSessionManager sessionManager = new CFUserSessionManager();
    private CFUserSessionManager() {
    }

    private static Context applicationContext;

    private SharedPreferences pref;

    public String createUserLoginSession(String email, String password) {

        String status = CFHttpManager.authenticate(email, password);

        if(status.equals(CFConstants.STATUS_OK)) {
            SharedPreferences.Editor editor = applicationContext.getSharedPreferences("com.cfu.user", Context.MODE_PRIVATE).edit();
            editor.putString("userEmail", email);
            editor.putString("userPw", password);
            editor.apply();
            return status;
        }
        return status;
    }

    public static CFUserSessionManager getInstance(Context appContext) {
        // TODO implement here
        applicationContext = appContext;
        return sessionManager;
    }

    private Boolean authenticate(String email, String password) {
        // TODO implement here
        return null;
    }

    public CFUser getUser() {
        // TODO implement here
        return null;
    }

    public void logoutUser() {
        // TODO implement here
    }

}