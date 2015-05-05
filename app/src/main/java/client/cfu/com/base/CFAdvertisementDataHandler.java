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
import client.cfu.com.entities.CFFavourite;

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

        return result.equals(CFConstants.STATUS_OK);

    }

    public Boolean addFavourite(CFFavourite favourite) {

        Gson gson = new Gson();
        Bitmap bmp = null;
        String result = CFHttpManager.addData(CFConstants.SERVICE_ROOT + "CFUDBService/webresources/entities.favourite", gson.toJson(favourite), bmp);

        return result.equals(CFConstants.STATUS_OK);
    }

    public Boolean deleteFavourite(CFFavourite favourite)
    {
       String result = CFHttpManager.deleteData(CFConstants.SERVICE_ROOT + "CFUDBService/webresources/entities.favourite/"+favourite.getId()+"/");
       return result.equals(CFConstants.STATUS_OK);
    }

    public String getFavourite(Long userId, Long advertisementId)
    {
        String result = CFHttpManager.getData(CFConstants.SERVICE_ROOT+"CFUDBService/webresources/entities.favourite/get/"+userId+"/"+advertisementId, new HashMap<String, String>());

        if(!result.equals(""))
        {
            return result;
        }
        return CFConstants.STATUS_ERROR;
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