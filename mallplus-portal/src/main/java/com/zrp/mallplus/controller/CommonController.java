package com.zrp.mallplus.controller;

import com.zrp.mallplus.annotation.IgnoreAuth;
import com.zrp.mallplus.enums.AllEnum;
import com.zrp.mallplus.ums.entity.UmsMember;
import com.zrp.mallplus.ums.entity.UmsMemberLocation;
import com.zrp.mallplus.ums.service.IUmsMemberLocationService;
import com.zrp.mallplus.ums.service.IUmsMemberService;
import com.zrp.mallplus.util.LocationUtil;
import com.zrp.mallplus.util.OssAliyunUtil;
import com.zrp.mallplus.util.vo.ExpressInfoList;
import com.zrp.mallplus.util.vo.ExpressVo;
import com.zrp.mallplus.util.vo.LocalResoultVo;
import com.zrp.mallplus.utils.CommonResult;
import com.zrp.mallplus.vo.AppletLoginParam;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.Data;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Date;

/**
 * @Author: 乔黎莱莱
 * @Version: 1.0.0
 * @time Auto
 * @remark: 公共的通用类接口
 */

@Data
@CrossOrigin
@RestController
@Api(tags = "CommonController", description = "通用")
@RequestMapping("/api/common")
public class CommonController {


    @Resource
    private OssAliyunUtil ossAliyunUtil;

    @Resource
    private IUmsMemberService umsMemberService;

    @Resource
    private IUmsMemberLocationService memberLocationService;


    /**
     * 根据经纬度返回城市
     * @param param
     * @return
     * @throws Exception
     */
    @IgnoreAuth
    @ApiOperation("获取定位城市")
    @PostMapping("/getAddressInfo")
    public Object getAddressInfo(@RequestBody AppletLoginParam param) throws Exception {
        LocalResoultVo address = LocationUtil.getAddress(param.getLat(), param.getLng());
        System.out.println(address);
        String status = address.getStatus();
        String msg = address.getMsg();
        if (status.equals("0")){
            return new CommonResult().success(address);
        }
        return new CommonResult().validateFailed(msg);
    }


    /**
     * 根据城市返回经纬度
     * @param param
     * @return
     */
    @IgnoreAuth
    @ApiOperation("获取经纬度根据地址")
    @PostMapping("/addr2coord")
    public Object addr2coord(@RequestBody AppletLoginParam param) {
        LocalResoultVo address = LocationUtil.addr2coord(param.getAddress());
        System.out.println(address);
        String status = address.getStatus();
        String msg = address.getMsg();
        if (status.equals("0")){
            return new CommonResult().success(address);
        }
        return new CommonResult().validateFailed(msg);
    }



    /**
     * 根据快递号返回快递信息
     * @param param
     * @return
     * @throws Exception
     */
    @IgnoreAuth
    @ApiOperation("获取快递节点")
    @PostMapping("/getExpress")
    public Object getExpress(@RequestBody AppletLoginParam param) throws Exception {
        ExpressVo queryexpress = LocationUtil.queryexpress(param.getExpressNumber(), param.getType());
        return new CommonResult().success(queryexpress);
    }


    @IgnoreAuth
    @ApiOperation("查询主流快递")
    @GetMapping("/getExpressInfo")
    public Object getExpressInfo(){
        ExpressInfoList expressInfo = LocationUtil.findExpressInfo();
        return new CommonResult().success(expressInfo);
    }


    /**
     * 签到送积分
     * @param param
     * @return
     */
    @Transactional
    @ApiOperation("签到")
    @GetMapping("/signLocaltion")
    public Object signLocaltion(@RequestBody AppletLoginParam param){
        boolean save =false;
        try {
            UmsMember newCurrentMember = umsMemberService.getNewCurrentMember();
            Long id = newCurrentMember.getId();
            Integer integration = 5;        //积分
            int changeType = 1;             //1 为增加积分 2 为减少积分
            String note = "签到送积分";      //备注
            Integer code = AllEnum.ChangeSource.sign.code();    //获取积分途径    登录
            String username = newCurrentMember.getUsername();    //操作人
            if (newCurrentMember!=null){
                LocalResoultVo address = LocationUtil.getAddress(param.getLat(), param.getLng());
                UmsMemberLocation umsMemberLocation = new UmsMemberLocation();
                umsMemberLocation.setLatitude(Double.valueOf(param.getLat()));
                umsMemberLocation.setLongitude(Double.valueOf(param.getLng()));
                umsMemberLocation.setMemberId(newCurrentMember.getId());
                umsMemberLocation.setUserName(newCurrentMember.getNickname());
                umsMemberLocation.setPic(newCurrentMember.getIcon());
                umsMemberLocation.setCreateTime(new Date());
                umsMemberLocation.setAddress(address.getResult().getAddress());
                save = memberLocationService.save(umsMemberLocation);
                umsMemberService.addIntegration(id,integration,changeType,note,code,username);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new CommonResult().success(save);
    }




}
