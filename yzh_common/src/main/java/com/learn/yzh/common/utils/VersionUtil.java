package com.learn.yzh.common.utils;

/**
 * 判断版本号
 * ClassName:VersionUtil
 *
 * @author yw
 * @create 2017-11-23 16:07
 */
public class VersionUtil {
    /**
     *  比较版本号的大小
     * @param version1 版本1
     * @param version2 版本12
     * @return ,前者大则返回一个正数,后者大返回一个负数,相等则返回0
     */
    public static int compareVersion(String version1, String version2) {
        if (version1.equals(version2)) {
            return 0;
        }
        String[] version1Array = version1.split("\\.");//以.分隔需要转义
        String[] version2Array = version2.split("\\.");
        int index = 0;
        //获取最小长度值
        int minLen = Math.min(version1Array.length, version2Array.length);
        int diff = 0;
        //循环判断每位的大小
        while (index < minLen && (diff = Integer.parseInt(version1Array[index]) - Integer.parseInt(version2Array[index])) == 0) {
            index ++;
        }
        if (diff == 0) {//位数相同时，比较大小
            //如果位数不一致，比较多余位数
            for (int i = index; i < version1Array.length; i ++) {
                if (Integer.parseInt(version1Array[i]) > 0) {
                    return 1;
                }
            }
            for (int i = index; i < version2Array.length; i ++) {
                if (Integer.parseInt(version2Array[i]) > 0) {
                    return -1;
                }
            }
            return 0;
        } else { //位数不同时，直接输出
            return diff > 0 ? 1 : -1;
        }
    }
}
