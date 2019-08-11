package com.learn.yzh.common.constants;

/**
 * ClassName:RedPacketConstants
 *
 * @author yangbin
 * @create 2018-01-14 16:16
 */
public class RedPacketConstants {
	/** 1票务定向红包 */
	public static final int PACKET_TYPE_TICKETDIRECTIONAL = 1;
	/** 2购票赠送红包 */
	public static final int PACKET_TYPE_BUYTICKET = 2;
	/** 3限量领取红包 */
	public static final int PACKET_TYPE_LIMIT = 3;
	/** 4附近商家红包 */
	public static final int PACKET_TYPE_NEARBY = 4;
	/** 5卖品定向红包 */
	public static final int PACKET_TYPE_GOODSDIRECTIONAL = 5;
	/** 6购物赠送红包 */
	public static final int PACKET_TYPE_BUYGOODS = 6;

	/** 关系类型1影院 */
	public static final int PACKET_RELATE_TYPE_CINEMA = 1;
	/** 关系类型2会员组 */
	public static final int PACKET_RELATE_TYPE_MEMBERGROUP = 2;
	/** 关系类型3影片 */
	public static final int PACKET_RELATE_TYPE_FILM = 3;
	/** 关系类型4.卖品 */
	public static final int PACKET_RELATE_TYPE_GOODS = 4;
	/** 关系类型5券 */
	public static final int PACKET_RELATE_TYPE_VOUCHER = 5;

	/** 未开始 */
	public static final int PACKET_STATUS_WAITSTART = 0;
	/** 进行中 */
	public static final int PACKET_STATUS_STARTING = 1;
	/** 已结束 */
	public static final int PACKET_STATUS_END = 2;
	/** 已过期 */
	public static final int PACKET_STATUS_EXPIRED = 3;


	/** 已发放 */
	public static final int PACKET_MEMBER_RELATE_STATUS_GIVE_YES = 1;
	/** 未发放 */
	public static final int PACKET_MEMBER_RELATE_STATUS_GIVE_NO = 2;

	/** 全部会员 */
	public static final int PACKET_MEMBER_GROUP_TYPE_ALL = 1;
	/** 指定会员 */
	public static final int PACKET_MEMBER_GROUP_TYPE_SOME = 2;

	/** 全部卖品 */
	public static final int PACKET_SELL_TYPE_ALL = 1;
	/** 指定卖品 */
	public static final int PACKET_SELL_TYPE_SOME = 2;

	/** 全部影片 */
	public static final int PACKET_FILM_TYPE_ALL = 1;
	/** 指定影片 */
	public static final int PACKET_FILM_TYPE_SOME = 2;
	/** 不限影片 */
	public static final int PACKET_FILM_TYPE_UNLIMIT = 3;

	/** 首页未弹出 */
	public static final int PACKET_MEMBER_RELATE_OPEN_NO = 1;
	/** 首页弹出 */
	public static final int PACKET_MEMBER_RELATE_OPEN_YES = 2;

	/** 全部用户存的groupid */
	public static final String DEFAULT_GROUP_ID = "1";
	/** 全部用户存的groupname */
	public static final String DEFAULT_GROUP_NAME = "全部用户";

	/** 卖品 */
	public static final int LOG_TYPE_GOODS = 1;
	/** 影票 */
	public static final int LOG_TYPE_TICKET = 2;
	/**评价**/
	public static final int LOG_TYPE_CONTENT=3;
}
