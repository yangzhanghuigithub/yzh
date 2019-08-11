package com.learn.yzh.common.utils;


/**
 * 已知经纬度获得距离的工具类
 * @author Lomis
 *
 */
public class Gps2m {
	private final static double EARTH_RADIUS = 6378137.0; 
	
	private static double rad(double d) {
		return d * Math.PI / 180.0;
	}
	
	/**
	 * 计算2个坐标点的距离
	 * @param lat1 纬度1
	 * @param lng1 经度1
	 * @param lat2 纬度2
	 * @param lng2 经度2
	 * @return
	 */
	public static double getDistance(double lat1, double lng1, double lat2, double lng2) {
		double radLat1 = rad(lat1);
		double radLat2 = rad(lat2);
		double a = radLat1 - radLat2;
		double b = rad(lng1) - rad(lng2);
		double s = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(a / 2), 2) + Math.cos(radLat1) * Math.cos(radLat2) * Math.pow(Math.sin(b / 2), 2)));
		s = s * EARTH_RADIUS;
		s = Math.round(s * 10000) / 10000;
		return s;
	}
}
