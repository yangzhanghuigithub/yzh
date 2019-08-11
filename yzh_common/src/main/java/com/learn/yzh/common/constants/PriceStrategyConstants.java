package com.learn.yzh.common.constants;

/**
 * 价格策略常量
 * ClassName:PriceStrategyConstants
 *
 * @author yangbin
 * @create 2017-12-07 14:37
 */
public class PriceStrategyConstants {
	/** 1全部影院 */
	public static final int CINEMA_WAY_ALL = 1;
	/** 2指定影院 */
	public static final int CINEMA_WAY_SOME = 2;

	/** 1按影厅制式（类型）选择 */
	public static final int HALL_WAY_TYPE = 1;
	/** 2按影厅名称选择 */
	public static final int HALL_WAY_NAME = 2;

	/** 1影院 */
	public static final int RELATE_TYPE_CINEMA = 1;
	/** 2影厅类型 */
	public static final int RELATE_TYPE_HALLTYPE = 2;
	/** 3影厅code */
	public static final int RELATE_TYPE_HALLNAME = 3;

	/** 1正常 */
	public static final int STATUS_NORMAL = 1;
	/** 2关闭 */
	public static final int STATUS_SHUTDOWN = 2;
	/** 3过期 */
	public static final int STATUS_EXPIRED = 3;
	/** 1可以开启 */
	public static final int CAN_OPEN = 1;

	/** 1选中（一周中的一天是否被选中选中） */
	public static final int WEEKDAY_YES = 1;
	/** 0未选中（一周中的一天是否被选中选中） */
	public static final int WEEKDAY_NO = 0;

	/** 1影院补贴 */
	public static final int CINEMA_SUBSIDY_YES = 1;
	/** 2非影院补贴 */
	public static final int CINEMA_SUBSIDY_NO = 2;

	/** 1全是特殊厅（自定义服务费） */
	public static final int ALL_SPECIAL_HALL_YES = 1;
	/** 2不全是特殊厅 */
	public static final int ALL_SPECIAL_HALL_NO = 2;

	/** 自选时间 */
	public static final int GOLD_TIME_FREE = 1;
	/** 黄金时间 周一至周五 17:00-21:59 的场次，周六日全天场次可用（包含法定节假日全天场次） */
	public static final int GOLD_TIME_YES = 2;
	/** 非黄金时间 周一至周五 00:00-16:59，22:00-23:59 的场次可用（不包含法定节假日） */
	public static final int GOLD_TIME_NO = 3;

	/** 黄金时间段开始17:00 */
	public static final int GOLD_TIME_RANGE_START = 1700;
	/** 黄金时间段结束21:59 */
	public static final int GOLD_TIME_RANGE_END = 2159;

	/** 按固定价 自有电商售价=固定价+服务费（活动场次除外） */
	public static final int PRICE_TYPE_FIXED = 1;
	/** 按挂牌价折扣（折） 自有电商售价=挂牌价*折扣+服务费（活动场次除外） */
	public static final int PRICE_TYPE_STAND_PRICE_DISCOUNT = 2;
	/** 按挂牌价立减（元） 自有电商售价=挂牌价-立减+服务费（活动场次除外） */
	public static final int PRICE_TYPE_STAND_PRICE_SUB = 3;
	/** 按最低限价加价（元） 自有电商售价=最低限价+加价+服务费（活动场次除外） */
	public static final int PRICE_TYPE_LOWEST_PRICE_ADD = 4;
}
