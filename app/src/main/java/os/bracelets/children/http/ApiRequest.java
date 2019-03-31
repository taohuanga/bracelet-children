package os.bracelets.children.http;


import android.text.TextUtils;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import aio.health2world.http.HttpResult;
import aio.health2world.http.tool.RxTransformer;
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
    public static Subscription updatePwd(String oldPwd, String newPwd, Subscriber<HttpResult> subscriber) {
        Map<String, Object> map = new HashMap<>();
        map.put("tokenId", MyApplication.getInstance().getTokenId());
        map.put("oldPass", oldPwd);
        map.put("newPass", newPwd);
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


//    //首页获取步数
//    public static Subscription homeMsg(Subscriber<HttpResult> subscriber) {
//        Map<String, Object> map = new HashMap<>();
//        map.put("tokenId", MyApplication.getInstance().getTokenId());
//        return ServiceFactory.getInstance()
//                .createService(ApiService.class)
//                .homeMsg(map)
//                .compose(RxTransformer.<HttpResult>defaultSchedulers())
//                .subscribe(subscriber);
//    }

//    //首页待办
//    public static Subscription remindList(Subscriber<HttpResult> subscriber) {
//        Map<String, Object> map = new HashMap<>();
//        map.put("tokenId", MyApplication.getInstance().getTokenId());
//        return ServiceFactory.getInstance()
//                .createService(ApiService.class)
//                .remindList(map)
//                .compose(RxTransformer.<HttpResult>defaultSchedulers())
//                .subscribe(subscriber);
//    }

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
    public static Subscription saveBaseInfo(String portrait, String nickName, String account, int sex,
                                            Subscriber<HttpResult> subscriber) {
        Map<String, Object> map = new HashMap<>();
        map.put("tokenId", MyApplication.getInstance().getTokenId());
        if (!TextUtils.isEmpty(portrait))
            map.put("portrait", portrait);

        if (!TextUtils.isEmpty(nickName))
            map.put("nickName", nickName);

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
    public static Subscription msgList(String accountId, Subscriber<HttpResult> subscriber) {
        Map<String, Object> map = new HashMap<>();
        map.put("tokenId", MyApplication.getInstance().getTokenId());
        map.put("accountId", accountId);
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
    public static Subscription familyAdd(String accountId, String profile, String nickName, String realName,
                                         int sex, String birthday, String height, String weight, String relationship,
                                         String phone, Subscriber<HttpResult> subscriber) {
        Map<String, Object> map = new HashMap<>();
        map.put("tokenId", MyApplication.getInstance().getTokenId());
        map.put("accountId", accountId);
        map.put("portrait", profile);
        map.put("nickName", nickName);
        map.put("realName", realName);
        map.put("sex", String.valueOf(sex));
        map.put("birthday", birthday);
        map.put("height", height);
        map.put("weight", weight);
        map.put("relationship", relationship);
        map.put("phone", phone);
        return ServiceFactory.getInstance()
                .createService(ApiService.class)
                .addMember(map)
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
    public static Subscription uploadLocation(String longitude,String latitude,Subscriber<HttpResult> subscriber) {
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
    public static Subscription remind(String accountId,String remindTitle,String remind,String remindPeriod,
                                      String remindTime,Subscriber<HttpResult> subscriber) {
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

    //给亲人设置标签
    public static Subscription setTag(String accountId,String labelIds,Subscriber<HttpResult> subscriber) {
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
}
