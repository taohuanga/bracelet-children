package os.bracelets.children.utils;

import android.text.TextUtils;
import android.widget.TextView;

import java.math.BigDecimal;

/**
 * Created by lishiyou on 2019/3/18.
 */

public class AppUtils {

    public static String getSex(int sex) {
        switch (sex) {
            case 0:
                return "未知";
            case 1:
                return "男";
            case 2:
                return "女";
        }
        return "";
    }

    public static String getDistance(int distance) {
        if (distance < 1000)
            return distance + "m";

        double f = (double) distance/1000;

        double d = new BigDecimal(f).setScale(1, BigDecimal.ROUND_HALF_UP).doubleValue();
        return d + "公里";
    }

    public static String getRemindDate(String date){
        if(TextUtils.isEmpty(date))
            return "";
        String sdate = "";
        String[] mDate = date.split(";");
        for (String s:mDate){
            if(s.equals("1"))
                sdate+="周一";
            if(s.equals("2"))
                sdate+=" 周二";
            if(s.equals("3"))
                sdate+=" 周三";
            if(s.equals("4"))
                sdate+=" 周四";
            if(s.equals("5"))
                sdate+=" 周五";
            if(s.equals("6"))
                sdate+=" 周六";
            if(s.equals("7"))
                sdate+=" 周日";
        }

        return sdate;
    }
}
