package os.bracelets.children.jpush;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;

import aio.health2world.utils.Logger;
import cn.jpush.android.api.JPushInterface;
import os.bracelets.children.app.home.EleFenceActivity;
import os.bracelets.children.app.home.FallPositionActivity;
import os.bracelets.children.bean.RemindBean;

/**
 * 自定义接收器
 * <p>
 * 如果不定义这个 Receiver，则：
 * 1) 默认用户会打开主界面
 * 2) 接收不到自定义消息
 */
public class MyReceiver extends BroadcastReceiver {
    private static final String TAG = "JIGUANG-PUSH";

    @Override
    public void onReceive(Context context, Intent intent) {
        try {
            Bundle bundle = intent.getExtras();
            Logger.d(TAG, "[MyReceiver] onReceive - " + intent.getAction() + ", extras: " + printBundle(bundle));

            if (JPushInterface.ACTION_REGISTRATION_ID.equals(intent.getAction())) {
                String regId = bundle.getString(JPushInterface.EXTRA_REGISTRATION_ID);
                Logger.d(TAG, "[MyReceiver] 接收Registration Id : " + regId);
                //send the Registration Id to your server...

            } else if (JPushInterface.ACTION_MESSAGE_RECEIVED.equals(intent.getAction())) {
                Logger.d(TAG, "[MyReceiver] 接收到推送下来的自定义消息: " + bundle.getString(JPushInterface.EXTRA_MESSAGE));

            } else if (JPushInterface.ACTION_NOTIFICATION_RECEIVED.equals(intent.getAction())) {
                Logger.d(TAG, "[MyReceiver] 接收到推送下来的通知");
                int notifactionId = bundle.getInt(JPushInterface.EXTRA_NOTIFICATION_ID);
                Logger.d(TAG, "[MyReceiver] 接收到推送下来的通知的ID: " + notifactionId);

            } else if (JPushInterface.ACTION_NOTIFICATION_OPENED.equals(intent.getAction())) {
                Logger.d(TAG, "[MyReceiver] 用户点击打开了通知");

                // Bundle[{cn.jpush.android.ALERT=温馨提示，您的母亲佩戴的衣带保在【深圳龙华】处触发，时间为2019-5-27左右， 请及时关注!,
                // cn.jpush.android.EXTRA={"accountId":"228","latitude":"22.654879725982337","longitude":"114.06953839705474","type":"fallNotify"},
                // cn.jpush.android.NOTIFICATION_ID=463012671,
                // cn.jpush.android.ALERT_TYPE=-1,
                // cn.jpush.android.NOTIFICATION_CONTENT_TITLE=跌倒提示,
                // cn.jpush.android.MSG_ID=9007208654041682}]
                String alert = bundle.getString(JPushInterface.EXTRA_ALERT);
                String message = bundle.getString(JPushInterface.EXTRA_EXTRA);
                String title = bundle.getString(JPushInterface.EXTRA_NOTIFICATION_TITLE);
                Logger.i("lsy", "title=" + title + ",alert=" + alert + ",message=" + message);
                JSONObject object = new JSONObject(message);
                String type = object.optString("type");
                //跌倒
                if (type.equals("fallNotify")) {
                    String latitude = object.optString("latitude");
                    String longitude = object.optString("longitude");
                    String location = object.optString("location");
                    String nickName = object.optString("nickName");
                    String phone = object.optString("phone");
                    String portrait = object.optString("portrait");
                    String relation = object.optString("relation");
                    String createDate = object.optString("createDate");

                    RemindBean remind = new RemindBean();
                    remind.setNickName(nickName);
                    remind.setPortrait(portrait);
                    remind.setPhone(phone);
                    remind.setLongitude(longitude);
                    remind.setLatitude(latitude);
                    remind.setLocation(location);
                    remind.setRelation(relation);
                    remind.setCreateDate(createDate);
                    Intent intent2 = new Intent(context, FallPositionActivity.class);
                    intent2.putExtra("remind", remind);
                    intent2.putExtra("type",0);
                    intent2.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent2);
                }
                //预警
                if(type.equals("preFallNotify")){
                    String latitude = object.optString("latitude");
                    String longitude = object.optString("longitude");
                    String location = object.optString("location");
                    String nickName = object.optString("nickName");
                    String phone = object.optString("phone");
                    String portrait = object.optString("portrait");
                    String relation = object.optString("relation");
                    String createDate = object.optString("createDate");

                    RemindBean remind = new RemindBean();
                    remind.setNickName(nickName);
                    remind.setPortrait(portrait);
                    remind.setPhone(phone);
                    remind.setLongitude(longitude);
                    remind.setLatitude(latitude);
                    remind.setLocation(location);
                    remind.setRelation(relation);
                    remind.setCreateDate(createDate);
                    Intent intent2 = new Intent(context, FallPositionActivity.class);
                    intent2.putExtra("remind", remind);
                    intent2.putExtra("type",1);
                    intent2.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent2);
                }
                if (type.equals("equipmentInfo")) {
                    String latitude = object.optString("latitude");
                    String longitude = object.optString("longitude");
                    String location = object.optString("location");
                    RemindBean remind = new RemindBean();
                    remind.setLongitude(longitude);
                    remind.setLatitude(latitude);
                    remind.setLocation(location);
                    Intent intent3 = new Intent(context, EleFenceActivity.class);
                    intent3.putExtra("remind", remind);
                    intent3.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent3);
                }


            } else if (JPushInterface.ACTION_RICHPUSH_CALLBACK.equals(intent.getAction())) {
                Logger.d(TAG, "[MyReceiver] 用户收到到RICH PUSH CALLBACK: " + bundle.getString(JPushInterface.EXTRA_EXTRA));
                //在这里根据 JPushInterface.EXTRA_EXTRA 的内容处理代码，比如打开新的Activity， 打开一个网页等..

            } else if (JPushInterface.ACTION_CONNECTION_CHANGE.equals(intent.getAction())) {
                boolean connected = intent.getBooleanExtra(JPushInterface.EXTRA_CONNECTION_CHANGE, false);
                Logger.w(TAG, "[MyReceiver]" + intent.getAction() + " connected state change to " + connected);
            } else {
                Logger.d(TAG, "[MyReceiver] Unhandled intent - " + intent.getAction());
            }
        } catch (Exception e) {

        }

    }

    // 打印所有的 intent extra 数据
    private static String printBundle(Bundle bundle) {
        StringBuilder sb = new StringBuilder();
        for (String key : bundle.keySet()) {
            if (key.equals(JPushInterface.EXTRA_NOTIFICATION_ID)) {
                sb.append("\nkey:" + key + ", value:" + bundle.getInt(key));
            } else if (key.equals(JPushInterface.EXTRA_CONNECTION_CHANGE)) {
                sb.append("\nkey:" + key + ", value:" + bundle.getBoolean(key));
            } else if (key.equals(JPushInterface.EXTRA_EXTRA)) {
                if (TextUtils.isEmpty(bundle.getString(JPushInterface.EXTRA_EXTRA))) {
                    Logger.i(TAG, "This message has no Extra data");
                    continue;
                }

                try {
                    JSONObject json = new JSONObject(bundle.getString(JPushInterface.EXTRA_EXTRA));
                    Iterator<String> it = json.keys();

                    while (it.hasNext()) {
                        String myKey = it.next();
                        sb.append("\nkey:" + key + ", value: [" +
                                myKey + " - " + json.optString(myKey) + "]");
                    }
                } catch (JSONException e) {
                    Logger.e(TAG, "Get message extra JSON error!");
                }

            } else {
                sb.append("\nkey:" + key + ", value:" + bundle.get(key));
            }
        }
        return sb.toString();
    }

    //send msg to MainActivity
//	private void processCustomMessage(Context context, Bundle bundle) {
//		if (MainActivity.isForeground) {
//			String message = bundle.getString(JPushInterface.EXTRA_MESSAGE);
//			String extras = bundle.getString(JPushInterface.EXTRA_EXTRA);
//			Intent msgIntent = new Intent(MainActivity.MESSAGE_RECEIVED_ACTION);
//			msgIntent.putExtra(MainActivity.KEY_MESSAGE, message);
//			if (!ExampleUtil.isEmpty(extras)) {
//				try {
//					JSONObject extraJson = new JSONObject(extras);
//					if (extraJson.length() > 0) {
//						msgIntent.putExtra(MainActivity.KEY_EXTRAS, extras);
//					}
//				} catch (JSONException e) {
//
//				}
//
//			}
//			LocalBroadcastManager.getInstance(context).sendBroadcast(msgIntent);
//		}
//	}
}
