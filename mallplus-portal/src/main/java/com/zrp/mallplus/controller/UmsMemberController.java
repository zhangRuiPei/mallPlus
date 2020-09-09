package com.zrp.mallplus.controller;


import com.zrp.mallplus.annotation.IgnoreAuth;

import com.zrp.mallplus.ums.entity.UmsMember;
import com.zrp.mallplus.ums.service.IUmsMemberService;
import com.zrp.mallplus.ums.service.RedisService;
import com.zrp.mallplus.util.JsonUtils;
import com.zrp.mallplus.utils.CommonResult;
import com.zrp.mallplus.vo.Rediskey;
import com.zrp.mallplus.vo.SmsCode;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author: 乔黎莱莱
 * @Version: 1.0.0
 * @time Auto
 * @remark:
 */
@RestController
@Api(tags = "UmsMemberController")
@RequestMapping("/api/member")
public class UmsMemberController  {
    @Autowired
    private IUmsMemberService memberService;
    @Value("${jwt.tokenHeader}")
    private String tokenHeader;
    @Value("${jwt.tokenHead}")
    private String tokenHead;

    @Resource
    private RedisService redisService;

    @IgnoreAuth
    @ApiOperation(value = "登录以后返回token")
    @PostMapping(value = "/login")
    @ResponseBody
    public Object login(@RequestBody UmsMember umsMember) {
        if (umsMember == null) {
            return new CommonResult().validateFailed("用户名或密码错误");
        }
        if (umsMember.getLoginType()==1){
            try {
                Map<String, Object> token = memberService.login(umsMember.getUsername(), umsMember.getPassword());
                if (token.get("token") == null) {
                    return new CommonResult().validateFailed("用户名或密码错误");
                }
                redisService.set(String.format(Rediskey.MEMBER, umsMember.getUsername()), JsonUtils.objectToJson(umsMember));
                return new CommonResult().success(token);
            } catch (AuthenticationException e) {
                return new CommonResult().validateFailed("用户名或密码错误");
            }
        }else if (umsMember.getLoginType() ==2){
            Map<String, Object> token = memberService.loginBySms(umsMember.getPhone(),umsMember.getPhonecode());
            if (token.get("token") == null) {
                return new CommonResult().validateFailed("用户名或密码错误");
            }
            redisService.set(String.format(Rediskey.MEMBER, umsMember.getUsername()), JsonUtils.objectToJson(umsMember));
            return new CommonResult().success(token);
        }else if (umsMember.getLoginType() == 3){
            Map<String, Object> objectMap = memberService.loginByWeixin2(umsMember.getCode(), umsMember.getUserInfo(), umsMember.getCloudID());
            if (objectMap.get("token")==null){
                return new CommonResult().validateFailed("用户名或密码错误");
            }
            redisService.set(String.format(Rediskey.MEMBER, umsMember.getUsername()), JsonUtils.objectToJson(umsMember));
            return new CommonResult().success(objectMap);
        }
        return new CommonResult().validateFailed("用户名或密码错误");
    }

    @IgnoreAuth
    @ApiOperation("注册")
    @RequestMapping(value = "/register")
    @ResponseBody
    public Object register(UmsMember umsMember) {
        if (umsMember == null) {
            return new CommonResult().validateFailed("用户名或密码错误");
        }
        return memberService.register(umsMember);
    }


    @IgnoreAuth
    @ApiOperation("获取验证码")
    @RequestMapping(value = "/getAuthCode", method = RequestMethod.GET)
    @ResponseBody
    public Object getAuthCode(@RequestParam String telephone) {
        return memberService.generateAuthCode(telephone);
    }


    @ApiOperation("修改密码")
    @RequestMapping(value = "/updatePassword", method = RequestMethod.POST)
    @ResponseBody
    public Object updatePassword(@RequestParam String telephone,
                                 @RequestParam String password,
                                 @RequestParam String authCode) {
        return memberService.updatePassword(telephone, password, authCode);
    }

    @IgnoreAuth
    @GetMapping("/user")
    @ResponseBody
    public Object user() {
        UmsMember umsMember = memberService.getNewCurrentMember();
        if (umsMember != null && umsMember.getId() != null) {
            return new CommonResult().success(umsMember);
        }
        return new CommonResult().failed();

    }

    @ApiOperation(value = "刷新token")
    @RequestMapping(value = "/token/refresh", method = RequestMethod.GET)
    @ResponseBody
    public Object refreshToken(HttpServletRequest request) {
        String token = request.getHeader(tokenHeader);
        String refreshToken = memberService.refreshToken(token);
        if (refreshToken == null) {
            return new CommonResult().failed();
        }
        Map<String, String> tokenMap = new HashMap<>();
        tokenMap.put("token", token);
        tokenMap.put("tokenHead", tokenHead);
        return new CommonResult().success(tokenMap);
    }


    @ApiOperation(value = "登出功能")
    @RequestMapping(value = "/logout", method = RequestMethod.POST)
    @ResponseBody
    public Object logout() {
        return new CommonResult().success(null);
    }



    @ApiOperation(value = "短信")
    @PostMapping(value = "/smsPhone")
    @ResponseBody
    public Object generateCode(String phone){
        SmsCode smsCode = memberService.generateCode(phone);
        return new CommonResult().success(smsCode);
    }
}
