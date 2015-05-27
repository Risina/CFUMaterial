package client.cfu.com.base;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.*;

import client.cfu.com.constants.CFConstants;

/**
 * 
 */
public class CFMinorDataHandler {

    /**
     * 
     */
    public CFMinorDataHandler() {
    }

    /**
     * @return
     */
    public static List<JSONObject> getLocations() {

        String result = CFHttpManager.getData(CFConstants.SERVICE_ROOT+"CFUDBService/webresources/entities.location", new HashMap<String, String>());
        return getValuesFromJsonString(result, "locationString");
    }

    /**
     * @return
     */
    public static List<JSONObject> getVehicleTypes() {
        String result = CFHttpManager.getData(CFConstants.SERVICE_ROOT+"CFUDBService/webresources/entities.vehicletype", new HashMap<String, String>());
        return getValuesFromJsonString(result, "typeString");
    }

    /**
     * @return
     */
    public static List<JSONObject> getBrands() {
        String result = CFHttpManager.getData(CFConstants.SERVICE_ROOT+"CFUDBService/webresources/entities.brand", new HashMap<String, String>());
        return getValuesFromJsonString(result, "brandString");
    }

    /**
     * @return
     */
    public static List<JSONObject> getBodyTypes() {
        String result = CFHttpManager.getData(CFConstants.SERVICE_ROOT+"CFUDBService/webresources/entities.bodytype", new HashMap<String, String>());
        return getValuesFromJsonString(result, "bodyTypeString");
    }

    /**
     * @return
     */
    public static List<JSONObject> getTransmissionTypes() {
        String result = CFHttpManager.getData(CFConstants.SERVICE_ROOT+"CFUDBService/webresources/entities.transmissiontype", new HashMap<String, String>());
        return getValuesFromJsonString(result, "transmissionTypeString");
    }

    /**
     * @return
     */
    public static List<JSONObject> getConditions() {
        String result = CFHttpManager.getData(CFConstants.SERVICE_ROOT+"CFUDBService/webresources/entities.conditiontype", new HashMap<String, String>());
        return getValuesFromJsonString(result, "conditionString");
    }

    /**
     * @return
     */
    public static List<JSONObject> getFuelTypes() {
        String result = CFHttpManager.getData(CFConstants.SERVICE_ROOT+"CFUDBService/webresources/entities.fueltype", new HashMap<String, String>());
        return getValuesFromJsonString(result, "fuelTypeString");
    }

    private static List<JSONObject> getValuesFromJsonString(String result, String key){
        ArrayList<JSONObject> valueArrayList = new ArrayList<>();
        HashMap<String, String> map;
        try {
            JSONArray jsonArray = new JSONArray(result);
            for(int i=0; i<jsonArray.length(); i++) {
                JSONObject obj = jsonArray.getJSONObject(i);
                obj.put("string",  obj.get(key).toString());
                valueArrayList.add(obj);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return valueArrayList;
    }

}