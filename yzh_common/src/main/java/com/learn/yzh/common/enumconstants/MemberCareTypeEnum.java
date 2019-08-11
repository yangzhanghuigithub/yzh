package com.learn.yzh.common.enumconstants;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * ClassName:MemberCareEnum
 *会员关怀活动相关的常量值
 * 2，4,9、
 * 2.注册送礼,3.开卡送礼4.完善资料送礼5.关注影片上映通知6.影片映前提醒7.影片映后提醒8.满赠（消费金额）9.满赠（消费次数）10.生日关怀.11不活跃会员唤醒,12会员卡到期提醒.13会员卡充值送礼'
 * @author licho
 * @create 2018-11-01 15:26
 */
public enum  MemberCareTypeEnum {
    REGISTER_TYPE(2,"注册送礼"),
    BUYCARD_TYPE(3,"开卡送礼"),
    FINISHDATA_TYPE(4,"完善资料送礼"),
    CAREFILMSHOW_TYPE(5,"关注影片上映通知"),
    SHOWBEFOR_TYPE(6,"影片映前提醒"),
    SHOWAFTER_TYPE(7,"影片映后提醒"),
    CONSUMEAMOUNT_TYPE(8,"满赠消费金额"),
    CONSUMENUM_TYPE(9,"满赠消费次数"),
    CAREBITHDAY_TYPE(10,"生日关怀"),
    NOTIFYNOTACTICE_TYPE(11,"不活跃会员唤醒"),
    NOTIFYCARDEXPIRE_TYPE(12,"会员卡到期提醒"),
    RECHARGECARD_TYPE(13,"会员卡充值送礼");
    private Integer valueType;
    private String careDesc;
    MemberCareTypeEnum(Integer value,String careDesc){
        this.valueType=value;
        this.careDesc=careDesc;
    }

    public Integer getValueType() {
        return valueType;
    }

    public String getCareDesc() {
        return careDesc;
    }

    /**
     * 根据enumType值获取其MemberCareTypeNum值
     * @param valueType
     * @return
     */
    public static MemberCareTypeEnum getEnumByTypeValue(Integer valueType){
        MemberCareTypeEnum data=null;
        for(MemberCareTypeEnum type:MemberCareTypeEnum.values()){
            if(type.valueType.equals(valueType)){
                data=type;
            }
        }
        return data;
    }

    public static List<Integer> getValues(){
        return Arrays.asList(MemberCareTypeEnum.values()).stream().map(MemberCareTypeEnum::getValueType).collect(Collectors.toList());
    }
}
