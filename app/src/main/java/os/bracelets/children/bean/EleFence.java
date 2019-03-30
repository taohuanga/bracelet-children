package os.bracelets.children.bean;

import org.json.JSONObject;

import java.io.Serializable;

public class EleFence implements Serializable {

//     "id":4,
//             "parentAccountId":205,
//             "location":"深圳市",
//             "longitude":"114.040966",
//             "latitude":"22.620508",
//             "fenceScope":15,
//             "createBy":199,
//             "createDate":1553785114000,
//             "updateBy":199,
//             "updateDate":1553824872000,
//             "delFlag":"0"


    private int id;
    private int parentAccountId;
    private int fenceScope;
    private String location;
    private String longitude;
    private String latitude;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getParentAccountId() {
        return parentAccountId;
    }

    public void setParentAccountId(int parentAccountId) {
        this.parentAccountId = parentAccountId;
    }

    public int getFenceScope() {
        return fenceScope;
    }

    public void setFenceScope(int fenceScope) {
        this.fenceScope = fenceScope;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public static EleFence parseBean(JSONObject object) {
        EleFence eleFence = new EleFence();
        eleFence.setId(object.optInt("id"));
        eleFence.setParentAccountId(object.optInt("parentAccountId"));
        eleFence.setFenceScope(object.optInt("fenceScope"));

        eleFence.setLocation(object.optString("location"));
        eleFence.setLongitude(object.optString("longitude"));
        eleFence.setLatitude(object.optString("latitude"));

        return eleFence;
    }
}
