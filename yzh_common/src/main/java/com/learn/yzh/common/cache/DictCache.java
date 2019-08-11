package com.learn.yzh.common.cache;

import com.fasterxml.jackson.databind.JavaType;
import com.learn.yzh.common.constants.RedisConstants;
import com.learn.yzh.common.mapper.JsonMapper;
import com.learn.yzh.common.model.Dict.Dict;
import com.learn.yzh.common.redis.IRedis;
import com.learn.yzh.common.utils.SpringContextUtils;
import com.learn.yzh.common.utils.StringUtils;

import java.util.List;

/**
 * 字典缓存类
 * @author Lomis
 *
 */
@SuppressWarnings("unchecked")
public class DictCache {

	private IRedis redis;
	private static DictCache _instance;
	private RedisConstants redisConstants;
	public static DictCache getInstance () {
		if (_instance == null) {
			_instance = new DictCache();
		}
		return _instance;
	}
	/**
	 * 获取字典的label
	 * @param dictValue
	 * @param dictType
	 * @param defaultValue
	 * @return
	 */
	public String getDictLabel(String dictValue, String dictType, String defaultValue){
		if (StringUtils.isNotBlank(dictType) && StringUtils.isNotBlank(dictValue)){
			String dictJson = getRedis().hget(getCacheName(),dictType,String.class);
			if (StringUtils.isNotBlank(dictJson)) {
				JavaType javaType = JsonMapper.getInstance().createCollectionType(List.class, Dict.class);;
				List<Dict> dicts = JsonMapper.getInstance().fromJson(dictJson, javaType);
				for (Dict dict : dicts) {
					if (dictValue.equals(dict.getDictLabel())) {
						return dict.getDictLabel();
					}
				}
			}
		}
		return defaultValue;
	}

	/**
	 * 获取字典的Value
	 * @param dictLabel
	 * @param dictType
	 * @param defaultLabel
	 * @return
	 */
	public  String getDictValue(String dictLabel, String dictType, String defaultLabel){

		if (StringUtils.isNotBlank(dictType) && StringUtils.isNotBlank(dictLabel)){
			String dictJson = getRedis().hget(getCacheName(),dictType,String.class);
			if (StringUtils.isNotBlank(dictJson)) {
				JavaType javaType = JsonMapper.getInstance().createCollectionType(List.class, Dict.class);
				List<Dict> dicts = JsonMapper.getInstance().fromJson(dictJson, javaType);
				for (Dict dict : dicts) {
					if (dictLabel.equals(dict.getDictValue())) {
						return dict.getDictValue();
					}
				}
			}
		}
		return defaultLabel;
	}
	
	/**
	 * 获取字典列表
	 * @param dictType
	 * @return
	 */
	public List<Dict> getDictList(String dictType){
		String dictJson = getRedis().hget(getCacheName(),dictType,String.class);
		if (StringUtils.isNotBlank(dictJson)) {
			JavaType javaType = JsonMapper.getInstance().createCollectionType(List.class, Dict.class);;
			List<Dict> dicts = JsonMapper.getInstance().fromJson(dictJson, javaType);
			return dicts;
		}
		return null;
	}

	private IRedis getRedis(){
		if (redis == null){
			redis = SpringContextUtils.getBean(IRedis.class);
		}
		return redis;
	}

	private String getCacheName() {
		if(redisConstants==null){
			redisConstants= SpringContextUtils.getBean(RedisConstants.class);
		}
		return redisConstants.getDict();
	}
}
