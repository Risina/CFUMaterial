package client.cfu.com.base;

import org.json.JSONException;
import org.json.JSONObject;

import java.math.BigInteger;
import java.util.*;

import client.cfu.com.constants.CFConstants;
import client.cfu.com.entities.CFEntityHelper;
import client.cfu.com.entities.CFUser;


/**
 * 
 */
public class CFUserDataHandler {

    public CFUserDataHandler() {
    }

    public CFUserSessionManager sessionManager;


    public CFUser getUserById(long userId) {
        String result = CFHttpManager.getData(CFConstants.SERVICE_ROOT+"CFUDBService/webresources/entities.user/"+userId, new HashMap<String, String>());

        JSONObject obj = null;

        try {
            obj = new JSONObject(result);
            return CFEntityHelper.getUserFromJSON(obj);

        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    public Boolean addUser(CFUser user) {
        // TODO implement here
        return null;
    }

}