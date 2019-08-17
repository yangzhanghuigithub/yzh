package com.learn.yzh.common.model.voucher;

import com.learn.yzh.common.Enum.voucher.GiveVoucherStrategy;
import com.learn.yzh.common.constants.ResultConstants;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

/**
 * ClassName:GiveVoucherWrapper
 *
 * @author licho
 * @create 2018-12-26 14:44
 */
public class GiveVoucherWrapper {
    private static final Logger logger = LoggerFactory.getLogger(GiveVoucherWrapper.class);
    private String businessId;//业务主键ID
    private Integer channel;//发券渠道
    private String memberCode;//会员编码
    private Map<String,Integer> applyCount;//发送的批次及每个批次发送数量
    private Integer giveVoucherStrategy;//发券策略

    public String getMemberCode() {
        return memberCode;
    }

    public void setMemberCode(String memberCode) {
        this.memberCode = memberCode;
    }

    public String getBusinessId() {
        return businessId;
    }

    public void setBusinessId(String businessId) {
        this.businessId = businessId;
    }

    public Integer getChannel() {
        return channel;
    }

    public void setChannel(Integer channel) {
        this.channel = channel;
    }

    public Map<String, Integer> getApplyCount() {
        return applyCount;
    }

    public void setApplyCount(Map<String, Integer> applyCount) {
        this.applyCount = applyCount;
    }

    public Integer getGiveVoucherStrategy() {
        return giveVoucherStrategy;
    }

    public void setGiveVoucherStrategy(Integer giveVoucherStrategy) {
        this.giveVoucherStrategy = giveVoucherStrategy;
    }

    public boolean assertNecessaryPro(){
        boolean flag=true;
        String resultDesc="校验成功";
        if(StringUtils.isBlank(businessId)||applyCount==null||applyCount.keySet().size()<=0||giveVoucherStrategy==null|| StringUtils.isBlank(memberCode)){
            flag=false;
            resultDesc="Wrapper所有参数都不应该为null。";
        }
        for(String applyID:applyCount.keySet()){
            Integer integer = applyCount.get(applyID);
            if(integer==null||integer<1){
                flag=false;
                resultDesc="每个券批次的赠送数量不能小于1";
                break;
            }
        }
        if(GiveVoucherStrategy.mapOf(giveVoucherStrategy)==null){
            flag=false;
            resultDesc="不存在对应的发券策略。";
        }
        logger.info("业务主键：{}的给用户：{}送券第一步参数校验:{},提示消息:{}",businessId,memberCode,flag ? "通过":"未通过",resultDesc);
        return flag;
    }
}
