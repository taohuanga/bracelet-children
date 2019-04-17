package os.bracelets.children.bean;

import android.text.TextUtils;

import org.json.JSONObject;

import java.io.Serializable;

/**
 * Created by lishiyou on 2019/3/5.
 */

public class RemindBean implements Serializable {
// "createDate":"2019-04-17 19:26:35",
//         "msgId":85,
//         "content":"温馨提示，您的父母在【广东省深圳市龙岗区坂田街道何背】处跌倒，请及时关注!",
//         "msgType":1,
//         "isRead":0,
//         "longitude":"114.064304",
//         "latitude":"22.657569",
//         "location":"广东省深圳市龙岗区坂田街道何背",
//         "title":"跌倒提示",
//         "parentAccountId":226,
//         "accountId":225,
//         "nickName":"施雅雯",
//         "phone":"13640919463",
//         "portrait":"http://47.101.221.44/pic/0/group/logo/201904/1904072204332846.jpg"

    //提醒ID
    private int msgId;
    private int msgType;
    private int isRead;
    //提醒时间
    private String createDate;
    //提醒内容
    private String content;

    private String location;

    private String latitude;

    private String longitude;
    private String phone;
    private String nickName;
    private String portrait;



    public int getMsgId() {
        return msgId;
    }

    public void setMsgId(int msgId) {
        this.msgId = msgId;
    }

    public int getMsgType() {
        return msgType;
    }

    public void setMsgType(int msgType) {
        this.msgType = msgType;
    }

    public int getIsRead() {
        return isRead;
    }

    public void setIsRead(int isRead) {
        this.isRead = isRead;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
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

    public static RemindBean parseBean(JSONObject object) {
        RemindBean remind = new RemindBean();
        remind.setMsgId(object.optInt("msgId"));
        remind.setMsgType(object.optInt("msgType"));
        remind.setIsRead(object.optInt("isRead"));
        remind.setCreateDate(object.optString("createDate"));
        remind.setLatitude(object.optString("latitude"));
        remind.setLongitude(object.optString("longitude"));
        remind.setLocation(object.optString("location"));
        remind.setContent(object.optString("content"));
        remind.setNickName(object.optString("nickName"));
        remind.setPhone(object.optString("phone"));
        remind.setPortrait(object.optString("portrait"));
        return remind;
    }
}
