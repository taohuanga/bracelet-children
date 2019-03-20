package os.bracelets.children.http;


import java.util.Map;

import aio.health2world.http.HttpResult;
import retrofit2.http.Body;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Created by lishiyou on 2017/6/26.
 */
interface ApiService {
    /**
     * 登录
     */
    @POST("member/memberAccount/login")
    Observable<HttpResult> login(@Body Map<String, Object> map);

    /**
     * 短信登录
     */
    @POST("member/memberAccount/fastLogin")
    Observable<HttpResult> fastLogin(@Body Map<String, Object> map);

    /**
     * 获取手机验证码
     * 短信类型（1:注册 2:登录 3:修改密码 4:认证审核通过 5:认证审核不通过）
     */
    @POST("sys/sms/send")
    Observable<HttpResult> code(@Body Map<String, Object> map);

    /**
     * 用户注册
     */
    @POST("member/memberAccount/register")
    Observable<HttpResult> register(@Body Map<String, Object> map);

    /**
     * 修改密码
     */
    @POST("member/memberAccount/updatePasswd")
    Observable<HttpResult> updatePwd(@Body Map<String, Object> map);

    /**
     * 重置密码
     */
    @POST("member/memberAccount/reset")
    Observable<HttpResult> resetPwd(@Body Map<String, Object> map);

    /**
     * 判断手机号是否存在
     */
    @POST("member/memberAccount/phoneExist")
    Observable<HttpResult> phoneExist(@Body Map<String, Object> map);

    /**
     * 首页信息
     */
    @POST("parent/home/index")
    Observable<HttpResult> homeMsg(@Body Map<String, Object> map);

    /**
     * 首页待办
     */
    @POST("parent/home/remindList")
    Observable<HttpResult> remindList(@Body Map<String, Object> map);

    /**
     * 用户信息
     *
     * @param map
     * @return
     */
    @POST("member/memberAccount/baseInfo")
    Observable<HttpResult> userInfo(@Body Map<String, Object> map);

    /**
     * 图片上传
     */
    @POST("sys/image/wxupload")
    Observable<HttpResult> uploadImage(@Body Map<String, Object> map);

    /**
     * 文件上传
     */
    @POST("sys/file/upload")
    Observable<HttpResult> uploadFile(@Body Map<String, Object> map);

    /**
     * 意见反馈
     */
    @POST("parent/setting/feedback")
    Observable<HttpResult> feedBack(@Body Map<String, Object> map);


    /**
     * 联系人列表
     */
    @POST("parent/contact/list")
    Observable<HttpResult> contactList(@Body Map<String, Object> map);


    /**
     * 资讯列表
     */
    @POST("parent/information/list")
    Observable<HttpResult> informationList(@Body Map<String, Object> map);


    /**
     * 附近的人列表
     */
    @POST("parent/nearby/list")
    Observable<HttpResult> nearbyList(@Body Map<String, Object> map);

    /**
     * 附近的人资料
     */
    @POST(" parent/nearby/info")
    Observable<HttpResult> nearbyInfo(@Body Map<String, Object> map);

    /**
     * 修改资料
     */
    @POST("parent/setting/modifiedData")
    Observable<HttpResult> updateMsg(@Body Map<String, Object> map);

    /**
     * 关于我们
     */
    @POST("sys/app/about")
    Observable<HttpResult> aboutApp(@Body Map<String, Object> map);
}
