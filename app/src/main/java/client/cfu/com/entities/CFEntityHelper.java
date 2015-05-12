package client.cfu.com.entities;

import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

/**
 * Created by Risina on 4/12/15.
 */
public class CFEntityHelper {
    public static CFAdvertisement getAdvertisementFromJSON(JSONObject obj, boolean isAdView){

        CFAdvertisement ad = new CFAdvertisement();
        try {
            ad.setId(Long.parseLong(obj.get("id").toString()));
            ad.setTitle(obj.get("title").toString());
            ad.setDescription(obj.get("description").toString());
            ad.setPrice(Long.parseLong(obj.get("price").toString()));
            ad.setIsPriceNegotiable(obj.get("isPriceNegotiable").toString().equals("1"));
            ad.setModel(obj.get("model").toString());
            ad.setModelYear(Short.parseShort(obj.get("modelYear").toString()));
            ad.setEngineCapacity(Integer.parseInt(obj.get("engineCapacity").toString()));
            ad.setMilage(Long.parseLong(obj.get("milage").toString()));
            ad.setImageLocation(obj.get("imageLocation").toString());


            JSONObject fuelTypeObj = new JSONObject(obj.get("fuelTypeId").toString());
            CFFuelType ft=new CFFuelType(Integer.parseInt(fuelTypeObj.get("id").toString()));
            ft.setFuelTypeString(fuelTypeObj.get("fuelTypeString").toString());

            ad.setFuelTypeId(ft);

            JSONObject transmissionTypeObj = new JSONObject(obj.get("transmissionTypeId").toString());
            CFTransmissionType tt=new CFTransmissionType(Integer.parseInt(transmissionTypeObj.get("id").toString()));
            tt.setTransmissionTypeString(transmissionTypeObj.get("transmissionTypeString").toString());

            ad.setTransmissionTypeId(tt);

            JSONObject conditionObj = new JSONObject(obj.get("conditionId").toString());
            CFConditionType ct=new CFConditionType(Integer.parseInt(conditionObj.get("id").toString()));
            ct.setConditionString(conditionObj.get("conditionString").toString());

            ad.setConditionId(ct);

            JSONObject bodyTypeObj = new JSONObject(obj.get("bodyTypeId").toString());
            CFBodyType bt=new CFBodyType(Integer.parseInt(bodyTypeObj.get("id").toString()));
            bt.setBodyTypeString(bodyTypeObj.get("bodyTypeString").toString());

            ad.setBodyTypeId(bt);

            JSONObject brandObj = new JSONObject(obj.get("brandId").toString());
            CFBrand brt=new CFBrand(Integer.parseInt(brandObj.get("id").toString()));
            brt.setBrandString(brandObj.get("brandString").toString());

            ad.setBrandId(brt);

            JSONObject vehicleTypeObj = new JSONObject(obj.get("vehicleTypeId").toString());
            CFVehicleType vt=new CFVehicleType(Integer.parseInt(vehicleTypeObj.get("id").toString()));
            vt.setTypeString(vehicleTypeObj.get("typeString").toString());

            ad.setVehicleTypeId(vt);

            JSONObject userTypeObj = new JSONObject(obj.get("userId").toString());
            CFUser user = new CFUser(Long.parseLong(userTypeObj.get("id").toString()));
            user.setUName(isAdView?userTypeObj.get("uName").toString():userTypeObj.get("UName").toString());
            user.setPhoneNo1(userTypeObj.get("phoneNo1").toString());


            if(isSafeMethod(userTypeObj, "phoneNo2")){
                user.setPhoneNo2(userTypeObj.get("phoneNo2").toString());
            }
            if(isSafeMethod(userTypeObj, "phoneNo3")){
                user.setPhoneNo3(userTypeObj.get("phoneNo3").toString());
            }


            user.setEmail(userTypeObj.get("email").toString());

            JSONObject locationObj = new JSONObject(userTypeObj.get("locationId").toString());
            CFLocation location = new CFLocation(Integer.parseInt(locationObj.get("id").toString()));
            location.setLocationString(locationObj.get("locationString").toString());
            user.setLocationId(location);

            ad.setUserId(user);

        } catch (JSONException e) {
            e.printStackTrace();
            return null;

        }

        return ad;
    }

    public static CFUser getUserFromJSON(JSONObject obj){
        CFUser user = new CFUser();

        try {
            user.setId(Long.parseLong(obj.get("id").toString()));
            user.setUName(obj.get("UName").toString());
            user.setEmail(obj.get("email").toString());
        }
        catch(JSONException e)
        {
            e.printStackTrace();
            return null;
        }

        return user;

    }

    private static boolean isSafeMethod(JSONObject obj, String key) {
        boolean b;
        try {
            obj.get(key);
            b = true;
        }
        catch (JSONException e) {
            b = false;
        }
        return b;

    }
}
