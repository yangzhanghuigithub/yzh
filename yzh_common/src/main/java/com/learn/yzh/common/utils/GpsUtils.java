package com.learn.yzh.common.utils;


import java.text.DecimalFormat;


public abstract class GpsUtils
{
//	private static JsonMapper mapper = JsonMapper.nonDefaultMapper();
	public final static double PI = 3.14159265358979323846d;
	private final static double D2R = 0.017453;
	private final static double a2 = 6378137.0;
	private final static double e2 = 0.006739496742337;

	public static Double distance(Double lat1, Double lng1, Double lat2, Double lng2)
	{
		if(lat1==null||lng1==null||lat2==null||lng2==null)
			return null;
		return Double.valueOf(distance(lat1.doubleValue(), lng1.doubleValue(), lat2.doubleValue(), lng2.doubleValue()));
	}
	/**
	 * 
	 * @param lat1 纬度
	 * @param lng1 经度
	 * @param lat2
	 * @param lng2
	 * @return
	 */
	public static Double distance(double lat1, double lng1, double lat2, double lng2) {
	 
		if (lng1 == lng2 && lat1 == lat2)
		{
			return 0.0;
		}
		else
		{
			lat1 = lat1 * D2R;
			lat2 = lat2 * D2R;
			lng1 = lng1 * D2R;
			lng2 = lng2 * D2R;

			double d_lng = (lng1 - lng2);
			double d_lat = (lat1 - lat2);

			double COS_LAT = Math.cos(lat1);
			double COS_LAT_USER = Math.cos(lat2);
			//			double SIN_D_LNG = Math.sin(d_lng);
			double SIN_D_LNG_2 = Math.sin(d_lng / 2d);
			double SIN_D_LAT_2 = Math.sin(d_lat / 2d);
			double _SIN_M_LAT_2_e2 = 1 - Math.sin((lat1 + lat2) / 2d) * Math.sin((lat1 + lat2) / 2d) * e2;

			double fRho = (a2 * (1 - e2)) / Math.pow(_SIN_M_LAT_2_e2, 1.5);
			double fNu = a2 / (Math.sqrt(_SIN_M_LAT_2_e2));
			double fz = 2 * Math.asin(Math.sqrt(SIN_D_LAT_2 * SIN_D_LAT_2 + COS_LAT_USER * COS_LAT * SIN_D_LNG_2 * SIN_D_LNG_2));

			double x = Math.cos(lat1) * Math.sin((lng1 - lng2)) / Math.sin(2 * Math.asin(Math.sqrt(Math.sin(d_lat / 2d) * Math.sin(d_lat / 2d) + Math.cos(lat2) * Math.cos(lat1) * Math.pow(Math.sin(d_lng / 2d), 2))));

			double fAlpha = Math.asin(x);

			double fR = (fRho * fNu) / ((fRho * Math.pow(Math.sin(fAlpha), 2)) + (fNu * Math.pow(Math.cos(fAlpha), 2)));

			return fz * fR;
		}
	}
	public static void main(String[] args) {
//		lon：39.955044    lat：116.502655
		DecimalFormat  df   = new DecimalFormat("######0.0");   
		System.out.println(df.format(distance(116.319546,39.987411,39.955044,116.502655)));
	}
}
