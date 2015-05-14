package client.cfu.com.constants;

import org.json.JSONObject;
import java.util.List;

/**
 * Created by Risina on 4/14/15.
 */
public class CFConstants {
    public static final String STATUS_OK = "status ok";
    public static final String STATUS_ERROR = "status error";
    public static final String SERVICE_ROOT = "https://car4u-risina.rhcloud.com/";
//    public static final String SERVICE_ROOT = "http://192.168.1.17:8255/";
    public static final String CURRENCY = "Rs. ";
    public static List<JSONObject> LOCATIONS = null;
    public static List<JSONObject> BRANDS = null;
    public static List<JSONObject> TRANSMISSION_TYPES = null;
    public static List<JSONObject> BODY_TYPES = null;
    public static List<JSONObject> VEHICLE_TYPES = null;
    public static List<JSONObject> CONDITION_TYPES = null;
    public static List<JSONObject> FUEL_TYPES = null;
}
