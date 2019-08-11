package com.learn.yzh.common.utils;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import com.learn.yzh.common.constants.ResultConstants;

import java.util.Date;
import java.util.Map;

/**
 * ClassName:MemberTagUtils
 *
 * @author bilaoye
 * @create 2017-12-26 13:50
 */
public class MemberTagUtils {

    public static  String getSqlByTagCondition(Map<String,String> map){
        StringBuffer sbsql= new StringBuffer();
        if(map!=null){
          String membertag=  map.get("tag");
            switch (membertag){
                case ResultConstants.ACTOR_TAG:
                    sbsql.append("SELECT  DISTINCT(member_id) as member_code FROM  t_orders  t1 INNER JOIN t_film_info  tm ON t1.film_custom_code=tm.film_key");
                    sbsql.append("  where t1.order_status = '1001' AND t1.cinema_code in("+map.get("cinemaCodes")+") and  tm.film_leading LIKE  '%"+map.get("tagValue").split("_")[1]+"%' AND t1.create_time > '" + map.get("startTime") + "' AND t1.create_time < '" + map.get("endTime") + "'");
                  break;
                case ResultConstants.SEX_TAG:
                    String sex = "";
                    if("1".equals(map.get("tagValue").split("_")[0])){
                        sex = "and (member_sex = 1 or member_sex = '男')";
                    }else if("0".equals(map.get("tagValue").split("_")[0])){
                        sex="";
                    }else{
                        sex = "and (member_sex = 2 or member_sex = '女')";
                    }
                    sbsql.append("SELECT DISTINCT(member_code)  FROM t_loyalty_member where member_owner in("+map.get("cinemaCodes")+") "+sex);
                  break;
                case ResultConstants.AGE_TAG:
                    Map birthdayMap=getBirthday(map.get("tagValue"));
                    if("2000-01-01".equals(birthdayMap.get("startDate"))){
                        sbsql.append("SELECT DISTINCT(member_code)  FROM t_loyalty_member where  member_owner in("+map.get("cinemaCodes")+") and  member_birthday >='"+birthdayMap.get("startDate")+"'");
                    }else{
                        sbsql.append("SELECT DISTINCT(member_code)  FROM t_loyalty_member where  member_owner in("+map.get("cinemaCodes")+") and  member_birthday >='"+birthdayMap.get("startDate")+"' and member_birthday <='"+birthdayMap.get("endDate")+"'");
                    }
                    break;
                case ResultConstants.CARD_TAG:
                        sbsql.append("SELECT DISTINCT(t1.member_code) FROM t_card_sale AS t1 LEFT JOIN t_loyalty_member t2 ON t1.member_code = t2.member_code WHERE t1.member_code IS NOT NULL AND t1.bind_status = 2 AND t1.sale_status = 2 AND t1.del_flag = 0 AND t2.member_owner IN (" + map.get("cinemaCodes") + ")");
                    break;
                case ResultConstants.MOVIE_TAG:
                    sbsql.append("SELECT  DISTINCT(member_id) FROM  t_orders  where  cinema_code in("+map.get("cinemaCodes")+") AND order_status = '1001' AND del_flag = 0 and  film_custom_code='"+map.get("tagValue").split("_")[0]+"' AND create_time > '" + map.get("startTime") + "' AND create_time < '" + map.get("endTime") + "'");
                    break;
                case ResultConstants.MOVIE_TYPE_TAG:
                    sbsql.append("SELECT  DISTINCT(t1.member_id)  FROM t_orders t1 INNER JOIN  t_film_info t2 ")
                          .append( "on t1.film_custom_code=t2.film_key where t1.cinema_code in("+map.get("cinemaCodes")+") and  t2.film_type like '%"+map.get("tagValue").split("_")[1]+"%' and t1.order_status=1001 AND t1.create_time > '" + map.get("startTime") + "' AND t1.create_time < ' " + map.get("endTime") + " '");
                  break;
                case ResultConstants.VIEW_TYPE_TAG:
                    String day="";
                    if(StringUtils.equals("2",map.get("tagValue").split("_")[0])){
                        day=" WEEKDAY  (show_start_time)   between 5 and 6";//非工作日
                    }
                    if(StringUtils.equals("1",map.get("tagValue").split("_")[0])){
                        day=" WEEKDAY  (show_start_time)   between 0 and 4";//工作日
                    }
                    sbsql.append("select  DISTINCT(member_id) from t_orders where  order_status=1001 AND type in (1,3) and del_flag=0 and  cinema_code in("+map.get("cinemaCodes")+") and "+day+" AND create_time > '" + map.get("startTime") + "' AND create_time < '" + map.get("endTime") + "' ORDER BY show_start_time DESC");
                  break;
                case ResultConstants.VIEW_TIME_TAG:
                    if(map.get("tagValue") != null && map.get("tagValue").toString().contains("午夜场")){
                        sbsql.append("SELECT  DISTINCT(member_id)  FROM t_orders where  cinema_code in("+map.get("cinemaCodes")+")  and ((DATE_FORMAT( `show_start_time` , '%H' ) >= 0 ")
                                .append("and DATE_FORMAT( `show_start_time` , '%H' ) <= "+map.get("tagValue").split("_")[0].split("-")[1].split(":")[0]+") or DATE_FORMAT( `show_start_time` , '%H' ) = 23) and del_flag=0 and order_status=1001 AND create_time > '" + map.get("startTime") + "' AND create_time < '" + map.get("endTime") + "'");
                    }else{
                        sbsql.append("SELECT  DISTINCT(member_id)  FROM t_orders where  cinema_code in("+map.get("cinemaCodes")+")  and DATE_FORMAT( `show_start_time` , '%H' ) >= "+map.get("tagValue").split("_")[0].split("-")[0].split(":")[0]+" ")
                                .append("and DATE_FORMAT( `show_start_time` , '%H' ) <= "+map.get("tagValue").split("_")[0].split("-")[1].split(":")[0]+" and del_flag=0 and order_status=1001 AND create_time > '" + map.get("startTime") + "' AND create_time < '" + map.get("endTime") + "'");
                    }
                  break;
                case ResultConstants.SELL_TYPE_TAG:
                    sbsql.append("SELECT DISTINCT(member_id) FROM t_orders t INNER JOIN t_order_detail  t1 ON t.order_code=t1.order_code where t.order_status=1001 AND TYPE IN( 2,3,4) and t.cinema_code IN("+map.get("cinemaCodes")+")");
                    sbsql.append("and t1.sell_id='"+map.get("tagValue").split("_")[0]+"' and t.del_flag=0 AND t.create_time > '" + map.get("startTime") + "' AND t.create_time < '" + map.get("endTime") + "'");
                  break;
                case ResultConstants.PAY_TYPE_TAG:
                    sbsql.append("SELECT  DISTINCT(member_id) FROM t_orders where order_status = '1001' AND cinema_code in("+map.get("cinemaCodes")+") and pay_way="+map.get("tagValue").split("_")[0] + " AND create_time > '" + map.get("startTime") + "' AND create_time < '" + map.get("endTime") + "'");
                  break;
                case ResultConstants.DIRECTOR_TAG:
                    sbsql.append("SELECT  DISTINCT(member_id) as member_code FROM  t_orders  t1 INNER JOIN t_film_info  tm ON t1.film_custom_code=tm.film_key ");
                    sbsql.append("where t1.order_status = '1001' AND t1.cinema_code in("+map.get("cinemaCodes")+") and  tm.film_regisseur = '"+map.get("tagValue").split("_")[1]+"' AND t1.create_time > '" + map.get("startTime") + "' AND t1.create_time < '" + map.get("endTime") + "'");
                    break;
                case ResultConstants.SCREENWRITER_TAG:
                    sbsql.append("SELECT  DISTINCT(member_id) as member_code FROM  t_orders  t1 INNER JOIN t_film_info  tm ON t1.film_custom_code=tm.film_key ");
                    sbsql.append("where t1.order_status = '1001' AND t1.cinema_code in("+map.get("cinemaCodes")+") and  tm.film_writer like '%"+map.get("tagValue").split("_")[1]+"%' AND t1.create_time > '" + map.get("startTime") + "' AND t1.create_time < '" + map.get("endTime") + "'");
                    break;
                case ResultConstants.SELLSINGLENUM_TAG:
                    sbsql.append("SELECT DISTINCT(t1.member_id) FROM  t_orders t1  " )
                            .append(  "INNER JOIN  (SELECT  order_code FROM t_order_detail where  detail_type=2 and  cinema_code  in("+map.get("cinemaCodes")+") " );
                    if(map.get("tagValue") != null && map.get("tagValue").contains("10元以内")){
                        sbsql.append(  "GROUP BY order_code  HAVING MAX(pay_amount) > 0 AND MAX(pay_amount) < 1000)");
                    }else if(map.get("tagValue") != null && map.get("tagValue").contains("15元以上")){
                        sbsql.append(  "GROUP BY order_code  HAVING SUM(pay_amount) > 1500)");
                    }else{
                        sbsql.append(  "GROUP BY order_code  HAVING SUM(pay_amount) >= 1000 AND SUM(pay_amount) <= 1500)");
                    }
                    sbsql.append( "t2 ON t1.order_code=t2.order_code where t1.order_status=1001 AND t1.create_time > '" + map.get("startTime") + "' AND t1.create_time < '" + map.get("endTime") + "'");
                    break;
                case ResultConstants.SELL_NUM_TAG:
                    if(map.get("tagValue") != null && "0".equals(map.get("tagValue").split("_")[0])){
                        sbsql.append("SELECT DISTINCT(member_id) FROM t_orders where type in(2,3,4) and order_status=1001 and cinema_code in("+map.get("cinemaCodes")+") AND create_time > '" + map.get("startTime") + "' AND create_time < '" + map.get("endTime") + "'");
                    }else if (map.get("tagValue") != null && map.get("tagValue").contains("5")){
                        sbsql.append("SELECT DISTINCT(member_id) FROM t_orders where type in(2,3,4) and order_status=1001 and cinema_code in("+map.get("cinemaCodes")+") AND create_time > '" + map.get("startTime") + "' AND create_time < '" + map.get("endTime") + "' GROUP BY member_id HAVING COUNT(member_id)>="+map.get("tagValue").split("_")[0]+"");
                    }else{
                        sbsql.append("SELECT DISTINCT(member_id) FROM t_orders where type in(2,3,4) and order_status=1001 and cinema_code in("+map.get("cinemaCodes")+") AND create_time > '" + map.get("startTime") + "' AND create_time < '" + map.get("endTime") + "' GROUP BY member_id HAVING COUNT(member_id)="+map.get("tagValue").split("_")[0]+"");
                    }
                 break;
                case ResultConstants.CARD_TYPE_TAG:
                    sbsql.append("SELECT DISTINCT(member_code) FROM t_card_sale where buy_cinema_code in("+map.get("cinemaCodes")+") and   card_id='"+map.get("tagValue").split("_")[0]+"' and del_flag=0 AND sale_status = 2 AND status = 1 AND bind_status = 2 and member_code is NOT null AND bind_time > '" + map.get("startTime") + "' AND bind_time < '" + map.get("endTime") + "'");
                    break;
                case ResultConstants.VIEW_NUM_TAG:
                    if(map.get("tagValue") != null && "0".equals(map.get("tagValue").split("_")[0])){
                        sbsql.append("SELECT DISTINCT(member_id) FROM t_orders where order_status=1001 AND type in (1,3) and cinema_code in(" + map.get("cinemaCodes") + ") AND create_time > '" + map.get("startTime") + "' AND create_time < '" + map.get("endTime") + "'");
                    }else if(map.get("tagValue") != null && map.get("tagValue").contains("5")){
                        sbsql.append("SELECT DISTINCT(member_id) FROM t_orders where order_status=1001 AND type in (1,3) and cinema_code in(" + map.get("cinemaCodes") + ") AND create_time > '" + map.get("startTime") + "' AND create_time < '" + map.get("endTime") + "' GROUP BY member_id HAVING  COUNT(member_id) >= " + map.get("tagValue").split("_")[0]);
                    }else {
                        sbsql.append("SELECT DISTINCT(member_id) FROM t_orders where order_status=1001 AND type in (1,3) and cinema_code in(" + map.get("cinemaCodes") + ") AND create_time > '" + map.get("startTime") + "' AND create_time < '" + map.get("endTime") + "' GROUP BY member_id HAVING  COUNT(member_id) = " + map.get("tagValue").split("_")[0]);
                    }
                    break;
                case ResultConstants.MEMBERACTIVE_TAG:
                    if(map.get("tagValue") != null && map.get("tagValue").contains("30")){
                        sbsql.append("SELECT t1.member_id FROM t_orders AS t1 RIGHT JOIN (SELECT member_id,MAX( create_time ) AS maxTime FROM t_orders WHERE cinema_code IN (" + map.get("cinemaCodes") + ") AND order_status = '1001' GROUP BY member_id) AS t2 ON ( t1.member_id = t2.member_id AND t1.create_time = t2.maxTime ) WHERE DATE_SUB( curdate(), INTERVAL 30 DAY ) <= t1.create_time");
                    }else if (map.get("tagValue") != null && map.get("tagValue").contains("120")) {
                        sbsql.append("SELECT t1.member_id FROM t_orders AS t1 RIGHT JOIN (SELECT member_id,MAX( create_time ) AS maxTime FROM t_orders WHERE cinema_code IN (" + map.get("cinemaCodes") + ") AND order_status = '1001' GROUP BY member_id) AS t2 ON ( t1.member_id = t2.member_id AND t1.create_time = t2.maxTime ) WHERE DATE_SUB( curdate(), INTERVAL 90 DAY ) >= t1.create_time");
                    }else {
                        sbsql.append("SELECT t1.member_id FROM t_orders AS t1 RIGHT JOIN (SELECT member_id,MAX( create_time ) AS maxTime FROM t_orders WHERE cinema_code IN (" + map.get("cinemaCodes") + ") AND order_status = '1001' GROUP BY member_id) AS t2 ON ( t1.member_id = t2.member_id AND t1.create_time = t2.maxTime ) WHERE DATE_SUB( curdate(), INTERVAL 30 DAY ) >= t1.create_time AND DATE_SUB( curdate(), INTERVAL 90 DAY ) <= t1.create_time");
                    }
                    break;

            }
        }
        return  sbsql.toString();
    }
    public static  String getSqlByTagConditionAndTime(Map<String,String> map,String startTime,String endTime){
        StringBuffer sbsql= new StringBuffer();
        if(map!=null){
            String membertag=  map.get("tag");
            switch (membertag){
                case ResultConstants.ACTOR_TAG:
                    sbsql.append("SELECT  DISTINCT(member_id) as member_code FROM  t_orders  t1 INNER JOIN t_film_info  tm ON t1.film_custom_code=tm.film_key");
                    sbsql.append("  where  to_days(t1.create_time) >= to_days('"+startTime+"') and to_days(t1.create_time) <= to_days(DATE_ADD('"+endTime+"',INTERVAL 1 DAY)) and  t1.cinema_code in("+map.get("cinemaCodes")+") and  tm.film_leading LIKE  '%"+map.get("tagValue")+"%'");
                    break;
                case ResultConstants.SEX_TAG:
                    String sex="and   member_sex="+map.get("tagValue").split("_")[0];
                    if("0".equals(map.get("tagValue").split("_")[0])){
                        sex="";
                    }
                    sbsql.append("SELECT member_code   FROM t_loyalty_member where member_owner in("+map.get("cinemaCodes")+") "+sex);
                    break;
                case ResultConstants.AGE_TAG:
                    Map birthdayMap=getBirthday(map.get("tagValue"));
                    sbsql.append("SELECT member_code   FROM t_loyalty_member where  member_owner in("+map.get("cinemaCodes")+") and  member_birthday >='"+birthdayMap.get("startDate")+"' and member_birthday <='"+birthdayMap.get("endDate")+"'");
                    break;
                case ResultConstants.CARD_TAG:
                    sbsql.append("SELECT  DISTINCT(member_code) FROM t_card_sale where buy_cinema_code in("+map.get("cinemaCodes")+") and del_flag=0 and member_code is NOT null");

                    break;
                case ResultConstants.MOVIE_TAG:
                    sbsql.append("SELECT  DISTINCT(member_id) FROM  t_orders  where to_days(create_time) >= to_days('"+startTime+"') and to_days(create_time) <= to_days(DATE_ADD('"+endTime+"',INTERVAL 1 DAY)) and cinema_code in("+map.get("cinemaCodes")+") and  film_custom_code='"+map.get("tagValue").split("_")[0]+"'");
                    break;
                case ResultConstants.MOVIE_TYPE_TAG:
                    sbsql.append("SELECT  DISTINCT(t1.member_id)  FROM t_orders t1 INNER JOIN  t_film_info t2 ")
                            .append( "on t1.film_custom_code=t2.film_key where to_days(t1.create_time) >= to_days('"+startTime+"') and to_days(t1.create_time) <= to_days(DATE_ADD('"+endTime+"',INTERVAL 1 DAY)) and t1.cinema_code in("+map.get("cinemaCodes")+") and  t2.film_type like '%"+map.get("tagValue").split("_")[0]+"%' and t1.order_status=1001");
                    break;
                case ResultConstants.VIEW_TYPE_TAG:
                    String day="";
                    if(StringUtils.equals("1",map.get("tagValue").split("_")[0])){
                        day=" WEEKDAY  (show_start_time)   between 1 and 5";//工作日
                    }
                    if(StringUtils.equals("2",map.get("tagValue").split("_")[0])){
                        day=" WEEKDAY  (show_start_time)   between 6 and 7";//非工作日
                    }
                    sbsql.append("select  DISTINCT(member_id) from t_orders where to_days(create_time) >= to_days('"+startTime+"') and to_days(create_time) <= to_days(DATE_ADD('"+endTime+"',INTERVAL 1 DAY)) and order_status=1001 and del_flag=0 and  cinema_code in("+map.get("cinemaCodes")+") and "+day+"   ORDER BY show_start_time DESC");
                    break;
                case ResultConstants.VIEW_TIME_TAG:
                    sbsql.append("SELECT  DISTINCT(member_id)  FROM t_orders where to_days(create_time) >= to_days('"+startTime+"') and to_days(create_time) <= to_days(DATE_ADD('"+endTime+"',INTERVAL 1 DAY)) and  cinema_code in("+map.get("cinemaCodes")+")  and DATE_FORMAT(show_start_time,'%Y-%m-%d %H:%m:%s')>=CONCAT(DATE_FORMAT(show_start_time,'%Y-%m-%d'),' "+map.get("tagValue").split("_")[0]+"') ")
                            .append("and DATE_FORMAT(show_start_time,'%Y-%m-%d %H:%m:%s')<=CONCAT(DATE_FORMAT(show_start_time,'%Y-%m-%d'),' "+map.get("tagValue1").split("_")[0]+"') and del_flag=0 and order_status=1001");
                    break;
                case ResultConstants.SELL_TYPE_TAG:
                    sbsql.append("SELECT DISTINCT(member_id) FROM t_orders t INNER JOIN t_order_detail  t1 ON t.order_code=t1.order_code where to_days(t.create_time) >= to_days('"+startTime+"') and to_days(t.create_time) <= to_days(DATE_ADD('"+endTime+"',INTERVAL 1 DAY)) and t.order_status=1001 AND TYPE IN( 2,3) and t.cinema_code IN("+map.get("cinemaCodes")+")");
                    sbsql.append("and t1.sell_id='"+map.get("tagValue").split("_")[0]+"' and t.del_flag=0");
                    break;
                case ResultConstants.PAY_TYPE_TAG:
                    sbsql.append("SELECT  DISTINCT(member_id) FROM t_orders where to_days(create_time) >= to_days('"+startTime+"') and to_days(create_time) <= to_days(DATE_ADD('"+endTime+"',INTERVAL 1 DAY)) and pay_way="+map.get("tagValue").split("_")[0]+" and  cinema_code in("+map.get("cinemaCodes")+")");
                    break;
                case ResultConstants.DIRECTOR_TAG:
                    sbsql.append("SELECT  DISTINCT(member_id) as member_code FROM  t_orders  t1 INNER JOIN t_film_info  tm ON t1.film_custom_code=tm.film_key ");
                    sbsql.append("where  to_days(t1.create_time) >= to_days('"+startTime+"') and to_days(t1.create_time) <= to_days(DATE_ADD('"+endTime+"',INTERVAL 1 DAY)) and t1.cinema_code in("+map.get("cinemaCodes")+") and  tm.film_writer like '%"+map.get("tagValue").split("_")[0]+"%'");
                    break;
                case ResultConstants.SCREENWRITER_TAG:
                    sbsql.append("SELECT  DISTINCT(member_id) as member_code FROM  t_orders  t1 INNER JOIN t_film_info  tm ON t1.film_custom_code=tm.film_key ");
                    sbsql.append("where to_days(t1.create_time) >= to_days('"+startTime+"') and to_days(t1.create_time) <= to_days(DATE_ADD('"+endTime+"',INTERVAL 1 DAY)) and t1.cinema_code in("+map.get("cinemaCodes")+") and  tm.film_regisseur like '%"+map.get("tagValue")+"%'");
                    break;
                case ResultConstants.SELLSINGLENUM_TAG:
                    sbsql.append("SELECT  t1.member_id FROM  t_orders t1  " )
                            .append(  "INNER JOIN  (SELECT  order_code FROM t_order_detail where  detail_type=2 and  cinema_code  in("+map.get("cinemaCodes")+") " )
                            .append(  "GROUP BY order_code  HAVING SUM(pay_amount)> "+map.get("tagValue").split("_")[0]+") t2 ON t1.order_code=t2.order_code where to_days(t1.create_time) >= to_days('"+startTime+"') and to_days(t1.create_time) <= to_days(DATE_ADD('"+endTime+"',INTERVAL 1 DAY)) and  t1.order_status=1001");

                    break;
                case ResultConstants.SELL_NUM_TAG:
                    if(Integer.valueOf(map.get("tagValue").split("_")[0]) > 4){
                        sbsql.append("SELECT member_id FROM t_orders where to_days(create_time) >= to_days('"+startTime+"') and to_days(create_time) <= to_days(DATE_ADD('"+endTime+"',INTERVAL 1 DAY)) and type in(2,3) and order_status=1001 and cinema_code in("+map.get("cinemaCodes")+") GROUP BY member_id HAVING COUNT(member_id)>="+map.get("tagValue").split("_")[0]+"");
                    }else{
                        sbsql.append("SELECT member_id FROM t_orders where to_days(create_time) >= to_days('"+startTime+"') and to_days(create_time) <= to_days(DATE_ADD('"+endTime+"',INTERVAL 1 DAY)) and type in(2,3) and order_status=1001 and cinema_code in("+map.get("cinemaCodes")+") GROUP BY member_id HAVING COUNT(member_id)="+map.get("tagValue").split("_")[0]+"");
                    }

                    break;
                case ResultConstants.CARD_TYPE_TAG:
                    sbsql.append("SELECT  member_code FROM t_card_sale where buy_cinema_code in("+map.get("cinemaCodes")+") and   card_id='"+map.get("tagValue").split("_")[0]+"' and del_flag=0 and member_code is NOT null and to_days(create_time) >= to_days('"+startTime+"') and to_days(create_time) <= to_days(DATE_ADD('"+endTime+"',INTERVAL 1 DAY))");
                    break;
                case ResultConstants.VIEW_NUM_TAG:
                    if(Integer.valueOf(map.get("tagValue").split("_")[0]) > 4){
                        sbsql.append("SELECT DISTINCT(member_id) FROM t_orders where  to_days(create_time) >= to_days('"+startTime+"') and to_days(create_time) <= to_days(DATE_ADD('"+endTime+"',INTERVAL 1 DAY)) and   order_status=1001 and cinema_code in("+map.get("cinemaCodes")+") GROUP BY member_id HAVING  COUNT(member_id)>="+map.get("tagValue").split("_")[0]+"");
                    }else{
                        sbsql.append("SELECT DISTINCT(member_id) FROM t_orders where  to_days(create_time) >= to_days('"+startTime+"') and to_days(create_time) <= to_days(DATE_ADD('"+endTime+"',INTERVAL 1 DAY)) and   order_status=1001 and cinema_code in("+map.get("cinemaCodes")+") GROUP BY member_id HAVING  COUNT(member_id)="+map.get("tagValue").split("_")[0]+"");
                    }
                    break;
                case ResultConstants.MEMBERACTIVE_TAG:
                    sbsql.append("SELECT  member_code FROM  t_loyalty_member  where  last_consume_datetime<=CONCAT(DATE_SUB(curdate(),INTERVAL +"+map.get("tagValue").split("_")[0]+" DAY),' 23:59:59')");
                    break;

            }
        }
        return  sbsql.toString();
    }
    private static Map getBirthday(String tagValue) {
        Map map= Maps.newHashMap();
        switch (tagValue.split("_")[0]){
            case "10":
                map.put("startDate","2010-01-01");
                map.put("endDate","2019-12-31");
                break;
            case "00":
                map.put("startDate","2000-01-01");
                map.put("endDate","2009-12-31");
                break;
            case "90":
                map.put("startDate","1990-01-01");
                map.put("endDate","1999-12-31");
                break;
            case "80":
                map.put("startDate","1980-01-01");
                map.put("endDate","1989-12-31");
                break;
            case "70":
                map.put("startDate","1970-01-01");
                map.put("endDate","1979-12-31");
                break;
            case "60":
                map.put("startDate","1960-01-01");
                map.put("endDate","1969-12-31");
                break;
            case "50":
                map.put("startDate","1950-01-01");
                map.put("endDate","1959-12-31");
                break;
        }
        return  map;
    }
}