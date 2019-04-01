package os.bracelets.children.bean;

import org.json.JSONObject;

import java.io.Serializable;

/**
 * Created by lishiyou on 2019/3/5.
 */

public class RemindBean implements Serializable {


    //提醒ID
    private int msgId;
    private int msgType;
    private int isRead;
    //提醒时间
    private String createDate;
    //提醒内容
    private String content;


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

    public static RemindBean parseBean(JSONObject object) {
        RemindBean remind = new RemindBean();
        remind.setMsgId(object.optInt("msgId"));
        remind.setMsgType(object.optInt("msgType"));
        remind.setIsRead(object.optInt("isRead"));
        remind.setCreateDate(object.optString("createDate"));
        remind.setContent(object.optString("content"));
        return remind;
    }
}
