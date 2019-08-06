package os.bracelets.children.bean;

import org.json.JSONObject;

import java.io.Serializable;

public class Remind implements Serializable {

//    {"remindTitle":"提醒标题","remindContent":"提醒内容","remindPeriod":"3;4;5","remindTime":"11:00:00"}

    private String remindTitle;
    private String remindContent;
    private String remindPeriod;
    private String remindTime;
    private int remindId;

    public String getRemindTitle() {
        return remindTitle;
    }

    public void setRemindTitle(String remindTitle) {
        this.remindTitle = remindTitle;
    }

    public String getRemindContent() {
        return remindContent;
    }

    public void setRemindContent(String remindContent) {
        this.remindContent = remindContent;
    }

    public String getRemindPeriod() {
        return remindPeriod;
    }

    public void setRemindPeriod(String remindPeriod) {
        this.remindPeriod = remindPeriod;
    }

    public String getRemindTime() {
        return remindTime;
    }

    public void setRemindTime(String remindTime) {
        this.remindTime = remindTime;
    }

    public int getRemindId() {
        return remindId;
    }

    public void setRemindId(int remindId) {
        this.remindId = remindId;
    }

    public static Remind parseBean(JSONObject object){

        Remind remind = new Remind();

        remind.setRemindId(object.optInt("remindId"));
        remind.setRemindTitle(object.optString("remindTitle"));
        remind.setRemindContent(object.optString("remindContent"));
        remind.setRemindPeriod(object.optString("remindPeriod"));
        remind.setRemindTime(object.optString("remindTime"));

        return remind;
    }
}
