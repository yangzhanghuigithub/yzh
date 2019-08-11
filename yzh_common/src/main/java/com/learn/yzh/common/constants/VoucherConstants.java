package com.learn.yzh.common.constants;

import org.omg.CORBA.INVALID_ACTIVITY;

/**
 * ClassName:VoucherConstants
 *
 * @author yangbin
 * @create 2018-01-14 14:25
 */
public class VoucherConstants {
    /**
     * 1.影院
     */
    public static final int relate_type_cinema = 1;
    /**
     * 2影厅制式
     */
    public static final int relate_type_hallsight = 2;
    /**
     * 3影厅名
     */
    public static final int relate_type_hallname = 3;
    /**
     * 4影片名
     */
    public static final int relate_type_filmCode= 4;
    /**
     * 5卖品
     */
    public static final int relate_type_goods = 5;
    /**
     * 6商家
     */
    public static final int relate_type_nearby = 6;

    /**
     * 券状态 未使用
     */
    public static final int VOUCHER_SALE_STATUS_NOUSE = 0;

    /**
     * 券状态 已使用
     */
    public static final int VOUCHER_SALE_STATUS_USED = 1;

    /**
     * 券状态 作废
     */
    public static final int VOUCHER_SALE_STATUS_INVALID = 2;
    /**
     * 未绑定
     */
    public static final int VOUCHER_SALE_BIND_NO = 0;
    /**
     * 已绑定
     */
    public static final int VOUCHER_SALE_BIND_YES = 1;

    /**
     * 固定时间
     */
    public static final int VOUCHER_SALE_EXPIRETYPE_FIXED = 1;

    /**
     * 绑券时计算天数
     */
    public static final int VOUCHER_SALE_EXPIRETYPE_FIXEDNO = 2;

    /**
     * 代金券
     */
    public static final int VOUCHER_TYPE_CASHCOUPON = 0;
    /**
     * 折扣券
     */
    public static final int VOUCHER_TYPE_COUPON = 1;
    /**
     * 兑换券
     */
    public static final int VOUCHER_TYPE_EXCHANGE = 2;
    /**
     * 其它
     */
    public static final int VOUCHER_TYPE_OTHER = 3;
    /**
     * 0元兑换券
     */
    public static final int VOUCHER_TYPE_ZEROEXCHANGE = 4;
    /**
     * 卖品券
     */
    public static final int VOUCHER_TYPE_SELL = 5;
    /**
     * 异业券
     */
    public static final int VOUCHER_TYPE_INDUSTRY = 6;
    /**
     * 商家券
     */
    public static final int VOUCHER_TYPE_MERCHANTS = 7;

    /**
     * 不限影片
     */
    public static final int VOUCHER_FILM_CHOOSE_WAY_NOLIMIT = 0;
    /**
     * 全部影片
     */
    public static final int VOUCHER_FILM_CHOOSE_WAY_ALL = 1;
    /**
     * 指定影片
     */
    public static final int VOUCHER_FILM_CHOOSE_WAY_SPECIFIED = 2;
    /**
     * 指定影片不参加'
     */
    public static final int VOUCHER_FILM_CHOOSE_WAY_NOTSPECIFIED = 3;

    /**
     * 不限卖品
     */
    public static final int VOUCHER_SELL_CHOOSE_WAY_NOLIMIT = 1;
    /**
     * 全部卖品
     */
    public static final int VOUCHER_SELL_CHOOSE_WAY_ALL = 2;
    /**
     * 指定卖品
     */
    public static final int VOUCHER_SELL_CHOOSE_WAY_SPECIFIED = 3;
    /**
     * 全部影城
     */
    public static final int VOUCHER_CINEMA_CHOOSE_WAY_ALL = 1;
    /**
     * 指定影城
     */
    public static final int VOUCHER_CINEMA_CHOOSE_WAY_SPECIFIED = 2;

    /** 红包:21票务定向红包 */
    public static final int VOUCHER_SALE_CHANNEL_PACKET_TICKETDIRECTIONAL = 21;
    /** 红包:22购票赠送红包 */
    public static final int VOUCHER_SALE_CHANNEL_PACKET_BUYTICKET = 22;
    /** 红包:23限量领取红包 */
    public static final int VOUCHER_SALE_CHANNEL_PACKET_LIMIT = 23;
    /** 红包:24附近商家红包 */
    public static final int VOUCHER_SALE_CHANNEL_PACKET_NEARBY = 24;
    /** 红包:25卖品定向红包 */
    public static final int VOUCHER_SALE_CHANNEL_PACKET_GOODSDIRECTIONAL = 25;
    /** 红包:26购物赠送红包 */
    public static final int VOUCHER_SALE_CHANNEL_PACKET_BUYSGOODS = 26;
    /** 红包：28 影核总公司发放红包*/
    public static final int VOUCHER_SALE_CHANNEL_PACKET_YINGHE = 28;

    /** 影片制式选择。1,全部影片制式 */
    public static final int VOUCHER_FILM_SIGHT_CHOOSE_ALL = 1;
    /** 影片制式选择。1,指定制式 */
    public static final int VOUCHER_FILM_SIGHT_CHOOSE_PART = 2;

    /** 影片兑换制式选择。1,全部影片制式 */
    public static final int VOUCHER_FILM_CONVERT_WAY_ALL = 1;
    /** 影片兑换制式选择。1,指定制式 */
    public static final int VOUCHER_FILM_CONVERT_WAY_PART = 2;

    /** 消息jsonkey，发卡数量 */
    /** 消息jsonkey，发券数量 */
    public static final String VOUCHER_MESSAGE_KEY_AMOUNT = "amount";
    /** 消息jsonkey，vouchersale */
    public static final String VOUCHER_MESSAGE_KEY_VOUCHERSALE = "vouchersale";
    /** 0:最低票价出票 */
    public static final int VOUCHER_POS_STRATEGY_LOWEST = 0;
    /** 1:影院补贴  */
    public static final int VOUCHER_POS_STRATEGY_SUB = 1;
    /** 2:不可使用 */
    public static final int VOUCHER_POS_STRATEGY_NO = 2;

}
