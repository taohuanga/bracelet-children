package os.bracelets.children.bean;

import org.json.JSONObject;

import java.io.Serializable;

/**
 * Created by lishiyou on 2019/3/24.
 */

public class FamilyMember implements Serializable {
//
//     "profile":"https://api.jixiancai.cn/0/post/pic/20190108/1901081042347239.jpg",
//             "nickName":"习大大",
//             "relationship":"母女",
//             "sex":1,
//             "sexDesc":"男",
//             "labels":"",
//             "accountId":""

    private String nickName;
    private int sex;//0 未知 1 男 2 女
    private String relationship;
    private String profile;
    private String sexDesc;
    private String labels;

    private String realName;
    private String birthday;
    private String height;
    private String weight;
    private String phone;

    private int accountId;

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public String getRelationship() {
        return relationship;
    }

    public void setRelationship(String relationship) {
        this.relationship = relationship;
    }

    public String getProfile() {
        return profile;
    }

    public void setProfile(String profile) {
        this.profile = profile;
    }

    public String getSexDesc() {
        return sexDesc;
    }

    public void setSexDesc(String sexDesc) {
        this.sexDesc = sexDesc;
    }

    public String getLabels() {
        return labels;
    }

    public void setLabels(String labels) {
        this.labels = labels;
    }

    public int getAccountId() {
        return accountId;
    }

    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }


    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public static FamilyMember parseBean(JSONObject object) {
        FamilyMember member = new FamilyMember();
        member.setNickName(object.optString("nickName", ""));
        member.setRelationship(object.optString("relationship", ""));
        member.setProfile(object.optString("portrait", ""));
        member.setSexDesc(object.optString("sexDesc", ""));
        member.setLabels(object.optString("labels", ""));
        member.setSex(object.optInt("sex", 0));
        member.setAccountId(object.optInt("accountId", 0));

        member.setRealName(object.optString("realName", ""));
        member.setBirthday(object.optString("birthday", ""));
        member.setHeight(object.optString("height", ""));
        member.setWeight(object.optString("weight", ""));
        member.setPhone(object.optString("phone", ""));
        return member;
    }

    @Override
    public String toString() {
        return "FamilyMember{" +
                "nickName='" + nickName + '\'' +
                ", sex=" + sex +
                ", relationship='" + relationship + '\'' +
                ", profile='" + profile + '\'' +
                ", sexDesc='" + sexDesc + '\'' +
                ", labels='" + labels + '\'' +
                ", realName='" + realName + '\'' +
                ", birthday='" + birthday + '\'' +
                ", height='" + height + '\'' +
                ", weight='" + weight + '\'' +
                ", phone='" + phone + '\'' +
                ", accountId=" + accountId +
                '}';
    }
}
