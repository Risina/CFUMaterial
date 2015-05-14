package client.cfu.com.base;


import android.graphics.Bitmap;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.math.BigInteger;
import java.util.*;


import client.cfu.com.cfumaterial.LoginFragment;
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



    public List<CFAdvertisement> getAdvertisements() {
        List<CFAdvertisement> advertisementList = new ArrayList<>();

        String result = CFHttpManager.getData(CFConstants.SERVICE_ROOT+"CFUDBService/webresources/entities.advertisement", new HashMap<String, String>());

        List<JSONObject> advertisementObjects = getValuesFromJsonString(result);
        for (JSONObject obj:advertisementObjects) {
            advertisementList.add(CFEntityHelper.getAdvertisementFromJSON(obj, false));
        }
        return advertisementList;
    }

    public List<CFAdvertisement>getAdvertisementsByRange(long from, long to){
        List<CFAdvertisement> advertisementList = new ArrayList<>();

        String result = CFHttpManager.getData(CFConstants.SERVICE_ROOT+"CFUDBService/webresources/entities.advertisement/"+from+"/"+to, new HashMap<String, String>());

        List<JSONObject> advertisementObjects = getValuesFromJsonString(result);
        for (JSONObject obj:advertisementObjects) {
            advertisementList.add(CFEntityHelper.getAdvertisementFromJSON(obj, false));
        }
        return advertisementList;
    }

    public CFAdvertisement getAdvertisementById(long id){
        String result = CFHttpManager.getData(CFConstants.SERVICE_ROOT+"CFUDBService/webresources/entities.advertisement/"+Long.toString(id), new HashMap<String, String>());
        JSONObject obj = null;

        try {
            obj = new JSONObject(result);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return CFEntityHelper.getAdvertisementFromJSON(obj, false);
    }




//    public List<CFAdvertisement> getAdvertisements(String searchQuery) {
//        // TODO implement here
//        return null;
//    }


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
        String imageResult;

        if(advertisement.getImage() != null && !advertisement.getImageName().equals(""))
        {
            imageResult = uploadImage(advertisement.getImage(), advertisement.getImageName());
        }
        else {
            imageResult = CFConstants.STATUS_OK;
        }


        String result = CFHttpManager.addData(CFConstants.SERVICE_ROOT + "CFUDBService/webresources/entities.advertisement", gson.toJson(advertisement));

        return imageResult.equals(CFConstants.STATUS_OK)
                && result.equals(CFConstants.STATUS_OK)
                ;

    }

    public Boolean addFavourite(CFFavourite favourite) {

        Gson gson = new Gson();
        Bitmap bmp = null;
        String result = CFHttpManager.addData(CFConstants.SERVICE_ROOT + "CFUDBService/webresources/entities.favourite", gson.toJson(favourite));

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

    public List<Long> getFavourites(Long userId)
    {
        String result = CFHttpManager.getData(CFConstants.SERVICE_ROOT+"CFUDBService/webresources/entities.favourite/get/"+userId, new HashMap<String, String>());

        JSONArray obj;
        List<Long> list = new ArrayList<>();

        try {
            obj = new JSONArray(result);
            for(int i=0; i<obj.length(); i++) {
                list.add(Long.parseLong(obj.get(i).toString()));
            }
            String s = "sad";
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return list;
    }


    public Boolean removeAdvertisement(BigInteger advertisementId) {
        // TODO implement here
        return null;
    }

    public String uploadImage(Bitmap image, String name) {
        return CFHttpManager.uploadImage(image, name, Long.parseLong("1"));
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