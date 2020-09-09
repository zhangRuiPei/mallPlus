package com.zrp.mallplus.ums.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zrp.mallplus.ums.entity.UmsMember;
import com.zrp.mallplus.ums.entity.UserInfo;
import com.zrp.mallplus.utils.CommonResult;
import com.zrp.mallplus.vo.AppletLoginParam;
import com.zrp.mallplus.vo.SmsCode;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

/**
 * <p>
 * 会员表 服务类
 * </p>
 *
 * @author zscat
 * @since 2019-04-19
 */
public interface IUmsMemberService extends IService<UmsMember> {


    /**
     * 根据订单数和消费总金额
     * 更新用户的会员等级信息
     */
    void updataMemberOrderInfo();

    /**
     * 获取小程序openid
     *
     * @param req
     * @return
     */
    Object getAppletOpenId(AppletLoginParam req);



    Object loginByWeixin(AppletLoginParam req);


    /**
     * 小程序 登录注册
     * @param req
     * @return
     */
    Object loginByWeixin1(AppletLoginParam req);

    /**
     * 根据用户名获取会员
     */
    UmsMember getByUsername(String username);

    /**
     * 根据会员编号获取会员
     */
    UmsMember getById(Long id);

    /**
     * 用户注册
     */
    @Transactional
    CommonResult register(String phone, String password, String confim, String authCode, String invitecode);

    /**
     * 生成验证码
     */
    CommonResult generateAuthCode(String telephone);


    /**
     * 修改密码
     */
    @Transactional
    CommonResult updatePassword(String telephone, String password, String authCode);


    /**
     * 根据会员id修改会员积分
     */
    void updateIntegration(Long id, Integer integration);


    UmsMember queryByOpenId(String openId);


    /**
     * 双端登陆
     * @param username
     * @param password
     * @return
     */
    Map<String, Object> login(String username, String password);

    /**
     * 短信验证码登录
     * @param phone
     * @param phonecode
     * @return
     */
    Map<String,Object> loginBySms(String phone,String phonecode);


    /**
     * 刷新token
     * @param token
     * @return
     */
    String refreshToken(String token);

    /**
     * 注册
     * @param umsMember
     * @return
     */
    Object register(UmsMember umsMember);

    /**
     * 获取验证码
     * @param phone
     * @return
     */
    SmsCode generateCode(String phone);

    Map<String, Object> loginByCode(String phone, String authCode);

    Object simpleReg(String phone, String password, String confimpassword, String invitecode);

    /**
     * 添加余额记录 并更新用户余额
     *
     * @param id
     * @param integration
     */
    void addBlance(Long id, Integer integration, int type, String note);

    /**
     * 添加积分记录 并更新用户积分
     *
     * @param id
     * @param integration
     */
    void addIntegration(Long id, Integer integration, int changeType, String note, int sourceType, String operateMan);

    Map<String, Object> appLogin(String openid, Integer sex, String headimgurl, String unionid, String nickname, String city, Integer source);


    Object initMemberRedis();

    Object getCurrentMember();

    UmsMember getNewCurrentMember();

    /**
     * openId，采用 网页授权获取 access_token API：SnsAccessTokenApi获取
     * @return
     */
    Object webLogin(String wxH5Appid, String wxH5Secret, String code);


    /**
     * 小程序获取的手机加密信息进行解密
     *
     * @param openid
     * @param keyStr
     * @param ivStr
     * @param encDataStr
     * @return
     */
    String getWxPhone(String openid, String keyStr, String ivStr, String encDataStr);

    /**
     * 重置密码
     * @param phone
     * @param password
     * @param confimpassword
     * @param authCode
     * @return
     */
    Object resetPassword(String phone, String password, String confimpassword, String authCode);

    /**
     * 移动端微信登录
     * @param code
     * @param userInfos
     * @return
     */
    Map<String, Object>  loginByWeixin2(String code, UserInfo userInfos, String cloudID);
}

