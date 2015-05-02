package client.cfu.com.entities;

import java.util.List;

/**
 * Created by Risina on 4/26/15.
 */
public class Data {
    private Header header;
    private String result;
    // Add/generate getters and setters.

    public static class Header {
        private List<Alert> alerts;
        private String session;
        // Add/generate getters and setters.
    }

    public static class Alert {
        private Long AlertID;
        private Object TSExpires;
        private Integer Target;
        private String Text;
        private Integer Type;
        // Add/generate getters and setters.
        // PS: I would lowercase the property names in both JSON as this class.
    }
}
