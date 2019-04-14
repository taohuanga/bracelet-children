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
     * 上传位置信息（经纬度坐标）
     */

    @POST("children/setting/location")
    Observable<HttpResult> uploadLocation(@Body Map<String, Object> map);

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
    @POST("children/setting/contactsList")
    Observable<HttpResult> contactList(@Body Map<String, Object> map);

    /**
     * 附近的人列表
     */
    @POST("children/nearby/list")
    Observable<HttpResult> nearbyList(@Body Map<String, Object> map);

    /**
     * 附近的人资料
     */
    @POST("children/nearby/info")
    Observable<HttpResult> nearbyInfo(@Body Map<String, Object> map);

    /**
     * 保存修改基本信息
     */
    @POST("member/memberAccount/saveBaseInfo")
    Observable<HttpResult> saveBaseInfo(@Body Map<String, Object> map);

    /**
     * 关于我们
     */
    @POST("sys/app/about")
    Observable<HttpResult> aboutApp(@Body Map<String, Object> map);

    /**
     * 首页头部亲人列表
     */
    @POST("children/relative/list")
    Observable<HttpResult> relative(@Body Map<String, Object> map);

    /**
     * 消息列表
     */
    @POST("children/relative/msgList")
    Observable<HttpResult> msgList(@Body Map<String, Object> map);

    /**
     * 亲人列表
     */
    @POST("children/relationship/list")
    Observable<HttpResult> familyList(@Body Map<String, Object> map);

    /**
     * 新增亲人
     */
    @POST("children/relationship/add")
    Observable<HttpResult> addMember(@Body Map<String, Object> map);

    /**
     * 编辑亲人
     */
    @POST("children/relationship/edit")
    Observable<HttpResult> editMember(@Body Map<String, Object> map);

    /**
     * 亲人详情
     */
    @POST("children/relationship/info")
    Observable<HttpResult> memberInfo(@Body Map<String, Object> map);

    /**
     * 资讯列表
     */
    @POST("children/information/list")
    Observable<HttpResult> informationList(@Body Map<String, Object> map);

    /**
     * 资讯详情
     */
    @POST("children/information/info")
    Observable<HttpResult> infoDetail(@Body Map<String, Object> map);

    /**
     * 标签列表
     */
    @POST("children/label/list")
    Observable<HttpResult> tagList(@Body Map<String, Object> map);

    /**
     * 新增标签
     */
    @POST("children/label/add")
    Observable<HttpResult> tagAdd(@Body Map<String, Object> map);

    /**
     * 电子围栏列表
     */
    @POST("children/setting/fenceList")
    Observable<HttpResult> fenceList(@Body Map<String, Object> map);

    /**
     * 添加电子围栏
     */
    @POST("children/setting/fence")
    Observable<HttpResult> fenceAdd(@Body Map<String, Object> map);

    /**
     * 设置提醒
     */
    @POST("children/setting/remind")
    Observable<HttpResult> remind(@Body Map<String, Object> map);

    /**
     * 提醒列表
     */
    @POST("children/setting/remindList")
    Observable<HttpResult> remindList(@Body Map<String, Object> map);

    /**
     * 亲人设置标签
     */
    @POST("children/label/tagged")
    Observable<HttpResult> setTag(@Body Map<String, Object> map);

    /**
     * 绑定硬件
     */
    @POST("children/setting/binding")
    Observable<HttpResult> bindDevice(@Body Map<String, Object> map);

    /**
     * 给亲人添加联系人
     */
    @POST("children/setting/contacts")
    Observable<HttpResult> contactAdd(@Body Map<String, Object> map);

    /**
     * 父母运动数据
     */
    @POST("children/relative/dailySports")
    Observable<HttpResult> dailySports(@Body Map<String, Object> map);

    /**
     * 父母运动数据列表
     */
    @POST("children/relative/dailySportsList")
    Observable<HttpResult> dailySportsList(@Body Map<String, Object> map);

    /**
     * 父母运动趋势图数据
     */
    @POST("children/relative/parentSportTrend")
    Observable<HttpResult> parentSportTrend(@Body Map<String, Object> map);

    /**
     * 编辑个人资料
     */
    @POST("children/setting/modifiedData")
    Observable<HttpResult> modifyData(@Body Map<String, Object> map);
}
