package com.learn.yzh.common.constants;

/**
 * 赠送积分操作类型
 * ClassName:IntegralActionConstants
 *
 * @author yw
 * @create 2018-03-12 13:57
 */
public class IntegralActionConstants {
    //0购买影票1购买卖品2购买卡3线上绑定会员卡4签到5资料完善6过期7影院扣减8退票扣减
    public static final String ACTION_BUY_TICKET="0";//购票送积分（消费多少金额积累1分）
    public static final String ACTION_BUY_GOODS="1";//购卖品送积分（消费多少金额积累1分）
    public static final String ACTION_BUY_CARD="2";//购卡送积分（消费多少金额积累1分）
    public static final String ACTION_BIND_CARD="3";//绑卡送积分
    public static final String ACTION_SIGN="4";//签到送积分
    public static final String ACTION_COMPLETE_INFO="5";//完善资料送积分
    public static final String ACTION_PAST="6";//过期扣减积分
    public static final String ACTION_REFUND="8";//退单扣减积分
}
