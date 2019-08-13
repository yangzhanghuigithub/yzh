package com.learn.yzh.common;

import com.learn.yzh.common.utils.PropertiesUtils;

/**
 * 常量集
 * @author yuwei
 *
 */
public class UrlConstants {
	
	/** 默认皮肤 */
	public static final String TemplateCSS="/resources/css";
	/** 动态皮肤 */
	public static final String TemplateCSS1="/resources/css1";
	
	public static final String skin1="/resources/css1/blue.css";
	//轻量化基础ip
	public static final String light_base_url=PropertiesUtils.getProperties("system.properties").getProperty("light_base_url");
	/** 默认域名地址 */
	public static final String Default_Domain = PropertiesUtils.getProperties("system.properties").getProperty("mplus_server_url");

	public static final String URL=PropertiesUtils.getProperties("system.properties").getProperty("URL");
	
	/** 获取缓存地址 */
	public static final String Sys_base_cache = PropertiesUtils.getProperties("system.properties").getProperty("sys_base_cache");

	public static final String imgUrl = PropertiesUtils.getProperties("system.properties").getProperty("imgUrl");
	/** 院线图片地址(logo图片、启动图片、app顶部图片) */
	public static final String COMPANY_IMAGE_URL = PropertiesUtils.getProperties("system.properties").getProperty("company_image_url");
	/** 阿里云图片地址 */
	public static final String ALI_IMAGE_URL = PropertiesUtils.getProperties("system.properties").getProperty("ali_image_url");
	/** 积分商城扫码key */
	public static final String INTEGRAL_STORE_KEY = PropertiesUtils.getProperties("system.properties").getProperty("integral_store_key");

	/** 查询院线覆盖城市列表 */
	public static final String APP_MOVIE_CITIES = "/app/movie/cities";
	/** 查询所有影院列表 */
	public static final String APP_MOVIE_CINEMAS = "/app/movie/cinemas";
	/** 查询所有的影片和场次列表 */
	public static final String APP_FILM_FILMS2 = "/app/film/films2";
	/** 查询所有的影片和场次列表，新版 */
	public static final String APP_FILM_FILMS2_NEW = "/app/film/films2New";
	/** 查询电影列表 */
	public static final String APP_FILM_FILMS = "/app/film/films";
	/** 查询影片详情 */
	public static final String APP_FILM_FILM = "/app/film/film";
	/** 查询广告列表 */
	public static final String APP_OTHER_AD = "/app/other/ad";
	/** 查询活动资讯列表 */
	public static final String APP_ACTIVITY_AC = "/app/ac/ac";
	/** 活动详情(赞)*/
	public static final String APP_ACTIVITY_INSERT = "/app/auc/insert";
	/** 活动详情(取消赞)*/
	public static final String APP_ACTIVITY_DELETE = "/app/auc/delete";
	/**活动详情(查询)*/
	public static final String APP_ACTIVITY_SELECT = "/app/auc/select";
	/** 查询活动详情*/
	public static final String APP_ACTIVITY_DETAIL = "/app/ac/detail";
	/** 活动交互增加阅读数量*/
	public static final String APP_ACTIVITY_UPDATE = "/app/ac/updateLikeAndRead";
	/** 关注/取消关注影片 */
	public static final String APP_FILM_ATTENTION = "/app/film/attention";
	/** 查询某影片场次列表 */
	public static final String APP_FILM_SHOWS = "/app/film/shows";
	/** 查询场次座位信息列表 */
	public static final String APP_FILM_SEATS = "/app/film/seats";
	/** 20190107优化版-查询场次座位信息列表 */
	public static final String APP_FILM_NSEATS = "/app/film/nseats";
	/** 查询影院详情 */
	public static final String APP_MOVIE_CINEMA = "/app/movie/cinema";
	/** 查询附近列表 */
	public static final String APP_NEARBY_NEARS = "/app/nearby/nears";
	/** 查询最新版本号 */
	public static final String APP_VERSION = "/app/version";
	/** 协议接口 */
	public static final String AP_MOVIE_AGREEMENT = "/app/movie/agreement";

	/***影票订单相关接口 START**/
	public static final String CREATE_ORDER_URL = "/app/trade/createOrder";//创建订单接口
	public static final String ADD_GOODS = "/app/trade/addGoods";//订单中添加卖品接口
	public static final String ORDER_REFUND_RULE = "/app/pay/refundRule";//退款规则
	public static final String ORDER_REFUND = "/app/pay/refund";//退款操作
	public static final String ORDER_DELETE = "/app/pay/delete";//订单删除操作

    public static final String ORDER_PREPAID = "/app/trade/prepaid";//卡券预支付

	public static final String CHECKORDERSTATE = "/app/trade/checkOrderState";//检查订单了支付状态

	public static final String ORDER_CANCEL= "/app/trade/cancelOrder";//取消订单

	public static final String CARD_BUY  = "/app/card/order";//创建购卡订单
	public static final String ORDER_STATUS_URL = "/app/trade/getOrderStatus";//订单状态（影票和卖品共用）

	/***影票订单相关接口 END**/


	/***卖品订单相关接口 START **/
	public static final String GOODS = "/app/goods/goods";//卖品列表接口
	public static final String GOODS_LIST = "/app/goods/sellProduct/querySellProductListByPresetCodeOrCinemaCode";//卖品列表接口
	public static final String GOODS_TYPE= "/app/goods/sellPresetType/querySellPresetTypeListByCinemaCode";//卖品种类列表接口
	public static final String SELLGOODS_CREATE_ORDER= "/app/goods/sellOrder/createOrder";//创建卖品订单
	public static final String SELLGOODS_PREPAID= "/app/goods/sellOrder/prepaid";//卖品订单选卡或券
	public static final String SELLGOODS_ORDERBINGDING ="/app/goods/sellOrder/orderConfirm";//卖品确认订单
	public static final String SELLGOODS_FINDTROLLEY ="/app/goods/trolley/findTrolley";//获取购物车列表
	public static final String SELLGOODS_SAVETROLLEY="/app/goods/trolleyDetail/saveTrolleyDetail";//添加购物车
	public static final String SELLGOODS_ORDERDETAIL = "/app/goods/sellOrder/getSellOrder";//查看商品订单详情
	public static final String SELLGOODS_CLEANTROLLEY= "/app/goods/trolley/deleteTrolleyById";//清空购物车
	public static final String SELLGOODS_CANCELORDER = "/app/goods/sellOrder/removeOrder";//取消订单
	public static final String SELLGOODS_SELLORDERLIST = "/app/goods/sellOrder/sellOrderList";//卖品订单列表
	public static final String CARD_CUSUMEINFO= "/app/wallet/cardCusumeInfo/queryCardConsumeListByMemberCodeAndCinemaCode";//卡消费列表
	public static final String CARD_ABOUT  = "/app/wallet/cardOrder/queryBuyCardListAndCardRechargeListByMemberCodeAndCinemaCode";//查询购卡和卡充值列表

	public static final String ZERO_PAY  = "/app/trade/payExchange";//支付参数接口

	public static final String INIT_PAY  = "/app/pay/initPay";//支付参数接口
	public static final String PAY_STATUS  = "/app/pay/getCinemaPay";//获取支付信息
	public static final String PAY_STATUS2  = "/app/pay/getCinemaPay2";//获取支付信息

	public static final String UPD_BalancePwd   = "/app/member/updatBalancePwd";//支付参数接口
	public static final String ORDER_ADDREMARKS = "/app/goods/sellOrder/addRemarks";//配送订单添加备注
	public static final String HALL_GETHALLSEATINFO = "/hall/getHallTSeatInfo";//点餐返回影厅信息

	public static final String CARDRECOMMEND4ORDER   = "/app/card/cardRecommend4Order";//确认订单卡推荐
	/***卖品订单相关接口 END **/

	public static final String ORDER = "/app/trade/orderDetail";//查询订单接口
	public static final String ORDEREVALUAT = "/app/member/memberBasics/orderEvaluat";//查询订单评价接口
	public static final String ADDEVALUAT = "/app/member/memberBasics/addEvaluat";//添加评价接口
	public static final String PAY = "/app/trade/pay";//支付接口
	public static final String ORDER_BINDING = "/app/trade/orderConfirm";//确认订单绑定卡或券接口
	public static final String PREPAIDCANCEL = "/app/trade/prepaidCancel";//影票取消卡券预支付接口
	public static final String PAY_NOTIFY  ="/app/trade/pay"; //支付回调接口
	public static final String NEARS  ="/app/nearby/nears"; //附近的
	public static final String ACTIVITY  ="/app/event/list"; //活动
	public static final String FOOTMOVIEDETAIL = "/app/member/films";//影迹接口
	public static final String CODE_PAY = "/app/card/cardsByMember";//线下会员卡
	public static final String DYNAMICENCRYPTCARDID = "/app/admin/order/pos/dynamiCencryptCardId";//会员卡卡号加密
	public static final String CHECKWALLETPSW = "/app/wallet/checkWalletPsw";//验证钱包密码
	public static final String CHECKPOSCARDPSW = "/app/wallet/checkPosCardPsw";//验证pos卡支付密码
	public static final String SELL_PRODUCT_DETAIL = "/app/goods/sellProduct/querySellProductById";//通过id获取商品信息
	public static final String CARD_DETAIL = "/app/card/getCardByCode";//通过卡的id获取卡详情
	
	public static final String COUPON_INFO_LIST = "/app/member/selectByMemberPhone";//券列表
	public static final String CARD_INFO_LIST = "/app/card/cards";//影院卡列表
	public static final String CARD_RECHARGE = "/app/card/rechargeOrder";//创建卡充值订单
	//public static final String CARD_RECHARGEBYMEMBERLIST = "/app/card/rechargeRecord";//充值记录
	
	
	public static final String CARD_RECHARGEBYMEMBERLIST="/app/wallet/cardOrder/queryBuyCardListAndCardRechargeListByMemberCodeAndCinemaCode";//个人购卡充值订单
	public static final String CONSUME_RECHARGEBYMEMBERLIST="/app/wallet/cardCusumeInfo/queryCardConsumeListByMemberCodeAndCinemaCode";//个人消费记录
	
	
	
	
	
	public static final String EVENT_GETEVENTLIST = "/app/event/list";//活动列表
	public static final String EVENT_GETEVENTINFO = "/app/event/event";//活动详情
	
	public static final String MEMBER_BINDCARD = "/app/card/bind";//会员绑定卡
	public static final String MEMBER_MODIFYPOSCARPWD = "/app/card/modifyPosCarPwd";//会员卡pos卡修改密码
	public static final String MEMBER_BINDVOUCHER = "/app/voucher/bind";//会员绑定券
	public static final String MEMER_CARD_PSW= "/app/member/wallet";//查询个人卡密码
	public static final String MEMER_LEVEL= "/app/member/getPersonLevel";//查询个人会员等级
	public static final String ALL_PREROGATIVE= "/app/member/getMemberLevelList";//查询个人会员等级

	
	/** 会员相关接口 */
	public static final String MEMBER_GETVERIFYCODE = "/app/member/verifyCode";//会员发送短信获取验证码
	public static final String GETVERIFYCODE = "/app/member/walletVerifyCode";//获取验证码
	public static final String GETVERIFYCODEBYPHONE = "/app/member/walletVerifyCodeByPhone";//获取验证码(判断手机号是否为当前用户绑定的号码)
	public static final String SELECTOLDMEMBER = "/app/member/selectOldMember";//老用户首次进入“我的”时alert提示（提示语“手机号、支付密码搬到“账号安全”了)

	public static final String MEMBER_SETREGISTER = "/app/member/register";//会员注册
	public static final String MEMBER_LOGIN = "/app/member/login";//会员登录
	public static final String MEMBEL_WX_LOGIN="/app/member/wxCheckLogin";//微信登录
	public static final String MEMBEL_WX_BINGD="/app/member/wxBindingPhone";//微信手机号绑定
	public static final String MEMBEL_PHONE_BINGD="/app/member/updateMemberPhone";//手机号绑定

	public static final String MEMBER_VERIFYCODELOGIN = "/app/member/verifyCodeLogin";//验证码登录
	public static final String MEMBER_MY_FILMS = "/app/member/films";//个人影迹
	public static final String MEMBER_UPDATE_PASSWORD = "/app/member/password";//修改密码
	public static final String MEMBER_UPDATE_INFO = "/app/member/member";//修改个人信息
	public static final String MEMBER_MESSAGE = "/app/member/selectByMemberPhone";//个人推送消息列表
	public static final String MEMBER_MESSAGE_PAGE = "/app/member/getMemberMsgPage";//个人推送消息列表-分页
	public static final String MEMBER_BALANCEPWD="/app/member/updatBalancePwd";//更新卡支付或初始化卡密码
	
	public static final String MEMBER_GETORDERSlIST = "/app/trade/orders";//个人订单列表



	public static final String MEMBER_GETCARDS_COUNT = "/app/card/getCardsCntMemberCode";//卡数量

	public static final String MEMBER_GETCARDSLIST = "/app/card/cardsByMember";//个人已购卡列表
	public static final String MEMBER_GETINTEGRAL= "/app/integralRecard/integralRecardsByMember";//个人积分列表
	public static final String MEMBER_GETTOTALINTEGRAL= "/app/member/wallet";//用于获取总积分
	public static final String VOUCHER_GETVOUCHERLIST= "/app/voucher/vouchersByMember";//个人券列表
	public static final String VOUCHER_CLEAN= "/app/voucher/cleanSubscript";//券角标删除

	public static final String MEMBER_IMAGE_UPLOAD = "/app/image/upload";//上传头像
	public static final String MEMBER_UPLOADPICTURES= "/app/member/memberBasics/uploadPictures";//图片上传
	public static final String MEMBER_Feedback = "/app/member/memberBasics/memberfeedback";//会员意见反馈
	public static final String MEMBER_FeedbackLIST = "/app/member/memberBasics/getFeedbackinfo";//会员意见反馈列表
	public static final String MEMBER_FeedbackLISTOP = "/app/member/memberBasics/getFeedbackinfoOp";//会员意见反馈列表根据订单查询

	public static final String MEMBER_CAREFILMS = "/app/member/careFilms";//个人关注列表
	
	/** 积分商城 begin */
	public static final String INTEGRAL_PRODUCTS = "/app/integralRecard/productList";//卖品列表
	public static final String INTEGRAL_ORDERS = "/app/integralRecard/orderList";//订单列表
	public static final String INTEGRAL_PRODUCT = "/app/integralRecard/productDetail";//卖品详细信息
	public static final String INTEGRAL_CREATERECORD = "/app/integralRecard/createRecord";//创建积分订单
	public static final String INTEGRAL_EXCHANGE = "/app/integralRecard/exchange";//创建积分订单
	/** 积分商城 end */
	public static final String NEAR_DETAIL = "/app/nearby/nearsDetail";//附近详情
	public static final String NEAR_BIND = "/app/nearby/bindVoucher";//绑定异业券
	public static final String NEAR_VOUCHER_DETAIL = "/app/nearby/getVoucherDetail";//券详情
	public static final String NEAR_VOUCHER_CHARGEOFF = "/app/nearby/chargeOff";//券核销
	/** 异业券 begin */
	
	/** 异业券 end */


	/** 红包 begin */
	public static final String REDPACKET_UNOPENRED = "/app/redpacket/getunopenredpacket";//获取用户的定向红包
	public static final String REDPACKET_GIVELIMITRED = "/app/redpacket/givelimitredpacket";//抢红包接口
	public static final String REDPACKET_GETLIMITRED = "/app/redpacket/getlimitredpacket";//获取可抢红包
	/** 红包 end */
	/** 影核地球最后的夜晚活动 begin*/
	public static final String MEMBER_BINDVOUCHERYINGHE = "/app/voucher/bindYingHe";//会员绑定影核发放的券
	/** 影核地球最后的夜晚活动 end*/
	public static final String TREELISTS = "/tree/home2";//会员绑定影核发放的券
}
