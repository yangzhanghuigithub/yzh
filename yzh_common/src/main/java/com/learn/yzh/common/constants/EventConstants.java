package com.learn.yzh.common.constants;

import org.omg.PortableInterceptor.INACTIVE;

/**
 * ClassName:EventConstants
 *
 * @author bilaoye
 * @create 2017-12-25 16:15
 */
public class EventConstants {
    
    public static  final  String update1st="1";
    public static  final  String update2nd="2";
    public static  final  String update4rd="4";
    public static  final  String del_yes="1";
    public static  final String del_No="0";
    public static  final  String hallName="2";
    public static  final  String hallCode="3";
    public static  final String choosehallType="2";
    public static  final  String choosehallCode="3";
    public static final String sightkey="sightkey_";
    public static  final String fix_Price="0";
    public static  final String lowwer_Price="1";
    public  static  final String  e_price="2";
    //全部会员
    public static  final  String group_All="0";
    //指定会员
    public static  final  String group_single="1";

    public static  final  String event_member="1";
    //共享补贴
    public static final  String shareSubsidy="4";
    //分影城补贴
    public static  final String partSubsidy="5";
    //推影补贴
    public static final String pushSubsidy="6";
    //不限影片
    public static  final String film_no_limit="0";
    //全部影片
    public static  final  String  film_all="1";
    //指定影片
    public  static final  String film_single="2";
//活动状态'0,发布，1进行中，2停止，3过期，4，结束'
    //活动发布状态
    public static final Integer EVENT_PUBLISH=0;//发布未到开始状态
    public static final Integer EVENT_RUNNING=1;
    public  static final Integer EVENT_STOP=2;//手动停止状态
    public static final Integer EVENT_OUTDATE=3;//活动已经过期
    public static final Integer EVENT_END=4;//不在活动时间段
    //分院补贴，影院补贴预警线，当剩余补贴金额少于这个金额时就不显示场次活动了。
    public static final Integer WARNING_AMOUNT=1000;//分

    //活动状态转换类型
    //发布->运行
    public static final Integer PUBLISH_TO_RUNNING=0;
    //运行->结束
    public static final Integer RUNNING_TO_END=1;

    //根据影厅制式来筛选
    public static final String HALL_MATCH_BY_SIGHT="2";
    //根据影厅名称
    public static final String HALL_MATCH_BY_NAME="3";
    //活动关系表的关联类型枚举
    //影厅制式
    public static final String EVENT_RELATE_TYPE_HALL_SIGHT="2";
    //影厅Code
    public static final String EVENT_RELATE_TYPE_HALL_CODE="3";
    //共享补贴类型
    public static final String EVENT_RELATE_TYPE_SHARE_SUBSIDY="4";
    //分院补贴类型
    public static final String EVENT_RELATE_TYPE_PART_SUBSIDY="5";

    //规则类型
    //统一定价
    public  static final String EVENT_PRICE_RULE_ALL="1";
    //按照影片制式来定价
    public static final String EVENT_PRICE_RULE_SIGHT="2";


    //分影院补贴标志
    public static final Integer EVENT_PART_SUBSIDY_CINEMA_NORMAL=0;
    public static final Integer EVENT_PART_SUBSIDY_CINEMA_STOP=1;


    //回滚补贴类型
    public static final Integer ROLLBACK_CANCEL_ORDER_SUBSIDY=0;//取消订单回滚补贴

    public static final Integer ROLLBACK_REFUND_ORDER_SUBSIDY=1;//订单退款返还补贴

    public static final Integer ROLLBACK_SUCCESS_ORDER_SUBSIDY=2;//订单成功返还多余补贴
}