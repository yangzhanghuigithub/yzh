package com.learn.yzh.common.constants;
/**
 * 订单常量
 * ClassName:OrderConstants
 *
 * @author yw
 * @create 2017-11-24 14:34
 */
public class OrderConstants {
    /** 订单状态 */
    public static final String ORDER_STATUS_CREATE="1000";//已创建
    public static final String ORDER_STATUS_COMPLETE="1001";//已完成
    public static final String ORDER_STATUS_FAIL="1002";//订单失败
    public static final String ORDER_STATUS_CANCEL="1003";//订单取消
    public static final String ORDER_STATUS_PROCESS="1007";//出票中
    public static final String ORDER_STATUS_REFUND_SUCCESS="1302";//退款成功
    public static final String ORDER_STATUS_REFUND_PROCESS="1304";//退款申请中
    public static final String ORDER_STATUS_REFUND_FAIL="1305";//退款失败
    public static final String ORDER_STATUS_MEALS_READ="1401";//已出餐
    public static final String ORDER_STATUS_MEALS_OVER="1402";//已配送

    public static final String COMPLETED_ORDER_REFUND_SUCCESS="1501";//订单成功退款成功
    public static final String FAIL_ORDER_REFUND_SUCCESS="1502";//订单失败退款成功
    public static final String COMPLETED_ORDER_REFUND_PROCESS="1503";//订单成功退款中
    public static final String FAIL_ORDER_REFUND_PROCESS="1504";//订单失败退款中
    public static final String ORDER_EXCEPTION_REFUND_PMENT_FAIL ="0";//订单异常类型 0 退款异常
    public static final String ORDER_EXCEPTION_OPERATING_UNTREATED ="0";//异常订单处理状态 0 未处理

    /** 订单支付状态 */
    public static final String ORDER_PAY_NOT="0";//待付
    public static final String ORDER_PAY_PAID="1";//已付
    public static final String ORDER_PAY_REFUND="2";//已退

    /** 活动优惠类型 */
    public static final String STRATEGY_DISCOUNTS_TYPE="0";//0价格策略
    public static final String ACTIVITY_DISCOUNTS_TYPE="1";//1活动
    public static final String STRATEGY_AND_ACTIVITY_DISCOUNTS_TYPE="2";//活动+价格策略
    /** 卡券优惠类型 */
    public static final String DISCOUNTS_TYPE_DJQ="0";//代金券
    public static final String DISCOUNTS_TYPE_ZKQ="1";//折扣券
    public static final String DISCOUNTS_TYPE_DHQ="2";//兑换券
    public static final String DISCOUNTS_TYPE_CZK="3";//储值卡
    public static final String DISCOUNTS_TYPE_QYK="5";//权益卡
    public static final String DISCOUNTS_TYPE_DHQLY="6";//零元兑换券
    public static final String DISCOUNTS_TYPE_DHQMP="7";//卖品券

    /** 订单类型 */
    public static final int ORDER_TYPE_TICKET=1;//影票订单
    public static final int ORDER_TYPE_GOODS=2;//商品订单
    public static final int ORDER_TYPE_MIXTURE=3;//混合订单
    public static final int ORDER_TYPE_CUSUME_INFO=5;//卡充值订单
    public static final int ORDER_TYPE_CUSUME_SELL=4;//卖品订单

    /** 订单支付方式 */
    public static final int ORDER_PAY_WAY_ALI=1;//支付宝
    public static final int ORDER_PAY_WAY_WECHAT=2;//微信
    public static final int ORDER_PAY_WAY_CARD=3;//储值卡
    public static final int ORDER_PAY_CHANNEL_=4;//渠道卡
    public static final int ORDER_PAY_WAY_ZERO=5;//0元支付
    /** 订单确认状态 */
    public static final String CONFIRM_FLAG_NO="0";//未确认
    public static final String CONFIRM_FLAG_YES="1";//已确认
    /** 支付流水状态 */
    public static final int PAYMENT_TYPE_IN=1;//收款
    public static final int PAYMENT_TYPE_OUT=2;//退款

    /**退款渠道**/
    public static final int REFUND_CHANNEL_APP=1;//app退
    public static final int REFUND_CHANNEL_ORDER_FAILURE=2;//订单失败退
    public static final int REFUND_CHANNEL_HAND=3;//b端手动退
    /**订单明细类型*/
    public static  final int ORDER_DETAIL_TYPE_TICKET=1;//影票类型
    public static  final int ORDER_DETAIL_TYPE_SELL=2;//卖品类型
    /**订单创建渠道*/
    public static final int ORDER_CHANNEL_APP=0;//app
    public static final int ORDER_CHANNEL_WEI=1;//微站
    public static final int ORDER_CHANNEL_LIGHT=3;//轻量化
    public static final int ORDER_CHANNEL_PC=4;//pc端购票
    public static final int ORDER_CHANNEL_MINI_APP=5;//小程序

    /** 订单类型 卡充值订单 */
    public static final String Order_Type_Cusume_Info = "3";
    /** 订单类型 购卡订单 */
    public static final String Order_Type_Card = "2";
    public static final String Auto_Refund_Account = "autoRefundAccount";

    //明细表中的优惠类型
    public static final  Integer DISCOUNT_TYPE_PRICE_STRATEGY=0;//

    public static final Integer  DISCOUNT_TYPE_EVENT=1;//活动

    public static final Integer DISCOUNT_TYPE_CARD=2;//卡

    public static final Integer DISCOUNT_TYPE_VOUCHER=3;//券

    public static final Integer DISCOUNT_TYPE_POS_CARD=4;//POS卡
}
