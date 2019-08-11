package com.learn.yzh.common.constants;

/**
 * ClassName:CareConstants
 *
 * @author bilaoye
 * @create 2018-01-17 15:28
 */
public class CareConstants {

    /****注册*****/
    public static  final  Integer REGISTER_TYPE=2;
    /****开卡*****/
    public static  final  Integer BUYCARD_TYPE=3;
    /****完善资料*****/
    public static  final  Integer FINISHDATA_TYPE=4;
    /****关注影片上映通知*****/
    public static  final  Integer CAREFILMSHOW_TYPE=5;
    /****影片映前提醒*****/
    public static  final  Integer SHOWBEFOR_TYPE=6;
    /****影片映后提醒*****/
    public static  final  Integer SHOWAFTER_TYPE=7;
    /****满赠（消费金额）*****/
    public static  final  Integer CONSUMEAMOUNT_TYPE=8;
    /****满赠（消费次数）*****/
    public static  final  Integer CONSUMENUM_TYPE=9;
    /****生日关怀*****/
    public static  final  Integer CAREBITHDAY_TYPE=10;
    /****不活跃会员唤醒*****/
    public static  final  Integer NOTIFYNOTACTICE_TYPE=11;
    /****会员卡到期提醒*****/
    public static  final  Integer NOTIFYCARDEXPIRE_TYPE=12;
    /****会员卡充值送礼*****/
    public static  final  Integer RECHARGECARD_TYPE=13;

    //会员关怀状态未开始
    public static final Integer MEMBERCARE_STATUS_PUBLSH=1;
    //会员关怀状态进行中
    public static final Integer MEMBERCARE_STATUS_RUNNING=2;
    //会员关怀状态停止
    public static final Integer MEMBERCARE_STATUS_STOP=3;
    //会员关怀状态结束（过期）
    public static final Integer MEMBERCARE_STATUS_OUTDATE=3;

    //开卡送礼不限会员卡
    public static final Integer MEMBERCARE_BUY_CARD_ANY_CARD=1;//

    public static final Integer MEMBERCARE_BUY_CARD_SPECIFIED_CARD=2;//指定会员卡

}