package os.bracelets.children.bean;

import org.json.JSONObject;

import java.io.Serializable;

/**
 * Created by lishiyou on 2019/2/23.
 */

public class UserInfo implements Serializable {
    private String name;
    private String nickName;
    private String portrait;
    private String appType;
    private int sex;
    private String birthday;
    private String weight;
    private String height;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getPortrait() {
        return portrait;
    }

    public void setPortrait(String portrait) {
        this.portrait = portrait;
    }

    public String getAppType() {
        return appType;
    }

    public void setAppType(String appType) {
        this.appType = appType;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public static UserInfo parseBean(JSONObject object) {
        UserInfo info = new UserInfo();
        info.setName(object.optString("name", ""));
        info.setNickName(object.optString("nickName", ""));
        info.setPortrait(object.optString("portrait", ""));
        info.setAppType(object.optString("appType", ""));
        info.setSex(object.optInt("sex",0));
        info.setHeight(object.optString("height"));
        info.setWeight(object.optString("weight"));
        info.setBirthday(object.optString("birthday"));
        return info;
    }
}
