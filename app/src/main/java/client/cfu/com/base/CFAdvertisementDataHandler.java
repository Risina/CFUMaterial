package client.cfu.com.base;


import android.graphics.Bitmap;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.math.BigInteger;
import java.util.*;


import client.cfu.com.constants.CFConstants;
import client.cfu.com.entities.CFAdvertisement;
import client.cfu.com.entities.CFAdvertisementObj;
import client.cfu.com.entities.CFEntityHelper;

/**
 * 
 */
public class CFAdvertisementDataHandler {


    public CFAdvertisementDataHandler() {
    }

    private List<CFAdvertisement> advertisements;
    private HashMap<String,String> filterParams;
    private CFUserSessionManager sessionManager;




    public CFAdvertisement getAdvertisementById(BigInteger id) {
        // TODO implement here
        return null;
    }

    public List<CFAdvertisement> getAdvertisements() {
        List<CFAdvertisement> advertisementList = new ArrayList<>();

        String result = CFHttpManager.getData(CFConstants.SERVICE_ROOT+"CFUDBService/webresources/entities.advertisement", new HashMap<String, String>());

        List<JSONObject> advertisementObjects = getValuesFromJsonString(result);
        for (JSONObject obj:advertisementObjects) {
            advertisementList.add(CFEntityHelper.getAdvertisementFromJSON(obj, false));
        }
        return advertisementList;
    }


    public List<CFAdvertisement> getAdvertisements(String searchQuery) {
        // TODO implement here
        return null;
    }


    public List<CFAdvertisement> getAdvertisements(int locationId) {
        // TODO implement here
        return null;
    }


    public List<CFAdvertisement> getFavourites(BigInteger userId) {
        // TODO implement here
        return null;
    }


    public Boolean addAdvertisement(CFAdvertisementObj advertisement) {


        Gson gson = new Gson();
        Bitmap bmp = null;

        String result = CFHttpManager.addData(CFConstants.SERVICE_ROOT + "CFUDBService/webresources/entities.advertisement", gson.toJson(advertisement), bmp);

        if(result.equals(CFConstants.STATUS_OK))
        {
            return true;
        }

        return false;

    }

    public Boolean addFavourite(BigInteger userId, BigInteger advertisementId) {
        // TODO implement here
        return null;
    }


    public Boolean removeAdvertisement(BigInteger advertisementId) {
        // TODO implement here
        return null;
    }

    public Boolean uploadImage(Bitmap image, String name) {
        // TODO implement here
        return null;
    }

    private List<JSONObject>getValuesFromJsonString(String result){
        ArrayList<JSONObject> valueArrayList = new ArrayList<>();
        try {
            JSONArray jsonArray = new JSONArray(result);
            for(int i=0; i<jsonArray.length(); i++) {
                JSONObject obj = jsonArray.getJSONObject(i);
                valueArrayList.add(obj);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return valueArrayList;
    }
}