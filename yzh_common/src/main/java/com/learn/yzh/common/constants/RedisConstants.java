package com.learn.yzh.common.constants;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * redis的key
 * ClassName:RedisConstants
 *
 * @author yw
 * @create 2017-11-16 11:30
 */
@Component
//@PropertySource("classpath:/redis-properties")
@ConfigurationProperties(prefix = "key")
public class RedisConstants {
    private String hall;//影厅key

    private String seat;//座位

    private String filmlang;
    private String filmsight;
    private String showinfo;
    private String show;//场次 hash存放
    private String filmsinfo;
    private String filmsnotshow;
    private String orderGoods;
    private String orderCard;
    private String orderVoucher;
    private String seperator;

    private String newsInfoList;//消息中心缓存key
    private String sections;

    private String dict;

    private String hasRefundOrder;//退款订单缓存

    private String showeventprice; // 场次的价格策略和活动
    private String showpricestategykeyset; // 场次的价格策略价key集合，用于删除
    private String showeventpriceStrategyId; // 场次的价格策略和活动_策略id
    private String showeventpriceStrategyRuleId; // 场次的价格策略和活动_策略规则id
    private String showeventpriceStrategyUpdateTime; // 场次的价格策略和活动_策略最后更新时间
    private String showeventpriceStrategyPriority; // 场次的价格策略和活动_策略优先级
    private String showeventpriceStrategyPrice; // 场次的价格策略和活动_策略价格(元)
    private String showeventpriceStrategyServicefee; // 场次的价格策略和活动_策略服务费
    private String showeventpriceStrategySubsidy; // 场次的价格策略和活动_策略补贴
    private String eventExceptionInfoLog;//活动异常日志bug。
    private String showeventpricekeyset;//场次的活动价格key 集合
    private String  cacheeventpriceId;// 活动id
    private String  cacheeventpriceRuleId;//活动规则
    private String  cacheeventpriceUpdateTime;//最后更新时间
    private String  cacheeventprice;//活动价
    private String  cacheeventServicefee;//活动服务费
    private String  cacheeventSubsidy;//活动补贴
    private String  cacheeventmember;//活动关联会员
    private String evenhistoryid;//场次活动历史Id
    private String cachememberid;//会员
    private String partSubsidy;//分影院补贴金额
    private String shareSubsidy;//共享补贴金额
    private String eventLimitNum;
    private String eventposprice;
    private String eventallmember;//是否是全部会员
    private String eventName;//活动名称
    private String eventTime;//活动时间
    private String memberCare;
    private String eventCreateTime;//活动创建时间
    private String eventShowMatchList;//场次匹配的活动列表
    private String eventList;//缓存中的活动列表
    private String eventSubsidyDecrease;//活动补贴扣减
    private String eventSubsidyIncrease;//活动补贴增加.
    private String memberCareGiveLog;//会员关怀分配日志。
    private String nearbyList;//附近商家列表缓存。
    public String getEventExceptionLog;
    public String indexFilmData;//首页影片场次信息，全部缓存

    public String getMemberCareGiveLog() {
        return memberCareGiveLog;
    }

    public void setMemberCareGiveLog(String memberCareGiveLog) {
        this.memberCareGiveLog = memberCareGiveLog;
    }

    public String getGetEventExceptionLog() {
        return getEventExceptionLog;
    }

    public void setGetEventExceptionLog(String getEventExceptionLog) {
        this.getEventExceptionLog = getEventExceptionLog;
    }


    public String getEventSubsidyDecrease() {
        return eventSubsidyDecrease;
    }

    public void setEventSubsidyDecrease(String eventSubsidyDecrease) {
        this.eventSubsidyDecrease = eventSubsidyDecrease;
    }

    public String getEventSubsidyIncrease() {
        return eventSubsidyIncrease;
    }

    public void setEventSubsidyIncrease(String eventSubsidyIncrease) {
        this.eventSubsidyIncrease = eventSubsidyIncrease;
    }

    public String getEventExceptionInfoLog() {
        return eventExceptionInfoLog;
    }

    public void setEventExceptionInfoLog(String eventExceptionInfoLog) {
        this.eventExceptionInfoLog = eventExceptionInfoLog;
    }

    public String getEventShowMatchList() {
        return eventShowMatchList;
    }

    public String getEventList() {
        return eventList;
    }

    public void setEventList(String eventList) {
        this.eventList = eventList;
    }

    public void setEventShowMatchList(String eventShowMatchList) {
        this.eventShowMatchList = eventShowMatchList;
    }

    public String getEventCreateTime() {
        return eventCreateTime;
    }

    public void setEventCreateTime(String eventCreateTime) {
        this.eventCreateTime = eventCreateTime;
    }

    public String getEventTime() {
        return eventTime;
    }

    public void setEventTime(String eventTime) {
        this.eventTime = eventTime;
    }

    private String filmskeyinfo;//影片信息

    private String cardordercardno; // 购卡锁定卡号临时key

    private String nearbyBindStoreVoucher;//附件商家领券绑定的券号


    public String getMemberIntegralExpire() {
        return memberIntegralExpire;
    }

    public void setMemberIntegralExpire(String memberIntegralExpire) {
        this.memberIntegralExpire = memberIntegralExpire;
    }

    private String memberIntegralExpire;//會員積分過期提醒



    public String getNearbyBindStoreVoucher() {
        return nearbyBindStoreVoucher;
    }

    public void setNearbyBindStoreVoucher(String nearbyBindStoreVoucher) {
        this.nearbyBindStoreVoucher = nearbyBindStoreVoucher;
    }

    public String getFilmskeyinfo() {
        return filmskeyinfo;
    }

    public void setFilmskeyinfo(String filmskeyinfo) {
        this.filmskeyinfo = filmskeyinfo;
    }

    public String  getMemberCare() {
        return memberCare;
    }

    public void setMemberCare(String memberCare) {
        this.memberCare = memberCare;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    private String sellEventMember;

    private String sellEventCinema;

    private String sellEventInfo;

    private String sellEventLimitNum;

    public String getEventallmember() {
        return eventallmember;
    }

    public void setEventallmember(String eventallmember) {
        this.eventallmember = eventallmember;
    }

    public String getShoweventpricekeyset() {
        return showeventpricekeyset;
    }
    public String getShoweventpricekeyset(String eventId) {
        return this.getShoweventpricekeyset()+ this.getSeperator() + eventId;
    }
    public void setShoweventpricekeyset(String showeventpricekeyset) {
        this.showeventpricekeyset = showeventpricekeyset;
    }

    public String getEventLimitNum() {
        return eventLimitNum;
    }

    public void setEventLimitNum(String eventLimitNum) {
        this.eventLimitNum = eventLimitNum;
    }

    public String getEventposprice() {
        return eventposprice;
    }

    public void setEventposprice(String eventposprice) {
        this.eventposprice = eventposprice;
    }

    public String getPartSubsidy() {
        return partSubsidy;
    }

    public void setPartSubsidy(String partSubsidy) {
        this.partSubsidy = partSubsidy;
    }

    public String getShareSubsidy() {
        return shareSubsidy;
    }

    public void setShareSubsidy(String shareSubsidy) {
        this.shareSubsidy = shareSubsidy;
    }

    public String getCachememberid() {
        return cachememberid;
    }

    public void setCachememberid(String cachememberid) {
        this.cachememberid = cachememberid;
    }

    public String getHall() {
        return hall;
    }

    public void setHall(String hall) {
        this.hall = hall;
    }

    public String getSeat() {
        return seat;
    }

    public void setSeat(String seat) {
        this.seat = seat;
    }

    public String getFilmlang() {
        return filmlang;
    }

    public void setFilmlang(String filmlang) {
        this.filmlang = filmlang;
    }

    public String getFilmsight() {
        return filmsight;
    }

    public void setFilmsight(String filmsight) {
        this.filmsight = filmsight;
    }

    public String getShowinfo() {
        return showinfo;
    }

    public String getShowinfoKey(String cinemaCode) {
        return this.getShowinfo() + this.getSeperator() + cinemaCode;
    }

    public void setShowinfo(String showinfo) {
        this.showinfo = showinfo;
    }

    public String getShow() {
        return show;
    }

    public String getShowKey(String cinemaCode) {
        return this.getShow() + this.getSeperator() + cinemaCode;
    }

    public void setShow(String show) {
        this.show = show;
    }

    public String getFilmsinfo() {
        return filmsinfo;
    }

    public void setFilmsinfo(String filmsinfo) {
        this.filmsinfo = filmsinfo;
    }

    public String getFilmsnotshow() {
        return filmsnotshow;
    }

    public void setFilmsnotshow(String filmsnotshow) {
        this.filmsnotshow = filmsnotshow;
    }

    public String getDict() {
        return dict;
    }

    public void setDict(String dict) {
        this.dict = dict;
    }

    public String getSections() {
        return sections;
    }

    public void setSections(String sections) {
        this.sections = sections;
    }

    public String getOrderGoods() {
        return orderGoods;
    }

    public void setOrderGoods(String orderGoods) {
        this.orderGoods = orderGoods;
    }

    public String getOrderCard() {
        return orderCard;
    }

    public void setOrderCard(String orderCard) {
        this.orderCard = orderCard;
    }

    public String getOrderVoucher() {
        return orderVoucher;
    }

    public void setOrderVoucher(String orderVoucher) {
        this.orderVoucher = orderVoucher;
    }

    public String getSeperator() {
        return seperator;
    }

    public void setSeperator(String seperator) {
        this.seperator = seperator;
    }

    public String getShoweventprice() {
        return showeventprice;
    }

    public String getShoweventpriceKey(String cinemaCode, String showCode) {
        return this.getShoweventprice() + this.getSeperator() + cinemaCode + this.getSeperator() + showCode;
    }

    public void setShoweventprice(String showeventprice) {
        this.showeventprice = showeventprice;
    }

    public String getShoweventpriceStrategyId() {
        return showeventpriceStrategyId;
    }

    public void setShoweventpriceStrategyId(String showeventpriceStrategyId) {
        this.showeventpriceStrategyId = showeventpriceStrategyId;
    }

    public String getShoweventpriceStrategyRuleId() {
        return showeventpriceStrategyRuleId;
    }

    public void setShoweventpriceStrategyRuleId(String showeventpriceStrategyRuleId) {
        this.showeventpriceStrategyRuleId = showeventpriceStrategyRuleId;
    }

    public String getShoweventpriceStrategyUpdateTime() {
        return showeventpriceStrategyUpdateTime;
    }

    public void setShoweventpriceStrategyUpdateTime(String showeventpriceStrategyUpdateTime) {
        this.showeventpriceStrategyUpdateTime = showeventpriceStrategyUpdateTime;
    }

    public String getShoweventpriceStrategyPriority() {
        return showeventpriceStrategyPriority;
    }

    public void setShoweventpriceStrategyPriority(String showeventpriceStrategyPriority) {
        this.showeventpriceStrategyPriority = showeventpriceStrategyPriority;
    }

    public String getShoweventpriceStrategyPrice() {
        return showeventpriceStrategyPrice;
    }

    public void setShoweventpriceStrategyPrice(String showeventpriceStrategyPrice) {
        this.showeventpriceStrategyPrice = showeventpriceStrategyPrice;
    }

    public String getShoweventpriceStrategyServicefee() {
        return showeventpriceStrategyServicefee;
    }

    public void setShoweventpriceStrategyServicefee(String showeventpriceStrategyServicefee) {
        this.showeventpriceStrategyServicefee = showeventpriceStrategyServicefee;
    }

    public String getShowpricestategykeyset() {
        return showpricestategykeyset;
    }

    public String getShowpricestategykeyset(String stategyCode) {
        return this.getShowpricestategykeyset() + this.getSeperator() + stategyCode;
    }

    public void setShowpricestategykeyset(String showpricestategykeyset) {
        this.showpricestategykeyset = showpricestategykeyset;
    }

    public String getShoweventpriceStrategySubsidy() {
        return showeventpriceStrategySubsidy;
    }

    public void setShoweventpriceStrategySubsidy(String showeventpriceStrategySubsidy) {
        this.showeventpriceStrategySubsidy = showeventpriceStrategySubsidy;
    }

    public String getCacheeventpriceId() {
        return cacheeventpriceId;
    }

    public void setCacheeventpriceId(String cacheeventpriceId) {
        this.cacheeventpriceId = cacheeventpriceId;
    }

    public String getCacheeventpriceRuleId() {
        return cacheeventpriceRuleId;
    }

    public void setCacheeventpriceRuleId(String cacheeventpriceRuleId) {
        this.cacheeventpriceRuleId = cacheeventpriceRuleId;
    }

    public String getCacheeventpriceUpdateTime() {
        return cacheeventpriceUpdateTime;
    }

    public void setCacheeventpriceUpdateTime(String cacheeventpriceUpdateTime) {
        this.cacheeventpriceUpdateTime = cacheeventpriceUpdateTime;
    }

    public String getCacheeventprice() {
        return cacheeventprice;
    }

    public void setCacheeventprice(String cacheeventprice) {
        this.cacheeventprice = cacheeventprice;
    }

    public String getCacheeventServicefee() {
        return cacheeventServicefee;
    }

    public void setCacheeventServicefee(String cacheeventServicefee) {
        this.cacheeventServicefee = cacheeventServicefee;
    }

    public String getCacheeventSubsidy() {
        return cacheeventSubsidy;
    }

    public void setCacheeventSubsidy(String cacheeventSubsidy) {
        this.cacheeventSubsidy = cacheeventSubsidy;
    }

    public String getCacheeventmember() {
        return cacheeventmember;
    }

    public void setCacheeventmember(String cacheeventmember) {
        this.cacheeventmember = cacheeventmember;
    }

    public String getSellEventMember() {
        return sellEventMember;
    }

    public void setSellEventMember(String sellEventMember) {
        this.sellEventMember = sellEventMember;
    }

    public String getSellEventCinema() {
        return sellEventCinema;
    }

    public void setSellEventCinema(String sellEventCinema) {
        this.sellEventCinema = sellEventCinema;
    }

    public String getSellEventInfo() {
        return sellEventInfo;
    }

    public void setSellEventInfo(String sellEventInfo) {
        this.sellEventInfo = sellEventInfo;
    }

    public String getSellEventLimitNum() {
        return sellEventLimitNum;
    }

    public void setSellEventLimitNum(String sellEventLimitNum) {
        this.sellEventLimitNum = sellEventLimitNum;
    }

    public String getCardordercardno() {
        return cardordercardno;
    }

    public void setCardordercardno(String cardordercardno) {
        this.cardordercardno = cardordercardno;
    }

    public String getNewsInfoList() {
        return newsInfoList;
    }

    public void setNewsInfoList(String newsInfoList) {
        this.newsInfoList = newsInfoList;
    }

    public String getEvenhistoryid() {
        return evenhistoryid;
    }

    public void setEvenhistoryid(String evenhistoryid) {
        this.evenhistoryid = evenhistoryid;
    }

    public String getHasRefundOrder() {
        return hasRefundOrder;
    }

    public void setHasRefundOrder(String hasRefundOrder) {
        this.hasRefundOrder = hasRefundOrder;
    }

    public String getNearbyList() {
        return nearbyList;
    }

    public void setNearbyList(String nearbyList) {
        this.nearbyList = nearbyList;
    }

    public String getIndexFilmData() {
        return indexFilmData;
    }

    public void setIndexFilmData(String indexFilmData) {
        this.indexFilmData = indexFilmData;
    }

}
