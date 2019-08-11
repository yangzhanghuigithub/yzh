package com.learn.yzh.common.utils;

/**
 * 常量工具类
 * ClassName:ConstantsUtils
 *
 * @author bilaoye
 * @create 2017-11-15 15:48
 */
public class ConstantsUtils {



    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	/* ============================================================== */
	/* 注释: 以下为固定的缓存主键  start*/
	/* ============================================================== */
    /** 影片列表缓存Key */

    /** 数据字典缓存Key */
    public static final String Cache_Dict_Key = PlatformProperties.getProperty("cache.dict.key");

    public static final String Cache_Films_Key = PlatformProperties.getProperty("cache.films.key");

    public static final String Cache_Films_NO_Key = PlatformProperties.getProperty("cache.filmsno.key");

    /***影片语言**/
    public static final String Dict_Film_Lang = PlatformProperties.getProperty("cache.dict.film.lang");

    /**影片制式*/
    public static final String Dict_Film_Sight = PlatformProperties.getProperty("cache.dict.film.sight");

    /**影院*/
    public static final String Cache_Cinema_Key = PlatformProperties.getProperty("cache.cinemas.key");

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	/* ============================================================== */
	/* 注释: 固定的缓存主键 end */
	/* ============================================================== */
}