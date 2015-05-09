package client.cfu.com.base;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;


import client.cfu.com.constants.CFConstants;
import client.cfu.com.entities.CFEntityHelper;
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

    private static CFUser user;

    public String createUserLoginSession(String email, String password) {

        String status = CFHttpManager.authenticate(email, password);


        if(!status.equals(CFConstants.STATUS_ERROR)) {
            user = new CFUserDataHandler().getUserById(Long.parseLong(status));
            SharedPreferences.Editor editor = applicationContext.getSharedPreferences("com.cfu.user", Context.MODE_PRIVATE).edit();
            editor.putString("userEmail", email);
            editor.putString("userPw", password);
            editor.putString("userId", status);
            editor.putString("userName", user.getUName());
            editor.apply();
            return CFConstants.STATUS_OK;
        }
        return status;
    }

    public static boolean isUserLoggedIn(Context context){
        SharedPreferences prefs = context.getSharedPreferences("com.cfu.user", Context.MODE_PRIVATE);
        String userE = prefs.getString("userEmail", null);
        String pw = prefs.getString("userPw", null);
        String id = prefs.getString("userId", null);
        String userName = prefs.getString("userName", null);

        return (userE!=null) && (pw!=null) && (id!=null) && (userName!=null);
    }


    public static long getUserId(Context context)
    {
        SharedPreferences prefs = context.getSharedPreferences("com.cfu.user", Context.MODE_PRIVATE);
        String id = prefs.getString("userId", null);

        if(id!=null)
        {
           return Long.parseLong(id);
        }

        return 0;
    }

    public static CFUser getUser(Context context)
    {
        if(isUserLoggedIn(context))
        {
            SharedPreferences prefs = context.getSharedPreferences("com.cfu.user", Context.MODE_PRIVATE);
            CFUser user = new CFUser();
            user.setEmail(prefs.getString("userEmail", null));
            user.setUName(prefs.getString("userName", null));
            user.setId(Long.parseLong(prefs.getString("userId", null)));
            return user;
        }
        return null;
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

    public static void logoutUser(Context context) {
        SharedPreferences.Editor editor = context.getSharedPreferences("com.cfu.user", Context.MODE_PRIVATE).edit();
        editor.putString("userEmail", null);
        editor.putString("userPw", null);
        editor.putString("userId", null);
        editor.putString("userName", null);
        editor.apply();
    }

}