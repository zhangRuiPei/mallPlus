package com.zrp.mallplus.ums.controller;

import com.zrp.mallplus.annotation.SysLog;
import com.zrp.mallplus.ums.entity.UmsMemberGoodsLog;
import com.zrp.mallplus.ums.service.IUmsMemberGoodsLogService;
import com.zrp.mallplus.utils.CommonResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Date;

/**
 * Created by ZRF on 2020/8/13.
 */

@Slf4j
@RestController
@Api(tags = "UmsMemberGoodsController", description = "会员、商品操作日志记录")
@RequestMapping("/ums/UmsMemberGoods")
public class UmsMemberGoodsLogController {

    @Resource
    private IUmsMemberGoodsLogService iUmsMemberGoodsLogService;


    @SysLog(MODULE = "ums", REMARK = "查询操作日志")
    @ApiOperation("查询操作日志")
    @GetMapping(value = "/list")
    //  @PreAuthorize("hasAuthority('ums:UmsMember:update')")
    public Object getLogList(UmsMemberGoodsLog entity,
                          @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                          @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize) {

        return new CommonResult().success(iUmsMemberGoodsLogService.getLogList(entity,pageNum,pageSize));
    }


    @SysLog(MODULE = "sys", REMARK = "新增操作日志")
    @ApiOperation("新增操作日志")
    @PostMapping(value = "/create")
//    @PreAuthorize("hasAuthority('sys:notice:create')")
    public  Object saveLogList(@RequestBody UmsMemberGoodsLog entity){
        entity.setOperateTime(new Date());
        try {
            if(iUmsMemberGoodsLogService.save(entity)){
                return new CommonResult().success();
            }
        }catch (Exception e){
            log.error("新增操作日志: %s",e.getMessage(),e);
        }
        return new CommonResult().failed();
    }

}
