package com.zrp.mallplus.ums.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.zrp.mallplus.live.entity.AliyunLive;
import com.zrp.mallplus.live.mapper.AliyunMapper;
import com.zrp.mallplus.oms.mapper.OmsOrderMapper;
import com.zrp.mallplus.oms.vo.OrderStstic;
import com.zrp.mallplus.ums.entity.UmsMember;
import com.zrp.mallplus.ums.entity.UmsMemberGoodsLog;
import com.zrp.mallplus.ums.entity.UmsMemberLevel;
import com.zrp.mallplus.ums.mapper.UmsMemberMapper;
import com.zrp.mallplus.ums.mapper.UmsMemberSmsMapper;
import com.zrp.mallplus.ums.service.IUmsMemberGoodsLogService;
import com.zrp.mallplus.ums.service.IUmsMemberLevelService;
import com.zrp.mallplus.ums.service.IUmsMemberService;
import com.zrp.mallplus.ums.service.IUmsMemberSmsService;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 会员表 服务实现类
 * </p>
 *
 * @author zscat
 * @since 2019-04-19
 */
@Log4j
@Service
public class UmsMemberServiceImpl extends ServiceImpl<UmsMemberMapper, UmsMember> implements IUmsMemberService {

    @Resource
    private IUmsMemberLevelService memberLevelService;
    @Resource
    private OmsOrderMapper omsOrderMapper;
    @Resource
    private UmsMemberMapper memberMapper;
    @Resource
    private AliyunMapper aliyunMapper;
    @Resource
    private IUmsMemberSmsService smsService;
    @Resource
    private UmsMemberSmsMapper umsMemberSmsMapper;
    @Resource
    private IUmsMemberGoodsLogService iUmsMemberGoodsLogService;

    @Value("${aliyun.sms.day-count:30}")
    private Integer dayCount;
    @Value("${redis.key.prefix.authCode}")
    private String REDIS_KEY_PREFIX_AUTH_CODE;

    @Override
    public void updataMemberOrderInfo() {
        List<OrderStstic> orders = omsOrderMapper.listOrderGroupByMemberId();
        List<UmsMemberLevel> levelList = memberLevelService.list(new QueryWrapper<UmsMemberLevel>().orderByDesc("price"));
        for (OrderStstic o : orders) {
            UmsMember member = new UmsMember();
            member.setId(o.getMemberId());
            member.setBuyMoney(o.getTotalPayAmount());
            for (UmsMemberLevel level : levelList) {
                if (member.getBuyMoney().compareTo(level.getPrice()) >= 0) {
                    member.setMemberLevelId(level.getId());
                    member.setMemberLevelName(level.getName());
                    break;
                }
            }
            member.setBuyCount(o.getTotalCount());
            memberMapper.updateById(member);
        }
    }



    @Override
    public Map<String, Object> findAll(UmsMember entity, Integer pageNum, Integer pageSize) {
        Map<String,Object> map = new HashMap();

        PageHelper.startPage(pageNum,pageSize);
        Page<UmsMember> all = (Page<UmsMember>) this.baseMapper.findAll(entity);
        map.put("data",all.getResult());
        map.put("total",all.getTotal());
        return map;
    }

    @Override
    public Boolean updateRoomNumById(AliyunLive entity) {
        Boolean data = false;
        String roomNums = null;
        UmsMember umsMember = memberMapper.selectById(entity.getUserId());//当前传过来的新id
        if (umsMember!=null){
            if (umsMember.getRoomNums()!=null && !umsMember.getRoomNums().equals("")){
                //判断当前用户有绑定直播间
                //拿出已绑直播间
                roomNums = umsMember.getRoomNums();

                //存入心直播间
                umsMember.setRoomNums(entity.getRoomNumber().toString());
                //更改
                if (memberMapper.updateById(umsMember)==1){

                    //获取之前直播间
                    AliyunLive aliyunLive = aliyunMapper.selectOne(new QueryWrapper<AliyunLive>().eq("room_number", roomNums));
                    aliyunLive.setUserId(null);
                    if (aliyunMapper.updateByIdCanNull(aliyunLive)==1){
                        data = true;
                    }
                }
            }
            umsMember.setRoomNums(entity.getRoomNumber().toString());
            if (memberMapper.updateById(umsMember)==1){
                data = true;
            }
            //获取准备绑定直播间
            AliyunLive aliyunLive = aliyunMapper.selectById(entity.getId());
            if (aliyunLive.getUserId()!=null){
                // 获取到之前绑定的user；
                UmsMember ordUser = memberMapper.selectById(aliyunLive.getUserId());
                ordUser.setRoomNums(null);
                memberMapper.updateByIdCanNull(ordUser);
            }
        }
        return data;
    }

    /**
     * 更新会员 保存操作日志
     * @param entity
     * @return
     */
    @Override
    @Transactional
    public boolean updateMemberGoodsLog(UmsMember entity,String operMan){
        UmsMember um = new UmsMember();
        UmsMemberGoodsLog umgLog = new UmsMemberGoodsLog();

        um = memberMapper.selectById(entity.getId());
        StringBuilder sbBefore = new StringBuilder();
        StringBuilder sbAfter = new StringBuilder();

        if(!um.getPhone().equals(entity.getPhone())){
            sbBefore.append("{phone：");
            sbBefore.append(um.getPhone()+"} ");

            sbAfter.append("{phone：");
            sbAfter.append(entity.getPhone()+"} ");
        }

        if(!um.getNickname().equals(entity.getNickname())){
            sbBefore.append("{nickName：");
            sbBefore.append(um.getNickname()+"} ");

            sbAfter.append("{nickName：");
            sbAfter.append(entity.getNickname()+"} ");
        }

        if(um.getGender() != entity.getGender()){
            sbBefore.append("{gender：");
            sbBefore.append(um.getGender()+"} ");

            sbAfter.append("{gender：");
            sbAfter.append(entity.getGender()+"} ");
        }

        umgLog.setShareId(entity.getId());
        umgLog.setName(entity.getUsername());
        umgLog.setOperateMan(operMan);
        umgLog.setOperateBefore(sbBefore.toString());
        umgLog.setOperateAfter(sbAfter.toString());
        umgLog.setOperateTime(new Date());
        umgLog.setType(0);
        umgLog.setNote("会员信息变更");
        boolean saveLog = iUmsMemberGoodsLogService.save(umgLog);
        int upMember = memberMapper.updateById(entity);
        if(upMember > 0 && saveLog ){
            return true;
        }else {
            return false;
        }
    }
}
