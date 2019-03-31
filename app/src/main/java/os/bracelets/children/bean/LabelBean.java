package os.bracelets.children.bean;

import org.json.JSONObject;

import java.io.Serializable;

public class LabelBean implements Serializable {

//    "labelId":1,
//            "labelType":3,
//            "labelTypeDesc":"其他",
//            "labelName":"感冒"

    private int labelId;
    private int labelType;
    private String labelName;
    private String labelTypeDesc;
    private boolean isChecked;

    public int getLabelId() {
        return labelId;
    }

    public void setLabelId(int labelId) {
        this.labelId = labelId;
    }

    public int getLabelType() {
        return labelType;
    }

    public void setLabelType(int labelType) {
        this.labelType = labelType;
    }

    public String getLabelName() {
        return labelName;
    }

    public void setLabelName(String labelName) {
        this.labelName = labelName;
    }

    public String getLabelTypeDesc() {
        return labelTypeDesc;
    }

    public void setLabelTypeDesc(String labelTypeDesc) {
        this.labelTypeDesc = labelTypeDesc;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    public static LabelBean parseBean(JSONObject object) {
        LabelBean bean = new LabelBean();
        bean.setLabelId(object.optInt("labelId"));
        bean.setLabelType(object.optInt("labelType"));
        bean.setLabelName(object.optString("labelName"));
        bean.setLabelTypeDesc(object.optString("labelTypeDesc"));
        return bean;
    }
}
