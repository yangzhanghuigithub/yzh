package com.learn.yzh.common.utils;

import java.util.List;
import java.util.Map;

/**
 * ClassName:InitCinemaNameList
 *
 * @author bilaoye
 * @create 2018-01-16 16:42
 */
public class InitCinemaNameList {


    public  static  String initCinemaList(List<String> cinemaCodes, Map cinemaMap){
        StringBuffer sb=new StringBuffer();
        int valid=0;
        for(int i=0;i<cinemaCodes.size(); i++){
            if(valid>3){
                break;//影城名称暂时只四个
            }
            if(cinemaMap.get(cinemaCodes.get(i))!=null){
                valid++;
                sb.append("<p>").append(cinemaMap.get(cinemaCodes.get(i))).append("</p>");
            }
        }
        if(cinemaCodes.size()>4){
            sb.append("<p>").append("...").append("</p>");
        }
        return  sb.toString();
    }
}