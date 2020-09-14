package com.zrp.mallplus.sys.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zrp.mallplus.build.entity.UserCommunityRelate;
import com.zrp.mallplus.build.mapper.UserCommunityRelateMapper;
import com.zrp.mallplus.exception.BusinessMallException;
import com.zrp.mallplus.sys.entity.*;
import com.zrp.mallplus.sys.mapper.*;
import com.zrp.mallplus.sys.service.*;
import com.zrp.mallplus.sys.vo.SysUserVo;
import com.zrp.mallplus.ums.entity.SysSms;
import com.zrp.mallplus.ums.service.RedisService;
import com.zrp.mallplus.util.JsonUtil;
import com.zrp.mallplus.util.JwtTokenUtil;
import com.zrp.mallplus.util.UserUtils;
import com.zrp.mallplus.utils.CommonResult;
import com.zrp.mallplus.utils.ValidatorUtils;
import com.zrp.mallplus.vo.Rediskey;
import com.zrp.mallplus.vo.SmsVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import javax.annotation.Resource;
import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

/**
 * <p>
 * 后台用户表 服务实现类
 * </p>
 *
 * @author zscat
 * @since 2019-04-14
 */
@Slf4j
@Service("sysUserService")
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements ISysUserService {

    @Value("${redis.key.prefix.authCode}")
    private String REDIS_KEY_PREFIX_AUTH_CODE;

    @Autowired(required = false)
    private AuthenticationManager authenticationManager;
    @Resource
    private UserDetailsService userDetailsService;
    @Resource
    private JwtTokenUtil jwtTokenUtil;
    @Resource
    private BCryptPasswordEncoder passwordEncoder;
    @Value("${jwt.tokenHead}")
    private String tokenHead;
    @Resource
    private SysUserMapper adminMapper;
    @Resource
    private SysUserRoleMapper adminRoleRelationMapper;
    @Resource
    private ISysUserRoleService adminRoleRelationService;
    @Resource
    private SysUserPermissionMapper adminPermissionRelationMapper;
    @Resource
    private SysRoleMapper roleMapper;
    @Resource
    private ISysRoleService roleService;
    @Resource
    private ISysUserPermissionService userPermissionService;
    @Resource
    private ISysRolePermissionService rolePermissionService;
    @Resource
    private ISysUserRoleService userRoleService;
    @Resource
    private SysPermissionMapper permissionMapper;
    @Resource
    private RedisService redisService;

    @Resource
    private SmsService smsService;
    @Resource
    private ISysStoreService iSysStoreService;
    @Resource
    private SysUserMapper sysUserMapper;


    @Value("${aliyun.sms.expire-minute:1}")
    private Integer expireMinute;
    @Value("${aliyun.sms.day-count:30}")
    private Integer dayCount;

    @Resource
    private UserCommunityRelateMapper userCommunityRelateMapper;

    /**
     * 刷新Token
     * @param oldToken 旧Token
     * @return
     */
    @Override
    public String refreshToken(String oldToken) {
        String token = oldToken.substring(tokenHead.length());
        if (jwtTokenUtil.canRefresh(token)) {
            return jwtTokenUtil.refreshToken(token);
        }
        return null;
    }

    @Override
    public String  login(String username, String password) {
        String token = null;
        //密码需要客户端加密后传递
        try {
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);
            if (!passwordEncoder.matches(password, userDetails.getPassword())) {
                throw new BadCredentialsException("密码不正确");
            }
            Authentication authentication = new UsernamePasswordAuthenticationToken(
                    userDetails, null, userDetails.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authentication);
            token = jwtTokenUtil.generateToken(userDetails);
            System.out.println(JSONObject.toJSONString(UserUtils.getCurrentMember()));
            log.info(JSONObject.toJSONString(UserUtils.getCurrentMember()));
            this.removePermissRedis(UserUtils.getCurrentMember().getId());
        } catch (AuthenticationException e) {
            log.warn("登录异常:{}", e.getMessage());
        }
        return token;
    }

    /**
     * 给用户分配角色
     * @param adminId 用户ID
     * @param roleId 角色ID
     * @return
     */
    @Override
    public Boolean updateUserRole(Long adminId, Long roleId) {
        //先删除原来的关系
        SysUserRole userRole = new SysUserRole();
        userRole.setAdminId(adminId);
        adminRoleRelationMapper.delete(new QueryWrapper<>(userRole));
        //修改note
        SysRole sysRole = roleService.getById(roleId);
        SysUser sysUser = adminMapper.selectById(adminId);
        sysUser.setNote(sysRole.getName());
        adminMapper.updateById(sysUser);
        //建立新关系
        SysUserRole roleRelation = new SysUserRole();
        roleRelation.setAdminId(adminId);
        roleRelation.setRoleId(roleId);

        return userRoleService.save(roleRelation);
    }


    /**
     * 根据用户ID查询 用户角色表
     * @param userId 用户ID
     * @return
     */
    public SysUserRole getUserRoleByUserId(Long userId){
        return adminMapper.getUserRoleByUserId(userId);
    }

    /**
     * 根据用户ID 删除用户角色表
     * @param adminId
     * @return
     */
    @Override
    public int removeById(Long adminId) {
        //删除原所有权限关系
        SysUserRole sysUserRole = getUserRoleByUserId(adminId);
        adminRoleRelationService.removeById(sysUserRole.getId());
        return adminMapper.deleteById(adminId);
    }

    @Override
    public List<SysRole> getRoleListByUserId(Long adminId) {
        return roleMapper.getRoleListByUserId(adminId);
    }

    @Override
    public int updatePermissionByUserId(Long adminId, List<Long> permissionIds) {
        //删除原所有权限关系
        SysUserPermission userPermission = new SysUserPermission();
        userPermission.setAdminId(adminId);
        adminPermissionRelationMapper.delete(new QueryWrapper<>(userPermission));
        //获取用户所有角色权限
        List<SysPermission> permissionList = permissionMapper.listMenuByUserId(adminId);
        List<Long> rolePermissionList = permissionList.stream().map(SysPermission::getId).collect(Collectors.toList());
        if (!CollectionUtils.isEmpty(permissionIds)) {
            List<SysUserPermission> relationList = new ArrayList<>();
            //筛选出+权限
            List<Long> addPermissionIdList = permissionIds.stream().filter(permissionId -> !rolePermissionList.contains(permissionId)).collect(Collectors.toList());
            //筛选出-权限
            List<Long> subPermissionIdList = rolePermissionList.stream().filter(permissionId -> !permissionIds.contains(permissionId)).collect(Collectors.toList());
            //插入+-权限关系
            relationList.addAll(convert(adminId, 1, addPermissionIdList));
            relationList.addAll(convert(adminId, -1, subPermissionIdList));
            userPermissionService.saveBatch(relationList);
        }
        return 0;
    }

    @Override
    public List<SysPermission> listMenuByUserId(Long adminId) {
//        if (!redisService.exists(String.format(Rediskey.menuTreesList, adminId))) {
        List<SysPermission> list = permissionMapper.listMenuByUserId(adminId);
//            redisService.set(String.format(Rediskey.menuTreesList, adminId), JsonUtil.objectToJson(list));
        return list;
//        } else {
//            return JsonUtil.jsonToList(redisService.get(String.format(Rediskey.menuTreesList, adminId)), SysPermission.class);
//        }

    }

    @Override
    public List<SysPermission> getPermissionListByUserId(Long adminId) {
//        String listStr = redisService.get(String.format(Rediskey.permissionTreesList, adminId));
//        if (null == listStr) {
        List<SysPermission> list = permissionMapper.getPermissionListByUserId(adminId);
//            listStr = JsonUtil.objectToJson(list);
        redisService.set(String.format(Rediskey.permissionTreesList, adminId), JsonUtil.objectToJson(list));
        return list;
//        } else {
//            return JsonUtil.jsonToList(listStr, SysPermission.class);
//        }
    }

    /**
     * 新增后台用户
     * 参数 : username 用户门
     * 参数 : storeId 商铺ID
     * 参数 : password 密码(可为空)
     * 参数 : email 邮箱
     * 参数 : icon 头像URL
     * @param umsAdmin
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean saves(SysUserVo umsAdmin) {
        boolean result = false;
        umsAdmin.setCreateTime(new Date());
        umsAdmin.setStatus(1);
        umsAdmin.setEmail(umsAdmin.getEmail());
        umsAdmin.setIcon(umsAdmin.getIcon());
        //查询是否有相同用户名的用户
        SysUser umsAdminList = adminMapper.selectByUserName(umsAdmin.getUsername());
        if (umsAdminList == null) {
            if (umsAdmin.getStoreId() == 1) {
                umsAdmin.setNote("后台员工");
            } else {
                umsAdmin.setNote("商家员工");
            }//设置商铺名称
            SysStore sysStore = iSysStoreService.getById(umsAdmin.getStoreId());
            umsAdmin.setStoreName(sysStore.getName());
            if (StringUtils.isEmpty(umsAdmin.getPassword())) {
                umsAdmin.setPassword("123456");
            }
            String md5Password = passwordEncoder.encode(umsAdmin.getPassword());
            umsAdmin.setPassword(md5Password);
            umsAdmin.setStoreId(UserUtils.getCurrentMember().getStoreId());
            adminMapper.insert(umsAdmin);
            addUserRole(umsAdmin.getId(), umsAdmin.getRoleId(), umsAdmin.getStoreId());
            result = true;
        }

        return result;
    }

    /**
     * 删除 用户和用户之前的关系,建立新关系
     * @param adminId 用户id
     * @param roleId 角色ID
     * @param storeId 商铺ID
     */
    @Transactional(rollbackFor = Exception.class)
    public void addUserRole(Long adminId, Long roleId,Integer storeId) {
        //删除缓存,如果有 删除原有关系
        this.removePermissRedis(adminId);
        adminRoleRelationMapper.delete(new QueryWrapper<SysUserRole>().eq("admin_id", adminId));
        //建立新关系
        if (!StringUtils.isEmpty(roleId)) {
            SysUserRole roleRelation = new SysUserRole();
            roleRelation.setAdminId(adminId);
            roleRelation.setRoleId(roleId);
            roleRelation.setStoreId(storeId);
            adminRoleRelationService.save(roleRelation);
        }
    }

    /**
     * 修改店铺内用户角色
     * @param id 用户id
     * @param admin 用户角色
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean updates(Long id, SysUserVo admin) {
        updateRole(id, admin.getRoleIds());
        return true;
    }

    public void updateRole(Long adminId, String roleIds) {
        this.removePermissRedis(adminId);
        adminRoleRelationMapper.delete(new QueryWrapper<SysUserRole>().eq("admin_id", adminId));
        //建立新关系
        if (!StringUtils.isEmpty(roleIds)) {
            String[] rids = roleIds.split(",");
            List<SysUserRole> list = new ArrayList<>();
            for (String roleId : rids) {
                SysUserRole roleRelation = new SysUserRole();
                roleRelation.setAdminId(adminId);
                roleRelation.setRoleId(Long.valueOf(roleId));
                list.add(roleRelation);
            }
            adminRoleRelationService.saveBatch(list);
        }
    }


    @Override
    public List<SysPermission> listUserPerms(Long id) {
        if (!redisService.exists(String.format(Rediskey.menuList, id))) {
            List<SysPermission> list = permissionMapper.listUserPerms(id);
            String key = String.format(Rediskey.menuList, id);
            redisService.set(key, JsonUtil.objectToJson(list));
            return list;
        } else {
            return JsonUtil.jsonToList(redisService.get(String.format(Rediskey.menuList, id)), SysPermission.class);
        }
    }

    @Override
    public List<SysPermission> listPerms() {
        if (!redisService.exists(String.format(Rediskey.allMenuList, "admin"))) {
            List<SysPermission> list = permissionMapper.selectList(new QueryWrapper<>());
            String key = String.format(Rediskey.allMenuList, "admin");
            redisService.set(key, JsonUtil.objectToJson(list));
            return list;
        } else {
            return JsonUtil.jsonToList(redisService.get(String.format(Rediskey.allMenuList, "admin")), SysPermission.class);
        }
    }

    @Override
    public void removePermissRedis(Long id) {
        redisService.remove(String.format(Rediskey.menuTreesList, id));
        redisService.remove(String.format(Rediskey.menuList, id));
        redisService.remove(String.format(Rediskey.allTreesList, "admin"));
        redisService.remove(String.format(Rediskey.allMenuList, "admin"));
    }

    @Override
    public Object reg(SysUserVo umsAdmin) {
        if (ValidatorUtils.empty(umsAdmin.getUsername())) {
            return new CommonResult().failed("手机号为空");
        }
        if (ValidatorUtils.empty(umsAdmin.getPassword())) {
            return new CommonResult().failed("密码为空");
        }
        //验证验证码
        if (!verifyAuthCode(umsAdmin.getCode(), umsAdmin.getUsername())) {
            return new CommonResult().failed("验证码错误");
        }
        if (!umsAdmin.getPassword().equals(umsAdmin.getConfirmPassword())) {
            return new CommonResult().failed("密码不一致");
        }
        umsAdmin.setCreateTime(new Date());
        umsAdmin.setStatus(1);
        //查询是否有相同用户名的用户

        SysUser umsAdminList = adminMapper.selectByUserName(umsAdmin.getUsername());
        if (umsAdminList != null) {
            return new CommonResult().failed("手机号已注册");
        }
        //将密码进行加密操作
        if (StringUtils.isEmpty(umsAdmin.getPassword())) {
            umsAdmin.setPassword("123456");
        }
        String md5Password = passwordEncoder.encode(umsAdmin.getPassword());
        umsAdmin.setPassword(md5Password);
        adminMapper.insert(umsAdmin);
        SysUserRole roleRelation = new SysUserRole();
        roleRelation.setAdminId(umsAdmin.getId());
        roleRelation.setRoleId(5L);
        adminRoleRelationMapper.insert(roleRelation);
        return new CommonResult().failed("注册成功");
    }
//    @Override
//    public SmsCode generateCode(String phone) {
//        //生成流水号
//        String uuid = UUID.randomUUID().toString();
//        StringBuilder sb = new StringBuilder();
//        Random random = new Random();
//        for (int i = 0; i < 6; i++) {
//            sb.append(random.nextInt(10));
//        }
//        Map<String, String> map = new HashMap<>(2);
//        map.put("code", sb.toString());
//        map.put("phone", phone);
//
//        //短信验证码缓存15分钟，
//        redisService.set(REDIS_KEY_PREFIX_AUTH_CODE + phone, sb.toString());
//        redisService.expire(REDIS_KEY_PREFIX_AUTH_CODE + phone, 60);
//        log.info("缓存验证码：{}", map);
//
//        //存储sys_sms
//        saveSmsAndSendCode(phone, sb.toString());
//        SmsCode smsCode = new SmsCode();
//        smsCode.setKey(uuid);
//        return smsCode;
//    }

    //对输入的验证码进行校验
    public boolean verifyAuthCode(String authCode, String telephone) {
        if (StringUtils.isEmpty(authCode)) {
            return false;
        }
        String realAuthCode = redisService.get(REDIS_KEY_PREFIX_AUTH_CODE + telephone);
        return authCode.equals(realAuthCode);
    }

    @Override
    public int updateShowStatus(List<Long> ids, Integer showStatus) {
        SysUser productCategory = new SysUser();
        productCategory.setStatus(showStatus);
        return adminMapper.update(productCategory, new QueryWrapper<SysUser>().in("id", ids));

    }

    @Transactional
    @Override
    public Object userCommunityRelate(UserCommunityRelate entity) {
        //先删除原有关系
        userCommunityRelateMapper.delete(new QueryWrapper<UserCommunityRelate>().eq("user_id", entity.getUserId()));
        //批量插入新关系
        //  List<UserCommunityRelate> relationList = new ArrayList<>();
        if (!StringUtils.isEmpty(entity.getCommunityIds())) {
            String[] mids = entity.getCommunityIds().split(",");
            for (String permissionId : mids) {
                UserCommunityRelate relation = new UserCommunityRelate();
                relation.setUserId(entity.getUserId());
                relation.setCommunityId(Long.valueOf(permissionId));
                //  relationList.add(relation);
                userCommunityRelateMapper.insert(relation);
            }

        }
        return 1;
    }

    @Override
    public void updatePassword(String password, String newPassword) {
        SysUser oldUser = UserUtils.getCurrentMember();
        log.info("旧密码=" + passwordEncoder.encode(password));
        if (!passwordEncoder.matches(password, oldUser.getPassword())) {
            //   if (!oldUser.getPassword().equals(passwordEncoder.encode(password))){
            throw new BusinessMallException("旧密码错误");
        }
        SysUser role = new SysUser();
        role.setId(oldUser.getId());
        role.setPassword(passwordEncoder.encode(newPassword));
        adminMapper.updateById(role);
    }

    @Override
    public List<SysUserVo> findAll(SysUserVo entity) {
        return sysUserMapper.findAll(entity);
    }

    /**
     * 根据条件查询用户
     * 条件 : 店铺ID  参数 : storeId
     * 条件 : 角色ID 参数 : roleId
     * 条件 : 店铺名称/用户昵称 参数 : nickName
     * @param entity
     * @param pageNum
     * @param pageSize
     * @return
     */
    @Override
    public Map<String,Object> getUserList(SysUserVo entity,Integer pageNum,Integer pageSize) {
        Map<String,Object> map = new HashMap();
        PageHelper.startPage(pageNum,pageSize);
        List<SysUserVo> userLists = adminMapper.getUserLists(entity);
        Page<SysUserVo> all = (Page<SysUserVo>)userLists ;
        map.put("data",all.getResult());
        map.put("total",all.getTotal());
        return map;
    }


    /**
     * 保存短信记录，并发送短信
     *
     * @param phone
     */
//    private void saveSmsAndSendCode(String phone, String code) {
//        checkTodaySendCount(phone);
//
//        SysSms sms = new SysSms();
//        sms.setPhone(phone);
//        sms.setParams(code);
//        Map<String, String> params = new HashMap<>();
//        params.put("code", code);
//        params.put("admin", "admin");
//        smsService.save(sms, params);
//
//        //异步调用阿里短信接口发送短信
//        CompletableFuture.runAsync(() -> {
//            try {
//                smsService.sendSmsMsg(sms);
//            } catch (Exception e) {
//                params.put("err",  e.getMessage());
//                smsService.save(sms, params);
//                e.printStackTrace();
//                log.error("发送短信失败：{}", e.getMessage());
//            }
//
//        });
//
//        // 当天发送验证码次数+1
//        String countKey = countKey(phone);
//        redisService.increment(countKey, 1L);
//        redisService.expire(countKey, 1*3600*24);
//    }
    private String countKey(String phone) {
        return "sms:admin:count:" + LocalDate.now().toString() + ":" + phone;
    }

    private String smsRediskey(String str) {
        return "sms:admin:" + str;
    }

    /**
     * 获取当天发送验证码次数
     * 限制号码当天次数
     *
     * @param phone
     * @return
     */
    private void checkTodaySendCount(String phone) {
        String value = redisService.get(countKey(phone));
        if (value != null) {
            Integer count = Integer.valueOf(value);
            if (count > dayCount) {
                throw new IllegalArgumentException("已超过当天最大次数");
            }
        }

    }

    /**
     * 将+-权限关系转化为对象
     */
    private List<SysUserPermission> convert(Long adminId, Integer type, List<Long> permissionIdList) {
        List<SysUserPermission> relationList = permissionIdList.stream().map(permissionId -> {
            SysUserPermission relation = new SysUserPermission();
            relation.setAdminId(adminId);
            relation.setType(type);
            relation.setPermissionId(permissionId);
            return relation;
        }).collect(Collectors.toList());
        return relationList;
    }



    /**
     * 保存短信记录，并发送商家入驻消息
     *
     * @param phone
     */
    @Override
    public void saveSmsAndSendCode(String phone, String username,String password,String name) {
//        checkTodaySendCount(phone);

        SmsVo smsVo = new SmsVo();
        smsVo.setName(name);
        smsVo.setUsername(username);
        smsVo.setPassword(password);

        String smsvo = JSON.toJSONString(smsVo);


        SysSms sms = new SysSms();
        sms.setPhone(phone);
        sms.setParams(smsvo);
        Map<String, String> params = new HashMap<>();
        params.put("code", smsvo);
        params.put("admin", "admin");

        smsService.add(sms);

        //异步调用阿里短信接口发送短信
        CompletableFuture.runAsync(() -> {
            try {
                smsService.sendSmsMsg(sms);
            } catch (Exception e) {
                params.put("err",  e.getMessage());
                e.printStackTrace();
                log.error("发送短信失败：{}", e.getMessage());
            }

        });
      /*  // 当天发送验证码次数+1
        String countKey = countKey(phone);
        redisService.increment(countKey, 1L);
        redisService.expire(countKey, 1*3600*24);*/
    }
}
