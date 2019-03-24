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
    private int sex;
    private String relationship;
    private String profile;
    private String sexDesc;
    private String labels;
    private String accountId;

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

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public static FamilyMember parseBean(JSONObject object) {
        FamilyMember member = new FamilyMember();
        member.setNickName(object.optString("nickName", ""));
        member.setRelationship(object.optString("relationship", ""));
        member.setProfile(object.optString("profile", ""));
        member.setSexDesc(object.optString("sexDesc", ""));
        member.setLabels(object.optString("labels", ""));
        member.setSex(object.optInt("sex", 0));
        return member;
    }
}
