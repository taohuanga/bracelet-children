package os.bracelets.children.bean;

import org.json.JSONObject;

import java.io.Serializable;

public class DailySports implements Serializable {

    private String dailyDay;
    private String dailyTime;

    private int stepNum;

    public String getDailyDay() {
        return dailyDay;
    }

    public void setDailyDay(String dailyDay) {
        this.dailyDay = dailyDay;
    }

    public int getStepNum() {
        return stepNum;
    }

    public void setStepNum(int stepNum) {
        this.stepNum = stepNum;
    }

    public String getDailyTime() {
        return dailyTime;
    }

    public void setDailyTime(String dailyTime) {
        this.dailyTime = dailyTime;
    }

    public DailySports() {
    }

    public DailySports(String dailyTime, int stepNum) {
        this.dailyTime = dailyTime;
        this.stepNum = stepNum;
    }

    public static DailySports parseBean(JSONObject object) {
        DailySports sports = new DailySports();
        sports.setDailyDay(object.optString("dailyDay"));
        sports.setStepNum(object.optInt("stepNum"));
        sports.setDailyTime(object.optString("date"));
        return sports;
    }
}
