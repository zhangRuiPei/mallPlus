package com.zrp.mallplus.sys.service;

import com.alibaba.fastjson.JSONArray;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zrp.mallplus.bo.Tree;
import com.zrp.mallplus.sys.entity.SysPermission;
import com.zrp.mallplus.sys.entity.SysPermissionNode;

import java.util.List;

/**
 * <p>
 * 后台用户权限表 服务类
 * </p>
 *
 * @author zscat
 * @since 2019-04-14
 */
public interface ISysPermissionService extends IService<SysPermission> {

    List<Tree<SysPermission>> getPermissionsByUserId(Long id);

    List<SysPermissionNode> treeList();

    List<Tree<SysPermission>> getAllPermission();

    List<SysPermissionNode> getPermissionByRole(Long roleId);
    void addRedis();
    JSONArray testInterfacePermission();
    List<SysPermissionNode> getInterfacePermis();

//    List<Tree<SysPermission>> getRoleInterface(Long roleId);

    List<SysPermissionNode> getRoleInterface(Long roleId);
}
