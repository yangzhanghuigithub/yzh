package com.learn.yzh.common.constants;

/**
 * ClassName:CardConstants
 *
 * @author yangbin
 * @create 2017-12-28 17:36
 */
public class CardConstants {

	/** 卡类型-储值卡 */
	public static final int CARD_TYPE_storedvalue = 1;
	/** 卡类型-权益卡 */
	public static final int CARD_TYPE_equity = 2;

	/** 影票规则 */
	public static final int CARD_RULE_TYPE_TICKET = 1;
	/** 卖品规则 */
	public static final int CARD_RULE_TYPE_GOODS = 2;

	/** 1全部影院 */
	public static final int CINEMA_WAY_ALL = 1;
	/** 2指定影院 */
	public static final int CINEMA_WAY_SOME = 2;

	/** 1按影厅制式（类型）选择 */
	public static final int HALL_WAY_TYPE = 1;
	/** 2按影厅名称选择 */
	public static final int HALL_WAY_NAME = 2;

	/** 1已生成批次 */
	public static final int CARD_STATUS_HAS_APPLY_YES = 1;
	/** 2未生成批次 */
	public static final int CARD_STATUS_HAS_APPLY_NO = 2;

	/** 1可编辑 */
	public static final int CARD_RULE_CANEDIT_YES = 1;
	/** 2不可编辑 */
	public static final int CARD_RULE_CANEDIT_NO = 2;

	/** 1影院 */
	public static final int RELATE_TYPE_CINEMA = 1;
	/** 2影厅类型 */
	public static final int RELATE_TYPE_HALLTYPE = 2;
	/** 3影厅code */
	public static final int RELATE_TYPE_HALLNAME = 3;

	/** 未生成完成，不能操作激活、导出等 */
	public static final int CARD_APPLY_STAUTS_UNFINISHED = -1;

	/** 正常 */
	public static final int CARD_APPLY_STAUTS_NORMAL = 1;
	/** 作废 */
	public static final int CARD_APPLY_STAUTS_INVALID = 2;

	/** 未导出 */
	public static final int CARD_APPLY_EXPORT_NO = 1;
	/** 导出 */
	public static final int CARD_APPLY_EXPORT_YES = 2;

	/** 1未绑定 */
	public static final int CARD_SALE_BIND_NO = 1;
	/** 2绑定 */
	public static final int CARD_SALE_BIND_YES = 2;

	/** 正常 */
	public static final int CARD_SALE_STAUTS_NORMAL = 1;
	/** 作废 */
	public static final int CARD_SALE_STAUTS_INVALID = 2;
	/** 冻结 */
	public static final int CARD_SALE_STAUTS_FREEZE = 3;
	/** 过期 */
	public static final int CARD_SALE_STAUTS_EXPIRED = 4;
	/** 购卡锁定 */
	public static final int CARD_SALE_STAUTS_LOCK = 5;

	/** 1待售 */
	public static final int CARD_SALE_SALESTAUTS_WAITSALE = 1;
	/** 2已售 */
	public static final int CARD_SALE_SALESTAUTS_SALED = 2;
	/** 3已导出 */
	public static final int CARD_SALE_SALESTAUTS_EXPORTED = 3;

	/** 1固定有效期 */
	public static final int CARD_APPLY_EXPIRE_DATEPOINT = 1;
	/** 2绑定计算过期时间 */
	public static final int CARD_APPLY_EXPIRE_DAYS = 2;

	/** 消息jsonkey，发卡数量 */
	public static final String CARD_MESSAGE_KEY_AMOUNT = "amount";
	/** 消息jsonkey，cardsale */
	public static final String CARD_MESSAGE_KEY_CARDSALE = "cardsale";

	/** 卡订单支付状态 1已支付 */
	public static final int CARD_ORDER_PAY_STATUS_YES = 1;
	/** 卡订单支付状态 2未支付 */
	public static final int CARD_ORDER_PAY_STATUS_NO = 2;

	// 订单状态:1000:新创建 1001:下单成功 1002:下单失败 1003:已取消

	/** 卡订单状态 1000:新创建 */
	public static final int CARD_ORDER_STATUS_NEW = 1000;
	/** 卡订单状态 1001:下单成功 */
	public static final int CARD_ORDER_STATUS_SUCCESS = 1001;
	/** 卡订单状态 1002:下单失败 */
	public static final int CARD_ORDER_STATUS_FAIL = 1002;
	/** 卡订单状态 1003:已取消 */
	public static final int CARD_ORDER_STATUS_CANCEL = 1003;

	/** 卡上架状态 1:上架 */
	public static final int CARD_SHELVE_STATUS_YES = 1;
	/** 卡上架状态 2:未上架 */
	public static final int CARD_SHELVE_STATUS_NO = 2;

	/** 卡推荐状态 1:推荐 */
	public static final int CARD_SPREAD_STATUS_YES = 1;
	/** 卡推荐状态 2:未推荐 */
	public static final int CARD_SPREAD_STATUS_NO = 2;

	/** 卡推荐状态 0:充值 */
	public static final int CARD_CONSUME_TYPE_RECHARGE = 0;
	/** 卡推荐状态 1:消费 */
	public static final int CARD_CONSUME_TYPE_USE = 1;

	/**
	 * 2支付宝
	 */
	public static final String PAY_TYPE_ALIPAY="1";
	/**
	 * 3微信
	 */
	public static final String PAY_TYPE_WECHAT="2";


	/** 卡推荐 */
	public static final String RC_CINEMA_CARD = "rc_cinema_card";//影城推荐的卡
	/** 卡推荐 */
	public static final String RC_CARD_PRICE = "rc_card_price";//推荐影城的卡的价格

	/*首单立减类型*/
	public static final Integer FIRST_ORDER_DISCOUNT=1;//
	/*办卡促销类型*/
	public static final Integer BUY_CARD_DISCOUNT=2;

	/**
	 * 记录绑卡输入错误密码次数
	 */
	public static final String BIND_CARD_FIREWALL="bindCardFirewall";

}
