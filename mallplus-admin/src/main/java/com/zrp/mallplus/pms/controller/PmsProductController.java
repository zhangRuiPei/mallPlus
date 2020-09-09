package com.zrp.mallplus.pms.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zrp.mallplus.annotation.SysLog;
import com.zrp.mallplus.enums.ConstansValue;
import com.zrp.mallplus.pms.entity.*;
import com.zrp.mallplus.pms.service.IPmsProductService;
import com.zrp.mallplus.pms.vo.PmsProductResult;
import com.zrp.mallplus.utils.CommonResult;
import com.zrp.mallplus.utils.ValidatorUtils;
import com.zrp.mallplus.vo.IdStatus;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * 商品信息
 * @Author: 乔黎莱莱
 * @Version: 1.0.0
 * @time Auto
 * @remark:
 */
@Slf4j
@RestController
@Api(tags = "PmsProductController", description = "商品信息管理")
@RequestMapping("/pms/PmsProduct")
public class PmsProductController {

    @Resource
    private IPmsProductService IPmsProductService;

    @SysLog(MODULE = "pms", REMARK = "根据条件查询所有商品信息列表")
    @ApiOperation("根据条件查询所有商品信息列表")
    @GetMapping(value = "/list")
    @PreAuthorize("hasAuthority('pms:PmsProduct:read')")
    public Object getPmsProductByPage(PmsProduct entity,
                                      @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                                      @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize
    ) {
        try {
            return new CommonResult().success(IPmsProductService.getList(entity,pageNum,pageSize));
        } catch (Exception e) {
            log.error("根据条件查询所有商品信息列表：%s", e.getMessage(), e);
        }
        return new CommonResult().failed();
    }


    /**
     * 新增
     * @param
     * @return
     */
    @PostMapping(value = "/addPmsPro")
    @ResponseBody
    public Object addPmsPro(@RequestBody String JsonProduct){
        Integer integer = IPmsProductService.addList(JsonProduct);
        return new CommonResult().success(integer);
    }

    /**
     * 新增
     * @param
     * @return
     */
    @PostMapping(value = "/addproduct")
    public Object addproduct( @RequestBody PmsProduct pmsProduct){
        Long aLong = IPmsProductService.addList(pmsProduct, pmsProduct.getSkustockList(), pmsProduct.getPecarrs());
        if (aLong!=null){
        return new CommonResult().success(aLong);
        }
        return new CommonResult().failed();

    }

    /**
     * 修改
     * @param pmsProduct
     * @return
     */
    @PostMapping(value = "/updateProdu")
    @ResponseBody
    public Object updateProdu(@RequestBody PmsProduct pmsProduct){
        Integer integer = IPmsProductService.updatePmsPro(pmsProduct);
        return new CommonResult().success(integer);
    }




    /**
     * 根据商品名称或货号模糊查询
     * @param keyword
     * @return
     */
    @ApiOperation("根据商品名称或货号模糊查询")
    @RequestMapping(value = "/simpleList", method = RequestMethod.GET)
    @ResponseBody
    public Object getList(String keyword) {
        List<PmsProduct> productList = IPmsProductService.list(keyword);
        return new CommonResult().success(productList);
    }



    @SysLog(MODULE = "pms", REMARK = "删除商品信息")
    @ApiOperation("删除商品信息")
    @GetMapping(value = "/delete/{id}")
    @PreAuthorize("hasAuthority('pms:PmsProduct:delete')")
    public Object deletePmsProduct(@ApiParam("商品信息id") @PathVariable Long id) {
        try {
            if (ValidatorUtils.empty(id)) {
                return new CommonResult().paramFailed("商品信息id");
            }
            if (IPmsProductService.removeById(id)) {
                List<Long> arr = new ArrayList<>();
                arr.add(id);
                //删除多规格
                IPmsProductService.removeMultiple(arr);
                return new CommonResult().success();
            }
        } catch (Exception e) {
            log.error("删除商品信息：%s", e.getMessage(), e);
            return new CommonResult().failed();
        }
        return new CommonResult().failed();
    }

    @SysLog(MODULE = "pms", REMARK = "给商品信息分配商品信息")
    @ApiOperation("查询商品信息明细")
    @GetMapping(value = "/{id}")
    @PreAuthorize("hasAuthority('pms:PmsProduct:read')")
    public Object getPmsProductById(@ApiParam("商品信息id") @PathVariable Long id) {
        try {
            if (ValidatorUtils.empty(id)) {
                return new CommonResult().paramFailed("商品信息id");
            }
            PmsProduct coupon = IPmsProductService.getProductById(id);
            return new CommonResult().success(coupon);
        } catch (Exception e) {
            log.error("查询商品信息明细：%s", e.getMessage(), e);
            return new CommonResult().failed();
        }

    }

    @ApiOperation(value = "批量删除商品信息")
    @RequestMapping(value = "/delete/batch", method = RequestMethod.POST)
    @ResponseBody
    @SysLog(MODULE = "pms", REMARK = "批量删除商品信息")
    @PreAuthorize("hasAuthority('pms:PmsProduct:delete')")
    public Object deleteBatch(@RequestParam("ids") List<Long> ids) {
        boolean count = IPmsProductService.removeByIds(ids);
        if (count) {
            //删除多规格
            IPmsProductService.removeMultiple(ids);
            return new CommonResult().success(count);
        } else {
            return new CommonResult().failed();
        }
    }

    @ApiOperation("根据商品id获取商品编辑信息")
    @RequestMapping(value = "/updateInfo/{id}", method = RequestMethod.GET)
    @ResponseBody
    @SysLog(MODULE = "pms", REMARK = "根据商品id获取商品编辑信息")
    @PreAuthorize("hasAuthority('pms:PmsProduct:read')")
    public Object getUpdateInfo(@PathVariable Long id) {
        PmsProductResult productResult = IPmsProductService.getUpdateInfo(id);
        return new CommonResult().success(productResult);
    }

    @ApiOperation("根据商品id获取审核信息")
    @RequestMapping(value = "/fetchVList/{id}", method = RequestMethod.GET)
    @ResponseBody
    @SysLog(MODULE = "pms", REMARK = "据商品id获取审核信息")
    public Object fetchVList(@PathVariable Long id) {
        List<PmsProductVertifyRecord> list = IPmsProductService.getProductVertifyRecord(id);
        return new CommonResult().success(list);
    }

    @ApiOperation("批量修改审核状态")
    @RequestMapping(value = "/update/verifyStatus")
    @ResponseBody
    @SysLog(MODULE = "pms", REMARK = "批量修改审核状态")
    @PreAuthorize("hasAuthority('pms:PmsProduct:update')")
    public Object updateVerifyStatus(@RequestParam("ids") Long ids,
                                     @RequestParam("verifyStatus") Integer verifyStatus,
                                     @RequestParam("detail") String detail) {
        int count = IPmsProductService.updateVerifyStatus(ids, verifyStatus, detail);
        if (count > 0) {
            return new CommonResult().success(count);
        } else {
            return new CommonResult().failed();
        }
    }

    @ApiOperation("批量上下架")
    @RequestMapping(value = "/update/publishStatus", method = RequestMethod.POST)
    @ResponseBody
    @SysLog(MODULE = "pms", REMARK = "批量上下架")
    @PreAuthorize("hasAuthority('pms:PmsProduct:update')")
    public Object updatePublishStatus(@RequestParam("ids") List<Long> ids,
                                      @RequestParam("publishStatus") Integer publishStatus,
                                      @RequestParam("storeId") Integer storeId) {
        int count = IPmsProductService.updatePublishStatus(ids, publishStatus,storeId);
        if (count > 0) {
            return new CommonResult().success(count);
        } else {
            return new CommonResult().failed();
        }
    }

    @ApiOperation("批量上下架")
    @RequestMapping(value = "/publishStatus", method = RequestMethod.POST)
    @ResponseBody
    @SysLog(MODULE = "pms", REMARK = "批量上下架")
    public Object updatePublishStatu(@RequestBody IdStatus ids, BindingResult result) {
        PmsProduct product = new PmsProduct();
        product.setId(ids.getId());
        product.setPublishStatus(ids.getStatus());
        Boolean count = IPmsProductService.updateById(product);
        if (count) {
            return new CommonResult().success(count);
        } else {
            return new CommonResult().failed();
        }
    }

    @ApiOperation("批量推荐商品")
    @RequestMapping(value = "/update/recommendStatus", method = RequestMethod.POST)
    @ResponseBody
    @SysLog(MODULE = "pms", REMARK = "批量推荐商品")
    @PreAuthorize("hasAuthority('pms:PmsProduct:update')")
    public Object updateRecommendStatus(@RequestParam("ids") List<Long> ids,
                                        @RequestParam("recommendStatus") Integer recommendStatus) {
        int count = IPmsProductService.updateRecommendStatus(ids, recommendStatus);
        if (count > 0) {
            return new CommonResult().success(count);
        } else {
            return new CommonResult().failed();
        }
    }

    @ApiOperation("批量设为新品")
    @RequestMapping(value = "/update/newStatus", method = RequestMethod.POST)
    @ResponseBody
    @SysLog(MODULE = "pms", REMARK = "批量设为新品")
    @PreAuthorize("hasAuthority('pms:PmsProduct:update')")
    public Object updateNewStatus(@RequestParam("ids") List<Long> ids,
                                  @RequestParam("newStatus") Integer newStatus) {
        int count = IPmsProductService.updateNewStatus(ids, newStatus);
        if (count > 0) {
            return new CommonResult().success(count);
        } else {
            return new CommonResult().failed();
        }
    }

    @ApiOperation("批量设为分销")
    @RequestMapping(value = "/update/isFenxiao", method = RequestMethod.POST)
    @ResponseBody
    @SysLog(MODULE = "pms", REMARK = "批量设为分销")
    public Object updateisFenxiao(@RequestParam("ids") List<Long> ids,
                                  @RequestParam("newStatus") Integer newStatus) {
        int count = IPmsProductService.updateisFenxiao(ids, newStatus);
        if (count > 0) {
            return new CommonResult().success(count);
        } else {
            return new CommonResult().failed();
        }
    }

    @ApiOperation("批量修改删除状态")
    @RequestMapping(value = "/update/deleteStatus", method = RequestMethod.POST)
    @ResponseBody
    @SysLog(MODULE = "pms", REMARK = "批量修改删除状态")
    @PreAuthorize("hasAuthority('pms:PmsProduct:delete')")
    public Object updateDeleteStatus(@RequestParam("ids") List<Long> ids,
                                     @RequestParam("deleteStatus") Integer deleteStatus) {
        int count = IPmsProductService.updateDeleteStatus(ids, deleteStatus);
        if (count > 0) {
            return new CommonResult().success(count);
        } else {
            return new CommonResult().failed();
        }
    }

    @GetMapping(value = "/goods/list")
    public Object getPmsProductListByPage(PmsProduct entity,
                                          @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                                          @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize
    ) {
        try {
            if (entity.getType() == 1) {
                return new CommonResult().success(IPmsProductService.page(new Page<PmsProduct>(pageNum, pageSize), new QueryWrapper<PmsProduct>().eq("publish_status", 1).gt("stock", 0).select(ConstansValue.sampleGoodsList).orderByDesc("create_time")));
            }
            if (entity.getType() == 2) {
                return new CommonResult().success(IPmsProductService.page(new Page<PmsProduct>(pageNum, pageSize), new QueryWrapper<PmsProduct>().eq("publish_status", 0).gt("stock", 0).select(ConstansValue.sampleGoodsList).orderByDesc("create_time")));
            }
            if (entity.getType() == 3) {
                return new CommonResult().success(IPmsProductService.page(new Page<PmsProduct>(pageNum, pageSize), new QueryWrapper<PmsProduct>().lt("stock", 1).select(ConstansValue.sampleGoodsList).orderByDesc("create_time")));
            }

            return new CommonResult().success(IPmsProductService.page(new Page<PmsProduct>(pageNum, pageSize), new QueryWrapper<PmsProduct>().select(ConstansValue.sampleGoodsList)));
        } catch (Exception e) {
            log.error("根据条件查询所有商品信息列表：%s", e.getMessage(), e);
        }
        return new CommonResult().failed();
    }



}
