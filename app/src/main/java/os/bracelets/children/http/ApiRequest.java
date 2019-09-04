package os.bracelets.children.http;


import android.text.TextUtils;
import android.widget.TextView;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import aio.health2world.http.HttpResult;
import aio.health2world.http.tool.RxTransformer;
import aio.health2world.utils.Logger;
import aio.health2world.utils.SPUtils;
import os.bracelets.children.AppConfig;
import os.bracelets.children.MyApplication;
import os.bracelets.children.utils.FileUtils;
import rx.Subscriber;
import rx.Subscription;

/**
 * e10adc3949ba59abbe56e057f20f883e
 * Created by Administrator on 2018/7/3 0003.
 */

public class ApiRequest {

    //登录
    public static Subscription login(String account, String password, Subscriber<HttpResult> subscriber) {
        Map<String, Object> map = new HashMap<>();
        map.put("account", account);
        map.put("password", password);
        return ServiceFactory.getInstance()
                .createService(ApiService.class)
                .login(map)
                .compose(RxTransformer.<HttpResult>defaultSchedulers())
                .subscribe(subscriber);
    }

    //短信快捷登录
    public static Subscription fastLogin(String account, String securityCode, Subscriber<HttpResult> subscriber) {
        Map<String, Object> map = new HashMap<>();
        map.put("account", account);
        map.put("securityCode", securityCode);
        return ServiceFactory.getInstance()
                .createService(ApiService.class)
                .fastLogin(map)
                .compose(RxTransformer.<HttpResult>defaultSchedulers())
                .subscribe(subscriber);
    }

    //获取手机验证码
    public static Subscription code(int type, String phone, Subscriber<HttpResult> subscriber) {
        Map<String, Object> map = new HashMap<>();
        map.put("type", String.valueOf(type));
        map.put("phone", phone);
        return ServiceFactory.getInstance()
                .createService(ApiService.class)
                .code(map)
                .compose(RxTransformer.<HttpResult>defaultSchedulers())
                .subscribe(subscriber);
    }

    //注册
    public static Subscription register(String phone, String securityCode, String code, String password,
                                        Subscriber<HttpResult> subscriber) {
        Map<String, Object> map = new HashMap<>();
        map.put("phone", phone);
        map.put("securityCode", securityCode);
        if (!TextUtils.isEmpty(code))
            map.put("code", code);
        map.put("password", password);
        return ServiceFactory.getInstance()
                .createService(ApiService.class)
                .register(map)
                .compose(RxTransformer.<HttpResult>defaultSchedulers())
                .subscribe(subscriber);
    }

    //判断手机号是否存在平台
    public static Subscription phoneExist(String phone, Subscriber<HttpResult> subscriber) {
        Map<String, Object> map = new HashMap<>();
        map.put("phone", phone);
        return ServiceFactory.getInstance()
                .createService(ApiService.class)
                .phoneExist(map)
                .compose(RxTransformer.<HttpResult>defaultSchedulers())
                .subscribe(subscriber);
    }

    //修改密码
    public static Subscription updatePwd(String oldPwd, String newPwd, String securityCode,
                                         Subscriber<HttpResult> subscriber) {
        Map<String, Object> map = new HashMap<>();
        map.put("tokenId", MyApplication.getInstance().getTokenId());
        map.put("oldPass", oldPwd);
        map.put("newPass", newPwd);
        map.put("securityCode", securityCode);
        return ServiceFactory.getInstance()
                .createService(ApiService.class)
                .updatePwd(map)
                .compose(RxTransformer.<HttpResult>defaultSchedulers())
                .subscribe(subscriber);
    }

    //重置密码
    public static Subscription resetPwd(String phone, String password, String securityCode,
                                        Subscriber<HttpResult> subscriber) {
        Map<String, Object> map = new HashMap<>();
        map.put("tokenId", MyApplication.getInstance().getTokenId());
        map.put("phone", phone);
        map.put("password", password);
        map.put("securityCode", securityCode);
        return ServiceFactory.getInstance()
                .createService(ApiService.class)
                .resetPwd(map)
                .compose(RxTransformer.<HttpResult>defaultSchedulers())
                .subscribe(subscriber);
    }


    //重置密码
    public static Subscription updatePhone(String accountId, String oldPhone, String newPhone, String loginPass, String securityCode,
                                           Subscriber<HttpResult> subscriber) {
        Map<String, Object> map = new HashMap<>();
        map.put("tokenId", MyApplication.getInstance().getTokenId());
        if (!TextUtils.isEmpty(accountId)) {
            map.put("accountId", accountId);
        }
        map.put("oldPhone", oldPhone);
        map.put("newPhone", newPhone);
        map.put("loginPass", loginPass);
        map.put("securityCode", securityCode);
        return ServiceFactory.getInstance()
                .createService(ApiService.class)
                .updatePhone(map)
                .compose(RxTransformer.<HttpResult>defaultSchedulers())
                .subscribe(subscriber);
    }

    //首页获取步数
    public static Subscription about(Subscriber<HttpResult> subscriber) {
        Map<String, Object> map = new HashMap<>();
        map.put("tokenId", MyApplication.getInstance().getTokenId());
        return ServiceFactory.getInstance()
                .createService(ApiService.class)
                .aboutApp(map)
                .compose(RxTransformer.<HttpResult>defaultSchedulers())
                .subscribe(subscriber);
    }

    //获取用户信息
    public static Subscription userInfo(Subscriber<HttpResult> subscriber) {
        Map<String, Object> map = new HashMap<>();
        map.put("tokenId", MyApplication.getInstance().getTokenId());
        return ServiceFactory.getInstance()
                .createService(ApiService.class)
                .userInfo(map)
                .compose(RxTransformer.<HttpResult>defaultSchedulers())
                .subscribe(subscriber);
    }

    //图片上传   imageType=图片类型   imageData=base64图片内容
    public static Subscription uploadImage(int imageType, String imageData, Subscriber<HttpResult> subscriber) {
        Map<String, Object> map = new HashMap<>();
        map.put("imageType", String.valueOf(imageType));
        map.put("imageData", imageData);
        return ServiceFactory.getInstance()
                .createService(ApiService.class)
                .uploadImage(map)
                .compose(RxTransformer.<HttpResult>defaultSchedulers())
                .subscribe(subscriber);
    }

    //上传文件
    public static Subscription uploadFile(File file, Subscriber<HttpResult> subscriber) {
        Map<String, Object> map = new HashMap<>();
        map.put("tokenId", MyApplication.getInstance().getTokenId());
        map.put("fileType", ".csv");
        map.put("fileData", FileUtils.file2Base64(file.getAbsolutePath()));
        map.put("fileName", file.getName());
        return ServiceFactory.getInstance()
                .createService(ApiService.class)
                .uploadFile(map)
                .compose(RxTransformer.<HttpResult>defaultSchedulers())
                .subscribe(subscriber);
    }

    //意见反馈
    public static Subscription feedBack(String title, String content, String imageUrls, Subscriber<HttpResult> subscriber) {
        Map<String, Object> map = new HashMap<>();
        map.put("tokenId", MyApplication.getInstance().getTokenId());
        map.put("title", title);
        map.put("content", content);
        if (!TextUtils.isEmpty(imageUrls))
            map.put("imageUrls", imageUrls);
        return ServiceFactory.getInstance()
                .createService(ApiService.class)
                .feedBack(map)
                .compose(RxTransformer.<HttpResult>defaultSchedulers())
                .subscribe(subscriber);
    }


    //联系人列表
    public static Subscription contactList(String accountId, int pageNo, Subscriber<HttpResult> subscriber) {
        Map<String, Object> map = new HashMap<>();
        map.put("tokenId", MyApplication.getInstance().getTokenId());
        map.put("pageNo", String.valueOf(pageNo));
        map.put("accountId", accountId);
        map.put("pageSize", String.valueOf(AppConfig.PAGE_SIZE));
        return ServiceFactory.getInstance()
                .createService(ApiService.class)
                .contactList(map)
                .compose(RxTransformer.<HttpResult>defaultSchedulers())
                .subscribe(subscriber);
    }


    //资讯列表
    public static Subscription informationList(int type, int pageNo, String releaseTime, Subscriber<HttpResult> subscriber) {
        Map<String, Object> map = new HashMap<>();
        map.put("tokenId", MyApplication.getInstance().getTokenId());
        map.put("type", String.valueOf(type));
        map.put("pageNo", String.valueOf(pageNo));
        map.put("pageSize", String.valueOf(AppConfig.PAGE_SIZE));
        if (!TextUtils.isEmpty(releaseTime))
            map.put("releaseTime", releaseTime);
        return ServiceFactory.getInstance()
                .createService(ApiService.class)
                .informationList(map)
                .compose(RxTransformer.<HttpResult>defaultSchedulers())
                .subscribe(subscriber);
    }

    //资讯详情
    public static Subscription infoDetail(String informationId, Subscriber<HttpResult> subscriber) {
        Map<String, Object> map = new HashMap<>();
        map.put("tokenId", MyApplication.getInstance().getTokenId());
        map.put("informationId", informationId);
        return ServiceFactory.getInstance()
                .createService(ApiService.class)
                .infoDetail(map)
                .compose(RxTransformer.<HttpResult>defaultSchedulers())
                .subscribe(subscriber);
    }


    //附近的人
    public static Subscription nearbyList(int pageNo, Subscriber<HttpResult> subscriber) {
        Map<String, Object> map = new HashMap<>();
        map.put("tokenId", MyApplication.getInstance().getTokenId());
        map.put("pageNo", String.valueOf(pageNo));
        map.put("pageSize", String.valueOf(AppConfig.PAGE_SIZE));
        map.put("longitude", SPUtils.get(MyApplication.getInstance(), AppConfig.LONGITUDE, ""));
        map.put("latitude", SPUtils.get(MyApplication.getInstance(), AppConfig.LATITUDE, ""));
        return ServiceFactory.getInstance()
                .createService(ApiService.class)
                .nearbyList(map)
                .compose(RxTransformer.<HttpResult>defaultSchedulers())
                .subscribe(subscriber);
    }

    //附近人的资料
    public static Subscription nearbyInfo(int accountId, Subscriber<HttpResult> subscriber) {
        Map<String, Object> map = new HashMap<>();
        map.put("tokenId", MyApplication.getInstance().getTokenId());
        map.put("accountId", String.valueOf(accountId));
        return ServiceFactory.getInstance()
                .createService(ApiService.class)
                .nearbyInfo(map)
                .compose(RxTransformer.<HttpResult>defaultSchedulers())
                .subscribe(subscriber);
    }

    //修改资料
    public static Subscription saveBaseInfo(String portrait, String nickName, String realName, String account, int sex,
                                            Subscriber<HttpResult> subscriber) {
        Map<String, Object> map = new HashMap<>();
        map.put("tokenId", MyApplication.getInstance().getTokenId());
        if (!TextUtils.isEmpty(portrait))
            map.put("portrait", portrait);

        if (!TextUtils.isEmpty(nickName))
            map.put("nickName", nickName);

        if (!TextUtils.isEmpty(realName))
            map.put("realName", realName);

        if (sex != 0)
            map.put("sex", String.valueOf(sex));

        if (!TextUtils.isEmpty(account))
            map.put("account", account);

        return ServiceFactory.getInstance()
                .createService(ApiService.class)
                .saveBaseInfo(map)
                .compose(RxTransformer.<HttpResult>defaultSchedulers())
                .subscribe(subscriber);
    }


    //首页头部亲人列表
    public static Subscription relative(Subscriber<HttpResult> subscriber) {
        Map<String, Object> map = new HashMap<>();
        map.put("tokenId", MyApplication.getInstance().getTokenId());
        return ServiceFactory.getInstance()
                .createService(ApiService.class)
                .relative(map)
                .compose(RxTransformer.<HttpResult>defaultSchedulers())
                .subscribe(subscriber);
    }

    //消息列表
    public static Subscription msgList(int pageNo, String accountId, Subscriber<HttpResult> subscriber) {
        Map<String, Object> map = new HashMap<>();
        map.put("tokenId", MyApplication.getInstance().getTokenId());
        map.put("accountId", accountId);
        map.put("pageNo", String.valueOf(pageNo));
        map.put("pageSize", String.valueOf(AppConfig.PAGE_SIZE));
        return ServiceFactory.getInstance()
                .createService(ApiService.class)
                .msgList(map)
                .compose(RxTransformer.<HttpResult>defaultSchedulers())
                .subscribe(subscriber);
    }

    //亲人列表
    public static Subscription familyList(Subscriber<HttpResult> subscriber) {
        Map<String, Object> map = new HashMap<>();
        map.put("tokenId", MyApplication.getInstance().getTokenId());
        return ServiceFactory.getInstance()
                .createService(ApiService.class)
                .familyList(map)
                .compose(RxTransformer.<HttpResult>defaultSchedulers())
                .subscribe(subscriber);
    }


    //添加亲人
    public static Subscription familyAdd(String profile, String nickName, String realName,
                                         int sex, String birthday, String height, String weight, String relationship,
                                         String phone, Subscriber<HttpResult> subscriber) {
        Map<String, Object> map = new HashMap<>();
        map.put("tokenId", MyApplication.getInstance().getTokenId());
        map.put("portrait", profile);
        map.put("nickName", nickName);
        if (!TextUtils.isEmpty(realName))
            map.put("realName", realName);
        map.put("sex", String.valueOf(sex));
        map.put("birthday", birthday);
        map.put("height", height);
        map.put("weight", weight);
        map.put("relationship", relationship);
        if (!TextUtils.isEmpty(phone))
            map.put("phone", phone);
        return ServiceFactory.getInstance()
                .createService(ApiService.class)
                .addMember(map)
                .compose(RxTransformer.<HttpResult>defaultSchedulers())
                .subscribe(subscriber);
    }

    //添加亲人
    public static Subscription familyEdit(String accountId, String profile, String nickName, String realName,
                                          int sex, String birthday, String height, String weight, String relationship,
                                          String phone, Subscriber<HttpResult> subscriber) {
        Map<String, Object> map = new HashMap<>();
        map.put("tokenId", MyApplication.getInstance().getTokenId());
        map.put("accountId", accountId);
        map.put("portrait", profile);
        map.put("nickName", nickName);
        if (!TextUtils.isEmpty(realName))
            map.put("realName", realName);
        map.put("sex", String.valueOf(sex));
        if (!TextUtils.isEmpty(birthday))
            map.put("birthday", birthday);
        map.put("height", height);
        map.put("weight", weight);
        map.put("relationship", relationship);
        map.put("phone", phone);
        return ServiceFactory.getInstance()
                .createService(ApiService.class)
                .editMember(map)
                .compose(RxTransformer.<HttpResult>defaultSchedulers())
                .subscribe(subscriber);
    }

    //亲人详情
    public static Subscription memberInfo(String accountId, Subscriber<HttpResult> subscriber) {
        Map<String, Object> map = new HashMap<>();
        map.put("tokenId", MyApplication.getInstance().getTokenId());
        map.put("accountId", accountId);
        return ServiceFactory.getInstance()
                .createService(ApiService.class)
                .memberInfo(map)
                .compose(RxTransformer.<HttpResult>defaultSchedulers())
                .subscribe(subscriber);
    }

    //标签列表
    public static Subscription tagList(Subscriber<HttpResult> subscriber) {
        Map<String, Object> map = new HashMap<>();
        map.put("tokenId", MyApplication.getInstance().getTokenId());
        return ServiceFactory.getInstance()
                .createService(ApiService.class)
                .tagList(map)
                .compose(RxTransformer.<HttpResult>defaultSchedulers())
                .subscribe(subscriber);
    }

    //新增标签到系统
    public static Subscription tagAdd(String labelName, Subscriber<HttpResult> subscriber) {
        Map<String, Object> map = new HashMap<>();
        map.put("tokenId", MyApplication.getInstance().getTokenId());
        map.put("labelName", labelName);
        return ServiceFactory.getInstance()
                .createService(ApiService.class)
                .tagAdd(map)
                .compose(RxTransformer.<HttpResult>defaultSchedulers())
                .subscribe(subscriber);
    }


    //电子围栏列表
    public static Subscription fenceList(String accountId, Subscriber<HttpResult> subscriber) {
        Map<String, Object> map = new HashMap<>();
        map.put("tokenId", MyApplication.getInstance().getTokenId());
        map.put("accountId", accountId);
        return ServiceFactory.getInstance()
                .createService(ApiService.class)
                .fenceList(map)
                .compose(RxTransformer.<HttpResult>defaultSchedulers())
                .subscribe(subscriber);
    }

    //上传地理位置经纬度
    public static Subscription uploadLocation(String longitude, String latitude, Subscriber<HttpResult> subscriber) {
        Map<String, Object> map = new HashMap<>();
        map.put("tokenId", MyApplication.getInstance().getTokenId());
        map.put("longitude", longitude);
        map.put("latitude", latitude);
        return ServiceFactory.getInstance()
                .createService(ApiService.class)
                .uploadLocation(map)
                .compose(RxTransformer.<HttpResult>defaultSchedulers())
                .subscribe(subscriber);
    }

    //设置提醒
    public static Subscription remind(String accountId, String remindTitle, String remind, String remindPeriod,
                                      String remindTime, Subscriber<HttpResult> subscriber) {
        Map<String, Object> map = new HashMap<>();
        map.put("tokenId", MyApplication.getInstance().getTokenId());
        map.put("accountId", accountId);
        map.put("remindTitle", remindTitle);
        map.put("remind", remind);
        map.put("remindPeriod", remindPeriod);
        map.put("remindTime", remindTime);
        return ServiceFactory.getInstance()
                .createService(ApiService.class)
                .remind(map)
                .compose(RxTransformer.<HttpResult>defaultSchedulers())
                .subscribe(subscriber);
    }

    //提醒列表
    public static Subscription remindList(String accountId, Subscriber<HttpResult> subscriber) {
        Map<String, Object> map = new HashMap<>();
        map.put("tokenId", MyApplication.getInstance().getTokenId());
        map.put("accountId", accountId);
        return ServiceFactory.getInstance()
                .createService(ApiService.class)
                .remindList(map)
                .compose(RxTransformer.<HttpResult>defaultSchedulers())
                .subscribe(subscriber);
    }


    //给亲人设置标签
    public static Subscription setTag(String accountId, String labelIds, Subscriber<HttpResult> subscriber) {
        Map<String, Object> map = new HashMap<>();
        map.put("tokenId", MyApplication.getInstance().getTokenId());
        map.put("accountId", accountId);
        map.put("labelIds", labelIds);
        return ServiceFactory.getInstance()
                .createService(ApiService.class)
                .setTag(map)
                .compose(RxTransformer.<HttpResult>defaultSchedulers())
                .subscribe(subscriber);
    }

    //绑定硬件
    public static Subscription bindDevice(String accountId, String deviceNo, String bindTimes, Subscriber<HttpResult> subscriber) {
        Map<String, Object> map = new HashMap<>();
        map.put("tokenId", MyApplication.getInstance().getTokenId());
        map.put("accountId", accountId);
        map.put("equipmentSn", deviceNo);
        if (!TextUtils.isEmpty(bindTimes)) {
            map.put("bindTimes", bindTimes);
        }
        return ServiceFactory.getInstance()
                .createService(ApiService.class)
                .bindDevice(map)
                .compose(RxTransformer.<HttpResult>defaultSchedulers())
                .subscribe(subscriber);
    }


    //亲人的联系人增加
    public static Subscription contactAdd(String accountId, String portrait, String nickName, String phone,
                                          int sex, Subscriber<HttpResult> subscriber) {
        Map<String, Object> map = new HashMap<>();
        map.put("tokenId", MyApplication.getInstance().getTokenId());
        map.put("accountId", accountId);
        map.put("portrait", portrait);
        map.put("nickName", nickName);
        map.put("phone", phone);
        map.put("sex", String.valueOf(sex));
        return ServiceFactory.getInstance()
                .createService(ApiService.class)
                .contactAdd(map)
                .compose(RxTransformer.<HttpResult>defaultSchedulers())
                .subscribe(subscriber);
    }


    //添加电子围栏
    public static Subscription fenceAdd(String accountId, String location, String longitude, String latitude,
                                        String fenceScope, Subscriber<HttpResult> subscriber) {
        Map<String, Object> map = new HashMap<>();
        map.put("tokenId", MyApplication.getInstance().getTokenId());
        map.put("accountId", accountId);
        map.put("location", location);
        map.put("longitude", longitude);
        map.put("latitude", latitude);
        map.put("fenceScope", fenceScope);
        return ServiceFactory.getInstance()
                .createService(ApiService.class)
                .fenceAdd(map)
                .compose(RxTransformer.<HttpResult>defaultSchedulers())
                .subscribe(subscriber);
    }

    //父母运动数据
    public static Subscription dailySports(String accountId, Subscriber<HttpResult> subscriber) {
        Map<String, Object> map = new HashMap<>();
        map.put("tokenId", MyApplication.getInstance().getTokenId());
        map.put("accountId", accountId);
        return ServiceFactory.getInstance()
                .createService(ApiService.class)
                .dailySports(map)
                .compose(RxTransformer.<HttpResult>defaultSchedulers())
                .subscribe(subscriber);
    }

    //父母运动数据列表
    public static Subscription dailySportsList(String accountId, String startDate, String ednData, Subscriber<HttpResult> subscriber) {
        Map<String, Object> map = new HashMap<>();
        map.put("tokenId", MyApplication.getInstance().getTokenId());
        map.put("accountId", accountId);
        map.put("startDate", startDate);
        map.put("ednData", ednData);
        return ServiceFactory.getInstance()
                .createService(ApiService.class)
                .dailySportsList(map)
                .compose(RxTransformer.<HttpResult>defaultSchedulers())
                .subscribe(subscriber);
    }

    //父母运动趋势图数据
    public static Subscription parentSportTrend(String accountId, Subscriber<HttpResult> subscriber) {
        Map<String, Object> map = new HashMap<>();
        map.put("tokenId", MyApplication.getInstance().getTokenId());
        map.put("accountId", accountId);
        return ServiceFactory.getInstance()
                .createService(ApiService.class)
                .parentSportTrend(map)
                .compose(RxTransformer.<HttpResult>defaultSchedulers())
                .subscribe(subscriber);
    }

    //编辑个人资料
    public static Subscription modifyData(String portrait, String nickName, String realName,
                                          String birthday, int sex, Subscriber<HttpResult> subscriber) {
        Map<String, Object> map = new HashMap<>();
        map.put("tokenId", MyApplication.getInstance().getTokenId());
        map.put("portrait", portrait);
        map.put("nickName", nickName);
        map.put("realName", realName);
        map.put("birthday", birthday);
        map.put("sex", String.valueOf(sex));
        return ServiceFactory.getInstance()
                .createService(ApiService.class)
                .modifyData(map)
                .compose(RxTransformer.<HttpResult>defaultSchedulers())
                .subscribe(subscriber);
    }

    //编辑提醒
    public static Subscription editRemind(int remindId, String remindTitle, String remind,
                                          String remindPeriod, String remindTime, Subscriber<HttpResult> subscriber) {
        Map<String, Object> map = new HashMap<>();
        map.put("tokenId", MyApplication.getInstance().getTokenId());
        map.put("remindId", String.valueOf(remindId));
        map.put("remindTitle", remindTitle);
        map.put("remind", remind);
        map.put("remindPeriod", remindPeriod);
        map.put("remindTime", remindTime);
        return ServiceFactory.getInstance()
                .createService(ApiService.class)
                .editRemind(map)
                .compose(RxTransformer.<HttpResult>defaultSchedulers())
                .subscribe(subscriber);
    }

    //编辑联系人
    public static Subscription editContacts(int accountId, int contactId, String portrait, String nickName,
                                            String phone, int sex, Subscriber<HttpResult> subscriber) {
        Map<String, Object> map = new HashMap<>();
        map.put("tokenId", MyApplication.getInstance().getTokenId());
        map.put("accountId", String.valueOf(accountId));
        map.put("contactId", String.valueOf(contactId));
        map.put("portrait", portrait);
        map.put("nickName", nickName);
        map.put("phone", phone);
        map.put("sex", String.valueOf(sex));
        return ServiceFactory.getInstance()
                .createService(ApiService.class)
                .editContacts(map)
                .compose(RxTransformer.<HttpResult>defaultSchedulers())
                .subscribe(subscriber);
    }

    //查询绑定的设备
    public static Subscription deviceBindInfo(int accountId, Subscriber<HttpResult> subscriber) {
        Map<String, Object> map = new HashMap<>();
        map.put("tokenId", MyApplication.getInstance().getTokenId());
        map.put("parentAccountId", String.valueOf(accountId));
        return ServiceFactory.getInstance()
                .createService(ApiService.class)
                .deviceBindInfo(map)
                .compose(RxTransformer.<HttpResult>defaultSchedulers())
                .subscribe(subscriber);
    }

    public static Subscription deviceUnbind(int accountId, String deviceInfo, Subscriber<HttpResult> subscriber) {
        Map<String, Object> map = new HashMap<>();
        map.put("tokenId", MyApplication.getInstance().getTokenId());
        map.put("parentAccountId", String.valueOf(accountId));
        map.put("deviceInfo", deviceInfo);
        return ServiceFactory.getInstance()
                .createService(ApiService.class)
                .deviceUnbind(map)
                .compose(RxTransformer.<HttpResult>defaultSchedulers())
                .subscribe(subscriber);
    }

    //亲人关系id 多个分号分割
    public static Subscription relationshipDelete(String ids, Subscriber<HttpResult> subscriber) {
        Map<String, Object> map = new HashMap<>();
        map.put("tokenId", MyApplication.getInstance().getTokenId());
        map.put("relationshipIds", ids);
        return ServiceFactory.getInstance()
                .createService(ApiService.class)
                .relationshipDelete(map)
                .compose(RxTransformer.<HttpResult>defaultSchedulers())
                .subscribe(subscriber);
    }

    //多个分号分割
    public static Subscription delRemind(String remindIds, Subscriber<HttpResult> subscriber) {
        Map<String, Object> map = new HashMap<>();
        map.put("tokenId", MyApplication.getInstance().getTokenId());
        map.put("remindIds", remindIds);
        return ServiceFactory.getInstance()
                .createService(ApiService.class)
                .delRemind(map)
                .compose(RxTransformer.<HttpResult>defaultSchedulers())
                .subscribe(subscriber);
    }

    public static Subscription delFence(String fenceIds, Subscriber<HttpResult> subscriber) {
        Map<String, Object> map = new HashMap<>();
        map.put("tokenId", MyApplication.getInstance().getTokenId());
        map.put("fenceIds", fenceIds);
        return ServiceFactory.getInstance()
                .createService(ApiService.class)
                .delFence(map)
                .compose(RxTransformer.<HttpResult>defaultSchedulers())
                .subscribe(subscriber);
    }


    public static Subscription delContacts(String contactIds, Subscriber<HttpResult> subscriber) {
        Map<String, Object> map = new HashMap<>();
        map.put("tokenId", MyApplication.getInstance().getTokenId());
        map.put("contactIds", contactIds);
        return ServiceFactory.getInstance()
                .createService(ApiService.class)
                .delContacts(map)
                .compose(RxTransformer.<HttpResult>defaultSchedulers())
                .subscribe(subscriber);
    }

    public static Subscription unreadMsg(String accountId, Subscriber<HttpResult> subscriber) {
        Map<String, Object> map = new HashMap<>();
        map.put("tokenId", MyApplication.getInstance().getTokenId());
        map.put("accountId", accountId);
        return ServiceFactory.getInstance()
                .createService(ApiService.class)
                .unreadMsg(map)
                .compose(RxTransformer.<HttpResult>defaultSchedulers())
                .subscribe(subscriber);
    }

    public static Subscription msgRead(int type, int msgId, Subscriber<HttpResult> subscriber) {
        Map<String, Object> map = new HashMap<>();
        map.put("tokenId", MyApplication.getInstance().getTokenId());
        map.put("type", String.valueOf(type));
        map.put("ids", String.valueOf(msgId));
        return ServiceFactory.getInstance()
                .createService(ApiService.class)
                .msgRead(map)
                .compose(RxTransformer.<HttpResult>defaultSchedulers())
                .subscribe(subscriber);
    }
}

