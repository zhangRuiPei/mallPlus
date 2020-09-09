package com.zrp.mallplus.sys.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.collect.Lists;
import com.zrp.mallplus.bo.Tree;
import com.zrp.mallplus.sys.entity.SysPermission;
import com.zrp.mallplus.sys.entity.SysPermissionNode;
import com.zrp.mallplus.sys.mapper.SysPermissionMapper;
import com.zrp.mallplus.sys.service.ISysPermissionService;
import com.zrp.mallplus.sys.service.ISysUserService;
import com.zrp.mallplus.ums.service.RedisService;
import com.zrp.mallplus.util.BuildTree;
import com.zrp.mallplus.util.JsonUtil;
import com.zrp.mallplus.utils.ValidatorUtils;
import com.zrp.mallplus.vo.Rediskey;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <p>
 * 后台用户权限表 服务实现类
 * </p>
 *
 * @author zscat
 * @since 2019-04-14
 */
@Service
public class SysPermissionServiceImpl extends ServiceImpl<SysPermissionMapper, SysPermission> implements ISysPermissionService {

    @Resource
    private SysPermissionMapper permissionMapper;
    @Resource
    private ISysUserService userService;

    @Resource
    private RedisService redisService;

    @Override
    public List<Tree<SysPermission>> getAllPermission() {
        List<Tree<SysPermission>> trees = Lists.newArrayList();
        List<SysPermission> menuDOs;
//        if (!redisService.exists(String.format(Rediskey.allTreesList, "admin"))) {
        List<Long> types = Lists.newArrayList(1L, 0L);
        menuDOs = permissionMapper.selectList(new QueryWrapper<SysPermission>().eq("status", 1).in("type", types).orderByDesc("sort"));
//            menuDOs = permissionMapper.selectList(new QueryWrapper<SysPermission>().eq("status", 1).in("type", types).orderByAsc("create_time"));
       /*     redisService.set(String.format(Rediskey.allTreesList, "admin"), JsonUtil.objectToJson(menuDOs));
        } else {
            menuDOs = JsonUtil.jsonToList(redisService.get(String.format(Rediskey.allTreesList, "admin")), SysPermission.class);
        }*/
        if (menuDOs != null && menuDOs.size() > 0) {
            for (SysPermission sysMenuDO : menuDOs) {
                Tree<SysPermission> tree = new Tree<SysPermission>();
                tree.setId(sysMenuDO.getId().toString());
                if (ValidatorUtils.notEmpty(sysMenuDO.getPid())) {
                    tree.setParentId(sysMenuDO.getPid().toString());
                }

                tree.setTitle(sysMenuDO.getName());
                Map<String, Object> attributes = new HashMap<>(16);
                attributes.put("url", sysMenuDO.getUrl());
                attributes.put("icon", sysMenuDO.getIcon());
                tree.setMeta(attributes);
                tree.setUrl(sysMenuDO.getUrl());
                tree.setIcon(sysMenuDO.getIcon());
                tree.setStatus(sysMenuDO.getStatus());
                tree.setSort(sysMenuDO.getSort());
                trees.add(tree);
            }
            // 默认顶级菜单为０，根据数据库实际情况调整
            List<Tree<SysPermission>> list = BuildTree.buildList(trees, "0");
            return list;
        }
        return new ArrayList<>();
    }

    @Override
    public List<Tree<SysPermission>> getPermissionsByUserId(Long id) {
        List<Tree<SysPermission>> trees = Lists.newArrayList();
        List<SysPermission> menuDOs = userService.listMenuByUserId(id);
        for (SysPermission sysMenuDO : menuDOs) {
            Tree<SysPermission> tree = new Tree<SysPermission>();
            tree.setId(sysMenuDO.getId().toString());
            tree.setParentId(sysMenuDO.getPid().toString());
            tree.setTitle(sysMenuDO.getName());
            Map<String, Object> attributes = new HashMap<>(16);
            attributes.put("url", sysMenuDO.getUrl());
            attributes.put("icon", sysMenuDO.getIcon());
            tree.setMeta(attributes);
            tree.setStatus(sysMenuDO.getStatus());
            tree.setSort(sysMenuDO.getSort());
            tree.setStatus(sysMenuDO.getStatus());
            trees.add(tree);
        }
        // 默认顶级菜单为０，根据数据库实际情况调整
        List<Tree<SysPermission>> list = BuildTree.buildList(trees, "0");
        return list;
    }

    @Override
    public List<SysPermissionNode> treeList() {
        List<SysPermission> permissionList = permissionMapper.getAllPermissionLists();/*selectList(new QueryWrapper<SysPermission>().orderByAsc("sort"))*/
        List<SysPermissionNode> result = permissionList.stream()
                .filter(permission -> permission.getPid().equals(0L))
                .map(permission -> covert(permission, permissionList)).collect(Collectors.toList());
        return result;
    }

    /*
     * 层级结构返回角色菜单
     */
    @Override
    public List<SysPermissionNode> getPermissionByRole(Long roleId){

        List<SysPermission> permissionList = permissionMapper.getPermissionList(roleId);
        List<SysPermissionNode> result = permissionList.stream()
                .filter(permission -> permission.getPid().equals(0L))
                .map(permission -> covert(permission,permissionList)).collect(Collectors.toList());
        return  result;
    }





    /*
        层级结构返回角色接口权限
     */
    @Override
    public List<SysPermissionNode> getRoleInterface(Long roleId){
        List<SysPermission> permissionList = permissionMapper.getRoleInterface(roleId);

        List<SysPermissionNode> result = permissionList.stream()
                .filter(permission -> permission.getPid().equals(0L))
                .map(permission -> covert(permission,permissionList)).collect(Collectors.toList());

        return result;

    }





  /*  public List<SysPermission> getAllId(Long pId, List<SysPermission> list){
        //根据父ID查底下的子节点
        List<SysPermission> org=permissionMapper.getRoleInterface(pId);

        if(null!=org){
            //如果底下有子节点-将子节点的ID加入list中
           for(int i=0;i<org.size();i++){
               list.add(org.get(i));
               //重新调用自身  -根据子节点的id和list
                list=getAllId(org.get(i).getId(),list);
           }
        }
        //返回list
        return list;
    }
*/

      /*
        层级结构返回所有接口权限
      */

    @Override
    public List<SysPermissionNode> getInterfacePermis(){
        List<SysPermission> permissionList = permissionMapper.getInterfacePermission();
        List<SysPermissionNode> result = permissionList.stream()
                .filter(permission -> permission.getPid().equals(0L))
                .map(permission -> covert(permission,permissionList)).collect(Collectors.toList());
        return result;
    }


    @Override
    public JSONArray testInterfacePermission(){
        List<SysPermission> data = permissionMapper.getInterfacePermission();
        System.out.println(JSON.toJSONString(data));
        JSONArray result = listToTree(JSONArray.parseArray(JSON.toJSONString(data)),"id","pid","children");
        System.out.println(JSON.toJSONString(result));
        return result;

    }


    public static JSONArray listToTree(JSONArray arr, String id, String pid, String child){
        JSONArray r = new JSONArray();
        JSONObject hash = new JSONObject();
        //将数组转为Object的形式，key为数组中的id
        for(int i=0;i<arr.size();i++){
            JSONObject json = (JSONObject) arr.get(i);
            hash.put(json.getString(id), json);
        }
        //遍历结果集
        for(int j=0;j<arr.size();j++){
            //单条记录
            JSONObject aVal = (JSONObject) arr.get(j);
            //在hash中取出key为单条记录中pid的值（aval pid--hash id 查aval父节点）
            JSONObject hashVP = (JSONObject) hash.get(aVal.get(pid).toString());
            //如果记录的pid存在，则说明aval它有父节点，将她添加到孩子节点的集合中
            if(hashVP!=null){
                //检查是否有child属性
                if(hashVP.get(child)!=null){
                    JSONArray ch = (JSONArray) hashVP.get(child);
                    ch.add(aVal);
                    hashVP.put(child, ch);
                }else{
                    JSONArray ch = new JSONArray();
                    ch.add(aVal);
                    hashVP.put(child, ch);
                }
            }else{
                r.add(aVal);
            }
        }
        return r;
    }









    public int updateShowStatus(List<Long> ids, Integer showStatus) {
        SysPermission productCategory = new SysPermission();
        productCategory.setStatus(showStatus);
        return permissionMapper.update(productCategory, new QueryWrapper<SysPermission>().in("id", ids));

    }

    /**
     * 将权限转换为带有子级的权限对象
     * 当找不到子级权限的时候map操作不会再递归调用covert
     */
    private SysPermissionNode covert(SysPermission permission, List<SysPermission> permissionList) {
        SysPermissionNode node = new SysPermissionNode();
        BeanUtils.copyProperties(permission, node);
        List<SysPermissionNode> children = permissionList.stream()
                .filter(subPermission -> subPermission.getPid().equals(permission.getId()))
                .map(subPermission -> covert(subPermission, permissionList)).collect(Collectors.toList());
        node.setChildren(children);
        return node;
    }

    /**
     * 将更改之后的商品从新存入redis
     * */
    @Override
    public void addRedis(){
        List<Long> types = Lists.newArrayList(1L, 0L);
        List<SysPermission> menuDOs = permissionMapper.selectList(new QueryWrapper<SysPermission>().eq("status", 1).in("type", types).orderByAsc("sort"));
        redisService.set(String.format(Rediskey.allTreesList, "admin"), JsonUtil.objectToJson(menuDOs));
    }
}
