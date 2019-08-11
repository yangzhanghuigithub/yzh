package com.learn.yzh.common.constants;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * 和Map相关的常量
 * ClassName:MapConstants
 *
 * @author licho
 * @create 2017-12-04 11:51
 */
public class MapConstants {
    private static  final Logger log= LoggerFactory.getLogger(MapConstants.class);
    private static  final Map<String,String> orderType=new HashMap<>();//订单类型字典
    private static final Map<Integer,String> refundChannel=new HashMap<>();//退款渠道字典
    private static final Map<Integer,String> payWay=new HashMap<>();//支付方式
    private static final Map<Integer,String> orderChannel=new HashMap<>();//订单渠道
    private static final Map<Integer,String> orderDetailDiscountType=new HashMap<>();//变价类型
    private static final Map<Integer,String> eventStatus=new HashMap<>();//活动状态
    private static final Map<Integer,String> voucherType=new HashMap<>();//订单明细支持的所有券类型
    static {
        initOrderType();//初始化订单类型常量
        initRefundChannel();//初始化退款渠道字典常量
        initPayWay();//初始化支付方式\
        initOrderChannel();//支付渠道
        initEventStatus();//活动状态
        initVoucherType();
    }
    public static String getEventStatus(Integer status){
        return eventStatus.get(status);
    }
    private static void initEventStatus(){
        eventStatus.put(0,"发布");
        eventStatus.put(1,"进行中");
        eventStatus.put(2,"停止");
        eventStatus.put(3,"过期");
        eventStatus.put(4,"结束");
    }
    public static void initVoucherType(){
        voucherType.put(0,"影票代金券");
        voucherType.put(1,"折扣券");
        voucherType.put(2,"固定金额通兑券");
        voucherType.put(3,"其它");
        voucherType.put(4,"免费兑换券");
        voucherType.put(5,"卖品券");
        voucherType.put(6,"暂未使用");
        voucherType.put(7,"商家券");
    }

    /**
     * 根据券类型获取其券类型名
     * @param voucherType
     * @return
     */
    public static String getVoucherTypeName(Integer voucherType){
        return MapConstants.voucherType.get(voucherType);
    }
    /**
     * 根据orderType 获取其文字描述
     * @param key
     * @return
     */
    public static String getOrderType(String key){
        return orderType.get(key);
    }

    /**
     * 根据退款渠道获取其文字描述
     * @param key
     * @return
     */
    public static String getRefundChannel(Integer key){
        return refundChannel.get(key);
    }
    public static String getPayPay(Integer key){
        return payWay.get(key);
    }

    public static  String getOrderChannel(Integer key){
        return orderChannel.get(key);
    }

    private static void initOrderDetailDiscountType(){
        orderDetailDiscountType.put(0,"价格策略");
        orderDetailDiscountType.put(1,"票务营销");
        orderDetailDiscountType.put(2,"会员卡权益");
        orderDetailDiscountType.put(3,"优惠券");
    }
    private static void initRefundChannel(){
        refundChannel.put(1,"APP申请退款");
        refundChannel.put(2,"订单失败自动退款");
        refundChannel.put(3,"B端退款");
        refundChannel.put(4,"h5/微站申请退款");
        refundChannel.put(5,"小程序退款");
    }
    private static void initOrderType(){
        orderType.put("1000","已创建");
        orderType.put("1001","已完成");
        orderType.put("1002","订单失败");
        orderType.put("1003","订单取消");
        orderType.put("1004","订单出票中");
        orderType.put("1302","退款成功");
        orderType.put("1304","退款申请中");
        orderType.put("1305","退款失败");
        orderType.put("1401","配送中");
        orderType.put("1402","已配送");
        orderType.put("1501","订单成功退款成功");
        orderType.put("1502","订单失败退款成功");
        orderType.put("1503","订单成功退款中");
        orderType.put("1504","订单失败退款中");
    }
    private static void initPayWay(){
        payWay.put(1,"支付宝");
        payWay.put(2,"微信");
        payWay.put(3,"储值卡");
        payWay.put(4,"渠道卡");
        payWay.put(5,"零元兑换券");//代金券和0元兑换券都有可能导致0元支付，所以，这时不显示支付方式
    }
    private static void initOrderChannel(){
        orderChannel.put(0,"官方APP");
        orderChannel.put(1,"微站");
       // orderChannel.put(3,"轻量化");
        orderChannel.put(3,"官方APP");
        orderChannel.put(4,"pc端购票");
        orderChannel.put(5,"小程序");
    }
}
